/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

/**
 * Ohjelmassa käytettävän tapahtuman formaati. Tapahtuma sisältää aina aloitusajan, lopetusajan, ja lyhyen
 * selosteen siitä mitä tehtiin.
 * @author lawkaita
 */
public class Tapahtuma implements Comparable<Tapahtuma>{

    private Kellonaika aloitusaika;
    private Kellonaika lopetusaika;
    private String seloste;

    public Tapahtuma(Kellonaika aloitusaika, Kellonaika lopetusaika, String seloste) {
        this.aloitusaika = aloitusaika;
        this.lopetusaika = lopetusaika;
        this.seloste = seloste;
    }

    /**
     * Vertailee kahden eri tapahtuman suhdetta toisiinsa.
     * Se tapahtuma lasketaan toista suuremmaksi, jonka aloitusaika on suurempi.
     * Jos aloitusajat ovat yhtäsuuret, verrataan lopetusaikoja.
     * @param t verrattava tapahtuma.
     * @return luku, joka ilmaisee tapahtumien suuruusjärjestyksen.
     */
    @Override
    public int compareTo(Tapahtuma t) {
        if (this.aloitusaika.compareTo(t.aloitusaika) != 0) {
            return this.aloitusaika.compareTo(t.aloitusaika);
        } else {
            return this.lopetusaika.compareTo(t.lopetusaika);
        }
    }
    
    @Override
    public String toString(){
        return "    " + aloitusaika + "-" + lopetusaika + ": " + seloste;
    }
    
    /**
     * Laskee tapahtuman keston.
     * @return tapahtuman kesto kellonaikana.
     */
    public Kellonaika kesto() {
        return lopetusaika.aikaEro(aloitusaika);
    }   
    
}
