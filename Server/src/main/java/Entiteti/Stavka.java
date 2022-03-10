/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misko
 */
@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdSta", query = "SELECT s FROM Stavka s WHERE s.idSta = :idSta"),
    @NamedQuery(name = "Stavka.findByDatum", query = "SELECT s FROM Stavka s WHERE s.datum = :datum"),
    @NamedQuery(name = "Stavka.findByIznos", query = "SELECT s FROM Stavka s WHERE s.iznos = :iznos"),
    @NamedQuery(name = "Stavka.findByRedBroj", query = "SELECT s FROM Stavka s WHERE s.redBroj = :redBroj"),
    @NamedQuery(name = "Stavka.findByVreme", query = "SELECT s FROM Stavka s WHERE s.vreme = :vreme")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdSta")
    private Integer idSta;
    @Column(name = "Datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Column(name = "Iznos")
    private Integer iznos;
    @Column(name = "RedBroj")
    private Integer redBroj;
    @Column(name = "Vreme")
    @Temporal(TemporalType.TIME)
    private Date vreme;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "stavka")
    private Isplata isplata;
    @JoinColumn(name = "IdFil", referencedColumnName = "IdFil")
    @ManyToOne
    private Filijala idFil;
    @JoinColumn(name = "IdRac", referencedColumnName = "IdRac")
    @ManyToOne
    private Racun idRac;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "stavka")
    private Uplata uplata;

    public Stavka() {
    }

    public Stavka(Integer idSta) {
        this.idSta = idSta;
    }

    public Integer getIdSta() {
        return idSta;
    }

    public void setIdSta(Integer idSta) {
        this.idSta = idSta;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Integer getIznos() {
        return iznos;
    }

    public void setIznos(Integer iznos) {
        this.iznos = iznos;
    }

    public Integer getRedBroj() {
        return redBroj;
    }

    public void setRedBroj(Integer redBroj) {
        this.redBroj = redBroj;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public Isplata getIsplata() {
        return isplata;
    }

    public void setIsplata(Isplata isplata) {
        this.isplata = isplata;
    }

    public Filijala getIdFil() {
        return idFil;
    }

    public void setIdFil(Filijala idFil) {
        this.idFil = idFil;
    }

    public Racun getIdRac() {
        return idRac;
    }

    public void setIdRac(Racun idRac) {
        this.idRac = idRac;
    }

    public Uplata getUplata() {
        return uplata;
    }

    public void setUplata(Uplata uplata) {
        this.uplata = uplata;
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
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.idSta == null && other.idSta != null) || (this.idSta != null && !this.idSta.equals(other.idSta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entiteti.Stavka[ idSta=" + idSta + " ]";
    }
    
}
