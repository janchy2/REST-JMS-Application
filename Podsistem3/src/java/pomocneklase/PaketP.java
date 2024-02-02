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
public class PaketP implements Serializable {
    private int IDPak;
    private int cena;

    public PaketP(int IDPak, int cena) {
        this.IDPak = IDPak;
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "IDPak=" + IDPak + ", cena=" + cena;
    }
}
