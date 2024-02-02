/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

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
import javax.jms.TextMessage;
import pomocneklase.KorisnikP;
import entiteti.Korisnik;
import entiteti.Mesto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import pomocneklase.ListaKorisnika;
import pomocneklase.ListaMesta;
import pomocneklase.MestoP;

/**
 *
 * @author Jana
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connFactory;
    
    @Resource(lookup="p1Queue")
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
       
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
       EntityManager em = emf.createEntityManager();
        
        while(true) {
           try {
               ObjectMessage msg = (ObjectMessage)consumer.receive();
               int tip = msg.getIntProperty("tip");
               ObjectMessage objMsg = null;
               int rezultat;
               switch(tip) {
                   case 1:
                       kreirajMesto(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(0));
                       break;
                   case 2:
                       rezultat = kreirajKorisnika(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 3:
                       int IDKor = msg.getIntProperty("IDKor");
                       rezultat = promeniEmail(msg.getObject(), em, IDKor);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 4:
                       IDKor = msg.getIntProperty("IDKor");
                       rezultat = promeniMesto(msg.getObject(), em, IDKor);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 17:
                       ListaMesta mesta = dohvatiMesta(em);
                       objMsg = context.createObjectMessage(mesta);
                       break;
                   case 18:
                       ListaKorisnika korisnici = dohvatiKorisnike(em);
                       objMsg = context.createObjectMessage(korisnici);
                       break;
               }
               producer.send(sendQueue, objMsg);
           } catch (JMSException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
    
   private static int kreirajKorisnika(Object o, EntityManager em) {
       KorisnikP k = (KorisnikP)o;
        
       Korisnik korisnik = new Korisnik(k.getIme(), k.getEmail(), k.getGodiste(), k.getPol());
       int IDMes = k.getiDMes();
        
       
        
       Mesto mesto = em.find(Mesto.class, IDMes);
       if(mesto == null)
           return -1; //ne postoji mesto sa datim ID
        
       korisnik.setIDMes(mesto);
        
       em.getTransaction().begin();
       em.persist(korisnik);
       em.getTransaction().commit();
        
       return 0;
   }
   
   private static void kreirajMesto(Object o, EntityManager em) {
       String naziv = (String)o;
       
       Mesto mesto = new Mesto();
       mesto.setNaziv(naziv);
       
       em.getTransaction().begin();
       em.persist(mesto);
       em.getTransaction().commit();
   }
    
   private static int promeniEmail(Object o, EntityManager em, int IDKor) {
       String email = (String)o;
       
       Korisnik korisnik = em.find(Korisnik.class, IDKor);
       if(korisnik == null) //ne postoji korisnik sa datim ID
           return -1;
       
       em.getTransaction().begin();
       korisnik.setEmail(email);
       em.getTransaction().commit();
       
       return 0;
   }
   
   private static int promeniMesto(Object o, EntityManager em, int IDKor) {
       Integer i = (Integer)o;
       int IDMes = i.intValue();
       
       Mesto mesto = em.find(Mesto.class, IDMes);
       if(mesto == null)
           return -1; //ne postoji mesto sa datim ID
       
       Korisnik korisnik = em.find(Korisnik.class, IDKor);
       if(korisnik == null) //ne postoji korisnik sa datim ID
           return -2;
        
       em.getTransaction().begin();
       korisnik.setIDMes(mesto);
       em.getTransaction().commit();
       
       return 0;
   }
   
   private static ListaMesta dohvatiMesta(EntityManager em) {
       TypedQuery<Mesto> query = em.createQuery("SELECT m FROM Mesto m", Mesto.class);
       List<Mesto> lista = query.getResultList();
       
       List<MestoP> mesta = new ArrayList<>();
       
       for(Mesto element: lista) {
           MestoP mesto = new MestoP(element.getIDMes(), element.getNaziv());
           mesta.add(mesto);
       }
       
       return new ListaMesta(mesta);
   }
   
   private static ListaKorisnika dohvatiKorisnike(EntityManager em) {
       TypedQuery<Korisnik> query = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class);
       List<Korisnik> lista = query.getResultList();
       
       List<KorisnikP> korisnici = new ArrayList<>();
       
       for(Korisnik element: lista) {
           int IDMes = element.getIDMes().getIDMes();
           KorisnikP korisnik = new KorisnikP(element.getIDKor(), element.getIme(), element.getEmail(), element.getGodiste(), element.getPol(), IDMes);
           korisnici.add(korisnik);
       }
       
       return new ListaKorisnika(korisnici);
   }
}
