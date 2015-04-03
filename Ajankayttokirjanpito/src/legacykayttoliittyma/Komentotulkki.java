/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legacykayttoliittyma;

import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.LegacyKomentoLogiikka;

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
    private LegacyKonsolinKorvaajaRajapinta lkkr;
    /**
     * Olio, joka pitää kirjaa siitä, missä asiayhteydessä konsolia käytetään.
     */
    private KontekstinHaltija kontekstinhaltija;
    /**
     * Ohjelman komentokokonaisuuksia suorittava olio.
     */
    private KomentoLogiikka komentologiikka;
    /**
     * Komentosarjoja osiin lajitteleva olio.
     */
    private Dekooderi dekooderi;

    public Komentotulkki(LegacyKonsolinKorvaajaRajapinta lkkr, KontekstinHaltija kontekstinhaltija, KomentoLogiikka komentologiikka, Dekooderi dekooderi) {
        this.lkkr = lkkr;
        this.kontekstinhaltija = kontekstinhaltija;
        this.komentologiikka = komentologiikka;
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
        if (komento.equals(":q")) {
            keskeytaKaikki();
            return;
        }

        if (komentojenMaara(komento) > 1) {
            kasitteleKomentoSarja(komento);

        } else {

            if (kontekstinhaltija.getOllaanPoistumassaOhjelmasta()) {
                if (komento.equals("k")) {
                    komentologiikka.ollaanPoistumassaOhjelmasta();
                } else if (komento.equals("e")) {
                    komentologiikka.poistutaanKonteksteista();
                } else {
                    komentologiikka.tulostetaanKyllaEi();
                }

                return;
            }

            if (kontekstinhaltija.getKysytaanPoistumisenYhteydessaTallennuksesta()) {
                if (komento.equals("k")) {
                    komentologiikka.poistutaanTallentaen();
                } else if (komento.equals("e")) {
                    komentologiikka.poistutaanTallentamatta();
                } else {
                    komentologiikka.tulostetaanKyllaEi();
                }

                return;
            }


            if (kontekstinhaltija.getHakuKaynnissa() == true) {
                komentologiikka.haarautaHaku(komento);
                return;
            }

            if (kontekstinhaltija.getMerkintaanPaiva() == true) {
                komentologiikka.otetaanPaiva(komento);
                return;
            }

            if (kontekstinhaltija.getMerkintaanAloitusAika() == true) {
                komentologiikka.otetaanAloitusAika(komento);
                return;
            }

            if (kontekstinhaltija.getMerkintaanLopetusAika() == true) {
                komentologiikka.otetaanLopetusAika(komento);
                return;
            }

            if (kontekstinhaltija.getMerkintaanSelostus() == true) {
                komentologiikka.otetaanSelostus(komento);

                return;
            }

            if (kontekstinhaltija.getPoistetaanMerkintaa() == true) {
                komentologiikka.poistetaanMerkinta(komento);
                return;
            }

            if (kontekstinhaltija.getLisataanseurattava()) {
                komentologiikka.otetaanSeurattava(komento);
                return;
            }

            if (kontekstinhaltija.getPoistetaanSeurattava()) {
                komentologiikka.poistetaanSeurattava(komento);
                return;
            }

            if (kontekstinhaltija.getKysytaanLisataankoSeurattava()) {
                if (komento.equals("k")) {
                    komentologiikka.lisätäänSeurattavaMuististaJaTehdaanSillaMerkinta();
                    return;
                } else if (komento.equals("e")) {
                    komentologiikka.luodaanMerkintaIlmanEttaLisataanSeurattavaMuistiin();
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

        if (komento.equals("merkinta")) {
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

        if (komento.equals("seurattava")) {
            this.komentologiikka.aloitetaanSeurattavanLisaaminen();
            return;
        }

        komentologiikka.tulostetaanVirhe();
    }

    /**
     * Ottaa käyttäjän antaman komennon konsolin komentoriviltä ja käsittelee
     * sen.
     */
    public void otaKomento(String komento) {
        //String komento = konsoli.getVarsinainenKomentoRivi().getText();
        //konsoli.tulostaKomento();
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
        return this.kontekstinhaltija;
    }
    
    /**
     * Laskee komentojen määrän annetusta String-oliosta.
     * @param komento annettu komento
     * @return komentojen määrä.
     */
    private int komentojenMaara(String komento) {
        return this.dekooderi.laskeOsienMaara(komento, null);
    }

    /**
     * Suorittaa moniosaisen komennon tulkitsemisen.
     * @param komento käyttäjän antama komento.
     */
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

            if (komentosarja[1].equals("merkintä")) {
                this.komentologiikka.aloitetaanPaivanPoisto();
                return;
            }

            if (komentosarja[1].equals("seurattava")) {
                this.komentologiikka.aloitetaanSeurattavanPoisto();
                return;
            }

            this.komentologiikka.tulostetaanVirhe();
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

    /**
     * Käsittelee komentosarjan, joka alkaa komennolla apua. Hoitaa kyseisen
     * komentosarjan jälkimmäisen osan.
     * @param string komennon jälkimmäinen osa
     */
    private void pyydetaanApua(String string) {
        if (string.equals("merkintä")) {
            this.komentologiikka.neuvottavaLisaamisessa();
            return;
        }

        if (string.equals("apua")) {
            this.komentologiikka.neuvottavaOhjelmanKaytossa();
            return;
        }

        if (string.equals("hae")) {
            this.komentologiikka.neuvottavaHakemisessa();
            return;
        }
        
        if (string.equals("seurattava")) {
            this.komentologiikka.neuvottavaSeurattavanLisaamisessa();
            return;
        }
        
        if (string.equals("nollaa")) {
            this.komentologiikka.neuvottavaNollaamisessa();
            return;
        }
        
        if (string.equals("poista")) {
            this.komentologiikka.neuvottavaPoistamisessa();
            return;
        }
        
        if (string.equals("yhteenveto")) {
            this.komentologiikka.neuvottavaYhteenvedossa();
            return;
        }

        this.komentologiikka.tulostetaanVirhe();
    }
}
