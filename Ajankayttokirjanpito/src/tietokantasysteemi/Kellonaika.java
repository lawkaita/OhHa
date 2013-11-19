/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

/**
 * Ohjelmassa käytettävä kellonajan muoto.
 * @author lawkaita
 */
public class Kellonaika implements Comparable<Kellonaika> {

    private int tunnit;
    private int minuutit;

    public Kellonaika(int tunnit, int minuutit) {
        this.tunnit = tunnit;
        this.minuutit = minuutit;
    }

    @Override
    public int compareTo(Kellonaika k) {
        int tuntiero = this.tunnit - k.tunnit;
        int minuuttiero = this.minuutit - k.minuutit;
        
        if (tuntiero > 0) {
            return 1;
        } else if (tuntiero < 0) {
            return -1;
        } else {
            return minuuttiero;
        }
    }

    private String muutaIntStringiksiJaLisaaNollaJosTarvitsee(int i) {
        String palautettava = "" + i;

        if (i < 10) {
            palautettava = "0" + palautettava;
        }

        return palautettava;
    }
    
    @Override
    public String toString() {
        return muutaIntStringiksiJaLisaaNollaJosTarvitsee(tunnit) + "."
                + muutaIntStringiksiJaLisaaNollaJosTarvitsee(minuutit);
    }
}
