/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Pod sis 1
package applicationclient1;

import Entiteti.Filijala;
import Entiteti.Imasediste;
import Entiteti.Komitent;
import Entiteti.Mesto;
import WraperPodsis1.WrapperP1;
import java.io.Serializable;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class Main {

    @Resource(lookup="PodsistemOneQueue")
    static Queue queue;
    @Resource(lookup="PodsistemSideQueue")
    static Queue queueSide;
    @Resource(lookup="jms/__defaultConnectionFactory")
    static ConnectionFactory conFact;
    @Resource(lookup="PodsistemTopic")
    static Topic topic;
    @Resource(lookup="PodsistemTreeSQueue")
    static Queue queueTS;
    @Resource(lookup="PodsistemTreeQueue")
    static Queue queueT;
    public static void main(String[] args) {
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsis1Unit");
        EntityManager em = emf.createEntityManager();
            
        JMSContext context = conFact.createContext();
        context.setClientID("Idr"+1);
        JMSConsumer consumer = context.createDurableConsumer(topic, "sub1", "IdR="+1, false);
         JMSProducer producer = context.createProducer();
         JMSProducer producerSide = context.createProducer();
         
         while(true){
            try {
                //EntityTransaction transaction = em.getTransaction();
                
                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                Integer toSw = (Integer)objMsg.getObject();
                switch(toSw){
                    case 0:
                        //**********MESTO*********
                       //put mesto
                        
                      ObjectMessage MestoDod = (ObjectMessage) consumer.receive();  
                        Mesto m= (Mesto)MestoDod.getObject();
                        EntityTransaction transaction = em.getTransaction();
                       try{
                            transaction.begin();
                            em.persist(m);
                            transaction.commit();
                       }finally{
                             if(transaction.isActive())transaction.rollback();
                       }
                       Mesto me = em.find(Mesto.class,m.getIdMes());
                       if(me!=null){
                           
                             ObjectMessage sedPs2= context.createObjectMessage(me);
                                    sedPs2.setIntProperty("statusS2", 0);
                                     producerSide.send(queueSide, sedPs2);

                           TextMessage txt= context.createTextMessage("uspelo");
                           producer.send(queue, txt);
                       }else{
                            TextMessage txt= context.createTextMessage("neuspelo");
                            producer.send(queue, txt);
                       }
                      
                        break;
                    case 1:
                        //doh po id mes
                        int id = objMsg.getIntProperty("idmes");
                        Mesto mestoVracaj=  em.createNamedQuery("Mesto.findByIdMes",Mesto.class).setParameter("idMes", id).getSingleResult();
                        ObjectMessage toSebd2 = context.createObjectMessage(mestoVracaj);
                        producer.send(queue, toSebd2);
        
                        break;
                    case 2:
                        //doh sva mesta
                         List<Mesto> listaMesta =  em.createQuery("SELECT m FROM Mesto m",Mesto.class).getResultList();
                        ObjectMessage toSebd = context.createObjectMessage((Serializable) listaMesta);
                        producer.send(queue, toSebd);
                        break;
                        
                        
                        //***********FILIJALE*********************
                    case 3:
                        //dohvati fili po id
                        int idF = objMsg.getIntProperty("idfil");
                        Filijala filiVracaj=  em.createNamedQuery("Filijala.findByIdFil",Filijala.class).setParameter("idFil", idF).getSingleResult();
                        ObjectMessage toSebdF = context.createObjectMessage(filiVracaj);
                        producer.send(queue, toSebdF);
                        
                        break;
                        
                    case 4: 
                        //dohvati sve fili 
                         List<Filijala> listaFili =  em.createQuery("SELECT f FROM Filijala f",Filijala.class).getResultList();
                        ObjectMessage toSebdLFili = context.createObjectMessage((Serializable) listaFili);
                        producer.send(queue, toSebdLFili);
                        break;
                        
                    case 5:
                        //post fili
                        ObjectMessage FiliDod = (ObjectMessage) consumer.receive();  
                        Filijala f= (Filijala)FiliDod.getObject();
                        EntityTransaction transactionf = em.getTransaction();
                       try{
                           
                           int idmF=f.getIdMes().getIdMes().intValue();
                             Mesto mesf = em.find(Mesto.class, idmF);
                             if(mesf!=null){ 
                                transactionf.begin();
                                em.persist(f);
                                transactionf.commit();
                             }
                       }finally{
                       if(transactionf.isActive())transactionf.rollback();
                       }
                       Filijala fi = em.find(Filijala.class,f.getIdFil());
                       if(fi!=null){
                           
                                 ObjectMessage sedPs2= context.createObjectMessage(fi);
                                    sedPs2.setIntProperty("statusS2", 2);
                                     producerSide.send(queueSide, sedPs2);

                           
                           TextMessage txt= context.createTextMessage("uspelo");
                           producer.send(queue, txt);
                       }else{
                             TextMessage txt= context.createTextMessage("neuspelo");
                             producer.send(queue, txt);
                       }
                        
                        break;
                        
                     //***************Komitent*********************   
                    case 6:
                       //get komitet po  id
                        int idK = objMsg.getIntProperty("idkom");
                        Komitent komVracaj=  em.createNamedQuery("Komitent.findByIdKom", Komitent.class).setParameter("idKom", idK).getSingleResult();
                        ObjectMessage toSebdK = context.createObjectMessage(komVracaj);
                        producer.send(queue, toSebdK);
                        break; 
                        
                    case 7:
                        //get svi komiteenti
                        
                         List<Komitent> listaKomi =  em.createQuery("SELECT k FROM Komitent k",Komitent.class).getResultList();
                        ObjectMessage toSebdLK = context.createObjectMessage((Serializable) listaKomi);
                        producer.send(queue, toSebdLK);
                        break;
                    case 8:
                        //post komitent
                        ObjectMessage KomDod = (ObjectMessage) consumer.receive();  
                        Komitent kom= (Komitent)KomDod.getObject();
                        EntityTransaction transactionk = em.getTransaction();
                       try{
                        transactionk.begin();
                        em.persist(kom);
                        transactionk.commit();
                       }finally{
                         if(transactionk.isActive())transactionk.rollback();
                       }
                       Komitent ko = em.find(Komitent.class,kom.getIdKom());
                       if(ko!=null){
                           
                                ObjectMessage objSide = context.createObjectMessage(ko);
                                objSide.setIntProperty("statusS2", 1);
                                producerSide.send(queueSide,objSide);
                                
                           TextMessage txt= context.createTextMessage("uspelo");
                           producer.send(queue, txt);
                       }else{
                           TextMessage txt= context.createTextMessage("neuspelo");
                           producer.send(queue, txt);
                       }
                        break;
                        
                        
                    case 9:
                        //PUT komitent
                        int idKomi= objMsg.getIntProperty("idKomi");
                        int idMesKom=objMsg.getIntProperty("idMesKomi");
                        Mesto mKomi= em.find(Mesto.class, idMesKom);
                        int state=0;
                        EntityTransaction transactionk2 = em.getTransaction();
                        if(mKomi!=null){
                            Komitent KomiZaProm= em.find(Komitent.class, idKomi);
                            if(KomiZaProm!=null){
                                Imasediste ims=em.createNamedQuery("Imasediste.findByIdKom",Imasediste.class).setParameter("idKom",idKomi).getSingleResult();
                                ims.setIdMes(mKomi);
                               try{
                                    transactionk2.begin();
                                    em.persist(ims);
                                    transactionk2.commit();
                                }finally{                                       
                                    if(transactionk2.isActive()){
                                        transactionk2.rollback();
                                    }
                                }
                                if(em.find(Imasediste.class,idKomi).getIdMes().getIdMes()!=idMesKom)state=1;
                               
                                if(state==0){      
                                    
                                    Imasediste imsPs2 = em.find(Imasediste.class,idKomi);
                                    ObjectMessage objSide = context.createObjectMessage(imsPs2);
                                            objSide.setIntProperty("statusS2", 3);
                                            producerSide.send(queueSide,objSide);
                                    
                                     TextMessage txt= context.createTextMessage("uspelo");
                                     producer.send(queue, txt);
                                }else{
                                      TextMessage txt= context.createTextMessage("neuspelo");
                                      producer.send(queue, txt);
                                }
                            }else{
                                      TextMessage txt= context.createTextMessage("neuspelo");
                                      producer.send(queue, txt);
                            
                            }
                        }else{
                                    TextMessage txt= context.createTextMessage("neuspelo");
                                    producer.send(queue, txt);
                        
                        }
                        break;
                        
                    case 10:
                         WrapperP1 wp1 = new WrapperP1();
                          wp1.setListaFili(em.createQuery("SELECT m FROM Filijala m",Filijala.class).getResultList());
                         wp1.setListaMesta(em.createQuery("SELECT m FROM Mesto m",Mesto.class).getResultList());
                         wp1.setListaKom(em.createQuery("SELECT m FROM Komitent m",Komitent.class).getResultList());
                         wp1.setListaIMA(em.createQuery("SELECT m FROM Imasediste m",Imasediste.class).getResultList());
                                 
                         ObjectMessage obwp = (ObjectMessage) context.createObjectMessage();
                         obwp.setObject(wp1);                         
                         producer.send(queueTS,obwp);
                        break;
                        
                        case 11:
                         WrapperP1 wp11 = new WrapperP1();
                          wp11.setListaFili(em.createQuery("SELECT m FROM Filijala m",Filijala.class).getResultList());
                         wp11.setListaMesta(em.createQuery("SELECT m FROM Mesto m",Mesto.class).getResultList());
                         wp11.setListaKom(em.createQuery("SELECT m FROM Komitent m",Komitent.class).getResultList());
                         wp11.setListaIMA(em.createQuery("SELECT m FROM Imasediste m",Imasediste.class).getResultList());
                                 
                         ObjectMessage obwp2 = (ObjectMessage) context.createObjectMessage();
                         obwp2.setObject(wp11);                         
                         producer.send(queueT,obwp2);
                        break;
                        
                        
                }

            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         
         
         }
    }  
}
