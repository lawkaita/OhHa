/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.util.ArrayList;
import java.util.Date;

/**
 *
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

    Merkinta(Paivays paivays, ArrayList<Tapahtuma> tapahtumat) {
        this.paivays = paivays;
        this.tapahtumat = tapahtumat;
    }

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
        
        //viimeinen rivinvaihto tulee viimeisen tapahtuman lisäyksessä.
        //palautettava += "\r\n"; //viimeinen rivinvaihto
        return palautettava;
    }

    public ArrayList<Tapahtuma> getTapahtumat() {
        return tapahtumat;
    }

    public void lisaaTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtumat.add(tapahtuma);
    }
    
    public int getTapahtumienMaara() {
        return this.tapahtumat.size();
    }
}
