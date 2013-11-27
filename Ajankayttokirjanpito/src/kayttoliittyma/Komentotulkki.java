/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import tietokantasysteemi.OmaTiedostonkasittelija;
import konsoli.OmaKonsoli;
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

    private OmaKonsoli konsoli;
    private KontekstinHaltija koha;
    private KomentoLogiikka komentologiikka;

    public Komentotulkki(OmaKonsoli konsoli, KontekstinHaltija koha, KomentoLogiikka kolo) {
        this.konsoli = konsoli;
        this.koha = koha;
        this.komentologiikka = kolo;
    }

    /**
     * Kuljettaa komennon oikeaan kontekstiisna.
     *
     * @param komento käyttäjän antama komento
     *
     *
     */
    public void haarauta(String komento) {

        if (koha.getHakuKaynnissa() == true) {
            komentologiikka.merkintaHaku(komento);
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
            komentologiikka.poistetaanMerkinta(komento);
            return;
        }

        tulkitse(komento);
    }
    
    /**
     * Tulkitsee käyttäjän antaman komennon, kun komentoa ei annettu missään kontekstissa.
     * 
     * @param komento käyttäjän antama komento
     */
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

        if (komento.equals("apua")) {
            komentologiikka.tulostetaanApua();
            return;
        }

        if (komento.equals("tulosta")) {
            komentologiikka.tulostetaanValimuisti();
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
            this.komentologiikka.nollaaValimuisti();
            return;
        }

        if (komento.equals("poista")) {
            komentologiikka.aloitetaanPaivanPoisto();
            return;
        }
        
        if (komento.equals("tallenna")) {
            komentologiikka.tallenna();
            return;
        }
        
        if (komento.equals("yhteenveto")) {
            this.komentologiikka.yhteenveto();
            return;
        }

        komentologiikka.tulostetaanVirhe();
        
    }

    /**
     * Ottaa käyttäjän antaman komennon konsolin komentoriviltä ja käsittelee sen.
     */
    public void otaKomento() {
        String komento = konsoli.getVarsinainenKomentoRivi().getText();
        konsoli.tulostaKomento();
        haarauta(komento);
    }

    /**
     * Kutsuu komentologiikkaa poistumaan kaikista konteksteista.
     */
    public void keskeytaKaikki() {
        this.komentologiikka.keskeytaKaikki();
    }
    
    /**
     * Palauttaa komentotulkin kontekstinHaltijan.
     * @return komentotulkin kontekstinhaltija  
     */
    public KontekstinHaltija getKontekstinHaltija() {
        return this.koha;
    }
}
