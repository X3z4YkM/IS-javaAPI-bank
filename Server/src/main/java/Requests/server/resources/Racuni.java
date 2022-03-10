package Requests.server.resources;


import Entiteti.Filijala;
import Entiteti.Imasediste;
import Entiteti.Isplata;
import Entiteti.Komitent;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import Entiteti.Mesto;
import Entiteti.Racun;
import Entiteti.Stavka;
import Entiteti.Uplata;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.inject.ResolutionException;
import javax.persistence.EntityExistsException;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT; 
import java.time.LocalDateTime;
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
import javax.ws.rs.core.GenericEntity;

@Path("racuni")
public class Racuni {
    @Resource(lookup="PodsistemTwoQueue")
     Queue queue;
    @Resource(lookup="jms/__defaultConnectionFactory")
     ConnectionFactory conFact;
    @Resource(lookup="PodsistemTopic")
     Topic topic;
    
     @GET
     @Path("{idRac}")
     public Response getRacun(@PathParam("idRac") int idrac){
             JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
    
           try {
               ObjectMessage toSen= context.createObjectMessage(0);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idrac", idrac);
               producer.send(topic, toSen);
               
               ObjectMessage getObj = (ObjectMessage) consumer.receive();
               Racun rac =  (Racun) getObj.getObject();
                if(rac==null)return Response.status(404).entity("nema racun sa ovim id-em").build();
                return Response.status(200).entity(rac).build();
             } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }   
           return null;
     }
     
     //----------stavka 5 ---------------------
    @POST
    public Response createRacun(Racun rac){
          JMSContext context = conFact.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
      
            
           try {
            
               ObjectMessage toSen= context.createObjectMessage(1);
               toSen.setIntProperty("IdR", 2);
               producer.send(topic, toSen);
               ObjectMessage toSen2= context.createObjectMessage(rac);
                  toSen2.setIntProperty("IdR", 2);
                   producer.send(topic, toSen2);
                   
                   
                TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                if(ispit.compareTo("uspelo")==0)
                     return Response.status(200).entity("Dodad racun").build();
                else return Response.status(404).entity("Nije dodato racun").build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
            return null;
        
    }
    
    //-------------stavka 6 -------------------
    @DELETE
    @Path("{IdRac}")
    public Response deleteRacun(@PathParam("IdRac") int idrac){
            JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
        try {
               ObjectMessage toSen= context.createObjectMessage(2);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idrac", idrac);
               producer.send(topic, toSen);
               
                 TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                 if(ispit.compareTo("ndrv")==0) return Response.status(200).entity("Taj racun sa tim id-em vec ne postoji").build();  
                if(ispit.compareTo("uspelo")==0)
                     return Response.status(200).entity("Obrisan racun").build();
                else return Response.status(404).entity("Nije obrisan racun").build();
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
    
    }
    
    //------stavka 7--------------
    @POST
    @Path("prenos/{idRacSa}/{idRacNa}/{iznos}")
    public Response uplRacNaRac(@PathParam("idRacSa")int idracsa, @PathParam("idRacNa")int idracna,@PathParam("iznos")int iznos) {
    
         JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
        try {
               ObjectMessage toSen= context.createObjectMessage(3);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idrsa", idracsa);
                toSen.setIntProperty("idrna", idracna);
                 toSen.setIntProperty("iznos", iznos);
               producer.send(topic, toSen);
               
                 TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                 if(ispit.compareTo("sr0")==0) return Response.status(404).entity("stanje racuna je 0").build();  
                if(ispit.compareTo("srB")==0) return Response.status(404).entity("stanje racuna s koga hocete da posaljete novac je B").build(); 
                 if(ispit.compareTo("neuspelo")==0) return Response.status(404).entity("Catch grana je uhvatila gresku").build(); 
                  if(ispit.compareTo("uspelo")==0) return Response.status(200).entity("Tranzakcija je uspesna").build(); 
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
    }
     
    //------------stavka 8 ---------------
    
    @POST
    @Path("uplataNovca/{idRac}/{iznos}")
    public Response createUplataNaracun(@PathParam("idRac")int idrac, @PathParam("iznos")int iznos){
         JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
        try {
               ObjectMessage toSen= context.createObjectMessage(4);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idracUp", idrac);
                 toSen.setIntProperty("iznosUp", iznos);
               producer.send(topic, toSen);
               
                TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                 if(ispit.compareTo("rsU")==0) return Response.status(404).entity("racun ima status U").build();  
                if(ispit.compareTo("rsig")==0) return Response.status(404).entity("racun sa tim idem nije pronadjen").build(); 
                 if(ispit.compareTo("gus")==0) return Response.status(404).entity("sistem greska u catch").build(); 
                  if(ispit.compareTo("uspelo")==0) return Response.status(200).entity("Tranzakcija je uspesna").build(); 
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
    }
    
    //------------isplata sa racuna --------------
    
    @POST
    @Path("isplataNovca/{idRac}/{iznos}")
    public Response createIsplataNaracun(@PathParam("idRac")int idrac, @PathParam("iznos")int iznos){
        JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
        try {
               ObjectMessage toSen= context.createObjectMessage(5);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idracIs", idrac);
                 toSen.setIntProperty("iznosIs", iznos);
               producer.send(topic, toSen);
               
                 TextMessage txtMsg= (TextMessage) consumer.receive();
                String ispit= txtMsg.getText();
                 if(ispit.compareTo("min")==0) return Response.status(404).entity("Vas racun je Blokiran i nije u stanju da izvrsi isplatu- prelazak dozMin").build();  
                 if(ispit.compareTo("idP")==0) return Response.status(404).entity("racun sa tim idem nije pronadjen").build();  
                if(ispit.compareTo("stU")==0) return Response.status(404).entity("racun ima status U").build(); 
                 if(ispit.compareTo("pars")==0) return Response.status(404).entity("ParseException catch").build(); 
                  if(ispit.compareTo("uspelo")==0) return Response.status(200).entity("Tranzakcija je uspesna").build(); 
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
        
    }
    
    //---------stavka 13-------------
    
    @GET
    @Path("sviRacuni/{idKomi}")
    public Response  getAllRacuneZaKomi(@PathParam("idKomi")int idkom){
         JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
         try {
               ObjectMessage toSen= context.createObjectMessage(6);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idkomRac", idkom);

               producer.send(topic, toSen);
               
                 ObjectMessage objRZK = (ObjectMessage) consumer.receive();
                int status = objRZK.getIntProperty("status");
                if(status==1)return Response.status(404).entity("Lista je prazna").build();
                  if(status==2)return Response.status(404).entity("Komitent ne postoji").build();
                  if(status==3){
                       List<Racun> listaRacZK = (List<Racun>)objRZK.getObject();
                      return Response.status(200).entity(new GenericEntity<List<Racun>>(listaRacZK){}).build();
                  }
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
    }
    
    
    //--------stavka 14---------------
    @GET
    @Path("sveTranzakcije/{idrac}")
    public Response getAllTransForRacun(@PathParam("idrac")int idrac){
    
         JMSContext context = conFact.createContext();
           JMSProducer producer = context.createProducer();
           JMSConsumer consumer = context.createConsumer(queue);
         try {
               ObjectMessage toSen= context.createObjectMessage(7);
               toSen.setIntProperty("IdR", 2);
               toSen.setIntProperty("idTRac", idrac);

               producer.send(topic, toSen);
               
                 ObjectMessage objRZT = (ObjectMessage) consumer.receive();
                int status = objRZT.getIntProperty("status");
                if(status==1)return Response.status(404).entity("Racun ne postoji").build();
                  if(status==2)return Response.status(404).entity("Lista je prazna").build();
                  if(status==3){
                        List<Stavka> ListStavki = (List<Stavka>)objRZT.getObject();
                      return Response.status(200).entity(new GenericEntity<List<Stavka>>(ListStavki){}).build();
                  }
           } catch (JMSException ex) {
               Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
           }
           return null;
    }   
}


