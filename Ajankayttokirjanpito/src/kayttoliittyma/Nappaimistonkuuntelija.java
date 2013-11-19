/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import konsoli.Konsoli;
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
            komentotulkki.otaKomento();
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            komentotulkki.keskeytaKaikki();
        }
        
        if (konsoli.getVarsinainenKomentoRivi().getText().length() > 30) {
            //konsoli.jatkaKirjoitustaTyhjaanKenttaan();
            konsoli.estaLiianPitkaKomento();
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}
