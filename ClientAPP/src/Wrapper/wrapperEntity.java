/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wrapper;

import java.io.Serializable;
import Entiteti.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author misko
 */
public class wrapperEntity implements Serializable{
     List<Mesto> listaMesta = new ArrayList<>();
    List<Filijala> listaFili = new ArrayList<>();
    List<Komitent> listaKom = new ArrayList<>();
    List<Racun> listaRac = new ArrayList<>();
    List<Uplata> listaUpla = new ArrayList<>();
    List<Isplata> listaIspla = new ArrayList<>();
    List<Stavka> listaStavka = new ArrayList<>();
    List<Imasediste> listaIMA = new ArrayList<>();
    
   public  wrapperEntity(){ 
    }

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

    public List<Imasediste> getListaIMA() {
        return listaIMA;
    }

    public void setListaIMA(List<Imasediste> listaIMA) {
        this.listaIMA = listaIMA;
    }

}
