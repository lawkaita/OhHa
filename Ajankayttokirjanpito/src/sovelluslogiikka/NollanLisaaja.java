/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Huolehtii siitä, että annettuun olioon kirjoitetaan etunolla,
 * String sisältää vain yhden merkin.
 * @author Envy 6-1010
 */
public class NollanLisaaja {
    
    /**
     * Muuttaa annetun luvun String-olioksi ja lisää etunollan jos tarvitsee.
     * @param i annettu luku
     * @return luku String-oliona, johon on lisätty tarvittaessa etunolla
     */
    public String lisaaNollaJosTarvitsee(int i) {
        String palautettava = "" + i;
        
        if (i < 10) {
            palautettava = "0" + palautettava;
        }
        
        return palautettava;
    }
    
    /**
     * Lisää annettuun string-olioon nollan jos tarvitsee.
     * @param s käsiteltävä String
     */
    public void lisaaNollaJosTarvitsee(String s) {
        if (s.length() == 1) {
            s = "0" + s;
        }
    }
}
