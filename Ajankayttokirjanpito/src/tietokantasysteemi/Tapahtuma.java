/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

/**
 *
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
    
    
}
