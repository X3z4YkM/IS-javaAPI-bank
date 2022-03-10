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
@Table(name = "isplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Isplata.findAll", query = "SELECT i FROM Isplata i"),
    @NamedQuery(name = "Isplata.findByIdSta", query = "SELECT i FROM Isplata i WHERE i.idSta = :idSta"),
    @NamedQuery(name = "Isplata.findByProvizija", query = "SELECT i FROM Isplata i WHERE i.provizija = :provizija")})
public class Isplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdSta")
    private Integer idSta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Provizija")
    private Double provizija;
    @JoinColumn(name = "IdSta", referencedColumnName = "IdSta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Stavka stavka;

    public Isplata() {
    }

    public Isplata(Integer idSta) {
        this.idSta = idSta;
    }

    public Integer getIdSta() {
        return idSta;
    }

    public void setIdSta(Integer idSta) {
        this.idSta = idSta;
    }

    public Double getProvizija() {
        return provizija;
    }

    public void setProvizija(Double provizija) {
        this.provizija = provizija;
    }

    public Stavka getStavka() {
        return stavka;
    }

    public void setStavka(Stavka stavka) {
        this.stavka = stavka;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSta != null ? idSta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Isplata)) {
            return false;
        }
        Isplata other = (Isplata) object;
        if ((this.idSta == null && other.idSta != null) || (this.idSta != null && !this.idSta.equals(other.idSta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Isplata[ idSta=" + idSta + " ]";
    }
    
}
