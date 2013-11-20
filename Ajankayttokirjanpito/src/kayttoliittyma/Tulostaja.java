/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.AjanAntaja;
import tietokantasysteemi.Tiedostonkasittelija;
import sovelluslogiikka.Dekooderi;
import java.io.FileNotFoundException;
import kayttoliittyma.Kayttoliittyma;
import konsoli.Konsoli;

/**
 * Tulostajan vastuulla on tulostaa ohjelman vastauksia tai 
 * tietokannan hakutuloksia käyttäjälle.
 * @author lawkaita
 */
public class Tulostaja {

    private Konsoli konsoli;
    private Tiedostonkasittelija tika;
    private AjanAntaja ajan;
    private Dekooderi dekooderi;

    public Tulostaja(Konsoli konsoli, Tiedostonkasittelija tika, Dekooderi dekooderi) {
        //laitetaan ohjelmalle suoraan konsoli.
        this.konsoli = konsoli;
        this.tika = tika;
        this.ajan = new AjanAntaja();
        this.dekooderi = dekooderi;
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

    //tämä jonnekin muualle?
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

        } catch (FileNotFoundException ex) {
            tulostaKonsoliin("Tiedostoa ei löydy");
        }
    }

    public void tulostaVirhe() {
        tulostaKonsoliin("Ei ole komento");
    }

    public void tulostaKonsoliin(String s) {
        konsoli.tulostaViesti(s);
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
            konsoli.tulostaViesti(" " + z);
        }
    }

    public void kerroLisayksesta() {
        tulostaKonsoliin("Lisätty merkintä:");
    }

    public void pyydaHakusana() {
        tulostaKonsoliin("Anna hakusana:");
    }

    public void tulostaHaunOsumat(String[] osumat) {
        if (osumat == null) {
            tulostaKonsoliin("Ei osumia");
        } else {

            String osumatString = "";
            for (String osuma : osumat) {
                osumatString += osuma + "!";
            }
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

    public void tulostaMerkinnanPoistoOnnistui() {
        tulostaKonsoliin("Merkinnän poisto onnistui");
    }

    public void tulostaKeskeytettiin() {
        tulostaKonsoliin("Keskeytettiin");
    }
}