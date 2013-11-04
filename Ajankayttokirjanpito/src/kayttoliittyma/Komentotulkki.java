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
     
    public void tulkitse(String komento) {
        if (komento.equals("exit")) {
            tapaOhjelma();
        } else {
            tulostaVirhe();
        }
    }
    
    public void tapaOhjelma() {
        kali.tapa();
    }

    private void tulostaVirhe() {
        kali.getKonsoli().tulostaViesti("Ei ole komento");
    }
    
    
}
