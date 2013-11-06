/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import kayttoliittyma.Kayttoliittyma;

/**
 *
 * @author lawkaita
 */
public class Ohjelma {

    private Kayttoliittyma kali;

    public Ohjelma(Kayttoliittyma kali) {
        this.kali = kali;
    }

    public Kayttoliittyma getKali() {
        return this.kali;
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
            
            if(!lukija.hasNext()) {
                tulostaKonsoliin("Ei merkintöjä");
            }

            while (lukija.hasNext()) {
                tulostaKonsoliin(lukija.nextLine());
            }
            
            tulostaKonsoliin("::::");
        } catch (FileNotFoundException ex) {
            tulostaKonsoliin("Tiedostoa ei löydy");
        }
    }

    public void tulostaVirhe() {
        tulostaKonsoliin("Ei ole komento");
    }

    public void tulostaKonsoliin(String s) {
        kali.getKonsoli().tulostaViesti(s);
    }
}
