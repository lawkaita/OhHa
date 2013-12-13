/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Ohjelman välimuistina toimiva olio, johon kaikki muutokset tallenetaan suoraan,
 * ennenkuin ne tallenetaan levylle.
 * @author Envy 6-1010
 */
public class OmaTietokantaValimuisti implements TietokantaValimuisti {

    private Tiedostonkasittelija tiedostonkasittelija;
    private ArrayList<Merkinta> muisti;
    private ArrayList<String> seurattavatToiminnot;

    public OmaTietokantaValimuisti(Tiedostonkasittelija tiedostonkasittelija) {
        this.tiedostonkasittelija = tiedostonkasittelija;
        this.muisti = tiedostonkasittelija.lataaTietokanta();
        this.seurattavatToiminnot = tiedostonkasittelija.lataaSeurattavatToiminnot();
    }

    /**
     * Laskee kuinka monta päivää on merkitty tietokantaan.
     *
     * @return päivien määrä
     */
    @Override
    public int laskeMerkintojenMaara() {
        return this.muisti.size();
    }

    /**
     * Hakee välimuistista Merkintaa annetulla päiväyksellä.
     *
     * @param paivays annettu päiväys
     * @return Kopio lötyneestä merkinnasta tai muuten null
     */
    @Override
    public Merkinta haeMuististaMerkintaPaivayksella(String paivays) {
        for (Merkinta merkinta : this.muisti) {
            if (merkinta.getPaivays().toString().equals(paivays)) {
                return merkinta;
            }
        }
        return null;
    }

    /**
     * Katsoo onko kannassa merkintää annetulla päivällä
     *
     * @param paivays annetty päiväys
     * @return true jos päiväys löytyi, false jos ei.
     */
    @Override
    public boolean kannassaOnMerkintaPaivalla(String paivays) {
        for (Merkinta merkinta : this.muisti) {
            if (merkinta.getPaivays().toString().equals(paivays)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Poistaa kannasta merkinnän annetun päiväyksen perusteella.
     *
     * @param paivays annettu päiväys
     */
    @Override
    public boolean poistaMerkintaPaivanPerusteella(String paivays) {
        int indeksi = haeMuististaMerkinnanIndeksiPaivayksenPerusteella(paivays);
        if (indeksi != -1) {
            this.muisti.remove(indeksi);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hakee muistista paikkaindeksin annetun päiväyksen perusteella.
     *
     * @param paivays annettu päiväys
     * @return päiväyksen indeksi muistissa, tai -1 jos ei osumaa.
     */
    private int haeMuististaMerkinnanIndeksiPaivayksenPerusteella(String paivays) {
        int paivanIndeksi = 0;
        int muistinKoko = this.muisti.size();

        while (paivanIndeksi < muistinKoko) {
            if (this.muisti.get(paivanIndeksi).getPaivays().toString().equals(paivays)) {
                return paivanIndeksi;
            }

            paivanIndeksi++;
        }

        return -1;
    }

    @Override
    public ArrayList<Merkinta> annaMuisti() {
        return this.muisti;
    }

    /**
     * Lisää merkinnän välimuistiin.
     *
     * @param uusiMerkinta lisättävä merkintä
     */
    @Override
    public void lisaaMerkinta(Merkinta uusiMerkinta) {
        this.muisti.add(uusiMerkinta);
        Collections.sort(this.muisti);
    }

    /**
     * Lisää merkinnän yhdistäen sen tietokannan vanhaan saman päivän
     * merkintään.
     *
     * @param uusiMerkinta yhdistettävä merkintä.
     */
    @Override
    public void lisaaMerkintaYhdistaen(Merkinta uusiMerkinta) {
        int vanhanMerkinnanIndeksi = haeMuististaMerkinnanIndeksiPaivayksenPerusteella(uusiMerkinta.getPaivays().toString());
        Merkinta vanhaMerkinta = this.muisti.get(vanhanMerkinnanIndeksi);
        this.tiedostonkasittelija.getMerkinnanKasittelija().yhdista(vanhaMerkinta, uusiMerkinta);
    }

    /**
     * Kirjoittaa koko välimuistin Stringiksi.
     *
     * @return välimuisti String-oliona.
     */
    @Override
    public String toString() {
        String toString = "";
        for (Merkinta merkinta : this.muisti) {
            toString += merkinta.toString() + "\r\n";
        }
        return toString;
    }

    /**
     * Nollaa ohjelman valimuistin.
     */
    @Override
    public void nollaaMuisti() {
        this.muisti = new ArrayList<Merkinta>();
    }

    /**
     * Antaa seurattavien toimintojen listan
     * @return seurattavien toimintojen lista
     */
    @Override
    public ArrayList<String> annaSeurattavatToiminnot() {
        return this.seurattavatToiminnot;
    }

    /**
     * Lisää seurattavien toimintojen listaan annetun String-olion.
     * @param string annettu String.
     */
    @Override
    public void lisaaSeurattava(String string) {
        this.seurattavatToiminnot.add(string);
    }

    /**
     * Tarkistaa onko kannassa seurattavaa toimintoa annetulla String-oliolla.
     * @param komento annettu String olio
     * @return true jos seurattavien toimintojen listalla on annettu String-olio, muuten false
     */
    @Override
    public boolean kannassaOnSeurattavaToiminta(String komento) {
        for (String seurattava : this.seurattavatToiminnot) {
            if (seurattava.equals(komento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Laskee, kuinka monta String-oliota seurattavien toimintojen listalla on.
     * @return seurattavien toimintojen määrä
     */
    @Override
    public int laskeSeurattavienMaara() {
        return this.seurattavatToiminnot.size();
    }

    /**
     * Nollaa seurattavien toimintojen listan.
     */
    @Override
    public void nollaaSeurattavat() {
        this.seurattavatToiminnot = new ArrayList<String>();
    }

    /**
     * Poistaa seurattavien toimintojen listalta annetun String-olion.
     * @param komento annettu string olio
     * @return true jos annettu olio löytyi ja poistettiinm, muuten false
     */
    @Override
    public boolean poistaSeurattava(String komento) {
        int indeksi = haeSeurattavanIndeksi(komento);
        if (indeksi != -1) {
            this.seurattavatToiminnot.remove(indeksi);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hakee annetun seurattavan toiminnon indeksin seurattavien toimintojen listalta.
     * @param seurattava annettu String-olio
     * @return annetun String-olion indeksi seurattavien listalta, tai -1 jos annettua 
     * oliota ei löydy.
     */
    private int haeSeurattavanIndeksi(String seurattava) {
        int indeksi = 0;
        int seurattavia = this.seurattavatToiminnot.size();

        while (indeksi < seurattavia) {
            if (this.seurattavatToiminnot.get(indeksi).equals(seurattava)) {
                return indeksi;
            }

            indeksi++;
        }

        return -1;

    }
}
