/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.Dekooderi;
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

    /**
     * Ohjelman käyttöliittymä.
     */
    private Konsoli konsoli;
    /**
     * Olio, joka pitää kirjaa siitä, missä asiayhteydessä konsolia käytetään.
     */
    private KontekstinHaltija koha;
    /**
     * Ohjelman komentokokonaisuuksia suorittava olio.
     */
    private KomentoLogiikka komentologiikka;
    /**
     * Komentosarjoja osiin lajitteleva olio.
     */
    private Dekooderi dekooderi;

    public Komentotulkki(Konsoli konsoli, KontekstinHaltija koha, KomentoLogiikka kolo, Dekooderi dekooderi) {
        this.konsoli = konsoli;
        this.koha = koha;
        this.komentologiikka = kolo;
        this.dekooderi = dekooderi;
    }

    /**
     * Kuljettaa komennon oikeaan kontekstiisna.
     *
     * @param komento käyttäjän antama komento
     *
     *
     */
    public void haarauta(String komento) {

        if (komentojenMaara(komento) > 1) {
            kasitteleKomentoSarja(komento);

        } else {

            if (koha.getOllaanPoistumassaOhjelmasta()) {
                if (komento.equals("k")) {
                    komentologiikka.ollaanPoistumassaOhjelmasta();
                } else if (komento.equals("e")) {
                    komentologiikka.poistutaanKonteksteista();
                } else {
                    komentologiikka.tulostetaanKyllaEi();
                }

                return;
            }

            if (koha.getKysytaanPoistumisenYhteydessaTallennuksesta()) {
                if (komento.equals("k")) {
                    komentologiikka.poistutaanTallentaen();
                } else if (komento.equals("e")) {
                    komentologiikka.poistutaanTallentamatta();
                } else {
                    komentologiikka.tulostetaanKyllaEi();
                }

                return;
            }


            if (koha.getHakuKaynnissa() == true) {
                komentologiikka.haarautaHaku(komento);
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

            if (koha.getKysytaanLisataankoSeurattava()) {
                if (komento.equals("k")) {
                    komentologiikka.lisätäänSeurattavaMuististaJaTehdaanSillaMerkinta();
                    return;
                } else if (komento.equals("e")) {
                    komentologiikka.poistutaanKonteksteista();
                    return;
                } else {
                    komentologiikka.tulostetaanKyllaEi();
                }


            }

            tulkitse(komento);
        }
    }

    /**
     * Tulkitsee käyttäjän antaman komennon, kun komentoa ei annettu missään
     * kontekstissa.
     *
     * @param komento käyttäjän antama komento
     */
    public void tulkitse(String komento) {

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            komentologiikka.aloitaOhjelmastaPoistuminen();
            return;
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

        if (komento.equals("merkintä")) {
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
     * Ottaa käyttäjän antaman komennon konsolin komentoriviltä ja käsittelee
     * sen.
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
     *
     * @return komentotulkin kontekstinhaltija
     */
    public KontekstinHaltija getKontekstinHaltija() {
        return this.koha;
    }

    private int komentojenMaara(String komento) {
        return this.dekooderi.laskeOsienMaara(komento, null);
    }

    private void kasitteleKomentoSarja(String komento) {
        String[] komentosarja = this.dekooderi.dekoodaa(komento, null);

        if (komentosarja[0].equals("lisää")) {
            if (komentosarja[1].equals("merkintä")) {
                this.komentologiikka.merkinnanAloitus();
                return;
            } else if (komentosarja[1].equals("seurattava")) {
                if (komentosarja.length > 2) {
                    this.komentologiikka.lisätäänSeurattava(komentosarja[2]);
                    return;
                }
            }

        }

        if (komentosarja[0].equals("hae")) {
            this.komentologiikka.haarautaHaku(komentosarja[1]);
            return;
        }

        if (komentosarja[0].equals("poista")) {
            this.komentologiikka.poistetaanMerkinta(komentosarja[1]);
            return;
        }

        if (komentosarja[0].equals("apua")) {
            pyydetaanApua(komentosarja[1]);
            return;
        }

        if (komentosarja[0].equals("nollaa")) {

            if (komentosarja[1].equals("välimuisti")) {
                this.komentologiikka.nollaaValimuisti();
                return;
            }

            if (komentosarja[1].equals("seurattavat")) {
                this.komentologiikka.nollaaSeurattavat();
                return;
            }

        }

        this.komentologiikka.tulostetaanVirhe();
    }

    private void pyydetaanApua(String string) {
        if (string.equals("merkintä")) {
            this.komentologiikka.neuvottavaLisaamisessa();
            return;
        }

        if (string.equals("apua")) {
            this.komentologiikka.neuvottavaOhjelmanKaytossa();
            return;
        }

        this.komentologiikka.tulostetaanVirhe();
    }
}
