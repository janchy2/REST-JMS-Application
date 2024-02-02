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
public class GledanjeP implements Serializable {
    private int IDGle;
    private int IDKor;
    private int IDVid;
    private Date datumVreme;
    private int sekund;
    private int brSekundi;

    public GledanjeP(int IDGle, int IDKor, int IDVid, Date datumVreme, int sekund, int brSekundi) {
        this.IDGle = IDGle;
        this.IDKor = IDKor;
        this.IDVid = IDVid;
        this.datumVreme = datumVreme;
        this.sekund = sekund;
        this.brSekundi = brSekundi;
    }
    
    public GledanjeP(int IDKor, int IDVid, Date datumVreme, int sekund, int brSekundi) {
        this.IDKor = IDKor;
        this.IDVid = IDVid;
        this.datumVreme = datumVreme;
        this.sekund = sekund;
        this.brSekundi = brSekundi;
    }

    @Override
    public String toString() {
        return "IDGle=" + IDGle + ", IDKor=" + IDKor + ", IDVid=" + IDVid + ", datumVreme=" + datumVreme + ", sekund=" + sekund + ", brSekundi=" + brSekundi;
    }

    public int getIDGle() {
        return IDGle;
    }

    public int getIDKor() {
        return IDKor;
    }

    public int getIDVid() {
        return IDVid;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public int getSekund() {
        return sekund;
    }

    public int getBrSekundi() {
        return brSekundi;
    }
    
}
