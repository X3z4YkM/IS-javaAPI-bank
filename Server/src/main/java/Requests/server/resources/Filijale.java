
package Requests.server.resources;


import Entiteti.Filijala;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import Entiteti.Mesto;
import java.lang.invoke.MethodHandles;
import java.util.List;
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




@Path("filijale")

public class Filijale {
  
         @Resource(lookup="PodsistemOneQueue")
             Queue queue;
         @Resource(lookup="jms/__defaultConnectionFactory")
            ConnectionFactory conFact;
         @Resource(lookup="PodsistemTopic")
            Topic topic;
    

 
      
        //--------------------------------------------------------------------------------
      
      @GET
      @Path("{IdFil}")
      public Response getFil(@PathParam("IdFil")int idfil){
             JMSContext context = conFact.createContext();
             JMSProducer producer = context.createProducer();
             JMSConsumer consumer = context.createConsumer(queue);
    
           try {
               ObjectMessage toSen= context.createObjectMessage(3);
               toSen.setIntProperty("IdR", 1);
               toSen.setIntProperty("idfil", idfil);
               producer.send(topic, toSen);
               
               ObjectMessage getObj = (ObjectMessage) consumer.receive();
               Filijala fil =  (Filijala) getObj.getObject();
               
               if (fil == null) return Response.status(404).entity("nema tog mesta").build();
               return Response.status(Response.Status.CREATED).entity(fil).build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           return null;
      }
      
      //--------------stavka 11-----------------
     
      @GET
      @Path("sveFili")
      public Response getAllFili(){
             JMSContext context = conFact.createContext();
             JMSProducer producer = context.createProducer();
             JMSConsumer consumer = context.createConsumer(queue);
    
                 
                try {
                    ObjectMessage toSen= context.createObjectMessage(4);
                    toSen.setIntProperty("IdR", 1);
                    producer.send(topic, toSen);
                
                        ObjectMessage getObj = (ObjectMessage) consumer.receive();
                        List<Filijala> listaFili =  (List<Filijala>) getObj.getObject();
                        if(!listaFili.isEmpty()){
                            return  Response.status(200).entity(new GenericEntity<List<Filijala>>(listaFili){}).build();
                        }
                        return  Response.status(404).entity("Tabela filijala je prazna ili je doslo do greske!").build();
                        
                        
                
                    
                } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
          return null;
        }

      //------------stavka 2-----------
    @POST
    public Response setMestoFIl(Filijala fil){
    
        JMSContext context = conFact.createContext();
      JMSProducer producer = context.createProducer();
      JMSConsumer consumer = context.createConsumer(queue);
      
            
           try {
            
               ObjectMessage toSen= context.createObjectMessage(5);
               toSen.setIntProperty("IdR", 1);
               producer.send(topic, toSen);
               ObjectMessage toSen2= context.createObjectMessage(fil);
                  toSen2.setIntProperty("IdR", 1);
                   producer.send(topic, toSen2);

                   
                TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                if(ispit.compareTo("uspelo")==0)
                     return Response.status(200).entity("Dodata filijala").build();
                else return Response.status(404).entity("Nije dodata filijala").build();
                
                
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
            return null;
    }
}