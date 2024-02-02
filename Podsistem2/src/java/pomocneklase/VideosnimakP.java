/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomocneklase;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jana
 */
public class VideosnimakP implements Serializable {
    private int IDVid;
    private String naziv;
    private int trajanje;
    private int IDKor;
    private Date datumVreme;

    public VideosnimakP(int IDVid, String naziv, int trajanje, int IDKor, Date datumVreme) {
        this.IDVid = IDVid;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.IDKor = IDKor;
        this.datumVreme = datumVreme;
    }

    public VideosnimakP(String naziv, int trajanje, int IDKor, Date datumVreme) {
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.IDKor = IDKor;
        this.datumVreme = datumVreme;
    }

    public int getIDVid() {
        return IDVid;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public int getIDKor() {
        return IDKor;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    @Override
    public String toString() {
        return "IDVid=" + IDVid + ", naziv=" + naziv + ", trajanje=" + trajanje + ", IDKor=" + IDKor + ", datumVreme=" + datumVreme;
    }
    
    
}
