/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WraperPodsis1;

import Entiteti.Filijala;
import Entiteti.Imasediste;
import Entiteti.Komitent;
import Entiteti.Mesto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author misko
 */
public class WrapperP1 implements Serializable{
    List<Mesto> listaMesta = new ArrayList<>();
    List<Filijala> listaFili = new ArrayList<>();
    List<Komitent> listaKom = new ArrayList<>();
     List<Imasediste> listaIMA = new ArrayList<>();
     public WrapperP1(){}

    public List<Mesto> getListaMesta() {
        return listaMesta;
    }

    public void setListaMesta(List<Mesto> listaMesta) {
        this.listaMesta = listaMesta;
    }

    public List<Filijala> getListaFili() {
        return listaFili;
    }

    public void setListaFili(List<Filijala> listaFili) {
        this.listaFili = listaFili;
    }

    public List<Komitent> getListaKom() {
        return listaKom;
    }

    public void setListaKom(List<Komitent> listaKom) {
        this.listaKom = listaKom;
    }

    public List<Imasediste> getListaIMA() {
        return listaIMA;
    }

    public void setListaIMA(List<Imasediste> listaIMA) {
        this.listaIMA = listaIMA;
    }
     
    
    
}
