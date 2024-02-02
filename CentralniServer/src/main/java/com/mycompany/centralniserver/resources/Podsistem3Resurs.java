/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.resources;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import pomocneklase.GledanjeP;
import pomocneklase.OcenaP;
import pomocneklase.PretplataP;
import pomocneklase.VideosnimakP;

/**
 *
 * @author Jana
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("podsistem3")
public class Podsistem3Resurs {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connFactory;
    
    @Resource(lookup="CSQueue")
    private Queue receiveQueue;
    
    @Resource(lookup="p3Queue")
    private Queue podsistem3Queue;
    
    @Path("kreirajPaket/{cena}")
    @POST
    public Response kreirajPaket(@PathParam("cena") int cena) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(cena));
        objMsg.setIntProperty("tip", 9);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();

        return Response.status(200).entity("Uspesno izvrseno!").build();
    }
    
    @Path("promeniCenu/{IDPak}/{cena}")
    @POST
    public Response promeniNaziv(@PathParam("IDPak") int IDPak, @PathParam("cena") int cena) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(cena));
        objMsg.setIntProperty("tip", 10);
        objMsg.setIntProperty("IDPak", IDPak);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else 
           return Response.status(409).entity("Ne postoji paket sa datim ID!").build();
    }
    
    @Path("kreirajPretplatu/{IDKor}/{IDPak}")
    @POST
    public Response kreirajPretplatu(@PathParam("IDKor") int IDKor, @PathParam("IDPak") int IDPak) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        PretplataP pretplata = new PretplataP(IDKor, IDPak, new Date(), 0); //cena je 0 jer ce se gledati u odnosu na paket
        ObjectMessage objMsg = context.createObjectMessage(pretplata);
        objMsg.setIntProperty("tip", 11);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else if(result == -1)
           return Response.status(409).entity("Ne postoji paket sa datim ID!").build();
        else
            return Response.status(409).entity("Prethodna pretplata nije istekla!").build();
    }
    
    @Path("kreirajGledanje/{IDKor}/{IDVid}/{sekund}/{brSekundi}")
    @POST
    public Response kreirajGledanje(@PathParam("IDKor") int IDKor, @PathParam("IDVid") int IDVid, @PathParam("sekund") int sekund, @PathParam("brSekundi") int brSekundi) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        GledanjeP gledanje = new GledanjeP(IDKor, IDVid, new Date(), sekund, brSekundi);
        ObjectMessage objMsg = context.createObjectMessage(gledanje);
        objMsg.setIntProperty("tip", 12);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        return Response.status(200).entity("Uspesno izvrseno!").build();
    }
    
    @Path("kreirajOcenu/{IDKor}/{IDVid}/{vrednost}")
    @POST
    public Response kreirajOcenu(@PathParam("IDKor") int IDKor, @PathParam("IDVid") int IDVid, @PathParam("vrednost") int vrednost) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        OcenaP ocena = new OcenaP(IDKor, IDVid, vrednost, new Date());
        ObjectMessage objMsg = context.createObjectMessage(ocena);
        objMsg.setIntProperty("tip", 13);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        return Response.status(200).entity("Uspesno izvrseno!").build();
    }
    
    @Path("promeniOcenu/{IDKor}/{IDVid}/{vrednost}")
    @POST
    public Response promeniOcenu(@PathParam("IDKor") int IDKor, @PathParam("IDVid") int IDVid, @PathParam("vrednost") int vrednost) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(vrednost));
        objMsg.setIntProperty("tip", 14);
        objMsg.setIntProperty("IDVid", IDVid);
        objMsg.setIntProperty("IDKor", IDKor);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else 
           return Response.status(409).entity("Ne postoji ocena za dati video i korisnika!").build();
    }
    
    @Path("obrisiOcenu/{IDVid}/{IDKor}")
    @DELETE
    public Response obrisiOcenu(@PathParam("IDVid") int IDVid, @PathParam("IDKor") int IDKor) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDVid));
        objMsg.setIntProperty("tip", 15);
        objMsg.setIntProperty("IDKor", IDKor);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else 
           return Response.status(404).entity("Ne postoji ocena za dati video!").build();
    }
    
    @Path("dohvatiPakete")
    @GET
    public Response dohvatiPakete() throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(null);
        objMsg.setIntProperty("tip", 22);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
    
    @Path("dohvatiPretplateZaKorisnika/{IDKor}")
    @GET
    public Response dohvatiPretplateZaKorisnika(@PathParam("IDKor") int IDKor) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDKor));
        objMsg.setIntProperty("tip", 23);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
    
    @Path("dohvatiGledanjaZaVideo/{IDVid}")
    @GET
    public Response dohvatiGledanjaZaVideo(@PathParam("IDVid") int IDVid) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDVid));
        objMsg.setIntProperty("tip", 24);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
    
    @Path("dohvatiOceneZaVideo/{IDVid}")
    @GET
    public Response dohvatiOceneZaVideo(@PathParam("IDVid") int IDVid) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDVid));
        objMsg.setIntProperty("tip", 25);
        producer.send(podsistem3Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
}
