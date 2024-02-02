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
public class ListaGledanja implements Serializable {
    private List<GledanjeP> gledanja;

    public ListaGledanja(List<GledanjeP> gledanja) {
        this.gledanja = gledanja;
    }

    @Override
    public String toString() {
        return "Gledanja: " + gledanja;
    }
    
    
}
