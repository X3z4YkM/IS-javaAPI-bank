package Entiteti;

import Entiteti.Imasediste;
import Entiteti.Racun;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-25T20:11:00")
@StaticMetamodel(Komitent.class)
public class Komitent_ { 

    public static volatile SingularAttribute<Komitent, Integer> idKom;
    public static volatile SingularAttribute<Komitent, String> adresa;
    public static volatile SingularAttribute<Komitent, String> naziv;
    public static volatile CollectionAttribute<Komitent, Racun> racunCollection;
    public static volatile SingularAttribute<Komitent, Imasediste> imasediste;

}