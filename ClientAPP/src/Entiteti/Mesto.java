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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misko
 */
@Entity
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdMes", query = "SELECT m FROM Mesto m WHERE m.idMes = :idMes"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv"),
    @NamedQuery(name = "Mesto.findByPostBr", query = "SELECT m FROM Mesto m WHERE m.postBr = :postBr")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdMes")
    private Integer idMes;
    @Size(max = 255)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 255)
    @Column(name = "PostBr")
    private String postBr;
    @OneToMany(mappedBy = "idMes")
    private Collection<Filijala> filijalaCollection;
    @OneToMany(mappedBy = "idMes")
    private Collection<Imasediste> imasedisteCollection;

    public Mesto() {
    }

    public Mesto(Integer idMes) {
        this.idMes = idMes;
    }

    public Integer getIdMes() {
        return idMes;
    }

    public void setIdMes(Integer idMes) {
        this.idMes = idMes;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPostBr() {
        return postBr;
    }

    public void setPostBr(String postBr) {
        this.postBr = postBr;
    }

    @XmlTransient
    public Collection<Filijala> getFilijalaCollection() {
        return filijalaCollection;
    }

    public void setFilijalaCollection(Collection<Filijala> filijalaCollection) {
        this.filijalaCollection = filijalaCollection;
    }

    @XmlTransient
    public Collection<Imasediste> getImasedisteCollection() {
        return imasedisteCollection;
    }

    public void setImasedisteCollection(Collection<Imasediste> imasedisteCollection) {
        this.imasedisteCollection = imasedisteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMes != null ? idMes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idMes == null && other.idMes != null) || (this.idMes != null && !this.idMes.equals(other.idMes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Mesto[ idMes=" + idMes + " ]";
    }
    
}
