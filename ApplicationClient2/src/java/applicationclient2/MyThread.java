/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclient2;

import Entiteti.Filijala;
import Entiteti.Imasediste;
import Entiteti.Komitent;
import Entiteti.Mesto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class MyThread extends Thread{
    
          
              Queue queueS;      
              ConnectionFactory conFactS;
    
               MyThread(Queue q, ConnectionFactory cf){
                    queueS=q;
                    conFactS=cf;

                    }
             
             
	@Override
	public void run() {
                 try {
                     EntityManagerFactory emft = Persistence.createEntityManagerFactory("podsis2Unit");
                     EntityManager emt = emft.createEntityManager();
                     JMSContext context = conFactS.createContext();
                     JMSConsumer consumer = context.createConsumer(queueS);
                     
                     

                     while(true){
                         ObjectMessage objM = (ObjectMessage) consumer.receive();
                         
                     int st = objM.getIntProperty("statusS2");
                     switch(st){
                         case 0:
                             //dodavanje Mesta
                                try {
                                    Mesto mdod =  (Mesto) objM.getObject();
                                    if(mdod!=null){

                                        EntityTransaction transactionSide = emt.getTransaction();
                                        try{
                                            transactionSide.begin();
                                            emt.persist(mdod);
                                            transactionSide.commit();
                                        }finally{
                                            if(transactionSide.isActive())transactionSide.rollback();
                                        }
                                    }
                                    } catch (JMSException ex) {
                                        Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                break;
                         case 1:
                             //dodavanje kom
                              try {
                                    Komitent kdod =  (Komitent) objM.getObject();
                                    if(kdod!=null){

                                        EntityTransaction transactionSide = emt.getTransaction();
                                        try{
                                            transactionSide.begin();
                                            emt.persist(kdod);
                                            transactionSide.commit();
                                        }finally{
                                            if(transactionSide.isActive())transactionSide.rollback();
                                        }
                                    }
                                    } catch (JMSException ex) {
                                        Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                             
                             
                             break;
                         case 2:
                             //dodavanje fili
                              try {
                                    Filijala fdod =  (Filijala) objM.getObject();
                                    if(fdod!=null){

                                        EntityTransaction transactionSide = emt.getTransaction();
                                        try{
                                            transactionSide.begin();
                                            emt.persist(fdod);
                                            transactionSide.commit();
                                        }finally{
                                            if(transactionSide.isActive())transactionSide.rollback();
                                        }
                                    }
                                    } catch (JMSException ex) {
                                        Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                             
                             break;
                             
                        case 3:
                             //dodavanje imased
                             try {
                                    Imasediste idod =  (Imasediste) objM.getObject();
                                    if(idod!=null){

                                        EntityTransaction transactionSide = emt.getTransaction();
                                        try{
                                            transactionSide.begin();
                                            emt.persist(idod);
                                            transactionSide.commit();
                                        }finally{
                                            if(transactionSide.isActive())transactionSide.rollback();
                                        }
                                    }
                                    } catch (JMSException ex) {
                                        Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            
                            
                             break;      
                        }      
                    }
                 } catch (JMSException ex) {
                     Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                 }
    }      
}
