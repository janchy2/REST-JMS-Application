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
public class ListaKorisnika implements Serializable {
    private List<KorisnikP> korisnici;

    public ListaKorisnika(List<KorisnikP> korisnici) {
        this.korisnici = korisnici;
    }

    @Override
    public String toString() {
        return "Korisnici: " + korisnici;
    }
    
    
}
