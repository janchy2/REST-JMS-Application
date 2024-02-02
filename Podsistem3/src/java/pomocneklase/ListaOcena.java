/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomocneklase;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jana
 */
public class ListaOcena implements Serializable {
    private List<OcenaP> ocene;

    public ListaOcena(List<OcenaP> ocene) {
        this.ocene = ocene;
    }

    @Override
    public String toString() {
        return "Ocene: " + ocene;
    }
}
