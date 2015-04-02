/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import legacykayttoliittyma.LegacyKayttoliittyma;
import legacykayttoliittyma.LegacyKonsoliRajapinta;
import legacykayttoliittyma.KontekstinHaltija;
import legacykayttoliittyma.LegacyKonsolinKorvaajaRajapinta;
import legacykayttoliittyma.LegacyTulostaja;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.OmaTietokantaValimuisti;
import tietokantasysteemi.Tapahtuma;
import tietokantasysteemi.Tiedostonkasittelija;
import tietokantasysteemi.TietokantaValimuisti;

/**
 * Sisältää metodeinaan komentokokonaisuuksia, joita komentotulkki kutsuu
 * käskystä.
 *
 * @author Envy 6-1010
 */
public class LegacyKomentoLogiikka {

    private LegacyTulostaja tulostaja;
    private AjanTestaaja ajantestaaja;
    private AjanAntaja ajanAntaja;
    private Tiedostonkasittelija tiedostonkasittelija;
    private LegacyKonsoliRajapinta konsoli;
    private KontekstinHaltija kontekstinHaltija;
    private LegacyKayttoliittyma kayttoliittyma;
    private TietokantaValimuisti tietokantaValimuisti;
    private MerkinnanKasittelija merkinnanKasittelija;
    /**
     * merkki, joka annettaan dekooderille erottamaan Stringin osat toisistaan.
     */
    private char dekoodausMerkki;
    /**
     * Muistipaikka, johon säilötään tietoa useista eri konteksteista tullutta
     * tietoa.
     */
    private String muistettavaString;

    public LegacyKomentoLogiikka(LegacyTulostaja tulostaja,
            Tiedostonkasittelija tiedostonkasittelija, LegacyKonsoliRajapinta konsoli, KontekstinHaltija kontekstinHaltija, LegacyKayttoliittyma kayttoliittyma, MerkinnanKasittelija merkinnankasittelija) {
        this.tulostaja = tulostaja;
        this.ajantestaaja = new OmaAjanTestaaja();
        this.ajanAntaja = new OmaAjanAntaja();
        this.merkinnanKasittelija = merkinnankasittelija;
        this.tiedostonkasittelija = tiedostonkasittelija;
        this.konsoli = konsoli;
        this.kontekstinHaltija = kontekstinHaltija;
        this.kayttoliittyma = kayttoliittyma;
        this.tietokantaValimuisti = new OmaTietokantaValimuisti(this.tiedostonkasittelija);

        dekoodausMerkki = '!';
        muistettavaString = "";
    }

    /**
     * Aloittaa merkinnänteko-prosessin.
     */
    public void merkinnanAloitus() {
        tulostaja.otsikoiMerkinnanLuominen();
        tulostaja.pyydaPaivaa();
        String paiva = ajanAntaja.annaTamaPaiva();
        konsoli.kirjoitaKomentoriville(paiva);
        this.kontekstinHaltija.setMerkintaanPaiva(true);
    }

    /**
     * Tyhjentää ohjelman tietokantatiedoston.
     */
    public void nollaaTiedosto() {
        try {
            tiedostonkasittelija.nollaaTietokantaTiedosto();
            tulostaja.ilmoitaTiedostonNollaamisesta();
        } catch (IOException ex) {
            tulostaja.tulostaIOException();
        }
    }

    public void haarautaHaku(String hakusana) {
        if (this.ajantestaaja.onPaiva(hakusana)) {
            merkintaHaku(hakusana);
        } else if (this.tietokantaValimuisti.kannassaOnSeurattavaToiminta(hakusana)) {
            seurattavaHaku(hakusana);
        } else {
            //tämä pitää hoitaa eritavalla
            tulostaja.tulostaEiOsumia();
            this.kontekstinHaltija.setHakuKaynnissa(false);
        }
    }

