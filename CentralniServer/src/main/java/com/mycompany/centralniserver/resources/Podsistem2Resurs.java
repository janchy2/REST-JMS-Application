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
import pomocneklase.KorisnikP;
import pomocneklase.VideosnimakP;

/**
 *
 * @author Jana
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("podsistem2")
public class Podsistem2Resurs {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connFactory;
    
    @Resource(lookup="CSQueue")
    private Queue receiveQueue;
    
    @Resource(lookup="p2Queue")
    private Queue podsistem2Queue;
    
    @Resource(lookup="p3Queue")
    private Queue podsistem3Queue; //zbog neophodnih azuriranja kod brisanja
    
    @Path("kreirajKategoriju/{naziv}")
    @POST
    public Response kreirajKategoriju(@PathParam("naziv") String naziv) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(naziv);
        objMsg.setIntProperty("tip", 5);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();

        return Response.status(200).entity("Uspesno izvrseno!").build();
    }
    
    @Path("kreirajVideoSnimak/{naziv}/{trajanje}/{IDKor}")
    @POST
    public Response kreirajVideoSnimak(@PathParam("naziv") String naziv, @PathParam("trajanje") int trajanje,
            @PathParam("IDKor") int IDKor) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        VideosnimakP video = new VideosnimakP(naziv, trajanje, IDKor, new Date());
        ObjectMessage objMsg = context.createObjectMessage(video);
        objMsg.setIntProperty("tip", 6);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        return Response.status(200).entity("Uspesno izvrseno!").build();
    }
    
    @Path("promeniNaziv/{IDVid}/{naziv}")
    @POST
    public Response promeniNaziv(@PathParam("IDVid") int IDVid, @PathParam("naziv") String naziv) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(naziv);
        objMsg.setIntProperty("tip", 7);
        objMsg.setIntProperty("IDVid", IDVid);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else 
           return Response.status(409).entity("Ne postoji video sa datim ID!").build();
    }
    
    @Path("dodajKategoriju/{IDVid}/{IDKat}")
    @POST
    public Response dodajKategoriju(@PathParam("IDVid") int IDVid, @PathParam("IDKat") int IDKat) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDKat));
        objMsg.setIntProperty("tip", 8);
        objMsg.setIntProperty("IDVid", IDVid);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else if(result == -1)
           return Response.status(409).entity("Ne postoji video sa datim ID!").build();
        else
            return Response.status(409).entity("Ne postoji kategorija sa datim ID!").build();
    }
    
    @Path("obrisiVideo/{IDVid}/{IDKor}")
    @DELETE
    public Response obrisiVideo(@PathParam("IDVid") int IDVid, @PathParam("IDKor") int IDKor) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSConsumer consumer1 = context.createConsumer(podsistem2Queue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDKor));
        objMsg.setIntProperty("tip", 16);
        objMsg.setIntProperty("IDVid", IDVid);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        
        if(result == 0) {
            objMsg = context.createObjectMessage(new Integer(IDVid));
            objMsg.setIntProperty("tip", 26);
            producer.send(podsistem3Queue, objMsg); //azuriranja u podsistemu 3
            return Response.status(200).entity("Uspesno izvrseno!").build();
        }  
        else if(result == -1)
           return Response.status(404).entity("Ne postoji video sa datim ID!").build();
        else
            return Response.status(404).entity("Korisnik nije vlasnik videa!").build();
    }
    
    @Path("dohvatiKategorije")
    @GET
    public Response dohvatiKategorije() throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(null);
        objMsg.setIntProperty("tip", 19);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
    
    @Path("dohvatiSnimke")
    @GET
    public Response dohvatiSnimke() throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(null);
        objMsg.setIntProperty("tip", 20);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
    
    @Path("dohvatiKategorijeZaVideo/{IDVid}")
    @GET
    public Response dohvatiKategorijeZaVideo(@PathParam("IDVid") int IDVid) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDVid));
        objMsg.setIntProperty("tip", 21);
        producer.send(podsistem2Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        if(objMsg.getObject() == null) 
            return Response.status(404).entity("Ne postoji video sa datim ID!").build();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
}
