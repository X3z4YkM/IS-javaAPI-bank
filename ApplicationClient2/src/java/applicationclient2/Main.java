/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Pod sis 2
package applicationclient2;

import Entiteti.Filijala;
import Entiteti.Imasediste;
import Entiteti.Isplata;
import Entiteti.Komitent;
import Entiteti.Mesto;
import Entiteti.Racun;
import Entiteti.Stavka;
import Entiteti.Uplata;
import WraperPodsis2.WrapperP2;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.inject.ResolutionException;
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
    @Resource(lookup="PodsistemTwoQueue")
    static Queue queue;
    @Resource(lookup="jms/__defaultConnectionFactory")
    static ConnectionFactory conFact;
    @Resource(lookup="PodsistemTopic")
    static Topic topic;
    @Resource(lookup="PodsistemSideQueue")
      static Queue queueS;
   @Resource(lookup="PodsistemTreeSQueue")
    static Queue queueTS;
    @Resource(lookup="PodsistemTreeQueue")
    static Queue queueT;
    
    
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsis2Unit");
        EntityManager em = emf.createEntityManager();
            
        JMSContext context = conFact.createContext();
        context.setClientID("Idr"+2);
        JMSConsumer consumer = context.createDurableConsumer(topic, "sub1", "IdR="+2, false);
         JMSProducer producer = context.createProducer();
        
            MyThread myT= new MyThread(queueS,conFact);
            myT.start();
           while(true){
            try {
                //EntityTransaction transaction = em.getTransaction();
                
                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                Integer toSw = (Integer)objMsg.getObject();
                switch(toSw){
        
                    case 0:
                        //get racun by idRac
                        int id = objMsg.getIntProperty("idrac");
                        Racun RacunVr=  em.createNamedQuery("Racun.findByIdRac",Racun.class).setParameter("idRac", id).getSingleResult();
                        ObjectMessage toSebd2 = context.createObjectMessage(RacunVr);
                        producer.send(queue, toSebd2);
                        break;
                    case 1:
                        //post racun
                        
                        ObjectMessage RacDod = (ObjectMessage) consumer.receive();  
                        Racun rac= (Racun)RacDod.getObject();
                        EntityTransaction transactionr2 = em.getTransaction();
                       try{
                        transactionr2.begin();
                        em.persist(rac);
                        transactionr2.commit();
                       }finally{
                         if(transactionr2.isActive())transactionr2.rollback();
                       }
                        Racun ra = em.find(Racun.class,rac.getIdRac());
                       if(ra!=null){
                           TextMessage txt= context.createTextMessage("uspelo");
                           producer.send(queue, txt);
                       }else{
                           TextMessage txt= context.createTextMessage("neuspelo");
                           producer.send(queue, txt);
                       }
                        
                        break;
                    case 2:
                        //delete racun by idrac                      
                         int idR = objMsg.getIntProperty("idrac");
                         Racun racDe = em.find(Racun.class, idR);
                            if(racDe!=null){
                            EntityTransaction transactionr3 = em.getTransaction();
                            try{
                            transactionr3.begin();
                            em.remove(racDe);
                            transactionr3.commit();
                           }finally{
                             if(transactionr3.isActive())transactionr3.rollback();
                            }
                             try{
                            transactionr3.begin();
                               em.flush();
                               transactionr3.commit();
                           }finally{
                             if(transactionr3.isActive())transactionr3.rollback();
                            }
                               Racun racDeP = em.find(Racun.class, idR); 
                                if(racDeP==null){
                                 TextMessage txt= context.createTextMessage("uspelo");
                                 producer.send(queue, txt);
                                 break;
                                }else{
                                TextMessage txt= context.createTextMessage("neuspelo");
                                 producer.send(queue, txt);
                                 break;
                                }
                            }else{
                                TextMessage txt= context.createTextMessage("ndrv");
                                 producer.send(queue, txt);
                            }
                        break;
                    case 3:
                        //PUT prenos idrSa/idrNa iznos
                      Date t;
                      Date d;
                       
                             SimpleDateFormat  dtf = new SimpleDateFormat("yyyy-MM-dd");
                             LocalDateTime now = LocalDateTime.now();                            
                              d = dtf.parse(now.toString());
                             SimpleDateFormat  dtf2 = new SimpleDateFormat("HH:mm:ss");                                                               
                              t = dtf2.parse(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
                       
                        
                        int idracsa = objMsg.getIntProperty("idrsa");
                        int idracna = objMsg.getIntProperty("idrna");
                        int iznos =    objMsg.getIntProperty("iznos");            
                       
                        EntityTransaction transactionr4 = em.getTransaction();
                        
                      
                        
                        
                        
                       try {
                         Racun Rsa = em.find(Racun.class, idracsa);
                         if(Rsa != null){
                            Racun Rna = em.find(Racun.class, idracna);
                                if(Rna != null){
                                 int dozMin = Rsa.getDozvMinus();
                                 int stanje = Rsa.getStanje();
                                 char status = Rsa.getStatus();  
                                    if((status != 'B')&&(status!='U')){
                                       if(stanje != 0){
                                        if((stanje - iznos)>=(-1*dozMin)){
                                            stanje = stanje - iznos ;
                                            Rsa.setStanje(stanje);
                                            
                                            try{
                                            transactionr4.begin();
                                            em.persist(Rsa);
                                            transactionr4.commit();
                                              }finally{
                                                if(transactionr4.isActive())transactionr4.rollback();
                                               }
                                            int stanje2= Rna.getStanje();

                                            int dozMin2 = Rna.getDozvMinus();


                                            if((stanje2+iznos)>=(-1*dozMin2)){
                                                Rna.setStanje(stanje2+iznos);
                                                Rna.setStatus('A');
                                                try{
                                                 transactionr4.begin();
                                                em.persist(Rna);  
                                                transactionr4.commit();
                                                 }finally{
                                                    if(transactionr4.isActive())transactionr4.rollback();
                                                   }
                                            }                   
                                            else{
                                                 Rna.setStanje(stanje2+iznos);
                                                 try{
                                                 transactionr4.begin();
                                                em.persist(Rna);
                                                 transactionr4.commit();
                                                   }finally{
                                                    if(transactionr4.isActive())transactionr4.rollback();
                                                   }
                                            }
                                            
                                            //------Stavka za racun Sa-------
                                            List<Stavka> L_Stavke = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac=:idrac1 Order by s.redBroj DESC", Stavka.class)
                                                .setParameter("idrac1", Rsa).getResultList();
                                        
                                                Stavka sSa = new Stavka();
                                                sSa.setDatum(d);
                                                sSa.setIdFil(Rsa.getIdFil());
                                                sSa.setIdRac(Rsa);                              
                                                sSa.setRedBroj(L_Stavke.get(0).getRedBroj()+1);
                                                sSa.setIznos(iznos);
                                                sSa.setVreme(t);
                                                try{
                                               transactionr4.begin();
                                                em.persist(sSa);
                                                transactionr4.commit();
                                                  }finally{
                                                    if(transactionr4.isActive())transactionr4.rollback();
                                                   }
                                            //----------Stavka za racun Na-----
                                                List<Stavka> L_Stavke2 = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac=:idrac2 Order by s.redBroj DESC", Stavka.class)
                                                .setParameter("idrac2", Rna).getResultList();
                                        
                                                Stavka sNa = new Stavka();
                                                sNa.setDatum(d);
                                                sNa.setIdFil(Rna.getIdFil());
                                                sNa.setIdRac(Rna);                              
                                                sNa.setRedBroj(L_Stavke2.get(0).getRedBroj()+1);
                                                sNa.setIznos(iznos);
                                                sNa.setVreme(t);
                                                try{
                                                transactionr4.begin();
                                                em.persist(sNa);
                                                transactionr4.commit();
                                                  }finally{
                                                    if(transactionr4.isActive())transactionr4.rollback();
                                                  }

                                                try{
                                                transactionr4.begin();
                                                   em.flush();
                                                   em.refresh(sNa);
                                                em.refresh(sSa);
                                                   transactionr4.commit();
                                               }finally{
                                                 if(transactionr4.isActive())transactionr4.rollback();
                                                }                          
                                                //----------------
                                                //----dodajemo uplatu/isplatu-------
                                                
                                                 Uplata u = new Uplata();
                                                    u.setStavka(sNa);
                                                    u.setIdSta(sNa.getIdSta());
                                                    u.setOsnov("Plata");
                                                    try{
                                                    transactionr4.begin();
                                                    em.persist(u);
                                                    transactionr4.commit();
                                                    }finally{
                                                    if(transactionr4.isActive())transactionr4.rollback();
                                                   }
                                                    Isplata i = new Isplata();
                                                    i.setStavka(sSa);
                                                    i.setIdSta(sSa.getIdSta());
                                                    i.setProvizija(0.0);
                                                    try{
                                                      transactionr4.begin();
                                                    em.persist(i);
                                                      transactionr4.commit();
                                                    }finally{
                                                        if(transactionr4.isActive())transactionr4.rollback();
                                                       }
                                     }else{
                                    Rsa.setStatus('B');
                                    stanje = stanje - iznos ;
                                    Rsa.setStanje(stanje);
                                    try{
                                     transactionr4.begin();
                                    em.persist(Rsa);
                                      transactionr4.commit();
                                       }finally{
                                        if(transactionr4.isActive())transactionr4.rollback();
                                       } 

                                    int stanje2= Rna.getStanje();
                                  
                                    int dozMin2 = Rna.getDozvMinus();
                                    
                                    
                                    if((stanje2+iznos)>=(-1*dozMin2)){
                                        Rna.setStanje(stanje2+iznos);
                                        Rna.setStatus('A');
                                        try{
                                         transactionr4.begin();
                                        em.persist(Rna);  
                                         transactionr4.commit();
                                          }finally{
                                            if(transactionr4.isActive())transactionr4.rollback();
                                           }
                                    }                   
                                    else{
                                     Rna.setStanje(stanje2+iznos); 
                                     try{
                                      transactionr4.begin();
                                     em.persist(Rna);
                                       transactionr4.commit();
                                         }finally{
                                            if(transactionr4.isActive())transactionr4.rollback();
                                           }
                                       
                                    }
                                 //------Stavka za racun Sa-------
                                        List<Stavka> L_Stavke = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac=:idrac Order by s.redBroj DESC", Stavka.class)
                                                .setParameter("idrac", Rsa).getResultList();
                                        
                                        Stavka sSa = new Stavka();
                                        sSa.setDatum(d);
                                        sSa.setIdFil(Rsa.getIdFil());
                                        sSa.setIdRac(Rsa);                              
                                        sSa.setRedBroj(L_Stavke.get(0).getRedBroj()+1);
                                        sSa.setIznos(iznos);
                                        sSa.setVreme(t);
                                        try{
                                         transactionr4.begin();
                                        em.persist(sSa);    
                                          transactionr4.commit();
                                            }finally{
                                                 if(transactionr4.isActive())transactionr4.rollback();
                                            }

                                          
                                        //----------Stavka za racun Na-----
                                       List<Stavka> L_Stavke2 = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac=:idrac Order by s.redBroj DESC", Stavka.class)
                                                .setParameter("idrac", Rna).getResultList();
                                        
                                        Stavka sNa = new Stavka();
                                        sNa.setDatum(d);
                                        sNa.setIdFil(Rna.getIdFil());
                                        sNa.setIdRac(Rna);                              
                                        sNa.setRedBroj(L_Stavke2.get(0).getRedBroj()+1);
                                        sNa.setIznos(iznos);
                                        sNa.setVreme(t);
                                        try{
                                         transactionr4.begin();
                                        em.persist(sNa);
                                         transactionr4.commit();
                                           }finally{
                                            if(transactionr4.isActive())transactionr4.rollback();
                                           }
                                         try{
                                                transactionr4.begin();
                                                   em.flush();                                           
                                                   transactionr4.commit();
                                               }finally{
                                                 if(transactionr4.isActive())transactionr4.rollback();
                                                }   
                                        //----------------

                                           //----dodajemo uplatu/isplatu-------
                                        Uplata u = new Uplata();
                                        u.setStavka(sNa);
                                        u.setIdSta(sNa.getIdSta());
                                        u.setOsnov("Plata");
                                        try{
                                         transactionr4.begin();
                                        em.persist(u);
                                         transactionr4.commit();
                                           }finally{
                                                if(transactionr4.isActive())transactionr4.rollback();
                                               }
                                        Isplata i = new Isplata();
                                        i.setStavka(sSa);
                                        i.setIdSta(sSa.getIdSta());
                                        i.setProvizija(0.0);
                                        try{
                                         transactionr4.begin();
                                        em.persist(i); 
                                         transactionr4.commit();
                                           }finally{
                                                if(transactionr4.isActive())transactionr4.rollback();
                                               }
                                    }
                                }
                                 else{
                                            TextMessage txt= context.createTextMessage("sr0");
                                                 producer.send(queue, txt);
                                                 break;
                                     //return Response.status(404).entity("stanje racuna je 0").build();
                                   }
                                }else{
                                         TextMessage txt= context.createTextMessage("srB");
                                                  producer.send(queue, txt);
                                                  break;
                                 //return Response.status(404).entity("stanje racuna s koga hocete da posaljete novac je B").build();
                                }
                            }
                        }
                    } catch (ResolutionException e) {                       
                                //GRESKA U SISTEM
                              TextMessage txt= context.createTextMessage("neuspelo");
                             producer.send(queue, txt);
                             break;
                          }
                                TextMessage txt= context.createTextMessage("uspelo");
                                producer.send(queue, txt);
                        break;

                    case 4:
                        //PUT uplataNovca 
                                
                                int idracUpl = objMsg.getIntProperty("idracUp");
                                int iznosUp = objMsg.getIntProperty("iznosUp");
                                 EntityTransaction transaction5 = em.getTransaction();
                            try{

                                SimpleDateFormat  dtfU = new SimpleDateFormat("yyyy-MM-dd");
                                LocalDateTime now2 = LocalDateTime.now();
                                Date d2 = dtfU.parse(now2.toString());

                                SimpleDateFormat  dtfU2 = new SimpleDateFormat("HH:mm:ss");
                                Date t2 = dtfU2.parse(now2.getHour()+":"+now2.getMinute()+":"+now2.getSecond());


                                try{
                                    Racun racun = em.find(Racun.class, idracUpl);
                                    if(racun != null){
                                        if(racun.getStatus()!='U'){
                                            if(racun.getStatus()=='A'){
                                                int stanje = racun.getStanje();
                                                stanje += iznosUp;
                                                racun.setStanje(stanje);
                                                try{
                                                transaction5.begin();
                                                em.persist(racun);
                                                transaction5.commit();
                                                }
                                                finally{
                                                    if(transaction5.isActive())transaction5.rollback();
                                                    }
                                            }else{
                                                int stanje = racun.getStanje();
                                                if((stanje+iznosUp)>=(-1*racun.getDozvMinus())){
                                                    racun.setStatus('A');
                                                    racun.setStanje(stanje+iznosUp);
                                                    try{
                                                        transaction5.begin();
                                                    em.persist(racun);
                                                    transaction5.commit();
                                                    }
                                                    finally{
                                                    if(transaction5.isActive())transaction5.rollback();
                                                    }
                                                }else{
                                                    racun.setStanje(stanje+iznosUp);
                                                    try{
                                                       transaction5.begin();
                                                    em.persist(racun);
                                                    transaction5.commit();
                                                    } 
                                                     finally{
                                                    if(transaction5.isActive())transaction5.rollback();
                                                    }
                                                }
                                            }
                                            try{
                                                transaction5.begin();
                                                   em.flush();
                                                   transaction5.commit();
                                               }finally{
                                                 if(transaction5.isActive())transaction5.rollback();
                                                }   
                                            //---------dodavanje Stavke ------
                                            Stavka s = new Stavka();
                                            List<Stavka> L_Stavke = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac=:idrac1 Order by s.redBroj DESC", Stavka.class)
                                                    .setParameter("idrac1", racun).getResultList();

                                            s.setDatum(d2);
                                            s.setIdFil(racun.getIdFil());
                                            s.setIdRac(racun);
                                            s.setIznos(iznosUp);
                                            s.setVreme(t2);
                                            s.setRedBroj(L_Stavke.get(0).getRedBroj()+1);
                                            try{
                                            transaction5.begin();
                                            em.persist(s);
                                            transaction5.commit();
                                             }
                                             finally{
                                                    if(transaction5.isActive())transaction5.rollback();
                                             }
                                             try{
                                                transaction5.begin();
                                                   em.flush();
                                                   transaction5.commit();
                                               }finally{
                                                 if(transaction5.isActive())transaction5.rollback();
                                                }  
                                            em.refresh(s);
                                            //-----------dodavanje u Uplate----------

                                            Uplata u = new Uplata();
                                            u.setStavka(s);
                                            u.setIdSta(s.getIdSta());
                                            u.setOsnov("Uplata");
                                            try{                                              
                                            transaction5.begin();
                                            em.persist(u);  
                                           transaction5.commit();
                                            }
                                            finally{
                                                 if(transaction5.isActive())transaction5.rollback();
                                            }


                                        }else{
                                                TextMessage txt2 = context.createTextMessage("rsU");
                                              producer.send(queue, txt2);
                                              break;
                                            //return Response.status(404).entity("racun ima status U").build();
                                        }
                                    }
                                }catch(ResolutionException e){
                                    TextMessage txt2 = context.createTextMessage("rsig");
                                              producer.send(queue, txt2);
                                              break;
                                    //return Response.status(404).entity("racun sa tim idem nije pronadjen").build();
                                }
                            }catch(ParseException ex){
                                             TextMessage txt2 = context.createTextMessage("gus");
                                              producer.send(queue, txt2);
                                              break;
                            }
                           TextMessage txt2 = context.createTextMessage("uspelo");
                                              producer.send(queue, txt2);
                        break;
                        
                    case 5:
                        //PUT isplataNovca                            
                                 int idracIsp = objMsg.getIntProperty("idracIs");
                                  int iznosIs = objMsg.getIntProperty("iznosIs");
                                 EntityTransaction transaction6 = em.getTransaction();
                              try{

                                SimpleDateFormat  dtfIs = new SimpleDateFormat("yyyy-MM-dd");
                                LocalDateTime now3 = LocalDateTime.now();
                                Date d3 = dtfIs.parse(now3.toString());

                                SimpleDateFormat  dtfIs2 = new SimpleDateFormat("HH:mm:ss");
                                Date t3 = dtfIs2.parse(now3.getHour()+":"+now3.getMinute()+":"+now3.getSecond());

                                        
                                try{
                                    Racun racun = em.find(Racun.class, idracIsp);
                                    if(racun != null){
                                        if(racun.getStatus()!='U'){

                                            if(racun.getStatus()=='A'){
                                                if((racun.getStanje()-iznosIs)<=(-1*racun.getDozvMinus())){
                                                    racun.setStatus('B');
                                                    racun.setStanje(racun.getStanje()-iznosIs);
                                                    try{
                                                        
                                                    transaction6.begin();
                                                    em.persist(racun);
                                                    transaction6.commit();
                                                    
                                                    }finally{
                                                        if(transaction6.isActive())transaction6.rollback();
                                                        }
                                                }else{
                                                racun.setStanje(racun.getStanje()-iznosIs);
                                                try{
                                                    transaction6.begin();
                                                em.persist(racun);
                                                transaction6.commit();
                                                 }finally{
                                                        if(transaction6.isActive())transaction6.rollback();
                                                        }
                                                }           
                                            } else {
                                                TextMessage txt3= context.createTextMessage("min");
                                                 producer.send(queue, txt3);
                                                 break;
                                                //return Response.status(404).entity("Vas racun je Blokiran i nije u stanju da izvrsi isplatu- prelazak dozMin").build();
                                            }
                                             try{
                                                transaction6.begin();
                                                   em.flush();
                                                   transaction6.commit();
                                               }finally{
                                                 if(transaction6.isActive())transaction6.rollback();
                                                }  
                                            //---------dodavanje Stavke ------
                                            Stavka s = new Stavka();
                                            List<Stavka> L_Stavke = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac=:idrac1 Order by s.redBroj DESC", Stavka.class)
                                                    .setParameter("idrac1", racun).getResultList();

                                            s.setDatum(d3);
                                            s.setIdFil(racun.getIdFil());
                                            s.setIdRac(racun);
                                            s.setIznos(iznosIs);
                                            s.setVreme(t3);
                                            s.setRedBroj(L_Stavke.get(0).getRedBroj()+1);
                                            try{
                                                transaction6.begin();
                                            em.persist(s);
                                            transaction6.commit();
                                             }finally{
                                                        if(transaction6.isActive())transaction6.rollback();
                                                        }
                                          try{
                                                transaction6.begin();
                                                   em.flush();
                                                   transaction6.commit();
                                               }finally{
                                                 if(transaction6.isActive())transaction6.rollback();
                                                }  
                                            em.refresh(s);
                                            //-----------dodavanje u Uplate----------

                                            Isplata i = new Isplata();
                                            i.setStavka(s);
                                            i.setProvizija(0.0);
                                            i.setIdSta(s.getIdSta());
                                            try{
                                                transaction6.begin();
                                            em.persist(i);
                                            transaction6.commit();
                                             }finally{
                                                        if(transaction6.isActive())transaction6.rollback();
                                                        }

                                        }else{
                                            TextMessage txt3= context.createTextMessage("stU");
                                                 producer.send(queue, txt3);
                                                 break;
                                        //    return Response.status(404).entity("racun ima status U").build();
                                        }
                                    }
                                }catch(ResolutionException e){
                                    TextMessage txt3= context.createTextMessage("idP");
                                                 producer.send(queue, txt3);
                                                 break;
                                 //   return Response.status(404).entity("racun sa tim idem nije pronadjen").build();
                                }

                             //   return Response.status(200).entity("uspesna isplata sa racuna").build();
                            }catch(ParseException ex){
                                 TextMessage txt3= context.createTextMessage("pars");
                                                 producer.send(queue, txt3);
                                                 break;
                            }
                              TextMessage txt3= context.createTextMessage("uspelo");
                                                 producer.send(queue, txt3);
                            // return Response.status(200).entity("uspesna isplata sa racuna").build();
      
                        break;
                    case 6:
                        //GET svi racuni za komi
                        
                        int idkomiRac = objMsg.getIntProperty("idkomRac");
                         Komitent k = em.find(Komitent.class, idkomiRac);
                        List<Racun> listaRacuna;
                            if(k!=null){
                            listaRacuna = em.createQuery("SELECT r FROM Racun r WHERE r.idKom = :idkom ", Racun.class).setParameter("idkom",k).getResultList();
                                  if(listaRacuna.isEmpty()){ 
                                      ObjectMessage toSebdK = context.createObjectMessage(null);
                                      toSebdK.setIntProperty("status", 1);
                                      producer.send(queue, toSebdK);
                                      break;
                                  }
                            }else{
                                  ObjectMessage toSebdK = context.createObjectMessage(null);
                                  toSebdK.setIntProperty("status", 2);
                                      producer.send(queue, toSebdK);
                                      break;
                            }
                              ObjectMessage toSebdK = context.createObjectMessage((Serializable) listaRacuna);
                              toSebdK.setIntProperty("status", 3);
                                      producer.send(queue, toSebdK);
                            
                            
                        break;
                    case 7: 
                        //GET sve tranzakcije
                        int idTRac = objMsg.getIntProperty("idTRac");
                        Racun r = em.find(Racun.class, idTRac);
                        if(r==null){
                                    ObjectMessage objTRacuni = context.createObjectMessage(null);
                                      objTRacuni.setIntProperty("status", 1);
                                      producer.send(queue, objTRacuni);
                                      break;
                        
                        }
                         List<Stavka> ListStavki = em.createQuery("SELECT s FROM Stavka s WHERE s.idRac = :idrac", Stavka.class).setParameter("idrac", r).getResultList();
                         if(ListStavki.isEmpty()){
                           ObjectMessage objTRacuni = context.createObjectMessage(null);
                                      objTRacuni.setIntProperty("status", 2);
                                      producer.send(queue, objTRacuni);
                                      break;
                         }         
                         ObjectMessage objTRacuni = context.createObjectMessage((Serializable) ListStavki);
                                 objTRacuni.setIntProperty("status", 3);
                                      producer.send(queue, objTRacuni);
                        break;
                        
                    case 8:
                         WrapperP2 wp1 = new WrapperP2();
                          wp1.setListaRac(em.createQuery("SELECT m FROM Racun m",Racun.class).getResultList());
                         wp1.setListaStavka(em.createQuery("SELECT m FROM Stavka m",Stavka.class).getResultList());
                         wp1.setListaIspla(em.createQuery("SELECT m FROM Isplata m",Isplata.class).getResultList());
                         wp1.setListaUpla(em.createQuery("SELECT m FROM Uplata m",Uplata.class).getResultList());
                                 
                         ObjectMessage obwp = (ObjectMessage) context.createObjectMessage();
                         obwp.setObject(wp1);                         
                         producer.send(queueTS,obwp);
                        break;
                    case 9:
                        
                         WrapperP2 wp12 = new WrapperP2();
                          wp12.setListaRac(em.createQuery("SELECT m FROM Racun m",Racun.class).getResultList());
                         wp12.setListaStavka(em.createQuery("SELECT m FROM Stavka m",Stavka.class).getResultList());
                         wp12.setListaIspla(em.createQuery("SELECT m FROM Isplata m",Isplata.class).getResultList());
                         wp12.setListaUpla(em.createQuery("SELECT m FROM Uplata m",Uplata.class).getResultList());
                                 
                         ObjectMessage obwp2 = (ObjectMessage) context.createObjectMessage();
                         obwp2.setObject(wp12);                         
                         producer.send(queueT,obwp2);
                        break;
       
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }      
}

