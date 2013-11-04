/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import Konsoli.Konsoli;

/**
 *
 * @author Envy 6-1010
 */
public class Komentotulkki {
    private Konsoli konsoli;
    
    public Komentotulkki(Konsoli konsoli) {
        this.konsoli = konsoli;
    }

    public void enter() {
        konsoli.tulostaKayttajanKomento();
    }

    
    public void v() {
        konsoli.tulostaViesti();
    }
    
    public Konsoli getKonsoli(){
        return this.konsoli;
    }  
    
    
}
