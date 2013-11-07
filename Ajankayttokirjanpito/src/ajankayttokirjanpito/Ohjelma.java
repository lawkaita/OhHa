/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import Tietokantasysteemi.Tiedostonkasittelija;
import kayttoliittyma.Dekooderi;
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

    public String paiva() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("dd.MM.YYYY");

        return ft.format(dNow);
    }

    public String aika() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("HH.mm");

        return ft.format(dNow);
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
            Tiedostonkasittelija tika = new Tiedostonkasittelija();
            tika.alustaTietokannanLukija();

            if (!tika.lukijallaSeuraavaRivi()) {
                tulostaKonsoliin("Ei merkintöjä");
            }

            while (tika.lukijallaSeuraavaRivi()) {
                tulostaKonsoliin(tika.lukijanSeuraavaRivi());
            }

            tulostaKonsoliin("::::");
            
            tika.suljeLukija();
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

    public void pyydaPaivaa() {
        tulostaKonsoliin("Päiväys:");
    }

    public void pyydaAloitusAikaa() {
        tulostaKonsoliin("Aloitusaika:");
    }

    public void pyydaLopetusAikaa() {
        tulostaKonsoliin("Lopetusaika:");
    }

    public void pyydaSelostus() {
        tulostaKonsoliin("Selostus:");
    }

    public void listaaKonsoliin(String s) {
        Dekooderi d = new Dekooderi();
        String[] tulostettava = d.dekoodaa(s, "\n".charAt(0));

        for (String z : tulostettava) {
            kali.getKonsoli().tulostaViesti(" " + z);
        }
    }

    public void kerroLisayksesta() {
        tulostaKonsoliin("Lisätty merkintä:");
    }

    public void pyydaHakusana() {
        tulostaKonsoliin("Anna hakusana:");
    }
}