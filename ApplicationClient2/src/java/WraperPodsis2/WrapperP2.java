/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WraperPodsis2;

import Entiteti.Isplata;
import Entiteti.Mesto;
import Entiteti.Racun;
import Entiteti.Stavka;
import Entiteti.Uplata;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author misko
 */
public class WrapperP2 implements Serializable{
        List<Racun> listaRac= new ArrayList<>();
    List<Uplata> listaUpla= new ArrayList<>();
    List<Isplata> listaIspla= new ArrayList<>();
    List<Stavka> listaStavka= new ArrayList<>();
    
    public WrapperP2(){}

    public List<Racun> getListaRac() {
        return listaRac;
    }

    public void setListaRac(List<Racun> listaRac) {
        this.listaRac = listaRac;
    }

    public List<Uplata> getListaUpla() {
        return listaUpla;
    }

    public void setListaUpla(List<Uplata> listaUpla) {
        this.listaUpla = listaUpla;
    }

    public List<Isplata> getListaIspla() {
        return listaIspla;
    }

    public void setListaIspla(List<Isplata> listaIspla) {
        this.listaIspla = listaIspla;
    }

    public List<Stavka> getListaStavka() {
        return listaStavka;
    }

    public void setListaStavka(List<Stavka> listaStavka) {
        this.listaStavka = listaStavka;
    }
    
    
    
}
