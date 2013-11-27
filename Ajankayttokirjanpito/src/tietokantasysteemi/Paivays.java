/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

/**
 * Ohjelmassa käytettävä päiväyksen formaatti.
 * @author lawkaita
 */
public class Paivays implements Comparable<Paivays>{
    private int paiva;
    private int kuukausi;
    private int vuosi;
    
    public Paivays(int paiva, int kuukausi, int vuosi) {
        this.paiva = paiva;
        this.kuukausi = kuukausi;
        this.vuosi = vuosi;
    }

    /**
     * 
     * @param p paivays jota verrataan kutsuvaan paivaykseen
     * @return merkki, joka kertoo kumpi paivays on 'suurempi' eli tapahtuu myöhemmin.
     */
    @Override
    public int compareTo(Paivays p) {
        int vuosiEro = this.vuosi - p.vuosi;
        int kuukausiEro = this.kuukausi - p.kuukausi;
        int paivaEro = this.paiva - p.paiva;
        
        if (vuosiEro > 0) {
            return 1;
        } else if (vuosiEro < 0){
            return -1;
        } else {
            if(kuukausiEro > 0) {
                return 1;
            } else if (kuukausiEro < 0){
                return -1;
            } else {
                return paivaEro;
            }
        }
    }
    
    /**
     * Testaa onko kaksi annettua päiväysoliota samat.
     * 
     * @param olio verrattava Paivays-olio
     * @return true jos päiväysten vuosi, kuukausi ja päivä ovat samat, muuten false
     */
    @Override
    public boolean equals(Object olio) {
        if (olio == null) {
            return false;
        }
        
        if (this.getClass() != olio.getClass()) {
            return false;
        }
        
        Paivays verrattava = (Paivays) olio;
        
        if(this.compareTo(verrattava) != 0){
            return false;
        }
        return true;
    }
    
    /**
     * Lisää paivaykselle tarvittaessa etunollan.
     * @param i päiväyksen kuukausi- tai päiväosa
     * @return muokattu päiväyksen osa.
     */
    private String muutaIntStringiksiJaLisaaNollaJosTarvitsee(int i){
        String palautettava = "" + i;
        
        if (i < 10) {
            palautettava = "0" + palautettava;
        }
        
        return palautettava;
    }
    
    @Override
    public String toString() {
        return muutaIntStringiksiJaLisaaNollaJosTarvitsee(paiva) + "."
                + muutaIntStringiksiJaLisaaNollaJosTarvitsee(kuukausi) + "."
                + muutaIntStringiksiJaLisaaNollaJosTarvitsee(vuosi);
    }
    
    
}
