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
public class ListaVidea implements Serializable {
    private List<VideosnimakP> snimci;

    public ListaVidea(List<VideosnimakP> snimci) {
        this.snimci = snimci;
    }

    @Override
    public String toString() {
        return "Video snimci: " + snimci;
    }
    
}
