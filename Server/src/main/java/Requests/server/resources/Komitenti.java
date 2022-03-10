package Requests.server.resources;


import Entiteti.Filijala;
import Entiteti.Imasediste;
import Entiteti.Komitent;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import Entiteti.Mesto;
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
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;

@Path("komitenti")
public class Komitenti {
       
             @Resource(lookup="PodsistemOneQueue")
              Queue queue;
             @Resource(lookup="jms/__defaultConnectionFactory")
              ConnectionFactory conFact;
             @Resource(lookup="PodsistemTopic")
              Topic topic;
        
    
        //@PersistenceContext(unitName = "my_persistence_unit")
           //EntityManager em ;

        
        @GET
        @Path("{idKom}")
        public Response findKomi(@PathParam("idKom")int idkom){
           JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
    
           try {
               ObjectMessage toSen= context.createObjectMessage(6);
               toSen.setIntProperty("IdR", 1);
               toSen.setIntProperty("idkom", idkom);
               producer.send(topic, toSen);
               
               ObjectMessage getObj = (ObjectMessage) consumer.receive();
               Komitent kom =  (Komitent) getObj.getObject();
               
               if (kom == null) return Response.status(404).entity("nema tog komitenta").build();
               return Response.status(Response.Status.CREATED).entity(kom).build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           return null;
         
        }
        
        //------------stavka 12----------------
     
      @GET
      @Path("sviKomit")
      public Response getAllKomit(){
            
          JMSContext context = conFact.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
          try {
                    ObjectMessage toSen= context.createObjectMessage(7);
                    toSen.setIntProperty("IdR", 1);
                    producer.send(topic, toSen);
                
                        ObjectMessage getObj = (ObjectMessage) consumer.receive();
                        List<Komitent> listaKomi =  (List<Komitent>) getObj.getObject();
                        if(!listaKomi.isEmpty()){
                            return  Response.status(200).entity(new GenericEntity<List<Komitent>>(listaKomi){}).build();
                        }
                        return  Response.status(404).entity("Tabela komitent je prazna ili je doslo do greske!").build();                     
                } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           
          return null;
        }   
         
        
        
        
     @POST
     public Response createKomitent(Komitent komi){
             
            JMSContext context = conFact.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
      
            
           try {
            
               ObjectMessage toSen= context.createObjectMessage(8);
               toSen.setIntProperty("IdR", 1);
               producer.send(topic, toSen);
               ObjectMessage toSen2= context.createObjectMessage(komi);
                  toSen2.setIntProperty("IdR", 1);
                   producer.send(topic, toSen2);
                   
                   
                TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                if(ispit.compareTo("uspelo")==0)
                     return Response.status(200).entity("Dodad komitent").build();
                else return Response.status(404).entity("Nije dodato komitent").build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
            return null;
     }

     
     
     @PUT
     @Path("{idKom}/{idMes}")
     public Response changeKomMes(@PathParam("idKom") int idkom , @PathParam("idMes") int idmes){
            JMSContext context = conFact.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
      
            try{
                 ObjectMessage toSen= context.createObjectMessage(9);
                 toSen.setIntProperty("IdR", 1);
                 toSen.setIntProperty("idKomi", idkom);
                 toSen.setIntProperty("idMesKomi", idmes);
                 producer.send(topic, toSen);
                 
                 
                 
                TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                if(ispit.compareTo("uspelo")==0)
                     return Response.status(200).entity("Promena Mesta Komitenta").build();
                else return Response.status(404).entity("Nije uspela promena mesta").build();
                
            } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
     }
     
}