    /**
     * Suorittaa tietokannasa haun etsien päiväyksiä ja verraten niitä annettuun
     * komentoon
     *
     * @param paivays annettu päivämäärä Stringinä.
     */
    public void merkintaHaku(String paivays) {
        Merkinta osuma = tietokantaValimuisti.haeMuististaMerkintaPaivayksella(paivays);
        if (osuma != null) {
            tulostaja.tulostaHaunOsuma(osuma);
            kontekstinHaltija.setHakuKaynnissa(false);
        } else {
            tulostaja.tulostaEiOsumia();
            kontekstinHaltija.setHakuKaynnissa(false);
        }

    }

    /**
     * Luo merkintä-olion annetusta String-oliosta.
     *
     * @param komento annettu String
     */
    public void luoMerkinta(String komento) {
        Merkinta uusiMerkinta = merkinnanKasittelija
                .muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(komento);
        String paivaysMuistettavaStringista = tiedostonkasittelija.getDekooderi().dekoodaa(komento, '!')[0];

        boolean kannassaOnMerkintaSamallaPaivalla =
                tietokantaValimuisti.kannassaOnMerkintaPaivalla(paivaysMuistettavaStringista);

        if (kannassaOnMerkintaSamallaPaivalla) {
            tietokantaValimuisti.lisaaMerkintaYhdistaen(uusiMerkinta);
        } else {
            tietokantaValimuisti.lisaaMerkinta(uusiMerkinta);
        }

        tulostaja.kerroLisayksesta();
        tulostaja.listaaKonsoliin(komento);
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
            String paiva = merkinnanKasittelija.luoPaivays(komento).toString();
            muistettavaString = paiva + dekoodausMerkki;
            tulostaja.pyydaAloitusAikaa();
            kontekstinHaltija.setMerkintaanAloitusAika(true);
        } else {
            tulostaja.tulostaEiOlePaiva();
            String paiva = ajanAntaja.annaTamaPaiva();
            konsoli.kirjoitaKomentoriville(paiva);
            return;
        }
        kontekstinHaltija.setMerkintaanPaiva(false);
    }

    /**
     * Osa merkinnänluomis prosessia. Tässä kohdassa otetaan vastaan ja
     * testataan käyttäjän antama tapahtuman aloitusaika. Jos aika ei käy,
     * kontekstia ei muuteta ja virheestä ilmoitetaan käytäjälle.
     *
     * @param komento käyttäjän antama aloitusaika
     */
    public void otetaanAloitusAika(String komento) {
        if (ajantestaaja.onAika(komento)) {
            String aika = merkinnanKasittelija.luoKellonaikaStringina(komento);
            boolean osuu = aikaOsuuJoOlemassaOleviintapahtumiin(komento);

            if (!osuu) {
                muistettavaString += aika;
                tulostaja.pyydaLopetusAikaa();
                konsoli.kirjoitaKomentoriville(ajanAntaja.annaTamaAika());
                kontekstinHaltija.setMerkintaanAloitusAika(false);
                kontekstinHaltija.setMerkintaanLopetusaika(true);
            } else {
                tulostaja.tulostaAikaOsuuOlemassaolevaanTapahtumaan();
                merkintaHaku(haeMuistettavastaStringistaIndeksista(0));
                tulostaja.pyydaAloitusAikaa();

            }

        } else {
            tulostaja.tulostaEiOleAika();
        }


    }

    /**
     * Osa merkinnänluomis prosessia. Tässä kohdassa otetaan vastaan ja
     * testataan käyttäjän antama tapahtuman lopetusaika. Jos aika on pienempi
     * kuin aloitusaika, eli merkinnästä tulisi epämielekäs, konteksti ei muutu
     * ja käyttäjälle ilmoitetaan jälleen virheestä. Sama tapahtuu myös jos
     * annettu komento ei käy ajasta.
     *
     * @param komento
     */
    public void otetaanLopetusAika(String komento) {
        if (ajantestaaja.onAika(komento)) {
            if (onAloitusaikaaSuurempiKellonaika(komento)) {
                boolean osuu = aikaOsuuJoOlemassaOleviintapahtumiin(komento);

                if (!osuu) {
                    String aloitusaika = haeMuistettavastaStringistaIndeksista(1);
                    String aika = merkinnanKasittelija.luoKellonaikaStringina(komento);
                    boolean kellonaikaPariOsuu = aikaPariOsuuJoOlemassaOleviinTapahtumiin(aloitusaika, aika);

                    if (!kellonaikaPariOsuu) {
                        muistettavaString += "-" + aika + dekoodausMerkki;
                        tulostaja.pyydaSelostus();
                        kontekstinHaltija.setMerkintaanSelostus(true);
                        kontekstinHaltija.setMerkintaanLopetusaika(false);
                    } else {
                        tulostaja.luotavaTapahtumaLeikkaaOlemassaOlevanTapahtumanKanssa();
                        merkintaHaku(haeMuistettavastaStringistaIndeksista(0));
                        tulostaja.pyydaLopetusAikaa();
                    }

                } else {
                    tulostaja.tulostaAikaOsuuOlemassaolevaanTapahtumaan();
                    merkintaHaku(haeMuistettavastaStringistaIndeksista(0));
                    tulostaja.pyydaLopetusAikaa();
                }

            } else {
                tulostaja.tulostaLiianSuuriAika();
            }
        } else {
            tulostaja.tulostaEiOleAika();
        }
    }

    /**
     * Osa merkinnänluomisprosessia. Tässä kohdassa otetaan vastaan tapahtumaa
     * koskeva selostus, ja nollataan kyselyissä käytettävä muisti.
     *
     * @param komento
     */
    public void otetaanSelostus(String komento) {
        muistettavaString += komento;
        if (this.tietokantaValimuisti.kannassaOnSeurattavaToiminta(komento)) {

            luoMerkinta(muistettavaString);

            kontekstinHaltija.setMerkintaanSelostus(false);
            muistettavaString = "";
        } else {
            tulostaja.tulostaEiSeurattavissa();
            tulostaja.tulostaLisataankoSeurattava();
            kontekstinHaltija.setMerkintaanSelostus(false);
            kontekstinHaltija.setKysytaanLisataankoSeurattava(true);
        }
    }

    public void luodaanMerkintaIlmanEttaLisataanSeurattavaMuistiin() {
        luoMerkinta(muistettavaString);

        kontekstinHaltija.setMerkintaanSelostus(false);
        muistettavaString = "";
    }

    /**
     * Ohjaa merkinnän päivän perusteella poistamista. Testaa, onko annettu
     * komento päivä, ja jos tällä päivällä on tietokannassa merkintä, tuo
     * merkintä poistetaan. Muuten ilmoitetaan komennon virheellisestä muodosta
     * tai siitä, että annetulla päivällä ei löydy merkintää tietokannasta.
     *
     * @param komento
     */
    public void poistetaanMerkinta(String komento) {
        if (ajantestaaja.onPaiva(komento)) {
            boolean merkintaLoytyiJaSePoistettiin = tietokantaValimuisti.poistaMerkintaPaivanPerusteella(komento);
            if (merkintaLoytyiJaSePoistettiin) {
                tulostaja.tulostaMerkinnanPoistoOnnistui();
                kontekstinHaltija.setPoistetaanMerkintaa(false);
            } else {
                tulostaja.tulostaEiOsumia();
                kontekstinHaltija.setPoistetaanMerkintaa(false);
            }
        } else {
            tulostaja.tulostaEiOlePaiva();
        }
    }

    /**
     * Hoitaa tämän hetken tarkan merkinnän tulostuksen.
     */
    public void sanoMikaAikaNytOn() {
        tulostaja.tulostaKonsoliin(ajanAntaja.mikaAikaNytOn());
    }

    /**
     * Ohjaa ohjelman poistumaan kaikista konteksteista.
     */
    public void keskeytaKaikki() {

        if (this.kontekstinHaltija.onKontekstissa()) {
            this.tulostaja.tulostaKeskeytettiin();
        }

        this.kontekstinHaltija.poistuKaikistaKonteksteista();

        this.konsoli.kirjoitaKomentoriville("");
    }

    /**
     * Ohjaa ohjelman haun aloituksen.
     */
    public void haunAloitus() {
        tulostaja.otsikoiHaunAloitus();
        tulostaja.pyydaHakusana();
        this.kontekstinHaltija.setHakuKaynnissa(true);
    }

    /**
     * Kutsuu kayttoliittyman sulkemismetodia.
     */
    public void tapaKali() {
        this.kayttoliittyma.tapa();
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
    public void tulostetaanValimuisti() {
        String tuloste = tietokantaValimuisti.toString();
        if (tuloste.isEmpty()) {
            tulostaja.tulostaEiMerkintoja();
        }

        tulostaja.tulostaValimuisti(tuloste);

    }

    /**
     * Ohjaa merkinnän päivän perusteella poistoa.
     */
    public void aloitetaanPaivanPoisto() {
        tulostaja.pyydaPaivaa();
        this.kontekstinHaltija.setPoistetaanMerkintaa(true);
    }

    /**
     * Kutsuu tulostajaa tulostamaan virheilmoituksen.
     */
    public void tulostetaanVirhe() {
        tulostaja.tulostaVirhe();
    }

    /**
     * Ohjaa ohjelman käytössä kerätyn datan analysoinnin ja tulosten
     * tulostamisen.
     */
    public void yhteenveto() {
        DecimalFormat df = new DecimalFormat("#.##");

        String seurattavia = "Seurattavia toimintoja: " + tietokantaValimuisti.laskeSeurattavienMaara();
        tulostaja.tulostaKonsoliin(seurattavia);

        ArrayList<String> seurattavaTaulu = tietokantaValimuisti.annaSeurattavatToiminnot();

        String merkintoja = "Merkintöjä: " + tietokantaValimuisti.laskeMerkintojenMaara();
        tulostaja.tulostaKonsoliin(merkintoja);

        ArrayList<Merkinta> merkintaTaulu = tietokantaValimuisti.annaMuisti();
        int kaytetytMinuutit = 0;

        for (Merkinta merkinta : merkintaTaulu) {
            kaytetytMinuutit += merkinta.tapahtumiinKaytettyAikaMinuutteina();
        }

        String kaytettyAika = aikaTunteinaJaMinuutteina(kaytetytMinuutit);

        tulostaja.tulostaKonsoliin("Käytetty aika: " + kaytettyAika);

        for (String seurattava : seurattavaTaulu) {
            int seurattavaanKaytettyAika = seurattavanKaytettyAikaHaku(seurattava);

            String prosenttiosuus;
            if (seurattavaanKaytettyAika == 0) {
                prosenttiosuus = " (0%)";
            } else {
                prosenttiosuus = " (" + df.format(((double) seurattavaanKaytettyAika) * 100 / ((double) kaytetytMinuutit)) + "%)";
            }

            String tuloste = "  " + seurattava
                    + ": "
                    + aikaTunteinaJaMinuutteina(seurattavaanKaytettyAika)
                    + prosenttiosuus;

            tulostaja.tulostaKonsoliin(tuloste);
        }

    }

    /**
     * Vertaa merkinnänluomisessa annettua lopetuskellonaikaa
     * aloituskellonaikaan
     *
     * @param komento annettu lopetuskellonaika
     * @return true jos kellonaika on aloitusaikaa suurempi tai yhtäsuuri,
     * muuten false.
     */
    private boolean onAloitusaikaaSuurempiKellonaika(String komento) {
        String[] tahanAstiKeratytVastaukset = tiedostonkasittelija.getDekooderi().dekoodaa(muistettavaString, dekoodausMerkki);
        String annettuAloitusaika = tahanAstiKeratytVastaukset[tahanAstiKeratytVastaukset.length - 1];
        String[] annettuAloitusaikaOsina = tiedostonkasittelija.getDekooderi().dekoodaa(annettuAloitusaika, '.');
        Kellonaika aloitusaika = new Kellonaika(Integer.parseInt(annettuAloitusaikaOsina[0]), Integer.parseInt(annettuAloitusaikaOsina[1]));

        String[] annettuLopetusaikaOsina = tiedostonkasittelija.getDekooderi().dekoodaa(komento, '.');
        Kellonaika lopetusaika = new Kellonaika(Integer.parseInt(annettuLopetusaikaOsina[0]), Integer.parseInt(annettuLopetusaikaOsina[1]));

        if (aloitusaika.compareTo(lopetusaika) > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void tallenna() {
        this.tiedostonkasittelija.yliKirjoitaTietokantatiedosto(this.tietokantaValimuisti.annaMuisti());
        this.tiedostonkasittelija.ylikirjoitaSeurattavatToiminnotTiedosto(this.tietokantaValimuisti.annaSeurattavatToiminnot());
        tulostaja.ilmoitaTallennuksenOnnistumisesta();
    }

    public void nollaaValimuisti() {
        this.tietokantaValimuisti.nollaaMuisti();
        tulostaja.ilmoitaValimuistinNollaamisesta();
    }

    public TietokantaValimuisti getTietokantaValimuisti() {
        return this.tietokantaValimuisti;
    }

    public void neuvottavaLisaamisessa() {
        tulostaja.neuvoLisaamisessa();
    }

    public void aloitaOhjelmastaPoistuminen() {
        tulostaja.tulostaOllaanPoistumassaOhjelmasta();
        tulostaja.kysyOletkoVarma();
        this.kontekstinHaltija.setOllaanPoistumassaOhjelmasta(true);
    }

    public void ollaanPoistumassaOhjelmasta() {
        tulostaja.kysyTallenetaankoMuutokset();
        this.kontekstinHaltija.setOllaanPoistumassaOhjelmasta(false);
        this.kontekstinHaltija.setKysytaanPoistumisenYhteydessaTallennuksesta(true);
    }

    public void poistutaanKonteksteista() {
        this.kontekstinHaltija.poistuKaikistaKonteksteista();
    }

    public void tulostetaanKyllaEi() {
        tulostaja.tulostaKyllaEi();
    }

    public void poistutaanTallentaen() {
        tallenna();
        tapaKali();
    }

    public void poistutaanTallentamatta() {
        tapaKali();
    }

    public void lisätäänSeurattava(String string) {
        this.tietokantaValimuisti.lisaaSeurattava(string);
        this.tulostaja.tulostaSeurattavanLisaaminenOnnistui(string);
    }

    private void seurattavaHaku(String hakusana) {
        ArrayList<Merkinta> osumat = new ArrayList<>();

        for (Merkinta merkinta : this.tietokantaValimuisti.annaMuisti()) {
            ArrayList<Tapahtuma> merkinnanTapahtumat = merkinta.getTapahtumat();
            for (Tapahtuma tapahtuma : merkinnanTapahtumat) {
                if (tapahtuma.getSeloste().equals(hakusana)) {
                    if (!osumat.contains(merkinta)) {
                        osumat.add(merkinta);
                    }
                }
            }
        }

        for (Merkinta merkinta : osumat) {
            this.tulostaja.tulostaMerkinta(merkinta);
        }

        this.kontekstinHaltija.setHakuKaynnissa(false);
    }

    private int seurattavanKaytettyAikaHaku(String hakusana) {
        int kaytettyAikaMinuutteina = 0;

        for (Merkinta merkinta : this.tietokantaValimuisti.annaMuisti()) {
            ArrayList<Tapahtuma> merkinnanTapahtumat = merkinta.getTapahtumat();
            for (Tapahtuma tapahtuma : merkinnanTapahtumat) {
                if (tapahtuma.getSeloste().equals(hakusana)) {
                    kaytettyAikaMinuutteina += tapahtuma.kesto().minuutteina();
                }
            }
        }

        return kaytettyAikaMinuutteina;
    }

    private String aikaTunteinaJaMinuutteina(int kaytetytMinuutit) {
        int kaytetytTunnit = kaytetytMinuutit / 60;
        int kaytetytJaannosMinuutit = kaytetytMinuutit % 60;

        String kaytettyAika = kaytetytTunnit + "h " + kaytetytJaannosMinuutit + "min";
        return kaytettyAika;
    }

    private boolean aikaOsuuJoOlemassaOleviintapahtumiin(String aika) {
        String[] kellonaikaOsina = tiedostonkasittelija.getDekooderi().dekoodaa(aika, '.');
        Kellonaika aikaKellonaikana = merkinnanKasittelija.luoKellonaika(kellonaikaOsina);
        String paivays = haeMuistettavastaStringistaIndeksista(0);
        Merkinta merkintaSamallaPaivalla = tietokantaValimuisti.haeMuististaMerkintaPaivayksella(paivays);

        if (merkintaSamallaPaivalla == null) {
            return false;
        }

        return merkintaSamallaPaivalla.sisaltaaTapahtumanJohonKellonaikaOsuu(aikaKellonaikana);
    }

    public void nollaaSeurattavat() {
        this.tietokantaValimuisti.nollaaSeurattavat();
        this.tulostaja.ilmoitaSeurattavienNollaamisesta();
    }

    public void lisätäänSeurattavaMuististaJaTehdaanSillaMerkinta() {
        String uusiSeurattava = haeMuistettavastaStringistaIndeksista(2);
        lisätäänSeurattava(uusiSeurattava);
        luoMerkinta(muistettavaString);
        this.muistettavaString = "";

        this.kontekstinHaltija.setKysytaanLisataankoSeurattava(false);
    }

    private String haeMuistettavastaStringistaIndeksista(int indeksi) {
        String[] dekoodi = tiedostonkasittelija.getDekooderi().dekoodaa(muistettavaString, dekoodausMerkki);

        if (indeksi < dekoodi.length) {
            return dekoodi[indeksi];
        } else {
            return "";
        }
    }

    private boolean aikaPariOsuuJoOlemassaOleviinTapahtumiin(String aloitusaika, String lopetusaika) {
        Kellonaika[] ajat = this.merkinnanKasittelija.luoAloitusaikajaLopetusaika(aloitusaika + "-" + lopetusaika);
        String paivays = haeMuistettavastaStringistaIndeksista(0);
        Merkinta merkintaSamallaPaivalla = tietokantaValimuisti.haeMuististaMerkintaPaivayksella(paivays);

        if (merkintaSamallaPaivalla == null) {
            return false;
        }

        return merkintaSamallaPaivalla.sisaltaaTapahtumanJohonKellonaikaPariOsuu(ajat[0], ajat[1]);
    }

    public void neuvottavaOhjelmanKaytossa() {
        this.tulostaja.neuvoOhjelmanKaytossa();
    }

    public void aloitetaanSeurattavanLisaaminen() {
        this.kontekstinHaltija.setLisataanSeurattava(true);
        this.tulostaja.pyydaSeurattava();
    }

    public void otetaanSeurattava(String komento) {
        lisätäänSeurattava(komento);
        this.kontekstinHaltija.setLisataanSeurattava(false);
    }

    public void aloitetaanSeurattavanPoisto() {
        this.kontekstinHaltija.setPoistetaanSeurattava(true);
        this.tulostaja.pyydaPoistettavaSeurattava();
    }

    public void poistetaanSeurattava(String komento) {
        boolean seruattavaLoytyiJaSePoistettiin = this.tietokantaValimuisti.poistaSeurattava(komento);
        if (seruattavaLoytyiJaSePoistettiin) {
            tulostaja.tulostaSeurattavanPoistoOnnistui();
        } else {
            tulostaja.tulostaSeurattavaaEiLoydy();
        }

        this.kontekstinHaltija.setPoistetaanSeurattava(false);

    }

    public void neuvottavaHakemisessa() {
        tulostaja.neuvoHakemisessa();
    }

    public void neuvottavaSeurattavanLisaamisessa() {
        tulostaja.neuvoSeurattavanLisaamisessa();
    }

    public void neuvottavaNollaamisessa() {
        tulostaja.neuvoNollaamisessa();
    }

    public void neuvottavaPoistamisessa() {
        tulostaja.neuvoPoistamisessa();    
    }

    public void neuvottavaYhteenvedossa() {
        tulostaja.neuvoYhteenvedossa();
    }
    
}
