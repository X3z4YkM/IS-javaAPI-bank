/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
*/
package clientapp;


import Entiteti.*;

import Wrapper.wrapperEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Scanner; 
import javax.ws.rs.core.GenericType;
public class ClientAPP {

 
    public static void main(String[] args) {
         
             Scanner scaner = new Scanner(System.in);
             
            Client client = ClientBuilder.newClient();
            int odluka;
            int defId=0;
            boolean whileState=true;
            while(whileState){
                System.out.println("Menu: ");
                 System.out.println("1. Kreiranje mesta ");
                 System.out.println("2. Kreiranje filijale u mestu ");
                 System.out.println("3. Kreiranje komitenta ");
                 System.out.println("4. Promena sedišta za zadatog komitenta ");
                 System.out.println("5. Otvaranje računa ");
                 System.out.println("6. Zatvaranje računa ");
                 System.out.println("7. Kreiranje transakcije koja je prenos sume sa jednog računa na drugi račun ");
                 System.out.println("8. Kreiranje transakcije koja je uplata novca na račun ");
                 System.out.println("9. Kreiranje transakcije koja je isplata novca sa računa ");
                 System.out.println("10. Dohvatanje svih mesta ");
                 System.out.println("11. Dohvatanje svih filijala ");
                 System.out.println("12. Dohvatanje svih komitenata ");
                  System.out.println("13. Dohvatanje svih računa za komitenta ");
                   System.out.println("14. Dohvatanje svih transakcija za račun ");
                    System.out.println("15. Dohvatanje svih podataka iz rezervne kopije");
                     System.out.println("16. Dohvatanje razlike u podacima u originalnim podacima i u rezervnoj kopiji ");
                     System.out.println("17. ugaci APP ");
                     System.out.print(">: ");
                     odluka = scaner.nextInt();
                     switch(odluka){
                     
                          case 1:
                             int idMes=defId;
                              Mesto m;
                              System.out.println(">:Unesite sledece parametre: ");
                                 System.out.print(">:Naziv Metsa: ");
                                 String nazivMesta = scaner.next();
                                 System.out.print(">:Postanski Broj: ");
                                  String PB = scaner.next();
                                  if(idMes!=0){
                                      m = new Mesto(idMes);
                                  }else  m= new Mesto();
                          m.setNaziv(nazivMesta);
                          m.setPostBr(PB);

                        Response order =  client
                                  .target("http://localhost:8080/Server/resources/mesta")
                                  .request()
                                  .post(Entity.json(m));
                        System.out.println(order);
                         break;
                       case 2:
                          // Mesto mf;
                           Filijala f;
                            System.out.println(">:Unesite sledece parametre: ");
                                  System.out.print(">:Id Metsa: ");
                                 int IDmf = scaner.nextInt();
                                  Mesto mf = client
                                  .target("http://localhost:8080/Server/resources/mesta")
                                 .path("{idMes}")
                                  .resolveTemplate("idMes", IDmf)
                                  .request()
                                  .get(Mesto.class);                                                             
                                 //System.out.print("Naziv Metsa: ");
                                // String nazivMestaF = scaner.next();
                                // System.out.print("Postanski Broj: ");
                                //  String PBF = scaner.next();
                                  
                                // System.out.print("Id Filijale: ");
                                // int IDFi = scaner.nextInt();
                                 System.out.print(">:Naziv Filijale: ");
                                 String nazivFili = scaner.nextLine();
                                 System.out.print(">:Adresa Filijale: ");
                                  String adrFili = scaner.nextLine();
                                  
                                 // mf= new Mesto(IDmf);
                                //  mf.setNaziv(nazivMestaF);
                                 // mf.setPostBr(PBF);
                                  f= new Filijala();
                                  f.setIdMes(mf);
                                  f.setNaziv(nazivFili);
                                  f.setAdresa(adrFili);
                             Response orderF =  client
                                  .target("http://localhost:8080/Server/resources/filijale")
                                  .request()
                                  .post(Entity.json(f));
                             System.out.println(orderF);
                           
                         break;
                           case 3:
                                System.out.println(">:Unesite sledece parametre: ");
                               System.out.print(">:Naziv Komitenta: ");
                                 String nazivKomi = scaner.nextLine();
                                 System.out.print(">:Adresa Komitenta: ");
                                  String adrKomi = scaner.nextLine();
                               Komitent kom= new Komitent();
                               kom.setAdresa(adrKomi);
                               kom.setNaziv(nazivKomi);
                                Response orderK =  client
                                  .target("http://localhost:8080/Server/resources/komitenti")
                                  .request()
                                  .post(Entity.json(kom));
                                 System.out.println(orderK);
                         break;
                           case 4:
                               
                               System.out.println(">:Unesite sledece parametre: ");
                               System.out.print(">:Id Komitenta: ");
                                 int idkom = scaner.nextInt();
                                 System.out.print(">:Id Mesta: ");
                                 int idmes = scaner.nextInt();
                                  Response orderKM =  client
                                  .target("http://localhost:8080/Server/resources/komitenti")
                                  .path("{idKom}/{idMes}")
                                  .resolveTemplate("idKom", idkom)
                                  .resolveTemplate("idMes", idmes)
                                  .request()
                                  .put(Entity.text(""));
                                 System.out.println(orderKM);   
                         break;
                           case 5:
                               System.out.println(">:Unesite sledece parametre: ");
                                 System.out.print(">:Id Komitenta: ");
                                 int idkomR = scaner.nextInt();
                                 System.out.print(">:Id Filijale: ");
                                 int idfiliR = scaner.nextInt();
                                  System.out.print(">: Status: ");
                                  String sta= scaner.nextLine();
                                   System.out.print(">: BrojStavke: ");
                                   int brs = scaner.nextInt();
                                    System.out.print(">: DozMinus: ");
                                    int dm = scaner.nextInt();
                                     System.out.print(">: Stanje: ");
                                     int stR = scaner.nextInt();
                                     
                                     Komitent kR=client
                                  .target("http://localhost:8080/Server/resources/komitenti")
                                 .path("{idKom}")
                                  .resolveTemplate("idKom", idkomR)
                                  .request()
                                  .get(Komitent.class);  
                                     
                                    Filijala fR= client
                                  .target("http://localhost:8080/Server/resources/filijale")
                                 .path("{IdFil}")
                                  .resolveTemplate("IdFil", idfiliR)
                                  .request()
                                  .get(Filijala.class);  
                                     Racun r = new Racun();
                                     r.setIdKom(kR);
                                     r.setIdFil(fR);
                                     r.setStatus(sta.charAt(0));
                                     r.setBrojStavki(brs);
                                     r.setDozvMinus(dm);
                                     r.setStanje(stR);
                                  Response orderR =  client
                                  .target("http://localhost:8080/Server/resources/racuni")
                                  .request()
                                  .post(Entity.json(r));
                             System.out.println(orderR);

                         break;

                           case 6:
                               
                                System.out.println(">:Unesite sledece parametre: ");
                                 System.out.print(">:Id Racuna za brisanje: ");
                                 int idracb = scaner.nextInt();
                                Response orderRD =  client
                                  .target("http://localhost:8080/Server/resources/racuni")
                                        .path("{IdRac}")
                                        .resolveTemplate("IdRac", idracb)
                                        .request()
                                        .delete();
                               System.out.println(orderRD);
                               
                         break;
                           case 7:
                                  System.out.println(">:Unesite sledece parametre: ");
                                  System.out.print(">:Id RacunaSa: ");
                                  int idrsa = scaner.nextInt();
                                  System.out.print(">:Id RacunaNa: ");
                                      int idrna = scaner.nextInt();
                                  System.out.print(">:Iznos: ");
                                  int iznos = scaner.nextInt();
                                  
                                  Response orderSANA = client
                                           .target("http://localhost:8080/Server/resources/racuni")
                                        .path("prenos/{idRacSa}/{idRacNa}/{iznos}")
                                        .resolveTemplate("idRacSa", idrsa)
                                          .resolveTemplate("idRacNa", idrna)
                                          .resolveTemplate("iznos", iznos)
                                        .request()
                                        .post(Entity.text(""));
                                   System.out.println(orderSANA);
                         break;
                           case 8:
                               System.out.println(">:Unesite sledece parametre: ");
                                  System.out.print(">:Id RacunaNa: ");
                                      int idrna2 = scaner.nextInt();
                                  System.out.print(">:Iznos: ");
                                  int iznos2 = scaner.nextInt();
                               
                                   Response orderSANA2 = client
                                           .target("http://localhost:8080/Server/resources/racuni")
                                        .path("uplataNovca/{idRac}/{iznos}")
                                          .resolveTemplate("idRac", idrna2)
                                          .resolveTemplate("iznos", iznos2)
                                        .request()
                                        .post(Entity.text(""));
                                   System.out.println(orderSANA2);
                                  
                                  
                               
                         break;
                           case 9:
                                  System.out.println(">:Unesite sledece parametre: ");
                                  System.out.print(">:Id RacunaNa: ");
                                      int idrsa2 = scaner.nextInt();
                                  System.out.print(">:Iznos: ");
                                  int iznos22 = scaner.nextInt();
                               
                                   Response orderSANA22 = client
                                           .target("http://localhost:8080/Server/resources/racuni")
                                        .path("isplataNovca/{idRac}/{iznos}")
                                          .resolveTemplate("idRac", idrsa2)
                                          .resolveTemplate("iznos", iznos22)
                                        .request()
                                        .post(Entity.text(""));
                                   System.out.println(orderSANA22);
  
                         break;
                           case 10:
                               
                               
                              List<Mesto>lm = client
                                  .target("http://localhost:8080/Server/resources/mesta")
                                 .path("svaMesta")
                                  .request()
                                       //new GenericType<List<Mesto>>(){}
                                  .get(new GenericType<List<Mesto>>(){});
                                   
                                            for (Mesto mesto : lm) {
                                                  System.out.print("Mesto: ");
                                            System.out.println(mesto.getIdMes()+" "+mesto.getPostBr()+" "+mesto.getNaziv());
                                          }
                                
                                    
                         break;
                           case 11:
                               
                               List<Filijala> lf = client
                                  .target("http://localhost:8080/Server/resources/filijale")
                                 .path("sveFili")
                                  .request()
                                  .get(new GenericType<List<Filijala>>(){});
                               
                                   for (Filijala fili : lf) {
                                       System.out.print("Filijala: ");
                                   System.out.println(fili.getIdFil()+" "+fili.getAdresa()+" "+fili.getNaziv());
                                   for(int i =0;i<3;i++)
                                    System.out.println("|");
                                   System.out.print("-->");
                                    System.out.println("Mesto: ");
                                   System.out.println("        "+"idMes "+fili.getIdMes().getIdMes());
                                   System.out.println("        "+"postanskiBroj "+fili.getIdMes().getPostBr());
                                   System.out.println("        "+"naziv "+fili.getIdMes().getNaziv());
                                 }
                               
                               
                               
                         break;
                           case 12:
                               
                               List<Komitent> lk = client
                                  .target("http://localhost:8080/Server/resources/komitenti")
                                 .path("sviKomit")
                                  .request()
                                  .get(new GenericType<List<Komitent>>(){});
                               
                                   for (Komitent komi : lk) {
                                       System.out.print("Komitent: ");
                                       System.out.println(komi.getIdKom()+" "+komi.getAdresa()+" "+komi.getNaziv());
                                 }
   
                         break;
                           case 13:
                               
                               System.out.println(">:Unseite sledeeci parametar: ");
                                System.out.print(">:Id Komitenta: ");
                                 int idkomRac=scaner.nextInt();
                                List<Racun> lr = client
                                  .target("http://localhost:8080/Server/resources/racuni")
                                    .path("sviRacuni/{idKomi}")
                                  .resolveTemplate("idKomi", idkomRac)
                                  .request()
                                  .get(new GenericType<List<Racun>>(){});
                                
                                System.out.println();
                                   for (Racun rac : lr) {
                                       System.out.print("Racun: ");
                                       System.out.println(rac.getIdRac()+" "+rac.getStatus()+" "+rac.getBrojStavki()+" "+rac.getDozvMinus()+" "+rac.getStanje());
                                       for(int i=0;i<3;i++)
                                       System.out.println("|");
                                        System.out.print("--->");
                                         System.out.print("Filijala: ");
                                         System.out.println(rac.getIdFil().getIdFil()+" "+rac.getIdFil().getAdresa()+" "+rac.getIdFil().getNaziv());
                                         for(int i=0;i<3;i++)
                                        System.out.println("|             |");
                                         System.out.print("|              --->");
                                         System.out.print("Mesto: ");
                                         System.out.println(rac.getIdFil().getIdMes().getIdMes()+" "+rac.getIdFil().getIdMes().getPostBr()+" "+ rac.getIdFil().getIdMes().getNaziv());
                                          for(int i=0;i<3;i++)
                                       System.out.println("|");
                                        System.out.print("--->");
                                         System.out.print("Komitent: ");
                                          System.out.println(rac.getIdKom().getIdKom()+" "+rac.getIdKom().getAdresa()+" "+rac.getIdKom().getNaziv());
                                   }
                               
                         break;
                           case 14:
                               System.out.println(">:Unseite sledeeci parametar: ");
                                System.out.print(">:Id Racuna: ");
                                 int idRacSt=scaner.nextInt();
                                List<Stavka> ls = client
                                  .target("http://localhost:8080/Server/resources/racuni")
                                    .path("sveTranzakcije/{idrac}")
                                  .resolveTemplate("idrac", idRacSt)
                                  .request()
                                  .get(new GenericType<List<Stavka>>(){});
                                
                                for (Stavka l : ls) {
                                     System.out.print("Stavka: ");
                                     System.out.println(l.getIdSta()+" "+l.getDatum()+" "+l.getIznos()+" "+l.getRedBroj()+" "+l.getVreme());
                               }
                         break;
                           case 15:
                                
                               wrapperEntity wpe= client
                                  .target("http://localhost:8080/Server/resources/BackUp")
                                       .path("getBackUp")
                                       .request()
                                       .get(wrapperEntity.class);
                               
                               if (wpe==null)break;
                                for (Mesto mwp : wpe.getListaMesta()) {
                                   System.out.print("Mesto: ");
                                   System.out.println(mwp.getIdMes()+" "+mwp.getPostBr()+" "+mwp.getNaziv());
                               }
                               for (Komitent kwp : wpe.getListaKom()) {
                                   System.out.print("Komitent: ");
                                       System.out.println(kwp.getIdKom()+" "+kwp.getAdresa()+" "+kwp.getNaziv());
                               }
                               
                               for (Filijala fili : wpe.getListaFili()) {
                                       System.out.print("Filijala: ");
                                   System.out.println(fili.getIdFil()+" "+fili.getAdresa()+" "+fili.getNaziv());
                                   for(int i =0;i<3;i++)
                                    System.out.println("|");
                                   System.out.print("-->");
                                    System.out.println("Mesto: ");
                                   System.out.println("        "+"idMes "+fili.getIdMes().getIdMes());
                                   System.out.println("        "+"postanskiBroj "+fili.getIdMes().getPostBr());
                                   System.out.println("        "+"naziv "+fili.getIdMes().getNaziv());
                                 }
                               
                               for (Racun rac : wpe.getListaRac()) {
                                       System.out.print("Racun: ");
                                       System.out.println(rac.getIdRac()+" "+rac.getStatus()+" "+rac.getBrojStavki()+" "+rac.getDozvMinus()+" "+rac.getStanje());
                                       for(int i=0;i<3;i++)
                                       System.out.println("|");
                                        System.out.print("--->");
                                         System.out.print("Filijala: ");
                                         System.out.println(rac.getIdFil().getIdFil()+" "+rac.getIdFil().getAdresa()+" "+rac.getIdFil().getNaziv());
                                         for(int i=0;i<3;i++)
                                        System.out.println("|             |");
                                         System.out.print("|              --->");
                                         System.out.print("Mesto: ");
                                         System.out.println(rac.getIdFil().getIdMes().getIdMes()+" "+rac.getIdFil().getIdMes().getPostBr()+" "+ rac.getIdFil().getIdMes().getNaziv());
                                          for(int i=0;i<3;i++)
                                       System.out.println("|");
                                        System.out.print("--->");
                                         System.out.print("Komitent: ");
                                          System.out.println(rac.getIdKom().getIdKom()+" "+rac.getIdKom().getAdresa()+" "+rac.getIdKom().getNaziv());
                                   }
                               
                               
                               for (Stavka l : wpe.getListaStavka()) {
                                     System.out.print("Stavka: ");
                                     System.out.println(l.getIdSta()+" "+l.getDatum()+" "+l.getIznos()+" "+l.getRedBroj()+" "+l.getVreme());
                               }
                               
                               for(Uplata u : wpe.getListaUpla()){
                               System.out.print("Uplate: ");
                                   System.out.println(u.getIdSta()+" "+u.getOsnov());
                               }
                               
                               for(Isplata is : wpe.getListaIspla()){
                               System.out.print("Isplata: ");
                                   System.out.println(is.getIdSta()+" "+is.getProvizija());
                               }
     
                         break;
                           case 16:
                               
                                wrapperEntity wpe2= client
                                  .target("http://localhost:8080/Server/resources/BackUp")
                                       .path("compare")
                                       .request()
                                       .get(wrapperEntity.class);
                               
                               if (wpe2==null)break;
                                for (Mesto mwp : wpe2.getListaMesta()) {
                                   System.out.print("Mesto: ");
                                   System.out.println(mwp.getIdMes()+" "+mwp.getPostBr()+" "+mwp.getNaziv());
                               }
                               for (Komitent kwp : wpe2.getListaKom()) {
                                   System.out.print("Komitent: ");
                                       System.out.println(kwp.getIdKom()+" "+kwp.getAdresa()+" "+kwp.getNaziv());
                               }
                               
                               for (Filijala fili : wpe2.getListaFili()) {
                                       System.out.print("Filijala: ");
                                   System.out.println(fili.getIdFil()+" "+fili.getAdresa()+" "+fili.getNaziv());
                                   for(int i =0;i<3;i++)
                                    System.out.println("|");
                                   System.out.print("-->");
                                    System.out.println("Mesto: ");
                                   System.out.println("        "+"idMes "+fili.getIdMes().getIdMes());
                                   System.out.println("        "+"postanskiBroj "+fili.getIdMes().getPostBr());
                                   System.out.println("        "+"naziv "+fili.getIdMes().getNaziv());
                                 }
                               
                               for (Racun rac : wpe2.getListaRac()) {
                                       System.out.print("Racun: ");
                                       System.out.println(rac.getIdRac()+" "+rac.getStatus()+" "+rac.getBrojStavki()+" "+rac.getDozvMinus()+" "+rac.getStanje());
                                       for(int i=0;i<3;i++)
                                       System.out.println("|");
                                        System.out.print("--->");
                                         System.out.print("Filijala: ");
                                         System.out.println(rac.getIdFil().getIdFil()+" "+rac.getIdFil().getAdresa()+" "+rac.getIdFil().getNaziv());
                                         for(int i=0;i<3;i++)
                                        System.out.println("|             |");
                                         System.out.print("|              --->");
                                         System.out.print("Mesto: ");
                                         System.out.println(rac.getIdFil().getIdMes().getIdMes()+" "+rac.getIdFil().getIdMes().getPostBr()+" "+ rac.getIdFil().getIdMes().getNaziv());
                                          for(int i=0;i<3;i++)
                                       System.out.println("|");
                                        System.out.print("--->");
                                         System.out.print("Komitent: ");
                                          System.out.println(rac.getIdKom().getIdKom()+" "+rac.getIdKom().getAdresa()+" "+rac.getIdKom().getNaziv());
                                   }
                               
                               
                               for (Stavka l : wpe2.getListaStavka()) {
                                     System.out.print("Stavka: ");
                                     System.out.println(l.getIdSta()+" "+l.getDatum()+" "+l.getIznos()+" "+l.getRedBroj()+" "+l.getVreme());
                               }
                               
                               for(Uplata u : wpe2.getListaUpla()){
                               System.out.print("Uplate: ");
                                   System.out.println(u.getIdSta()+" "+u.getOsnov());
                               }
                               
                               for(Isplata is : wpe2.getListaIspla()){
                               System.out.print("Isplata: ");
                                   System.out.println(is.getIdSta()+" "+is.getProvizija());
                               }
     
                               
                               
                               
                               
                         break;
                           case 17:
                               whileState=false;
                         break;
                     }

            }
        



       }

}
