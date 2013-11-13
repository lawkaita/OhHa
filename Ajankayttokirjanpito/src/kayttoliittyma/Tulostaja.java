/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

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

    public Tulostaja(Kayttoliittyma kali) {
        //laitetaan ohjelmalle suoraan konsoli.
        this.kali = kali;
        this.tika = new Tiedostonkasittelija();
    }

    public Kayttoliittyma getKali() {
        return this.kali;
    }

    public void sanoMikaAikaNytOn() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        tulostaKonsoliin(ft.format(dNow));
    }

    public String annaTamaPaiva() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("dd.MM.YYYY");

        return ft.format(dNow);
    }

    public String annaTamaAika() {
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
    
    
    //tämä voisi olla jonkun muun tehtävä kuin tulostajan. Käyttöliittymän tehtävä kuitenkin.
    public void tapaKali() {
        this.kali.tapa();
    }

    public void haku(String komento) {
        //tämä tulisi hoitaa ohjelma -luokassa.
            String[] osumat = tika.haeTietoKannasta(komento);

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

    public void lisaaTiedostoonMuistettavaString(String muistettavaString) {
        try {
            tika.kirjoitaTietokantaanLisaten(muistettavaString,true);
        } catch (IOException ex) {
            //mitä tähän tulisi lisätä?
        }
    }

    public void tulostaEiOlePaiva() {
        tulostaKonsoliin("Ei ole päivä");
    }

    public void nollaaTietoKanta() {
        try {        
            tika.nollaaTiedosto();
        } catch (IOException ex) {
            //
        }
    }

    public void ilmoitaNollaamisesta() {
        tulostaKonsoliin("Muisti nollattu");
    }

}