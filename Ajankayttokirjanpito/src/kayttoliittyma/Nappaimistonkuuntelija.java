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
    private Komentotulkki komentotulkki;
    
    public Nappaimistonkuuntelija(Komentotulkki komentotulkki) {
        this.komentotulkki = komentotulkki;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER){
            komentotulkki.enter();
        }
        
        if (komentotulkki.getKonsoli().getVarsinainenKomentoRivi().getText().length() > 10) {
            komentotulkki.getKonsoli().jatkaKirjoitustaTyhjaanKenttaan();
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_V) {
            komentotulkki.v();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}
