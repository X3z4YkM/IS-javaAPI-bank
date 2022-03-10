/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misko
 */
@Entity
@Table(name = "imasediste")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imasediste.findAll", query = "SELECT i FROM Imasediste i"),
    @NamedQuery(name = "Imasediste.findByIdKom", query = "SELECT i FROM Imasediste i WHERE i.idKom = :idKom")})
public class Imasediste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKom")
    private Integer idKom;
    @JoinColumn(name = "IdKom", referencedColumnName = "IdKom", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Komitent komitent;
    @JoinColumn(name = "IdMes", referencedColumnName = "IdMes")
    @ManyToOne
    private Mesto idMes;

    public Imasediste() {
    }

    public Imasediste(Integer idKom) {
        this.idKom = idKom;
    }

    public Integer getIdKom() {
        return idKom;
    }

    public void setIdKom(Integer idKom) {
        this.idKom = idKom;
    }

    public Komitent getKomitent() {
        return komitent;
    }

    public void setKomitent(Komitent komitent) {
        this.komitent = komitent;
    }

    public Mesto getIdMes() {
        return idMes;
    }

    public void setIdMes(Mesto idMes) {
        this.idMes = idMes;
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
        if (!(object instanceof Imasediste)) {
            return false;
        }
        Imasediste other = (Imasediste) object;
        if ((this.idKom == null && other.idKom != null) || (this.idKom != null && !this.idKom.equals(other.idKom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Imasediste[ idKom=" + idKom + " ]";
    }
    
}
