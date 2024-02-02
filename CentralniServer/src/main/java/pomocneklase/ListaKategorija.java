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
public class ListaKategorija implements Serializable {
    private List<KategorijaP> kategorije;

    public ListaKategorija(List<KategorijaP> kategorije) {
        this.kategorije = kategorije;
    }

    @Override
    public String toString() {
        return "Kategorije: " + kategorije;
    }
    
    
}
