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

    /**
     * Vertaa tätä kellonaikaa annetuun kellonaikaan myöhemmyyden perusteella.
     * @param k verrattava kellonaika
     * @return luku, jonka merkki kertoo kumpi aika on suurempi, tai ovatko ne yhtäsuuret.
     */
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
    
    /**
     * Tutkii onko annettu olio sama kuin tämä kellonaika.
     * @param olio annettu olio
     * @return true jos kellonajat ovat yhtä myöhäiset, muuten false
     */
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

    /**
     * Muokkaa kellonajan osan etunollamuotoon tarvittaessa.
     * @param i kellonajan tunti- tai minuuttiosa
     * @return oikean muotoinen kellonajan tunti- tai minuuttiosa Stringinä
     */
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

    /**
     * Luo kellonajan, joka ilmaisee kahden kellonajan eron.
     * @param aikaisempi Kellonaika, jonka suhteen ero lasketaan
     * @return Kellonaika, joka ilmaisee eron suuruuden
     */
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
    
    /**
     * Laskee kahden kellonaika-olion summan.
     * @param toinenAika summassa käytettävä toinen kellonaika.
     * @return kellonaikojen suuruudet suhteessa aikaan 00.00 summattuina.
     */
    public int aikaSummaMinuutteina(Kellonaika toinenAika) {
        return this.minuutteina() + toinenAika.minuutteina();
    }
    
    /**
     * Muuntaa kellonajan minuuttiesityksen tunneiksi ja minuuteiksi
     * @param aikaSumma annetun kellonajan minuuttiesitys
     * @return kellonaika minuutteina ja tunteina suhteessa aikaan 00.00
     */
    public String minuuttiSummaTunneiksiJaMinuuteiksi(int aikaSumma) {
        return "" + aikaSumma%60;
    }
    
    /**
     * Muuntaa kellonajan minuuteiksi suhteessa aikaan 00.00.
     * @return Kellonajan minuuttiesitys suhteessa aikaan 00.00
     */
    public int minuutteina() {
        return this.tunnit * 60 + this.minuutit;
    }
}
