/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import tietokantasysteemi.Tiedostonkasittelija;
import konsoli.Konsoli;
import sovelluslogiikka.OmaAjanTestaaja;
import sovelluslogiikka.KomentoLogiikka;

/**
 * Komentotulkki ottaa vastaan komentoja String-muodossa. Tulkki tulkitsee
 * komennon ja kutsuu komennon suorittamiseen tarvittavia metodeja ohjelman
 * tulostajalta, ajantestaajalta, tiedostonkasittelijalta, konsolilta ja
 * komentologiikalta. Komentotulkin KontekstinHaltija seuraa, missä kontekstissa
 * komentotulkille annettavat komennot tulevat.
 *
 * @author Envy 6-1010
 */
public class Komentotulkki {

    private Konsoli konsoli;
    private KontekstinHaltija koha;
    private KomentoLogiikka komentologiikka;

    public Komentotulkki(Konsoli konsoli, KontekstinHaltija koha, KomentoLogiikka kolo) {
        this.konsoli = konsoli;
        this.koha = koha;
        this.komentologiikka = kolo;
    }

    public void enter(String komento) {
        haarauta(komento);
    }

    /**
     * Metodi kuljettaa komennon oikeaan kontekstiisna.
     *
     * @param komento käyttäjän antama komento
     *
     *
     */
    public void haarauta(String komento) {

        if (koha.getHakuKaynnissa() == true) {
            komentologiikka.haku(komento);
            return;
        }

        if (koha.getMerkintaanPaiva() == true) {
            komentologiikka.otetaanPaiva(komento);
            return;
        }

        if (koha.getMerkintaanAloitusAika() == true) {
            komentologiikka.otetaanAloitusAika(komento);
            return;
        }

        if (koha.getMerkintaanLopetusAika() == true) {
            komentologiikka.otetaanLopetusAika(komento);
            return;
        }

        if (koha.getMerkintaanSelostus() == true) {
            komentologiikka.otetaanSelostus(komento);

            return;
        }

        if (koha.getPoistetaanMerkintaa() == true) {
            komentologiikka.PoistetaanMerkinta(komento);
            return;
        }

        tulkitse(komento);
    }

    public void tulkitse(String komento) {

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            komentologiikka.tapaKali();
        }

        if (komento.equals("nyt")) {
            komentologiikka.sanoMikaAikaNytOn();
            return;
        }

//        if (komento.equals("paiva")) {
//            tulostaja.getAjan().annaTamaPaiva();
//            return;
//        }
//
//        if (komento.equals("aika")) {
//            tulostaja.getAjan().annaTamaAika();
//            return;
//        }

        if (komento.equals("apua")) {
            komentologiikka.tulostetaanApua();
            return;
        }

        if (komento.equals("tulosta")) {
            komentologiikka.tulostetaanTietokantatiedosto();
            return;
        }

        if (komento.equals("hae")) {
            this.komentologiikka.haunAloitus();
            return;
        }

        if (komento.equals("merk")) {
            this.komentologiikka.merkinnanAloitus();
            return;
        }

        if (komento.equals("nollaa")) {
            this.komentologiikka.nollaaTiedosto();
            return;
        }

        if (komento.equals("poista")) {
            komentologiikka.aloitetaanPaivanPoisto();
            return;
        }

        komentologiikka.tulostetaanVirhe();
    }

    public void otaKomento() {
        String komento = konsoli.getVarsinainenKomentoRivi().getText();
        konsoli.tulostaKomento();
        enter(komento);
    }

    public void keskeytaKaikki() {
        this.komentologiikka.keskeytaKaikki();
    }

    public KontekstinHaltija getKontekstinHaltija() {
        return this.koha;
    }
}
