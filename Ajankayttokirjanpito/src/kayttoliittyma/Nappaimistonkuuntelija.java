/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import Konsoli.Konsoli;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Envy 6-1010
 */
public class Nappaimistonkuuntelija implements KeyListener{
    private Konsoli konsoli;
    
    public Nappaimistonkuuntelija(Konsoli konsoli) {
        this.konsoli = konsoli;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER){
            konsoli.tulostaJaSuoritaKayttajanKomento();            
        }
        
        if (konsoli.getVarsinainenKomentoRivi().getText().length() > 30) {
            konsoli.jatkaKirjoitustaTyhjaanKenttaan();
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_V) {
            konsoli.getKomentotulkki().v();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    public Komentotulkki getKomentotulkki() {
        return this.konsoli.getKomentotulkki();
    }
    
}
