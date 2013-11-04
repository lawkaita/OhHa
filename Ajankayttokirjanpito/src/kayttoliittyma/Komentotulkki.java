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
    private Kayttoliittyma kali;
    
    public Komentotulkki(Kayttoliittyma kali) {
        this.kali = kali;
    }

    public void enter(String komento) {
        tulkitse(komento);        
    }
    
    public void v() {
        //konsoli.tulostaViesti();
    }
    
    public Konsoli getKonsoli(){
       return null;
        // return this.konsoli;
    }
    
    public void tulkitse(String komento) {
        if (komento.equals("exit")) {
            tapaOhjelma();
        }
    }
    
    public void tapaOhjelma() {
        kali.tapa();
    }
    
    
}
