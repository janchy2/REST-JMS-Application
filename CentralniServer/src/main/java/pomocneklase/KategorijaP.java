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
public class KategorijaP implements Serializable {
    private int IDKat;
    private String naziv;

    public KategorijaP(int IDKat, String naziv) {
        this.IDKat = IDKat;
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "IDKat=" + IDKat + ", naziv=" + naziv;
    }
    
    
}
