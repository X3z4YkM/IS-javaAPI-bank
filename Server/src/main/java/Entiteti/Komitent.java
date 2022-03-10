/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entiteti;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misko
 */
@Entity
@Table(name = "komitent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Komitent.findAll", query = "SELECT k FROM Komitent k"),
    @NamedQuery(name = "Komitent.findByIdKom", query = "SELECT k FROM Komitent k WHERE k.idKom = :idKom"),
    @NamedQuery(name = "Komitent.findByAdresa", query = "SELECT k FROM Komitent k WHERE k.adresa = :adresa"),
    @NamedQuery(name = "Komitent.findByNaziv", query = "SELECT k FROM Komitent k WHERE k.naziv = :naziv")})
public class Komitent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdKom")
    private Integer idKom;
    @Size(max = 255)
    @Column(name = "Adresa")
    private String adresa;
    @Size(max = 255)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(mappedBy = "idKom")
    private Collection<Racun> racunCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "komitent")
    private Imasediste imasediste;

    public Komitent() {
    }

    public Komitent(Integer idKom) {
        this.idKom = idKom;
    }

    public Integer getIdKom() {
        return idKom;
    }

    public void setIdKom(Integer idKom) {
        this.idKom = idKom;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public Collection<Racun> getRacunCollection() {
        return racunCollection;
    }

    public void setRacunCollection(Collection<Racun> racunCollection) {
        this.racunCollection = racunCollection;
    }

    public Imasediste getImasediste() {
        return imasediste;
    }

    public void setImasediste(Imasediste imasediste) {
        this.imasediste = imasediste;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKom != null ? idKom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komitent)) {
            return false;
        }
        Komitent other = (Komitent) object;
        if ((this.idKom == null && other.idKom != null) || (this.idKom != null && !this.idKom.equals(other.idKom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Komitent[ idKom=" + idKom + " ]";
    }
    
}
