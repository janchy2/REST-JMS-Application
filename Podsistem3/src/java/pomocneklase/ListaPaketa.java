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
public class ListaPaketa implements Serializable {
    private List<PaketP> paketi;

    public ListaPaketa(List<PaketP> paketi) {
        this.paketi = paketi;
    }

    @Override
    public String toString() {
        return "Paketi: " + paketi;
    }
    
}
