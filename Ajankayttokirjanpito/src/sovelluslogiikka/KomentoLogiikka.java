/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Konsoli;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Tulostaja;
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
public class KomentoLogiikka {

    private Tulostaja tulostaja;
    private AjanTestaaja ajantestaaja;
    private AjanAntaja ajan;
    private Tiedostonkasittelija tika;
    private Konsoli konsoli;
    private KontekstinHaltija koha;
    private Kayttoliittyma kali;
    private TietokantaValimuisti timu;
    private MerkinnanKasittelija meka;
    /**
     * merkki, joka annettaan dekooderille erottamaan Stringin osat toisistaan.
     */
    private char dekoodausMerkki;
    /**
     * Muistipaikka, johon säilötään tietoa useista eri konteksteista tullutta
     * tietoa.
     */
    private String muistettavaString;

    public KomentoLogiikka(Tulostaja tulostaja,
            Tiedostonkasittelija tika, Konsoli konsoli, KontekstinHaltija koha, Kayttoliittyma kali, MerkinnanKasittelija meka) {
        this.tulostaja = tulostaja;
        this.ajantestaaja = new OmaAjanTestaaja();
        this.ajan = new OmaAjanAntaja();
        this.meka = meka;
        this.tika = tika;
        this.konsoli = konsoli;
        this.koha = koha;
        this.kali = kali;
        this.timu = new OmaTietokantaValimuisti(this.tika);

        dekoodausMerkki = '!';
        muistettavaString = "";
    }

