/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

/**
 * Ohjelmassa käytettävä kellonajan muoto.
 *
 * @author lawkaita
 */
public class Kellonaika implements Comparable<Kellonaika>  {

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
    
    @Override
    public boolean equals(Object olio) {
        if (olio == null) {
            return false;
        }
        
        if (this.getClass() != olio.getClass()) {
            return false;
        }
        
        Kellonaika verrattavaa = (Kellonaika) olio;
        
        if(this.compareTo(verrattavaa) != 0) {
            return false;
        }
        
        return true;
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

    public Kellonaika aikaEro(Kellonaika aikaisempi) {
        if (this.compareTo(aikaisempi) > 0) {
            int tuntiEro = this.tunnit - aikaisempi.tunnit;
            int minuuttiEro = this.minuutit - aikaisempi.minuutit;
            
            if (minuuttiEro < 0) {
                tuntiEro--;
                minuuttiEro = 60 + minuuttiEro;
            }
            return new Kellonaika(tuntiEro, minuuttiEro);
        }
        
        return aikaisempi.aikaEro(this);        
    }
    
    public int aikaSummaMinuutteina(Kellonaika toinenAika) {
        return this.minuutteina() + toinenAika.minuutteina();
    }
    
    public String minuuttiSummaTunneiksiJaMinuuteiksi(int aikaSumma) {
        return "" + aikaSumma%60;
    }
    
    public int minuutteina() {
        return this.tunnit * 60 + this.minuutit;
    }
}
