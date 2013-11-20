/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import tietokantasysteemi.Tiedostonkasittelija;
import konsoli.Konsoli;
import sovelluslogiikka.Ajantestaaja;
import sovelluslogiikka.KomentoLogiikka;

/**
 * Komentotulkki ottaa vastaan komentoja String-muodossa. 
 * Tulkki tulkitsee komennon ja kutsuu komennon suorittamiseen tarvittavia 
 * metodeja ohjelman tulostajalta, ajantestaajalta, tiedostonkasittelijalta,
 * konsolilta ja komentologiikalta. Lisäksi Komentotulkki seuraa, missä
 * kontekstissa seuraava komento annetaan. 
 * @author Envy 6-1010
 */
public class Komentotulkki {

    private Tulostaja tulostaja;
    private Ajantestaaja ajantestaaja;
    private Tiedostonkasittelija tika;
    private Konsoli konsoli;
    private KomentoLogiikka komentologiikka;
    private Kayttoliittyma kali;
    public boolean merkintaanPaiva;
    public boolean merkintaanAloitusAika;
    public boolean merkintaanLopetusAika;
    public boolean merkintaanSelostus;
    public boolean hakuKaynnissa;
    public boolean poistetaanMerkintaa;
    
    /**
     * merkki, joka annettaan dekooderille erottamaan Stringin osat toisistaan.
     */
    private char dekoodausMerkki;
    public String muistettavaString;

    public Komentotulkki(Tulostaja tulostaja, Tiedostonkasittelija tika, Konsoli konsoli, Kayttoliittyma kali) {
        this.tulostaja = tulostaja;
        this.ajantestaaja = new Ajantestaaja();
        this.tika = tika;
        this.konsoli = konsoli;
        this.komentologiikka = new KomentoLogiikka(this, this.tulostaja, this.ajantestaaja, this.tika, this.konsoli);
        this.kali = kali;

        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;

        dekoodausMerkki = "!".charAt(0);
        muistettavaString = "";

    }

    public void enter(String komento) {
        haarauta(komento);
    }

    /**
     * Metodi kuljettaa komennon oikeaan kontekstiisna.
     * @param komento käyttäjän antama komento
     * 
     * 
     */
    public void haarauta(String komento) {

        if (hakuKaynnissa == true) {
            komentologiikka.haku(komento);
            hakuKaynnissa = false;
            return;
        }

        if (merkintaanPaiva == true) {
            if (ajantestaaja.onPaiva(komento)) {
                muistettavaString = komento + dekoodausMerkki;
                tulostaja.pyydaAloitusAikaa();
                konsoli.kirjoitaKomentoriville("hh.mm");
                merkintaanAloitusAika = true;
            } else {
                tulostaja.tulostaEiOlePaiva();
                String paiva = tulostaja.getAjan().annaTamaPaiva();
                konsoli.kirjoitaKomentoriville(paiva);
                return;
            }
            merkintaanPaiva = false;
            return;
        }

        if (merkintaanAloitusAika == true) {
            if (ajantestaaja.onAika(komento)) {
                muistettavaString += komento;
                tulostaja.pyydaLopetusAikaa();
                konsoli.kirjoitaKomentoriville(tulostaja.getAjan().annaTamaAika());
                merkintaanLopetusAika = true;

            } else {
                tulostaja.tulostaKonsoliin("Ei ole aika");
                konsoli.kirjoitaKomentoriville("hh.mm");
                return;
            }

            merkintaanAloitusAika = false;

            return;
        }

        if (merkintaanLopetusAika == true) {
            muistettavaString += "-" + komento + dekoodausMerkki;
            tulostaja.pyydaSelostus();
            merkintaanSelostus = true;
            merkintaanLopetusAika = false;

            return;
        }

        if (merkintaanSelostus == true) {
            muistettavaString += komento;
            komentologiikka.luodaanMerkinta(muistettavaString);            

            merkintaanSelostus = false;
            muistettavaString = "";
            return;
        }

        if (poistetaanMerkintaa == true) {
            if (ajantestaaja.onPaiva(komento)) {
                tika.poistaMerkintaPaivanPerusteella(komento);

                tulostaja.tulostaMerkinnanPoistoOnnistui();
                poistetaanMerkintaa = false;
            } else {
                tulostaja.tulostaEiOlePaiva();
            }
            return;
        }

        tulkitse(komento);
    }

    public void tulkitse(String komento) {

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            this.kali.tapa();
        }

        if (komento.equals("nyt")) {
            tulostaja.sanoMikaAikaNytOn();
            return;
        }

        if (komento.equals("paiva")) {
            tulostaja.getAjan().annaTamaPaiva();
            return;
        }

        if (komento.equals("aika")) {
            tulostaja.getAjan().annaTamaAika();
            return;
        }

        if (komento.equals("apua")) {
            tulostaja.apua();
            return;
        }

        if (komento.equals("tulosta")) {
            tulostaja.tulostaTiedosto();
            return;
        }

        if (komento.equals("hae")) {
            tulostaja.pyydaHakusana();
            this.hakuKaynnissa = true;
            return;
        }

        if (komento.equals("merk")) {
            this.komentologiikka.merkinnanAloitus();
            this.merkintaanPaiva = true;
            return;
        }

        if (komento.equals("nollaa")) {
            this.komentologiikka.nollaaTiedosto();
            tulostaja.ilmoitaNollaamisesta();
            return;
        }

        if (komento.equals("poista")) {
            tulostaja.pyydaPaivaa();
            this.poistetaanMerkintaa = true;
            return;
        }

        tulostaja.tulostaVirhe();
    }

    public void otaKomento() {
        String komento = konsoli.getVarsinainenKomentoRivi().getText();
        konsoli.tulostaKomento();

        enter(komento);
    }

    void keskeytaKaikki() {
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanPaiva = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;

        this.tulostaja.tulostaKeskeytettiin();

        this.konsoli.kirjoitaKomentoriville("");
    }
}