    /**
     * Aloittaa merkinnänteko-prosessin.
     */
    public void merkinnanAloitus() {
        tulostaja.otsikoiMerkinnanLuominen();
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
            tika.nollaaTietokantaTiedosto();
            tulostaja.ilmoitaTiedostonNollaamisesta();
        } catch (IOException ex) {
            tulostaja.tulostaIOException();
        }
    }

    public void haarautaHaku(String hakusana) {
        if (this.ajantestaaja.onPaiva(hakusana)) {
            merkintaHaku(hakusana);
        } else if (this.timu.kannassaOnSeurattavaToiminta(hakusana)) {
            seurattavaHaku(hakusana);
        } else {
            //tämä pitää hoitaa eritavalla
            tulostaja.tulostaEiOsumia();
            this.koha.setHakuKaynnissa(false);
        }
    }

    /**
     * Suorittaa tietokannasa haun etsien päiväyksiä ja verraten niitä annettuun
     * komentoon
     *
     * @param paivays annettu päivämäärä Stringinä.
     */
    public void merkintaHaku(String paivays) {
        Merkinta osuma = timu.haeMuististaMerkintaPaivayksella(paivays);
        if (osuma != null) {
            tulostaja.tulostaHaunOsuma(osuma);
            koha.setHakuKaynnissa(false);
        } else {
            tulostaja.tulostaEiOsumia();
            koha.setHakuKaynnissa(false);
        }

    }

    /**
     * Luo merkintä-olion annetusta String-oliosta.
     *
     * @param komento annettu String
     */
    public void luoMerkinta(String komento) {
        Merkinta uusiMerkinta = meka
                .muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(komento);
        String paivaysMuistettavaStringista = tika.getDekooderi().dekoodaa(komento, '!')[0];

        boolean kannassaOnMerkintaSamallaPaivalla =
                timu.kannassaOnMerkintaPaivalla(paivaysMuistettavaStringista);

        if (kannassaOnMerkintaSamallaPaivalla) {
            timu.lisaaMerkintaYhdistaen(uusiMerkinta);
        } else {
            timu.lisaaMerkinta(uusiMerkinta);
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
            String paiva = meka.luoPaivays(komento).toString();
            muistettavaString = paiva + dekoodausMerkki;
            tulostaja.pyydaAloitusAikaa();
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
     * Osa merkinnänluomis prosessia. Tässä kohdassa otetaan vastaan ja
     * testataan käyttäjän antama tapahtuman aloitusaika. Jos aika ei käy,
     * kontekstia ei muuteta ja virheestä ilmoitetaan käytäjälle.
     *
     * @param komento käyttäjän antama aloitusaika
     */
    public void otetaanAloitusAika(String komento) {
        if (ajantestaaja.onAika(komento)) {
            String aika = meka.luoKellonaikaStringina(komento);
            boolean osuu = aikaOsuuJoOlemassaOleviintapahtumiin(komento);

            if (!osuu) {
                muistettavaString += aika;
                tulostaja.pyydaLopetusAikaa();
                konsoli.kirjoitaKomentoriville(ajan.annaTamaAika());
                koha.setMerkintaanAloitusAika(false);
                koha.setMerkintaanLopetusaika(true);
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
                    String aika = meka.luoKellonaikaStringina(komento);
                    boolean kellonaikaPariOsuu = aikaPariOsuuJoOlemassaOleviinTapahtumiin(aloitusaika, aika);

                    if (!kellonaikaPariOsuu) {
                        muistettavaString += "-" + aika + dekoodausMerkki;
                        tulostaja.pyydaSelostus();
                        koha.setMerkintaanSelostus(true);
                        koha.setMerkintaanLopetusaika(false);
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
        if (this.timu.kannassaOnSeurattavaToiminta(komento)) {

            muistettavaString += komento;
            luoMerkinta(muistettavaString);

            koha.setMerkintaanSelostus(false);
            muistettavaString = "";
        } else {
            tulostaja.tulostaEiSeurattavissa();
            tulostaja.tulostaLisataankoSeurattava();
            muistettavaString += komento;
            koha.setMerkintaanSelostus(false);
            koha.setKysytaanLisataankoSeurattava(true);
        }
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
            boolean merkintaLoytyiJaSePoistettiin = timu.poistaMerkintaPaivanPerusteella(komento);
            if (merkintaLoytyiJaSePoistettiin) {
                tulostaja.tulostaMerkinnanPoistoOnnistui();
                koha.setPoistetaanMerkintaa(false);
            } else {
                tulostaja.tulostaEiOsumia();
                koha.setPoistetaanMerkintaa(false);
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
        tulostaja.otsikoiHaunAloitus();
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
    public void tulostetaanValimuisti() {
        String tuloste = timu.toString();
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
        this.koha.setPoistetaanMerkintaa(true);
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

        String seurattavia = "Seurattavia toimintoja: " + timu.laskeSeurattavienMaara();
        tulostaja.tulostaKonsoliin(seurattavia);

        ArrayList<String> seurattavaTaulu = timu.annaSeurattavatToiminnot();

        String merkintoja = "Merkintöjä: " + timu.laskeMerkintojenMaara();
        tulostaja.tulostaKonsoliin(merkintoja);

        ArrayList<Merkinta> merkintaTaulu = timu.annaMuisti();
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

    public void tallenna() {
        this.tika.yliKirjoitaTietokantatiedosto(this.timu.annaMuisti());
        this.tika.ylikirjoitaSeurattavatToiminnotTiedosto(this.timu.annaSeurattavatToiminnot());
        tulostaja.ilmoitaTallennuksenOnnistumisesta();
    }

    public void nollaaValimuisti() {
        this.timu.nollaaMuisti();
        tulostaja.ilmoitaValimuistinNollaamisesta();
    }

    public TietokantaValimuisti getTietokantaValimuisti() {
        return this.timu;
    }

    public void neuvottavaLisaamisessa() {
        tulostaja.neuvoLisaamisessa();
    }

    public void aloitaOhjelmastaPoistuminen() {
        tulostaja.tulostaOllaanPoistumassaOhjelmasta();
        tulostaja.kysyOletkoVarma();
        this.koha.setOllaanPoistumassaOhjelmasta(true);
    }

    public void ollaanPoistumassaOhjelmasta() {
        tulostaja.kysyTallenetaankoMuutokset();
        this.koha.setOllaanPoistumassaOhjelmasta(false);
        this.koha.setKysytaanPoistumisenYhteydessaTallennuksesta(true);
    }

    public void poistutaanKonteksteista() {
        this.koha.poistuKaikistaKonteksteista();
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
        this.timu.lisaaSeurattava(string);
        this.tulostaja.tulostaSeurattavanLisaaminenOnnistui(string);
    }

    private void seurattavaHaku(String hakusana) {
        ArrayList<Merkinta> osumat = new ArrayList<>();

        for (Merkinta merkinta : this.timu.annaMuisti()) {
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

        this.koha.setHakuKaynnissa(false);
    }

    private int seurattavanKaytettyAikaHaku(String hakusana) {
        int kaytettyAikaMinuutteina = 0;

        for (Merkinta merkinta : this.timu.annaMuisti()) {
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
        String[] kellonaikaOsina = tika.getDekooderi().dekoodaa(aika, '.');
        Kellonaika aikaKellonaikana = meka.luoKellonaika(kellonaikaOsina);
        String paivays = haeMuistettavastaStringistaIndeksista(0);
        Merkinta merkintaSamallaPaivalla = timu.haeMuististaMerkintaPaivayksella(paivays);

        if (merkintaSamallaPaivalla == null) {
            return false;
        }

        return merkintaSamallaPaivalla.sisaltaaTapahtumanJohonKellonaikaOsuu(aikaKellonaikana);
    }

    public void nollaaSeurattavat() {
        this.timu.nollaaSeurattavat();
        this.tulostaja.ilmoitaSeurattavienNollaamisesta();
    }

    public void lisätäänSeurattavaMuististaJaTehdaanSillaMerkinta() {
        String uusiSeurattava = haeMuistettavastaStringistaIndeksista(2);
        lisätäänSeurattava(uusiSeurattava);
        luoMerkinta(muistettavaString);
        this.muistettavaString = "";

        this.koha.setKysytaanLisataankoSeurattava(false);
    }

    private String haeMuistettavastaStringistaIndeksista(int indeksi) {
        String[] dekoodi = tika.getDekooderi().dekoodaa(muistettavaString, dekoodausMerkki);

        if (indeksi < dekoodi.length) {
            return dekoodi[indeksi];
        } else {
            return "";
        }
    }

    private boolean aikaPariOsuuJoOlemassaOleviinTapahtumiin(String aloitusaika, String lopetusaika) {
        Kellonaika[] ajat = this.meka.luoAloitusaikajaLopetusaika(aloitusaika + "-" + lopetusaika);
        String paivays = haeMuistettavastaStringistaIndeksista(0);
        Merkinta merkintaSamallaPaivalla = timu.haeMuististaMerkintaPaivayksella(paivays);

        if (merkintaSamallaPaivalla == null) {
            return false;
        }

        return merkintaSamallaPaivalla.sisaltaaTapahtumanJohonKellonaikaPariOsuu(ajat[0], ajat[1]);
    }

    public void neuvottavaOhjelmanKaytossa() {
        this.tulostaja.neuvoOhjelmanKaytossa();
    }
}
