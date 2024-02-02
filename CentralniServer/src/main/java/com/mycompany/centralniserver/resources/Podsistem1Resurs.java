/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.resources;

import java.util.StringTokenizer;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import pomocneklase.KorisnikP;

/**
 *
 * @author Jana
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("podsistem1")
public class Podsistem1Resurs {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connFactory;
    
    @Resource(lookup="CSQueue")
    private Queue receiveQueue;
    
    @Resource(lookup="p1Queue")
    private Queue podsistem1Queue;
    
    @Path("kreirajGrad/{naziv}")
    @POST
    public Response kreirajGrad(@PathParam("naziv") String naziv) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(naziv);
        objMsg.setIntProperty("tip", 1);
        producer.send(podsistem1Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        return Response.status(200).entity("Uspesno izvrseno!").build();
    }
    
    @Path("kreirajKorisnika/{ime}/{email}/{godiste}/{pol}/{IDMes}")
    @POST
    public Response kreirajKorisnika(@PathParam("ime") String ime, @PathParam("email") String email,
            @PathParam("godiste") int godiste, @PathParam("pol") String pol, @PathParam("IDMes") int IDMes) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        KorisnikP korisnik = new KorisnikP(ime, email, godiste, pol, IDMes);
        ObjectMessage objMsg = context.createObjectMessage(korisnik);
        objMsg.setIntProperty("tip", 2);
        producer.send(podsistem1Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else
            return Response.status(409).entity("Mesto sa datim ID ne postoji!").build();
    }
    
    @Path("promeniEmail/{IDKor}/{email}")
    @POST
    public Response promeniEmail(@PathParam("IDKor") int IDKor, @PathParam("email") String email) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(email);
        objMsg.setIntProperty("tip", 3);
        objMsg.setIntProperty("IDKor", IDKor);
        producer.send(podsistem1Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else
            return Response.status(409).entity("Ne postoji korisnik sa datim ID!").build();
    }
    
    @Path("promeniMesto/{IDKor}/{IDMes}")
    @POST
    public Response promeniMesto(@PathParam("IDKor") int IDKor, @PathParam("IDMes") int IDMes) throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(new Integer(IDMes));
        objMsg.setIntProperty("tip", 4);
        objMsg.setIntProperty("IDKor", IDKor);
        producer.send(podsistem1Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        int result = ((Integer)objMsg.getObject()).intValue();
        if(result == 0)
            return Response.status(200).entity("Uspesno izvrseno!").build();
        else if(result == -1)
            return Response.status(409).entity("Ne postoji mesto sa datim ID!").build();
        else
            return Response.status(409).entity("Ne postoji korisnik sa datim ID!").build();
    }
    
    @Path("dohvatiMesta")
    @GET
    public Response dohvatiMesta() throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(null);
        objMsg.setIntProperty("tip", 17);
        producer.send(podsistem1Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
    
    @Path("dohvatiKorisnike")
    @GET
    public Response dohvatiKorisnike() throws JMSException {
        JMSContext context = connFactory.createContext();
        JMSConsumer consumer = context.createConsumer(receiveQueue);
        JMSProducer producer = context.createProducer();
        
        ObjectMessage objMsg = context.createObjectMessage(null);
        objMsg.setIntProperty("tip", 18);
        producer.send(podsistem1Queue, objMsg);
        objMsg = (ObjectMessage)consumer.receive();
        
        return Response.status(200).entity(objMsg.getObject().toString()).build();
    }
}
