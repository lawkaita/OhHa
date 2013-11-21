/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.io.IOException;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Tulostaja;
import konsoli.Konsoli;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.Tiedostonkasittelija;

/**
 * Sisältää metodeinaan komentokokonaisuuksia, joita komentotulkki kutsuu
 * käskystä.
 *
 * @author Envy 6-1010
 */
public class KomentoLogiikka {

    private Tulostaja tulostaja;
    private Ajantestaaja ajantestaaja;
    private AjanAntaja ajan;
    private Tiedostonkasittelija tika;
    private Konsoli konsoli;
    private KontekstinHaltija koha;
    private Kayttoliittyma kali;
    /**
     * merkki, joka annettaan dekooderille erottamaan Stringin osat toisistaan.
     */
    private char dekoodausMerkki;
    private String muistettavaString;

    public KomentoLogiikka(Tulostaja tulostaja,
            Tiedostonkasittelija tika, Konsoli konsoli, KontekstinHaltija koha) {
        this.tulostaja = tulostaja;
        this.ajantestaaja = new Ajantestaaja();
        this.ajan = new AjanAntaja();
        this.tika = tika;
        this.konsoli = konsoli;
        this.koha = koha;

        dekoodausMerkki = '!';
        muistettavaString = "";
    }

    public void merkinnanAloitus() {
        tulostaja.pyydaPaivaa();
        String paiva = ajan.annaTamaPaiva();
        konsoli.kirjoitaKomentoriville(paiva);
        this.koha.setMerkintaanPaiva(true);
    }

    public void nollaaTiedosto() {
        try {
            tika.nollaaTiedosto();
            tulostaja.ilmoitaNollaamisesta();
        } catch (IOException ex) {
            //
        }
    }

    public void haku(String komento) {
        String[] osuma = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(komento);
        //Merkinta merkinta = tika.getMerkinnanKasittelija().luoMerkintaHaunTuloksesta(osuma);
        tulostaja.tulostaHaunOsumat(osuma);
        //tulostaja.listaaKonsoliin(merkinta.toString());
        koha.setHakuKaynnissa(false);
    }

    public void luodaanMerkinta(String komento) {
        tulostaja.kerroLisayksesta();
        tulostaja.listaaKonsoliin(komento);

        try {
            Merkinta uusiMerkinta = tika.getMerkinnanKasittelija().muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(komento);
            String paivaysMuistettavaStringista = tika.getDekooderi().dekoodaa(komento, '!')[0];

            boolean kannassaOnMerkintaSamallaPaivalla =
                    tika.kannassaOnMerkintaPaivalla(paivaysMuistettavaStringista);

            if (kannassaOnMerkintaSamallaPaivalla) {
                String[] vanhaMerkintaUudenPaivallaTauluna =
                        tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(paivaysMuistettavaStringista);

                Merkinta samallaPaivallaValmisMerkinta = tika.getMerkinnanKasittelija()
                        .luoMerkintaHaunTuloksesta(vanhaMerkintaUudenPaivallaTauluna);

                //+2 sillä 1 päiväykselle ja 1 viimeiselle rivinvaihdolle.
                int vanhanMerkinnanPituus = samallaPaivallaValmisMerkinta.getTapahtumienMaara() + 2;
                int vanhanMerkinnanPaikkaIndeksi =
                        tika.haeKannastaMerkinnanPaivayksenPaikkaPaivayksella(paivaysMuistettavaStringista);

                tika.getMerkinnanKasittelija().yhdista(uusiMerkinta, samallaPaivallaValmisMerkinta);

                tika.poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(vanhanMerkinnanPaikkaIndeksi, vanhanMerkinnanPituus, uusiMerkinta);

            } else {
                tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(uusiMerkinta.toString(), true);
            }

        } catch (IOException ex) {
            //mitä tähän tulisi lisätä?
        }
    }

    public void otetaanPaiva(String komento) {
        if (ajantestaaja.onPaiva(komento)) {
            muistettavaString = komento + dekoodausMerkki;
            tulostaja.pyydaAloitusAikaa();
            konsoli.kirjoitaKomentoriville("hh.mm");
            koha.setMerkintaanAloitusAika(true);
        } else {
            tulostaja.tulostaEiOlePaiva();
            String paiva = ajan.annaTamaPaiva();
            konsoli.kirjoitaKomentoriville(paiva);
            return;
        }
        koha.setMerkintaanPaiva(false);
    }

    public void otetaanAloitusAika(String komento) {
        if (ajantestaaja.onAika(komento)) {
            muistettavaString += komento;
            tulostaja.pyydaLopetusAikaa();
            konsoli.kirjoitaKomentoriville(ajan.annaTamaAika());
            koha.setMerkintaanLopetusaika(true);

        } else {
            tulostaja.tulostaEiOleAika();
            konsoli.kirjoitaKomentoriville("hh.mm");
            return;
        }

        koha.setMerkintaanAloitusAika(false);
    }

    public void otetaanLopetusAika(String komento) {
        muistettavaString += "-" + komento + dekoodausMerkki;
        tulostaja.pyydaSelostus();
        koha.setMerkintaanSelostus(true);
        koha.setMerkintaanLopetusaika(false);
    }

    public void otetaanSelostus(String komento) {

        muistettavaString += komento;
        luodaanMerkinta(muistettavaString);

        koha.setMerkintaanSelostus(false);
        muistettavaString = "";
    }

    public void PoistetaanMerkinta(String komento) {
        if (ajantestaaja.onPaiva(komento)) {
            if (tika.kannassaOnMerkintaPaivalla(komento)) {
                tika.poistaMerkintaPaivanPerusteella(komento);
                tulostaja.tulostaMerkinnanPoistoOnnistui();
                koha.setPoistetaanMerkintaa(false);
            } else {
                tulostaja.tulostaEiOsumia();
            }
        } else {
            tulostaja.tulostaEiOlePaiva();
        }
    }

    public void sanoMikaAikaNytOn() {
        tulostaja.tulostaKonsoliin(ajan.mikaAikaNytOn());
    }

    public void keskeytaKaikki() {

        if (this.koha.onKontekstissa()) {
            this.tulostaja.tulostaKeskeytettiin();
        }

        this.koha.poistuKaikistaKonteksteista();

        this.konsoli.kirjoitaKomentoriville("");
    }

    public void haunAloitus() {
        tulostaja.pyydaHakusana();
        this.koha.setHakuKaynnissa(true);
    }

    public void tapaKali() {
        this.kali.tapa();
    }

    public void tulostetaanApua() {
        tulostaja.apua();
    }

    public void tulostetaanTietokantatiedosto() {
        tulostaja.tulostaTiedosto();
    }

    public void aloitetaanPaivanPoisto() {
        tulostaja.pyydaPaivaa();
        this.koha.setPoistetaanMerkintaa(true);
    }

    public void tulostetaanVirhe() {
        tulostaja.tulostaVirhe();
    }
}
