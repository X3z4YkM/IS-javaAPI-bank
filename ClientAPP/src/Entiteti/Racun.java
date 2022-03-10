/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entiteti;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misko
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdRac", query = "SELECT r FROM Racun r WHERE r.idRac = :idRac"),
    @NamedQuery(name = "Racun.findByBrojStavki", query = "SELECT r FROM Racun r WHERE r.brojStavki = :brojStavki"),
    @NamedQuery(name = "Racun.findByDozvMinus", query = "SELECT r FROM Racun r WHERE r.dozvMinus = :dozvMinus"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdRac")
    private Integer idRac;
    @Column(name = "BrojStavki")
    private Integer brojStavki;
    @Column(name = "DozvMinus")
    private Integer dozvMinus;
    @Column(name = "Stanje")
    private Integer stanje;
    @Column(name = "Status")
    private Character status;
    @OneToMany(mappedBy = "idRac")
    private Collection<Stavka> stavkaCollection;
    @JoinColumn(name = "IdFil", referencedColumnName = "IdFil")
    @ManyToOne
    private Filijala idFil;
    @JoinColumn(name = "IdKom", referencedColumnName = "IdKom")
    @ManyToOne
    private Komitent idKom;

    public Racun() {
    }

    public Racun(Integer idRac) {
        this.idRac = idRac;
    }

    public Integer getIdRac() {
        return idRac;
    }

    public void setIdRac(Integer idRac) {
        this.idRac = idRac;
    }

    public Integer getBrojStavki() {
        return brojStavki;
    }

    public void setBrojStavki(Integer brojStavki) {
        this.brojStavki = brojStavki;
    }

    public Integer getDozvMinus() {
        return dozvMinus;
    }

    public void setDozvMinus(Integer dozvMinus) {
        this.dozvMinus = dozvMinus;
    }

    public Integer getStanje() {
        return stanje;
    }

    public void setStanje(Integer stanje) {
        this.stanje = stanje;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Stavka> getStavkaCollection() {
        return stavkaCollection;
    }

    public void setStavkaCollection(Collection<Stavka> stavkaCollection) {
        this.stavkaCollection = stavkaCollection;
    }

    public Filijala getIdFil() {
        return idFil;
    }

    public void setIdFil(Filijala idFil) {
        this.idFil = idFil;
    }

    public Komitent getIdKom() {
        return idKom;
    }

    public void setIdKom(Komitent idKom) {
        this.idKom = idKom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRac != null ? idRac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idRac == null && other.idRac != null) || (this.idRac != null && !this.idRac.equals(other.idRac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Racun[ idRac=" + idRac + " ]";
    }
    
}
