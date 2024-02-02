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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIDOcena", query = "SELECT o FROM Ocena o WHERE o.iDOcena = :iDOcena"),
    @NamedQuery(name = "Ocena.findByIDKor", query = "SELECT o FROM Ocena o WHERE o.iDKor = :iDKor"),
    @NamedQuery(name = "Ocena.findByIDVid", query = "SELECT o FROM Ocena o WHERE o.iDVid = :iDVid"),
    @NamedQuery(name = "Ocena.findByVrednost", query = "SELECT o FROM Ocena o WHERE o.vrednost = :vrednost"),
    @NamedQuery(name = "Ocena.findByDatumVreme", query = "SELECT o FROM Ocena o WHERE o.datumVreme = :datumVreme")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDOcena")
    private Integer iDOcena;
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
    @Column(name = "Vrednost")
    private int vrednost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;

    public Ocena() {
    }

    public Ocena(Integer iDOcena) {
        this.iDOcena = iDOcena;
    }

    public Ocena(Integer iDOcena, int iDKor, int iDVid, int vrednost, Date datumVreme) {
        this.iDOcena = iDOcena;
        this.iDKor = iDKor;
        this.iDVid = iDVid;
        this.vrednost = vrednost;
        this.datumVreme = datumVreme;
    }
    
    public Ocena(int iDKor, int iDVid, int vrednost, Date datumVreme) {
        this.iDKor = iDKor;
        this.iDVid = iDVid;
        this.vrednost = vrednost;
        this.datumVreme = datumVreme;
    }

    public Integer getIDOcena() {
        return iDOcena;
    }

    public void setIDOcena(Integer iDOcena) {
        this.iDOcena = iDOcena;
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

    public int getVrednost() {
        return vrednost;
    }

    public void setVrednost(int vrednost) {
        this.vrednost = vrednost;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDOcena != null ? iDOcena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.iDOcena == null && other.iDOcena != null) || (this.iDOcena != null && !this.iDOcena.equals(other.iDOcena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Ocena[ iDOcena=" + iDOcena + " ]";
    }
    
}
