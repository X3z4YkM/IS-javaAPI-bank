package Entiteti;

import Entiteti.Filijala;
import Entiteti.Imasediste;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-02-25T20:11:00")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, String> postBr;
    public static volatile SingularAttribute<Mesto, Integer> idMes;
    public static volatile CollectionAttribute<Mesto, Imasediste> imasedisteCollection;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile CollectionAttribute<Mesto, Filijala> filijalaCollection;

}