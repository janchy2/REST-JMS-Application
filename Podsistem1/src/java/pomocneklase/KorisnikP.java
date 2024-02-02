/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomocneklase;

import java.io.Serializable;

/**
 *
 * @author Jana
 */
public class KorisnikP implements Serializable {
    private int IDKor;
    private String ime;
    private String email;
    private int godiste;
    private String pol;
    private int iDMes;

    public int getIDKor() {
        return IDKor;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public int getiDMes() {
        return iDMes;
    }

    public void setiDMes(int iDMes) {
        this.iDMes = iDMes;
    }

    public KorisnikP(String ime, String email, int godiste, String pol, int iDMes) {
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
        this.iDMes = iDMes;
    }

    public KorisnikP(int IDKor, String ime, String email, int godiste, String pol, int iDMes) {
        this.IDKor = IDKor;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
        this.iDMes = iDMes;
    }
    

    @Override
    public String toString() {
        return "IDKor=" + IDKor + ", ime=" + ime + ", email=" + email + ", godiste=" + godiste + ", pol=" + pol + ", iDMes=" + iDMes;
    }
    
    
}

