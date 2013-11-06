/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import Konsoli.Konsoli;
import ajankayttokirjanpito.Ohjelma;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Envy 6-1010
 */
public class Komentotulkki {

    private Ohjelma ohjelma;

    public Komentotulkki(Ohjelma ohjelma) {
        this.ohjelma = ohjelma;
    }

    public void enter(String komento) {
        tulkitse(komento);
    }

    public void tulkitse(String komento) {

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            tapaOhjelma();
        }

        if (komento.equals("nyt")) {
            nyt();
            return;
        }

        if (komento.equals("apua")) {
            apua();
            return;
        }
        
        if (komento.equals("tulosta")) {
            tulostaTiedosto();
            return;
        }
        
        if (komento.equals("looppi")) {
            
        }
        
        if (komento.equals("merk")) {
            
        }

        tulostaVirhe();
    }

    public void tapaOhjelma() {
        this.ohjelma.getKali().tapa();
    }
    

    public void tulostaVirhe() {
        ohjelma.tulostaVirhe();
    }

    public void nyt() {
        ohjelma.nyt();
    }

    public void apua() {
        ohjelma.apua();
    }
    
    public void tulostaTiedosto() {
        ohjelma.tulostaTiedosto();
    }
    
}
