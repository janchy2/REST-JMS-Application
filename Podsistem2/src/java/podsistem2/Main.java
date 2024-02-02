/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Kategorija;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pomocneklase.VideosnimakP;
import entiteti.Videosnimak;
import entiteti.Kategorija;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import pomocneklase.KategorijaP;
import pomocneklase.ListaKategorija;
import pomocneklase.ListaVidea;

/**
 *
 * @author Jana
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connFactory;
    
    @Resource(lookup="p2Queue")
    private static Queue receiveQueue;
    
    @Resource(lookup="CSQueue")
    private static Queue sendQueue;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       JMSContext context = connFactory.createContext();
       JMSConsumer consumer = context.createConsumer(receiveQueue);
       JMSProducer producer = context.createProducer();
       
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
       EntityManager em = emf.createEntityManager();
        
        while(true) {
           try {
               ObjectMessage msg = (ObjectMessage)consumer.receive();
               int tip = msg.getIntProperty("tip");
               ObjectMessage objMsg = null;
               int rezultat;
               switch(tip) {
                   case 5:
                       kreirajKategoriju(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(0));
                       break;
                   case 6:
                       kreirajVideoSnimak(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(0));
                       break;
                   case 7:
                       int IDVid = msg.getIntProperty("IDVid");
                       rezultat = promeniNaziv(msg.getObject(), em, IDVid);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 8:
                       IDVid = msg.getIntProperty("IDVid");
                       rezultat = dodajKategoriju(msg.getObject(), em, IDVid);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 16:
                       IDVid = msg.getIntProperty("IDVid");
                       rezultat = obrisiVideo(msg.getObject(), em, IDVid);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 19:
                       ListaKategorija kategorije = dohvatiKategorije(em);
                       objMsg = context.createObjectMessage(kategorije);
                       break;
                   case 20:
                       ListaVidea snimci = dohvatiVideoSnimke(em);
                       objMsg = context.createObjectMessage(snimci);
                       break;
                   case 21:
                       kategorije = dohvatiKategorijeZaSnimak(msg.getObject(), em);
                       objMsg = context.createObjectMessage(kategorije);
                       break;
               }
               producer.send(sendQueue, objMsg);
           } catch (JMSException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
    
    private static void kreirajKategoriju(Object o, EntityManager em) {
       String naziv = (String)o;
       
       Kategorija kategorija = new Kategorija();
       kategorija.setNaziv(naziv);
       
       em.getTransaction().begin();
       em.persist(kategorija);
       em.getTransaction().commit();
    }
    
    private static void kreirajVideoSnimak(Object o, EntityManager em) {
        VideosnimakP v = (VideosnimakP)o;
        
       Videosnimak video = new Videosnimak(v.getNaziv(), v.getTrajanje(), v.getIDKor(), v.getDatumVreme());
               
       em.getTransaction().begin();
       em.persist(video);
       em.getTransaction().commit();
   }
    
    private static int promeniNaziv(Object o, EntityManager em, int IDVid) {
       String naziv = (String)o;
       
       Videosnimak video = em.find(Videosnimak.class, IDVid);
       if(video == null) //ne postoji video sa datim ID
           return -1;
       
       em.getTransaction().begin();
       video.setNaziv(naziv);
       em.getTransaction().commit();
       
       return 0;
   }
    
    private static int dodajKategoriju(Object o, EntityManager em, int IDVid) {
       Integer i = (Integer)o;
       int IDKat = i.intValue();
       
       Videosnimak video = em.find(Videosnimak.class, IDVid);
       if(video == null) //ne postoji video sa datim ID
           return -1;
       
       Kategorija kategorija = em.find(Kategorija.class, IDKat);
       if(kategorija == null) //ne postoji kategorija sa datim ID
           return -2;
       
       
       em.getTransaction().begin();
       
       List<Videosnimak> lista1 = kategorija.getVideosnimakList();
       if(lista1.contains(video)) //video vec ima datu kategoriju
           return -3;
       lista1.add(video);
       kategorija.setVideosnimakList(lista1);
       
       List<Kategorija> lista2 = video.getKategorijaList();
       lista2.add(kategorija);
       video.setKategorijaList(lista2);
       
       em.getTransaction().commit();
       
       return 0;
   }
    
    private static int obrisiVideo(Object o, EntityManager em, int IDVid) {
        Integer i = (Integer)o;
       int IDKor = i.intValue();
       
       Videosnimak video = em.find(Videosnimak.class, IDVid);
       if(video == null) //ne postoji video sa datim ID
           return -1;
       
       if(video.getIDKor() != IDKor) //korisnik nije vlasnik videa
           return -2;
       
       em.getTransaction().begin();
       em.remove(video);
       TypedQuery<Kategorija> query = em.createQuery("SELECT k FROM Kategorija k", Kategorija.class);
       List<Kategorija> lista = query.getResultList();
       for(Kategorija kat: lista) {
           List<Videosnimak> snimci = kat.getVideosnimakList();
           if(snimci.contains(video)) {
               snimci.remove(video);
               kat.setVideosnimakList(snimci);
           }
       }
       em.getTransaction().commit();
       
       return 0;
    }
    
    private static ListaKategorija dohvatiKategorije(EntityManager em) {
       TypedQuery<Kategorija> query = em.createQuery("SELECT k FROM Kategorija k", Kategorija.class);
       List<Kategorija> lista = query.getResultList();
       
       List<KategorijaP> kategorije = new ArrayList<>();
       for(Kategorija element: lista) {
           KategorijaP kat = new KategorijaP(element.getIDKat(), element.getNaziv());
           kategorije.add(kat);
       }
       
       return new ListaKategorija(kategorije);
    }
    
    private static ListaVidea dohvatiVideoSnimke(EntityManager em) {
       TypedQuery<Videosnimak> query = em.createQuery("SELECT v FROM Videosnimak v", Videosnimak.class);
       List<Videosnimak> lista = query.getResultList();
       
       List<VideosnimakP> snimci = new ArrayList<>();
       for(Videosnimak element: lista) {
           VideosnimakP vid = new VideosnimakP(element.getIDVid(), element.getNaziv(), element.getTrajanje(), element.getIDKor(), element.getDatumVreme());
           snimci.add(vid);
       }
       
       return new ListaVidea(snimci);
    }
    
    private static ListaKategorija dohvatiKategorijeZaSnimak(Object o, EntityManager em) {
       Integer i = (Integer)o;
       int IDVid = i.intValue();
        
       Videosnimak video = em.find(Videosnimak.class, IDVid);
       
       if(video == null)
           return null; //ne postoji video sa datim ID
       
       List<Kategorija> lista = video.getKategorijaList();
       
       List<KategorijaP> kategorije = new ArrayList<>();
       for(Kategorija element: lista) {
           KategorijaP kat = new KategorijaP(element.getIDKat(), element.getNaziv());
           kategorije.add(kat);
       }
       
       return new ListaKategorija(kategorije);
    }
}
