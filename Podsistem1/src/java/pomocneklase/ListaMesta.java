/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomocneklase;

import pomocneklase.MestoP;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jana
 */
public class ListaMesta implements Serializable {
    private List<MestoP> mesta;

    public ListaMesta(List<MestoP> mesta) {
        this.mesta = mesta;
    }

    @Override
    public String toString() {
        return "Mesta:" + mesta;
    }
}
