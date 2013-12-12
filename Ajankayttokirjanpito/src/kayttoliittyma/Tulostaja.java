/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.Dekooderi;
import java.util.Scanner;
import tietokantasysteemi.Merkinta;

/**
 * Tulostajan vastuulla on tulostaa ohjelman vastauksia tai tietokannan
 * hakutuloksia käyttäjälle.
 *
 * @author lawkaita
 */
public class Tulostaja {

    /**
     * Ohjelman käyttöliittymän pääkomponentti. Tulostaja tulostaa tekstiä tähän
     * komponenttiin.
     */
    private Konsoli konsoli;
    private Dekooderi dekooderi;

    public Tulostaja(Konsoli konsoli, Dekooderi dekooderi) {
        this.konsoli = konsoli;
        this.dekooderi = dekooderi;
    }

    /**
     * Tulostaa ohjeita ohjelman käyttöön.
     */
    public void apua() {
        String apua = "exit - poistu\n"
                + "nyt - tämänhetkinen aika\n"
                + "apua - tämä tuloste\n"
                + "apua <komento> - neuvoa komennon käytöstä\n"
                + "apua apua - neuvoa ohjelman käytössä\n"
                + "tulosta - tulostaa koko tietokannan\n"
                + "hae - hakutoiminnon aloitus\n"
                + "merkintä - aloittaa merkinnän luomisen\n"
                + "nollaa - nollaa välimuistin\n"
                + "poista - aloittaa merkinnän poistotoiminnon\n"
                + "tallenna - tallentaa välimuistin tiedostoon\n"
                + "yhteenveto - tekee annetuista tiedoista yhteenvedon \n"
                + "esc-näppäin - keskeytä käynnissäoleva tehtävä\n"
                + "page-up ja -down näppäimet - selaa dialogia\n";

        tulostaRivitettyString(apua);

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
        tulostaKonsoliin("Ei ole päivä:");
        tulostaKonsoliin("Anna päivä muodossa 'pp.kk.vvvv'");
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
        tulostaKonsoliin("Ei ole aika:");
        tulostaKonsoliin("Anna aika muodossa 'tt.mm'");
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

    public void otsikoiMerkinnanLuominen() {
        tulostaKonsoliin("Luodaan uusi merkintä");
    }

    public void otsikoiHaunAloitus() {
        tulostaKonsoliin("Aloitetaan haku");
    }

    public void neuvoLisaamisessa() {
        String neuvo = "Ohjelmalle annetaan ensin päiväys, jonka alle\n"
                + "merkintä tehdään. Ohjelma ehdottaa oletuksena\n"
                + "tämänhetkistä päivää. Päiväyksen jälkeen ohjelma\n"
                + "pyytää aloitus- ja lopetusajan. Ohjelma ehdottaa\n"
                + "lopetusajaksi tämänhetkistä kellonaikaa. Lopuksi\n"
                + "ohjelmalle syötetään lyhyt selostus siitä, mitä\n"
                + "tekemistä merkintä koski.";
        tulostaRivitettyString(neuvo);
    }
    
    public void neuvoOhjelmanKaytossa() {
        String neuvo = "Ohjelmaa käytetään pitämään ajankäytöstä kirjaa.\n"
                + "Ohjelmalle kerrotaan, minkä nimisiä asioita\n"
                + "seurataan. Ohjelmaan kirjoitetaan merkintöjä, \n"
                + "jotka ohjelma jäsentää kirjoittajan puolesta. \n"
                + "Merkinnät jäävät ohjelman välimuistiin yhdessä\n"
                + "seurattavien asioiden listan kanssa. Ohjelmaa voi \n"
                + "komentaa tallentamaan syötetyt tiedot tiedostoon.\n"
                + "Ohjelmaa voi pyytää tekemään yhteenvedon \n"
                + "annetuista tiedoista.";
        
        tulostaRivitettyString(neuvo);
    }

    public void tulostaOllaanPoistumassaOhjelmasta() {
        tulostaKonsoliin("Poistutaan ohjelmasta");
    }

    public void kysyOletkoVarma() {
        tulostaKonsoliin("Oletko varma? (k/e)");
    }

    public void kysyTallenetaankoMuutokset() {
        tulostaKonsoliin("Tallennetaanko muutokset? (k/e)");
    }

    public void tulostaKyllaEi() {
        tulostaKonsoliin("Vastaa k tai e");
    }

    public void tulostaMerkinta(Merkinta merkinta) {
        tulostaRivitettyString(merkinta.toString() + "\n");
    }

    public void ilmoitaTiedostonNollaamisesta() {
        tulostaKonsoliin("Tiedosto nollattu");
    }

    public void ilmoitaSeurattavienNollaamisesta() {
        tulostaKonsoliin("Seurattavat nollattu");
    }

    public void tulostaEiSeurattavissa() {
        tulostaKonsoliin("Annettu tekeminen ei ollut seurattavissa");
    }

    public void tulostaLisataankoSeurattava() {
        tulostaKonsoliin("Lisätäänkö tekeminen seurattaviin?");
    }

    public void tulostaSeurattavanLisaaminenOnnistui(String seurattava) {
        tulostaKonsoliin(seurattava + " lisättiin seurattaviin");
    }

    public void tulostaAikaOsuuOlemassaolevaanTapahtumaan() {
        tulostaKonsoliin("Merkinnässä on jo tapahtuma johon annettu aika osuu");
    }

    public void luotavaTapahtumaLeikkaaOlemassaOlevanTapahtumanKanssa() {
        tulostaRivitettyString("Luotava tapahtuma leikkaa olemassaolevan tapahtuman \nkanssa");
    }
}