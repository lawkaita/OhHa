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
    
    public boolean equals(Paivays p) {
        if(this.compareTo(p) == 0){
            return true;
        }
        return false;
    }
    
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
