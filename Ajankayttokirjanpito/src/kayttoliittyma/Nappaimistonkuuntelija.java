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
    private Konsoli paivitettava;
    
    public Nappaimistonkuuntelija(Konsoli paivitettava) {
        this.paivitettava = paivitettava;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER){
            paivitettava.paivita();
        }
        
        if (paivitettava.getVarsinainenKomentoRivi().getText().length() > 30) {
            paivitettava.jatkaKirjoitustaTyhjaanKenttaan();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}
