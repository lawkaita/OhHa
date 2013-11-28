/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author Envy 6-1010
 */
public class NollanLisaaja {
    
    public String lisaaNollaJosTarvitsee(int i) {
        String palautettava = "" + i;
        
        if (i < 10) {
            palautettava = "0" + palautettava;
        }
        
        return palautettava;
    }
    
    public void lisaaNollaJosTarvitsee(String s) {
        if (s.length() == 1) {
            s = "0" + s;
        }
    }
}
