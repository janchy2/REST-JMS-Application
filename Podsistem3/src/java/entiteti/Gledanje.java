/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jana
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIDGle", query = "SELECT g FROM Gledanje g WHERE g.iDGle = :iDGle"),
    @NamedQuery(name = "Gledanje.findByIDKor", query = "SELECT g FROM Gledanje g WHERE g.iDKor = :iDKor"),
    @NamedQuery(name = "Gledanje.findByIDVid", query = "SELECT g FROM Gledanje g WHERE g.iDVid = :iDVid"),
    @NamedQuery(name = "Gledanje.findByDatumVreme", query = "SELECT g FROM Gledanje g WHERE g.datumVreme = :datumVreme"),
    @NamedQuery(name = "Gledanje.findBySekund", query = "SELECT g FROM Gledanje g WHERE g.sekund = :sekund"),
    @NamedQuery(name = "Gledanje.findByBrSekundi", query = "SELECT g FROM Gledanje g WHERE g.brSekundi = :brSekundi")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGle")
    private Integer iDGle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVid")
    private int iDVid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Sekund")
    private int sekund;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrSekundi")
    private int brSekundi;

    public Gledanje() {
    }

    public Gledanje(Integer iDGle) {
        this.iDGle = iDGle;
    }

    public Gledanje(Integer iDGle, int iDKor, int iDVid, Date datumVreme, int sekund, int brSekundi) {
        this.iDGle = iDGle;
        this.iDKor = iDKor;
        this.iDVid = iDVid;
        this.datumVreme = datumVreme;
        this.sekund = sekund;
        this.brSekundi = brSekundi;
    }
    
    public Gledanje(int iDKor, int iDVid, Date datumVreme, int sekund, int brSekundi) {
        this.iDKor = iDKor;
        this.iDVid = iDVid;
        this.datumVreme = datumVreme;
        this.sekund = sekund;
        this.brSekundi = brSekundi;
    }

    public Integer getIDGle() {
        return iDGle;
    }

    public void setIDGle(Integer iDGle) {
        this.iDGle = iDGle;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    public int getIDVid() {
        return iDVid;
    }

    public void setIDVid(int iDVid) {
        this.iDVid = iDVid;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public int getSekund() {
        return sekund;
    }

    public void setSekund(int sekund) {
        this.sekund = sekund;
    }

    public int getBrSekundi() {
        return brSekundi;
    }

    public void setBrSekundi(int brSekundi) {
        this.brSekundi = brSekundi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGle != null ? iDGle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.iDGle == null && other.iDGle != null) || (this.iDGle != null && !this.iDGle.equals(other.iDGle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Gledanje[ iDGle=" + iDGle + " ]";
    }
    
}
