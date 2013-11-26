/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.OmaAjanAntaja;
import tietokantasysteemi.OmaTiedostonkasittelija;
import sovelluslogiikka.Dekooderi;
import java.io.FileNotFoundException;
import java.util.Scanner;
import kayttoliittyma.Kayttoliittyma;
import konsoli.OmaKonsoli;
import tietokantasysteemi.Merkinta;

/**
 * Tulostajan vastuulla on tulostaa ohjelman vastauksia tai tietokannan
 * hakutuloksia käyttäjälle.
 *
 * @author lawkaita
 */
public class Tulostaja {

    private OmaKonsoli konsoli;
    private OmaTiedostonkasittelija tika;
    private Dekooderi dekooderi;

    public Tulostaja(OmaKonsoli konsoli, OmaTiedostonkasittelija tika, Dekooderi dekooderi) {
        this.konsoli = konsoli;
        this.tika = tika;
        this.dekooderi = dekooderi;
    }

    /**
     * Tulostaa ohjeita ohjelman käyttöön.
     */
    public void apua() {
        String[] apua = {"exit - poistu",
            "nyt - tämänhetkinen aika",
            "apua - tämä tuloste",
            "tulosta - tulostaa koko tietokannan",
            "hae - hakutoiminnon",
            "merk - aloittaa merkinnän luomisen",
            "nollaa - nollaa koko tietokannan",
            "poista - aloittaa yksittäisen merkinnän",
            "         poistotoiminnon"};

        for (String s : apua) {
            tulostaKonsoliin(s);
        }

    }

    /**
     * Tulostaa koko ohjelman tietokantana käytettävän tiedoston sisällön.
     */
    public void tulostaTietokantaTiedosto() {
        try {
            tika.alustaTietokannanLukija();

            if (!tika.lukijallaSeuraavaRivi()) {
                tulostaEiMerkintoja();
            }

            while (tika.lukijallaSeuraavaRivi()) {
                tulostaKonsoliin(tika.lukijanSeuraavaRivi());
            }

            tulostaKonsoliin("::::");

        } catch (FileNotFoundException ex) {
            tulostaKonsoliin("Tiedostoa ei löydy");
        }
    }
    
    public void tulostaValimuisti(String muistiString) {
        tulostaRivitettyString(muistiString);
    }
    
    private void tulostaRivitettyString(String rivitettyString) {
        Scanner lukija = new Scanner(rivitettyString);
        while (lukija.hasNextLine()) {
            tulostaKonsoliin(lukija.nextLine());
        }
        lukija.close();
    }

    public void tulostaVirhe() {
        tulostaKonsoliin("Ei ole komento");
    }

    /**
     * Tulostaa kayttoliittyman konsoliin viestin.
     *
     * @param s tulostettava viesti
     */
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

    /**
     * Listaa konsoliin '!'-merkillä jäsennellyn Stirngin
     *
     * @param s jäsennelty String.
     */
    public void listaaKonsoliin(String s) {
        String[] tulostettava = dekooderi.dekoodaa(s, '!');

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

    /**
     * antaa listaaKonsoliin metodille ohjelmassa tehdyn haun osumat
     * jäsenneltynä '!'-merkillä.
     *
     * @param osumat haun tuloksena saatu tekstitaulu.
     */
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

    public void ilmoitaValimuistinNollaamisesta() {
        tulostaKonsoliin("Välimuisti nollattu");
    }

    public void tulostaMerkinnanPoistoOnnistui() {
        tulostaKonsoliin("Merkinnän poisto onnistui");
    }

    public void tulostaKeskeytettiin() {
        tulostaKonsoliin("Keskeytettiin");
    }

    public void tulostaEiOleAika() {
        tulostaKonsoliin("Ei ole aika");
    }

    public void tulostaEiOsumia() {
        tulostaKonsoliin("Ei osumia");
    }

    public void tulostaEiMerkintoja() {
        tulostaKonsoliin("Ei merkintöjä");
    }

    public void tulostaLiianSuuriAika() {
        tulostaKonsoliin("Aloitusaika suurempi kuin lopetusaika");
    }

    public void tulostaIOException() {
        System.out.println("IOException");
    }

    public void tulostaHaunOsuma(Merkinta osuma) {
        tulostaRivitettyString(osuma.toString());
    }

    public void ilmoitaTallennuksenOnnistumisesta() {
        tulostaKonsoliin("Tallennus onnistui");
    }
    
}