/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import Konsoli.Konsoli;
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

    private Kayttoliittyma kali;

    public Komentotulkki(Kayttoliittyma kali) {
        this.kali = kali;
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

        tulostaVirhe();
    }

    public void tapaOhjelma() {
        kali.tapa();
    }
    
    public void tulostaKonsoliin(String s) {
        kali.getKonsoli().tulostaViesti(s);
    }

    public void tulostaVirhe() {
        tulostaKonsoliin("Ei ole komento");
    }

    public void nyt() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        tulostaKonsoliin(ft.format(dNow));
    }

    public void apua() {
        String[] apua = {"exit - poistu",
                         "nyt  - tämänhetkinen aika",
                         "apua - tämä tuloste"};
        
        for (String s : apua) {
            tulostaKonsoliin(s);
        }
    }
    
    public void tulostaTiedosto() {
        try {
            Scanner lukija = new Scanner(new File("kirjaukset.txt"));
            
            while (lukija.hasNext()) {
                tulostaKonsoliin(lukija.nextLine());
            }
        } catch (FileNotFoundException ex) {
            tulostaKonsoliin("Tiedostoa ei löydy");
        }
    }
    
}
