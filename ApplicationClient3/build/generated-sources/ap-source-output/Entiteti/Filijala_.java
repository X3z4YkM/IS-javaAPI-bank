package Entiteti;

import Entiteti.Mesto;
import Entiteti.Racun;
import Entiteti.Stavka;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-25T20:19:03")
@StaticMetamodel(Filijala.class)
public class Filijala_ { 

    public static volatile SingularAttribute<Filijala, Mesto> idMes;
    public static volatile SingularAttribute<Filijala, Integer> idFil;
    public static volatile SingularAttribute<Filijala, String> adresa;
    public static volatile SingularAttribute<Filijala, String> naziv;
    public static volatile CollectionAttribute<Filijala, Stavka> stavkaCollection;
    public static volatile CollectionAttribute<Filijala, Racun> racunCollection;

}