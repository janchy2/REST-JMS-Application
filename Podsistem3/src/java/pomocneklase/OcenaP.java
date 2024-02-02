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
public class OcenaP implements Serializable {
    private int IDOce;
    private int IDKor;
    private int IDVid;
    private int Vrednost;
    private Date datumVreme;

    public OcenaP(int IDOce, int IDKor, int IDVid, int Vrednost, Date datumVreme) {
        this.IDOce = IDOce;
        this.IDKor = IDKor;
        this.IDVid = IDVid;
        this.Vrednost = Vrednost;
        this.datumVreme = datumVreme;
    }
    
    public OcenaP(int IDKor, int IDVid, int Vrednost, Date datumVreme) {
        this.IDKor = IDKor;
        this.IDVid = IDVid;
        this.Vrednost = Vrednost;
        this.datumVreme = datumVreme;
    }

    @Override
    public String toString() {
        return "IDOce=" + IDOce + ", IDKor=" + IDKor + ", IDVid=" + IDVid + ", Vrednost=" + Vrednost + ", datumVreme=" + datumVreme;
    }

    public int getIDOce() {
        return IDOce;
    }

    public int getIDKor() {
        return IDKor;
    }

    public int getIDVid() {
        return IDVid;
    }

    public int getVrednost() {
        return Vrednost;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }
    
}
