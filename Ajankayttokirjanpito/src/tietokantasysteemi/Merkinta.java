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
public class Merkinta implements Comparable<Merkinta>{
    private Paivays paivays;
    private ArrayList<Tapahtuma> tapahtumat;
    
    public Merkinta(Paivays paivays, Tapahtuma tapahtuma) {
        this.paivays = paivays;
        this.tapahtumat = new ArrayList<Tapahtuma>();
        this.tapahtumat.add(tapahtuma);        
    }
    
    @Override
    public int compareTo(Merkinta m) {
        return this.paivays.compareTo(m.paivays);
    }

    @Override
    public String toString() {
        String palautettava = this.paivays.toString() + "\n";
        
        for(Tapahtuma t : tapahtumat) {
            palautettava += t.toString();
        }
        
        palautettava += "\n";        
        return palautettava;        
    }

    public ArrayList<Tapahtuma> getTapahtumat() {
        return tapahtumat;
    }
     
    public void lisaaTapahtuma (Tapahtuma tapahtuma) {
        this.tapahtumat.add(tapahtuma);
    }   
     
}
