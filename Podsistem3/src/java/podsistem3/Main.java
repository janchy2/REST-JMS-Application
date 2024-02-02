/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entiteti.Paket;
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
import pomocneklase.PretplataP;
import entiteti.Pretplata;
import pomocneklase.GledanjeP;
import entiteti.Gledanje;
import entiteti.Ocena;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import pomocneklase.ListaGledanja;
import pomocneklase.ListaOcena;
import pomocneklase.ListaPaketa;
import pomocneklase.ListaPretplata;
import pomocneklase.OcenaP;
import pomocneklase.PaketP;

/**
 *
 * @author Jana
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connFactory;
    
    @Resource(lookup="CSQueue")
    private static Queue sendQueue;
    
    @Resource(lookup="p3Queue")
    private static Queue receiveQueue;
    
    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
       JMSContext context = connFactory.createContext();
       JMSConsumer consumer = context.createConsumer(receiveQueue);
       JMSProducer producer = context.createProducer();
       
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
       EntityManager em = emf.createEntityManager();
        
        while(true) {
           try {
               ObjectMessage msg = (ObjectMessage)consumer.receive();
               int tip = msg.getIntProperty("tip");
               ObjectMessage objMsg = null;
               int rezultat;
               switch(tip) {
                   case 9:
                       kreirajPaket(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(0));
                       break;
                   case 10:
                       int IDPak = msg.getIntProperty("IDPak");
                       rezultat = promeniCenu(msg.getObject(), em, IDPak);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 11:
                       rezultat = kreirajPretplatu(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 12:
                       kreirajGledanje(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(0));
                       break;
                   case 13:
                       kreirajOcenu(msg.getObject(), em);
                       objMsg = context.createObjectMessage(new Integer(0));
                       break;
                   case 14:
                       int IDVid = msg.getIntProperty("IDVid");
                       int IDKor = msg.getIntProperty("IDKor");
                       rezultat = promeniOcenu(msg.getObject(), em, IDKor, IDVid);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 15:
                       IDKor = msg.getIntProperty("IDKor");
                       rezultat = obrisiOcenu(msg.getObject(), em, IDKor);
                       objMsg = context.createObjectMessage(new Integer(rezultat));
                       break;
                   case 22:
                       ListaPaketa paketi = dohvatiPakete(em);
                       objMsg = context.createObjectMessage(paketi);
                       break;
                   case 23:
                       ListaPretplata pretplate = dohvatiPretplate(msg.getObject(), em);
                       objMsg = context.createObjectMessage(pretplate);
                       break;
                   case 24:
                       ListaGledanja gledanja = dohvatiGledanja(msg.getObject(), em);
                       objMsg = context.createObjectMessage(gledanja);
                       break;
                   case 25:
                       ListaOcena ocene = dohvatiOcene(msg.getObject(), em);
                       objMsg = context.createObjectMessage(ocene);
                       break;
                   case 26: //trazi podsistem 2 kada brise video
                       obrisiGledanjaIOcene(msg.getObject(), em);
                       break;
               }
               if(tip != 26) producer.send(sendQueue, objMsg);
           } catch (JMSException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
   
   private static void kreirajPaket(Object o, EntityManager em) {
       Integer i = (Integer)o;
       int cena = i.intValue();
       
       Paket paket = new Paket();
       paket.setCena(cena);
       
       em.getTransaction().begin();
       em.persist(paket);
       em.getTransaction().commit();
    }
   
   private static int promeniCenu(Object o, EntityManager em, int IDPak) {
       Integer i = (Integer)o;
       int cena = i.intValue();
       
       Paket paket = em.find(Paket.class, IDPak);
       if(paket == null) //ne postoji paket sa datim ID
           return -1;
       
       em.getTransaction().begin();
       paket.setCena(cena);
       em.getTransaction().commit();
       
       return 0;
    }
   
   private static int kreirajPretplatu(Object o, EntityManager em) {
       PretplataP p = (PretplataP)o;
       
       Paket paket = em.find(Paket.class, p.getIDPak());
       if(paket == null) //ne postoji paket sa datim ID
           return -1;
       
       TypedQuery<Pretplata> query = em.createQuery( "SELECT p FROM Pretplata p WHERE p.iDKor = " + p.getIDKor(), Pretplata.class);
       List<Pretplata> lista = query.getResultList();
       if(lista.size() != 0) {
           Pretplata stara = lista.get(0);
           Date datum = stara.getDatumVreme();
           long razlika = Math.abs(p.getDatumVreme().getTime() - datum.getTime());
           if(razlika / (1000 * 60 * 60 * 24) < 31)
               return -2; //prethodna pretplata nije istekla
       }
       Pretplata pretplata = new Pretplata(p.getIDKor(), p.getDatumVreme(), paket.getCena());
       pretplata.setIDPak(paket);
       
       em.getTransaction().begin();
       em.persist(pretplata);
       em.getTransaction().commit();
       
       return 0;
   }
    
   private static void kreirajGledanje(Object o, EntityManager em) {
       GledanjeP g = (GledanjeP)o;
       
       Gledanje gledanje = new Gledanje(g.getIDKor(), g.getIDVid(), g.getDatumVreme(), g.getSekund(), g.getBrSekundi());
       
       em.getTransaction().begin();
       em.persist(gledanje);
       em.getTransaction().commit();
   }
   
   private static void kreirajOcenu(Object o, EntityManager em) {
       OcenaP oc = (OcenaP)o;
       
       Ocena ocena = new Ocena(oc.getIDKor(), oc.getIDVid(), oc.getVrednost(), oc.getDatumVreme());
       
       em.getTransaction().begin();
       em.persist(ocena);
       em.getTransaction().commit();
   }
   
   private static int promeniOcenu(Object o, EntityManager em, int IDKor, int IDVid) {
       Integer i = (Integer)o;
       int vrednost = i.intValue();
       System.out.println(vrednost);
       
       TypedQuery<Ocena> query = em.createQuery( "SELECT o FROM Ocena o WHERE o.iDVid = " + IDVid, Ocena.class);
       List<Ocena> ocene = query.getResultList();
       Ocena ocena = null;
       for(Ocena oc: ocene) {
           if(oc.getIDKor() == IDKor) {
               ocena = oc;
               break;
           }
       }
       if(ocena == null) //ne postoji ocena za dati video i korisnika
           return -1;
       
       em.getTransaction().begin();
       ocena.setVrednost(vrednost);
       em.getTransaction().commit();
       
       return 0;
    }
   
   private static int obrisiOcenu(Object o, EntityManager em, int IDKor) {
       Integer i = (Integer)o;
       int IDVid = i.intValue();
       
       TypedQuery<Ocena> query = em.createQuery( "SELECT o FROM Ocena o WHERE o.iDVid = " + IDVid, Ocena.class);
       List<Ocena> ocene = query.getResultList();
       Ocena ocena = null;
       for(Ocena oc: ocene) {
           if(oc.getIDKor() == IDKor) {
               ocena = oc;
               break;
           }
       }
       if(ocena == null) //ne postoji ocena za dati video i korisnika
           return -1;
       
       em.getTransaction().begin();
       em.remove(ocena);
       em.getTransaction().commit();
       
       return 0;
   }
   
   private static ListaPaketa dohvatiPakete(EntityManager em) {
       TypedQuery<Paket> query = em.createQuery("SELECT p FROM Paket p", Paket.class);
       List<Paket> lista = query.getResultList();
       
       List<PaketP> paketi = new ArrayList<>();
       for(Paket element: lista) {
           PaketP pak = new PaketP(element.getIDPak(), element.getCena());
           paketi.add(pak);
       }
       
       return new ListaPaketa(paketi);
   }
   
   private static ListaPretplata dohvatiPretplate(Object o, EntityManager em) {
       Integer i = (Integer)o;
       int IDKor = i.intValue();
       
       TypedQuery<Pretplata> query = em.createQuery("SELECT p FROM Pretplata p WHERE p.iDKor = " + IDKor, Pretplata.class);
       List<Pretplata> lista = query.getResultList();
       
       List<PretplataP> pretplate = new ArrayList<>();
       for(Pretplata element: lista) {
           PretplataP pret = new PretplataP(element.getIdPre(), element.getIDKor(), element.getIDPak().getIDPak(), element.getDatumVreme(), element.getCena());
           pretplate.add(pret);
       }
       
       return new ListaPretplata(pretplate);
   }
   
   private static ListaGledanja dohvatiGledanja(Object o, EntityManager em) {
       Integer i = (Integer)o;
       int IDVid = i.intValue();
       
       TypedQuery<Gledanje> query = em.createQuery("SELECT g FROM Gledanje g WHERE g.iDVid = " + IDVid, Gledanje.class);
       List<Gledanje> lista = query.getResultList();
       
       List<GledanjeP> gledanja = new ArrayList<>();
       for(Gledanje element: lista) {
           GledanjeP gled = new GledanjeP(element.getIDGle(), element.getIDKor(), element.getIDVid(), element.getDatumVreme(), element.getSekund(), element.getBrSekundi());
           gledanja.add(gled);
       }
       
       return new ListaGledanja(gledanja);
   }
   
   private static ListaOcena dohvatiOcene(Object o, EntityManager em) {
       Integer i = (Integer)o;
       int IDVid = i.intValue();
       
       TypedQuery<Ocena> query = em.createQuery("SELECT o FROM Ocena o WHERE o.iDVid = " + IDVid, Ocena.class);
       List<Ocena> lista = query.getResultList();
       
       List<OcenaP> ocene = new ArrayList<>();
       for(Ocena element: lista) {
           OcenaP oc = new OcenaP(element.getIDOcena(), element.getIDKor(), element.getIDVid(), element.getVrednost(), element.getDatumVreme());
           ocene.add(oc);
       }
       
       return new ListaOcena(ocene);
   }
   
   private static void obrisiGledanjaIOcene(Object o, EntityManager em) {
       Integer i = (Integer)o;
       int IDVid = i.intValue();
       
       em.getTransaction().begin();
       em.createQuery("DELETE FROM Gledanje g WHERE g.iDVid = " + IDVid, Gledanje.class).executeUpdate();
       em.createQuery("DELETE FROM Ocena o WHERE o.iDVid = " + IDVid, Ocena.class).executeUpdate();
       em.getTransaction().commit();
   }
}
