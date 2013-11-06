/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import ajankayttokirjanpito.Ohjelma;

/**
 *
 * @author Envy 6-1010
 */
public class Komentotulkki {
    private Ohjelma ohjelma;
    
    private boolean lisataanMerkintaa;

    public Komentotulkki(Ohjelma ohjelma) {
        this.ohjelma = ohjelma;
        
        this.lisataanMerkintaa = false;
    }

    public void enter(String komento) {
        tulkitse(komento);
    }

    public void tulkitse(String komento) {
        
        if (lisataanMerkintaa == true) {            
            lisataanMerkintaa = false;
        }

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            this.ohjelma.getKali().tapa();
        }
       
        if (komento.equals("nyt")) {
            ohjelma.nyt();
            return;
        }
        
        if (komento.equals("paiva")) {
            ohjelma.paiva();
            return;
        }

        if (komento.equals("apua")) {
            ohjelma.apua();
            return;
        }
        
        if (komento.equals("tulosta")) {
            ohjelma.tulostaTiedosto();
            return;
        }
        
        if (komento.equals("looppi")) {
            
        }
        
        if (komento.equals("merk")) {
            ohjelma.pyydaPaivaa();
            String paiva = ohjelma.paiva();
            ohjelma.getKali().getKonsoli().getKomentoRivi().setText(paiva);
            this.lisataanMerkintaa = true;
            return;
        }
        
        ohjelma.tulostaVirhe();
    }    
}
