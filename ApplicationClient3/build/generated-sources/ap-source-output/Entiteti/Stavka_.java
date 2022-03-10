package Entiteti;

import Entiteti.Filijala;
import Entiteti.Isplata;
import Entiteti.Racun;
import Entiteti.Uplata;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-25T20:19:03")
@StaticMetamodel(Stavka.class)
public class Stavka_ { 

    public static volatile SingularAttribute<Stavka, Date> datum;
    public static volatile SingularAttribute<Stavka, Isplata> isplata;
    public static volatile SingularAttribute<Stavka, Integer> iznos;
    public static volatile SingularAttribute<Stavka, Integer> idSta;
    public static volatile SingularAttribute<Stavka, Date> vreme;
    public static volatile SingularAttribute<Stavka, Filijala> idFil;
    public static volatile SingularAttribute<Stavka, Integer> redBroj;
    public static volatile SingularAttribute<Stavka, Uplata> uplata;
    public static volatile SingularAttribute<Stavka, Racun> idRac;

}