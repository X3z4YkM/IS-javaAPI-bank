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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misko
 */
@Entity
@Table(name = "uplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uplata.findAll", query = "SELECT u FROM Uplata u"),
    @NamedQuery(name = "Uplata.findByIdSta", query = "SELECT u FROM Uplata u WHERE u.idSta = :idSta"),
    @NamedQuery(name = "Uplata.findByOsnov", query = "SELECT u FROM Uplata u WHERE u.osnov = :osnov")})
public class Uplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdSta")
    private Integer idSta;
    @Size(max = 255)
    @Column(name = "Osnov")
    private String osnov;
    @JoinColumn(name = "IdSta", referencedColumnName = "IdSta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Stavka stavka;

    public Uplata() {
    }

    public Uplata(Integer idSta) {
        this.idSta = idSta;
    }

    public Integer getIdSta() {
        return idSta;
    }

    public void setIdSta(Integer idSta) {
        this.idSta = idSta;
    }

    public String getOsnov() {
        return osnov;
    }

    public void setOsnov(String osnov) {
        this.osnov = osnov;
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
        if (!(object instanceof Uplata)) {
            return false;
        }
        Uplata other = (Uplata) object;
        if ((this.idSta == null && other.idSta != null) || (this.idSta != null && !this.idSta.equals(other.idSta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Uplata[ idSta=" + idSta + " ]";
    }
    
}
