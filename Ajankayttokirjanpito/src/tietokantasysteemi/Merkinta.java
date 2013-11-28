/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.util.ArrayList;
import java.util.Date;

/**
 * Ohjelmassa käytettävän merkinnän formaatti. Merkintä-olio sisältää aina
 * päivän, jona muistiin merkittävä tapahtuma tapahtui, sekä itse kaikki sinä päivänä tapahtuneet tapahtumat
 * Tapahtuma-olioina.
 * @author lawkaita
 */
public class Merkinta implements Comparable<Merkinta> {

    private Paivays paivays;
    private ArrayList<Tapahtuma> tapahtumat;

    public Merkinta(Paivays paivays, Tapahtuma tapahtuma) {
        this.paivays = paivays;
        this.tapahtumat = new ArrayList<Tapahtuma>();
        this.tapahtumat.add(tapahtuma);
    }

    public Merkinta(Paivays paivays, ArrayList<Tapahtuma> tapahtumat) {
        this.paivays = paivays;
        this.tapahtumat = tapahtumat;
    }
    
    /**
     * Vertaa kahta eri paivaa keskenaan ja palauttaa arvon riippuen siitä, kumpi
     * päivä on myöhäisempi.
     * 
     * @param m verrattava Merkinta
     * 
     * @return arvo, josta käy ilmi kumpi on myöhäisempi
     */
    @Override
    public int compareTo(Merkinta m) {
        return this.paivays.compareTo(m.paivays);
    }

    // tarvittiin tänne \r\n jotta windowsilla kirjoitus tiedostoon tapahtui oikein.
    // toisaalta se rikkoo merkinnankasittelijaTestin.
    @Override
    public String toString() {
        String palautettava = this.paivays.toString() + "\r\n";

        for (Tapahtuma t : tapahtumat) {
            palautettava += t.toString() + "\r\n";
        }
        
        //palautettava = palautettava.substring(0, palautettava.length() -2);
        //otetaan ylimääräinen \r\n lopusta pois
        
            //EIKUN        
        //viimeinen rivinvaihto tulee viimeisen tapahtuman lisäyksessä.
        //palautettava += "\r\n"; //viimeinen rivinvaihto
        return palautettava;
    }

    public ArrayList<Tapahtuma> getTapahtumat() {
        return tapahtumat;
    }

    /**
     * Lisää tapahtuman merkinnän tapahtumat-luetteloon.
     * @param tapahtuma 
     */
    public void lisaaTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtumat.add(tapahtuma);
    }
    
    /**
     * Merkinnän tapahtumien määrä.
     * @return tapahtumien määrä
     */
    public int getTapahtumienMaara() {
        return this.tapahtumat.size();
    }
    
    public Paivays getPaivays() {
        return this.paivays;
    }
    
    /**
     * Laskee merkinnan kaikkien tapahtumien yhteenlasketun ajan minuutteina.
     * @return yhteenlaskettu aika minuutteina
     */
    public int tapahtumiinKaytettyAikaMinuutteina() {
        int kaytetytMinuutit = 0;
                
        for (Tapahtuma t : this.tapahtumat) {
            kaytetytMinuutit += t.kesto().minuutteina();
        }
        
        return kaytetytMinuutit;
    }
}
