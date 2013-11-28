/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import konsoli.OmaKonsoli;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Nappaimistokuuntelija seuraa nappaimiston tapahtumia. 
 * Enter-näppäimellä annetaan konsoliin kirjoitettu komento komentotulkille ja 
 * esc-näppäimellä poistutaan käynnissäolevasta komentokontekstista. 
 * Nappaimistokuuntelija myös valvoo, ettei konsoliin ole kirjoitettu 
 * liian pitkää komentoa.
 * @author Envy 6-1010
 */
public class Nappaimistonkuuntelija implements KeyListener{
    private Konsoli konsoli;
    private Komentotulkki komentotulkki;
    
    public Nappaimistonkuuntelija(Konsoli konsoli, Komentotulkki komentotulkki) {
        this.konsoli = konsoli;
        this.komentotulkki = komentotulkki;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER){
            painettiinEnter();
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            painettiinEsc();            
        }
        
        //estaLiianPitkaKomentoKonsolissa();
                
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    private void painettiinEnter() {
        komentotulkki.otaKomento();
    }

    private void painettiinEsc() {
        komentotulkki.keskeytaKaikki();
    }

    private void estaLiianPitkaKomentoKonsolissa() {
        if (konsoli.getVarsinainenKomentoRivi().getText().length() > 30) {
            //konsoli.jatkaKirjoitustaTyhjaanKenttaan();
            konsoli.estaLiianPitkaKomento();
        }
    }
    
}
