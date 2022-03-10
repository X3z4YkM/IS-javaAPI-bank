/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Requests.server.resources;
 
import Entiteti.*;
import Wrapper.wrapperEntity;
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
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;




@Path("BackUp")
public class BackUp {

         @Resource(lookup="PodsistemTreeQueue")
         Queue queue;
         @Resource(lookup="jms/__defaultConnectionFactory")
         ConnectionFactory conFact;
         @Resource(lookup="PodsistemTopic")
         Topic topic;
    
    
    
    @GET
    @Path("getBackUp")
    public Response get_backUp(){
             JMSContext context = conFact.createContext();
             JMSProducer producer = context.createProducer();
              JMSConsumer consumer = context.createConsumer(queue);
             
             
             
             try {
              ObjectMessage toSen= context.createObjectMessage(0);
  
               toSen.setIntProperty("IdR", 3);
               producer.send(topic, toSen);
                 //System.out.println(queue.getQueueName());
                 
                 
              // ObjectMessage getObj = (ObjectMessage) consumer.receive();
               // wrapperEntity we =  (wrapperEntity) getObj.getObject();
             //if (we == null) return Response.status(404).entity("prazanWrapper").build();
             // return Response.status(200).entity(we).build();
           } catch (JMSException ex) {
              Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           return null;
             
             
             
    
    }
    
    
    @GET
    @Path("compare")
    public Response getCompare(){
    
            JMSContext context = conFact.createContext();
             JMSProducer producer = context.createProducer();
             JMSConsumer consumer = context.createConsumer(queue);
             
             
             
             try {
               ObjectMessage toSen= context.createObjectMessage(1);
               toSen.setIntProperty("IdR", 3);
               producer.send(topic, toSen);
               
               ObjectMessage getObj = (ObjectMessage) consumer.receive();
              wrapperEntity we =  (wrapperEntity) getObj.getObject();
               
               if (we == null) return Response.status(404).entity("prazanWrapper").build();
               return Response.status(Response.Status.CREATED).entity(we).build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           return null;
    }
    
    
}
