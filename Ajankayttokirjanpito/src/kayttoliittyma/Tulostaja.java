/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.AjanAntaja;
import tietokantasysteemi.Tiedostonkasittelija;
import sovelluslogiikka.Dekooderi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import kayttoliittyma.Kayttoliittyma;

/**
 *
 * @author lawkaita
 */
public class Tulostaja {

    private Kayttoliittyma kali;
    private Tiedostonkasittelija tika;
    private AjanAntaja ajan;
    private Dekooderi dekooderi;

    public Tulostaja(Kayttoliittyma kali, Tiedostonkasittelija tika, AjanAntaja ajan, Dekooderi dekooderi) {
        //laitetaan ohjelmalle suoraan konsoli.
        this.kali = kali;
        this.tika = tika;
        this.ajan = ajan;
        this.dekooderi = dekooderi;
    }

    public Kayttoliittyma getKali() {
        return this.kali;
    }

    public void sanoMikaAikaNytOn() {
        tulostaKonsoliin(ajan.mikaAikaNytOn());
    }

    public void apua() {
        String[] apua = {"exit - poistu",
            "nyt  - tämänhetkinen aika",
            "apua - tämä tuloste"};

        for (String s : apua) {
            tulostaKonsoliin(s);
        }

    }
    
    //tämä jonnekin muualle
    public void tulostaTiedosto() {
        try {
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
        String[] tulostettava = dekooderi.dekoodaa(s, "!".charAt(0));

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

    public void tulostaHaunOsumat(String[] osumat) {
            String osumatString = "";

            for (String osuma : osumat) {
                osumatString += osuma;
            }

            if (osumatString.isEmpty()) {
                tulostaKonsoliin("Ei osumia");
            } else {
                listaaKonsoliin(osumatString);
            }
    }


    public void tulostaEiOlePaiva() {
        tulostaKonsoliin("Ei ole päivä");
    }

    public void ilmoitaNollaamisesta() {
        tulostaKonsoliin("Muisti nollattu");
    }

    public AjanAntaja getAjan() {
        return ajan;
    }

}