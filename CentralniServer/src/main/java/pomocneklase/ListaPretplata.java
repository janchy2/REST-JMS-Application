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
public class ListaPretplata implements Serializable {
    private List<PretplataP> pretplate;

    public ListaPretplata(List<PretplataP> pretplate) {
        this.pretplate = pretplate;
    }

    @Override
    public String toString() {
        return "Pretplate: " + pretplate;
    }
    
    
}
