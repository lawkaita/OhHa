/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.io.IOException;
import java.util.ArrayList;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Tulostaja;
import konsoli.Konsoli;
import tietokantasysteemi.Kellonaika;
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
    private AjanTestaaja ajantestaaja;
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
            Tiedostonkasittelija tika, Konsoli konsoli, KontekstinHaltija koha, Kayttoliittyma kali) {
        this.tulostaja = tulostaja;
        this.ajantestaaja = new OmaAjanTestaaja();
        this.ajan = new OmaAjanAntaja();
        this.tika = tika;
        this.konsoli = konsoli;
        this.koha = koha;
        this.kali = kali;

        dekoodausMerkki = '!';
        muistettavaString = "";
    }

    /**
     * Aloittaa merkinnänteko-prosessin.
     */
    public void merkinnanAloitus() {
        tulostaja.pyydaPaivaa();
        String paiva = ajan.annaTamaPaiva();
        konsoli.kirjoitaKomentoriville(paiva);
        this.koha.setMerkintaanPaiva(true);
    }

    /**
     * Tyhjentää ohjelman tietokantatiedoston.
     */
    public void nollaaTiedosto() {
        try {
            tika.nollaaTiedosto();
            tulostaja.ilmoitaNollaamisesta();
        } catch (IOException ex) {
            //
        }
    }

    /**
     * Suorittaa tietokannasa haun etsien päiväyksiä ja verraten niitä annettuun
     * komentoon
     *
     * @param paivays annettu päivämäärä Stringinä.
     */
    public void haku(String paivays) {
        String[] osuma = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(paivays);
        //Merkinta merkinta = tika.getMerkinnanKasittelija().luoMerkintaHaunTuloksesta(osuma);
        tulostaja.tulostaHaunOsumat(osuma);
        //tulostaja.listaaKonsoliin(merkinta.toString());
        koha.setHakuKaynnissa(false);
    }

    /**
     * Luo merkintä-olion annetusta String-oliosta.
     *
     * @param komento annettu String
     */
    public void luoMerkinta(String komento) {       

        try {
            Merkinta uusiMerkinta = tika.getMerkinnanKasittelija()
                    .muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(komento);
            String paivaysMuistettavaStringista = tika.getDekooderi().dekoodaa(komento, '!')[0];

            boolean kannassaOnMerkintaSamallaPaivalla =
                    tika.kannassaOnMerkintaPaivalla(paivaysMuistettavaStringista);

            if (kannassaOnMerkintaSamallaPaivalla) {
                yhdistaUusiMerkintaTietokannanVanhaan(uusiMerkinta, paivaysMuistettavaStringista);
            } else {
                tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(uusiMerkinta.toString(), true);
            }

            tika.kirjoitaMerkinnatJarjestettynaKannanYli();
        } catch (IOException ex) {
            //mitä tähän tulisi lisätä?
        }
        
        tulostaja.kerroLisayksesta();
        tulostaja.listaaKonsoliin(komento);
    }
    
    /**
     * Yhdistää uuden merkinnän tietokannan vanhan samman päivän merkinnän kanssa, kirjoittaen muutoksen tietokantaan.
     * @param uusiMerkinta juuri luotu merkintä
     * @param paivaysMuistettavaStringista päivä, jonka merkintää tämä muutos tietokannassa koskee
     */
    private void yhdistaUusiMerkintaTietokannanVanhaan(Merkinta uusiMerkinta, String paivaysMuistettavaStringista) {
        String[] vanhaMerkintaUudenPaivallaTauluna =
                tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(paivaysMuistettavaStringista);

        Merkinta samallaPaivallaValmisMerkinta = tika.getMerkinnanKasittelija()
                .luoMerkintaAnnetustaTaulusta(vanhaMerkintaUudenPaivallaTauluna);

        //+2 sillä 1 päiväykselle ja 1 viimeiselle rivinvaihdolle.
        int vanhanMerkinnanPituus = samallaPaivallaValmisMerkinta.getTapahtumienMaara() + 2;
        int vanhanMerkinnanPaikkaIndeksi =
                tika.haeKannastaMerkinnanPaivayksenPaikkaPaivayksella(paivaysMuistettavaStringista);

        tika.getMerkinnanKasittelija().yhdista(uusiMerkinta, samallaPaivallaValmisMerkinta);

        tika.poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(vanhanMerkinnanPaikkaIndeksi, vanhanMerkinnanPituus, uusiMerkinta);
    }

    /**
     * Osa merkinnänluomisprosessia. Tässä kohdassa on annettu merkintään
     * liittyvä päiväys ja metodi suorittaa ohjelmaa eteenpäin riippuen siitä,
     * onko ohjelma tyytyväinen annetun päivän kanssa.
     *
     * @param komento annettu päiväys.
     */
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
    
    /**
     * Osa merkinnänluomis prosessia. Tässä kohdassa otetaan vastaan ja testataan
     * käyttäjän antama tapahtuman aloitusaika. Jos aika ei käy, kontekstia ei muuteta
     * ja virheestä ilmoitetaan käytäjälle.
     * @param komento käyttäjän antama aloitusaika
     */
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

    /**
     * Osa merkinnänluomis prosessia. Tässä kohdassa otetaan vastaan ja testataan
     * käyttäjän antama tapahtuman lopetusaika. Jos aika on pienempi kuin aloitusaika,
     * eli merkinnästä tulisi epämielekäs, konteksti ei muutu ja käyttäjälle ilmoitetaan jälleen
     * virheestä. Sama tapahtuu myös jos annettu komento ei käy ajasta.
     * @param komento 
     */
    public void otetaanLopetusAika(String komento) {
        if (ajantestaaja.onAika(komento)) {
            if (onAloitusaikaaSuurempiKellonaika(komento)) {
                muistettavaString += "-" + komento + dekoodausMerkki;
                tulostaja.pyydaSelostus();
                koha.setMerkintaanSelostus(true);
                koha.setMerkintaanLopetusaika(false);
            } else {
                tulostaja.tulostaLiianSuuriAika();
                konsoli.kirjoitaKomentoriville("hh.mm");
            }
        } else {
            tulostaja.tulostaEiOleAika();
            konsoli.kirjoitaKomentoriville("hh.mm");
        }
    }
    
    /**
     * Osa merkinnänluomisprosessia. Tässä kohdassa otetaan vastaan tapahtumaa
     * koskeva selostus, ja nollataan kyselyissä käytettävä muisti.
     * @param komento 
     */
    public void otetaanSelostus(String komento) {

        muistettavaString += komento;
        luoMerkinta(muistettavaString);

        koha.setMerkintaanSelostus(false);
        muistettavaString = "";
    }
    
    /**
     * Ohjaa merkinnän päivän perusteella poistamista. Testaa, onko annettu 
     * komento päivä, ja jos tällä päivällä on tietokannassa merkintä, tuo merkintä poistetaan.
     * Muuten ilmoitetaan komennon virheellisestä muodosta tai siitä, että annetulla päivällä
     * ei löydy merkintää tietokannasta.
     * @param komento 
     */
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

    /**
     * Hoitaa tämän hetken tarkan merkinnän tulostuksen.
     */
    public void sanoMikaAikaNytOn() {
        tulostaja.tulostaKonsoliin(ajan.mikaAikaNytOn());
    }
    
    /**
     * Ohjaa ohjelman poistumaan kaikista konteksteista.
     */
    public void keskeytaKaikki() {

        if (this.koha.onKontekstissa()) {
            this.tulostaja.tulostaKeskeytettiin();
        }

        this.koha.poistuKaikistaKonteksteista();

        this.konsoli.kirjoitaKomentoriville("");
    }

    /**
     * Ohjaa ohjelman haun aloituksen.
     */
    public void haunAloitus() {
        tulostaja.pyydaHakusana();
        this.koha.setHakuKaynnissa(true);
    }
    
    /**
     * Kutsuu kayttoliittyman sulkemismetodia.
     */
    public void tapaKali() {
        this.kali.tapa();
    }

    /**
     * Kutsuu tulostajaa tulostamaan ohjeita ohjelman käyttöä varten.
     */
    public void tulostetaanApua() {
        tulostaja.apua();
    }

    /**
     * Kutsuu tulostajaa tulostamaan koko tietokantatiedoston.
     */
    public void tulostetaanTietokantatiedosto() {
        tulostaja.tulostaTiedosto();
    }

    /**
     * Ohjaa merkinnän päivän perusteella poistoa.
     */
    public void aloitetaanPaivanPoisto() {
        tulostaja.pyydaPaivaa();
        this.koha.setPoistetaanMerkintaa(true);
    }

    /**
     * Kutsuu tulostajaa tulostamaan virheilmoituksen.
     */
    public void tulostetaanVirhe() {
        tulostaja.tulostaVirhe();
    }

    /**
     * Ohjaa ohjelman käytössä kerätyn datan analysoinnin ja tulosten tulostamisen.
     */
    public void yhteenveto() {

        String merkintoja = "Merkintöjä: " + tika.laskeMerkittyjenPaivienMaara();
        tulostaja.tulostaKonsoliin(merkintoja);

        ArrayList<Merkinta> merkintaTaulu = tika.merkinnatTaulussa();
        int kaytetytMinuutit = 0;

        for (Merkinta merkinta : merkintaTaulu) {
            kaytetytMinuutit += merkinta.tapahtumiinKaytettyAikaMinuutteina();
        }

        int kaytetytTunnit = kaytetytMinuutit / 60;
        int kaytetytJaannosMinuutit = kaytetytMinuutit % 60;

        String kaytettyAika = "Käytetty aika: " + kaytetytTunnit + "h " + kaytetytJaannosMinuutit + "min";
        tulostaja.tulostaKonsoliin(kaytettyAika);

    }

    /**
     * Vertaa merkinnänluomisessa annettua lopetuskellonaikaa aloituskellonaikaan
     * @param komento annettu lopetuskellonaika
     * @return true jos kellonaika on aloitusaikaa suurempi tai yhtäsuuri, muuten false.
     */
    private boolean onAloitusaikaaSuurempiKellonaika(String komento) {
        String[] tahanAstiKeratytVastaukset = tika.getDekooderi().dekoodaa(muistettavaString, dekoodausMerkki);
        String annettuAloitusaika = tahanAstiKeratytVastaukset[tahanAstiKeratytVastaukset.length - 1];
        String[] annettuAloitusaikaOsina = tika.getDekooderi().dekoodaa(annettuAloitusaika, '.');
        Kellonaika aloitusaika = new Kellonaika(Integer.parseInt(annettuAloitusaikaOsina[0]), Integer.parseInt(annettuAloitusaikaOsina[1]));
        
        String[] annettuLopetusaikaOsina = tika.getDekooderi().dekoodaa(komento, '.');
        Kellonaika lopetusaika = new Kellonaika(Integer.parseInt(annettuLopetusaikaOsina[0]), Integer.parseInt(annettuLopetusaikaOsina[1]));
        
        if (aloitusaika.compareTo(lopetusaika) > 0) {
            return false;
        } else {
            return true;
        }
        
    }
}
