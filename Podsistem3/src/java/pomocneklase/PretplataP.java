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
public class PretplataP implements Serializable {
    private int IDPre;
    private int IDKor;
    private int IDPak;
    private Date datumVreme;
    private int Cena;

    public PretplataP(int IDPre, int IDKor, int IDPak, Date datumVreme, int Cena) {
        this.IDPre = IDPre;
        this.IDKor = IDKor;
        this.IDPak = IDPak;
        this.datumVreme = datumVreme;
        this.Cena = Cena;
    }
    
    public PretplataP(int IDKor, int IDPak, Date datumVreme, int Cena) {
        this.IDKor = IDKor;
        this.IDPak = IDPak;
        this.datumVreme = datumVreme;
        this.Cena = Cena;
    }

    @Override
    public String toString() {
        return "IDPre=" + IDPre + ", IDKor=" + IDKor + ", IDPak=" + IDPak + ", datumVreme=" + datumVreme + ", Cena=" + Cena;
    }

    public int getIDPre() {
        return IDPre;
    }

    public int getIDKor() {
        return IDKor;
    }

    public int getIDPak() {
        return IDPak;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public int getCena() {
        return Cena;
    }
    
    
}
