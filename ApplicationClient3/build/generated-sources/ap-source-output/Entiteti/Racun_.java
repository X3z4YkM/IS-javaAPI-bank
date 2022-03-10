package Entiteti;

import Entiteti.Filijala;
import Entiteti.Komitent;
import Entiteti.Stavka;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-25T20:19:03")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, Integer> stanje;
    public static volatile SingularAttribute<Racun, Filijala> idFil;
    public static volatile SingularAttribute<Racun, Komitent> idKom;
    public static volatile SingularAttribute<Racun, Integer> dozvMinus;
    public static volatile CollectionAttribute<Racun, Stavka> stavkaCollection;
    public static volatile SingularAttribute<Racun, Integer> idRac;
    public static volatile SingularAttribute<Racun, Integer> brojStavki;
    public static volatile SingularAttribute<Racun, Character> status;

}