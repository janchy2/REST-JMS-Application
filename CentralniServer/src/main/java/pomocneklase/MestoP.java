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
public class MestoP implements Serializable {
    private int IDMes;
    private String naziv;

    public MestoP(int IDMes, String naziv) {
        this.IDMes = IDMes;
        this.naziv = naziv;
    }
    
    @Override
    public String toString() {
        return "IDMes=" + IDMes + ", naziv=" + naziv;
    }
}
