package Requests.server.resources;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import Entiteti.Mesto;

import java.util.List;
import java.util.Vector;
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
import javax.jms.Topic;
import javax.persistence.EntityExistsException;
import javax.ws.rs.POST;
import javax.ws.rs.core.GenericEntity;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;




@Path("mesta")
public class Mesta {
   
       @Resource(lookup="PodsistemOneQueue")
     Queue queue;
    @Resource(lookup="jms/__defaultConnectionFactory")
     ConnectionFactory conFact;
    @Resource(lookup="PodsistemTopic")
     Topic topic;
        
        
    // @PersistenceContext(unitName = "my_persistence_unit")
    //EntityManager em ;
   
    @POST
    public Response makeMesto(Mesto mesto){
                  
      JMSContext context = conFact.createContext();
      JMSProducer producer = context.createProducer();
      JMSConsumer consumer = context.createConsumer(queue);
      
            
           try {
            
               ObjectMessage toSen= context.createObjectMessage(0);
               toSen.setIntProperty("IdR", 1);
               producer.send(topic, toSen);
               ObjectMessage toSen2= context.createObjectMessage(mesto);
                  toSen2.setIntProperty("IdR", 1);
                   producer.send(topic, toSen2);
                   
                   
                TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                if(ispit.compareTo("uspelo")==0)
                     return Response.status(200).entity("Dodato metso").build();
                else return Response.status(404).entity("Nije dodato metso").build();
                
                
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
            return null;
    }
    
    @GET 
    @Path("{idMes}")
    public Response getMes(@PathParam("idMes")int idms){    
           JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
    
           try {
               ObjectMessage toSen= context.createObjectMessage(1);
               toSen.setIntProperty("IdR", 1);
               toSen.setIntProperty("idmes", idms);
               producer.send(topic, toSen);
               
               ObjectMessage getObj = (ObjectMessage) consumer.receive();
               Mesto mes =  (Mesto) getObj.getObject();
               
               if (mes == null) return Response.status(404).entity("nema tog mesta").build();
               return Response.status(Response.Status.CREATED).entity(mes).build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           return null;
    }
    
 //---------stavka 10 -----------------------
  
    @GET
    @Path("svaMesta")
     public Response  dohSvaMesta() {
            JMSContext context = conFact.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
    
                 
                try {
                    ObjectMessage toSen= context.createObjectMessage(2);
                    toSen.setIntProperty("IdR", 1);
                    producer.send(topic, toSen);
                
                        ObjectMessage getObj = (ObjectMessage) consumer.receive();
                        List<Mesto> listaMesta =  (List<Mesto>) getObj.getObject();
                        if(!listaMesta.isEmpty()){
                            return  Response.status(200).entity(new GenericEntity<List<Mesto>>(listaMesta){}).build();
                        }
                        
                        return  Response.status(404).entity("Tabela metsa je prazna ili je doslo do greske!").build();
                        
                        
                
                    
                } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
          return null;
     }
     
}