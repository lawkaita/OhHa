/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author Lauri
 */
//enterinkuuntelija on otus joka ampuu komennon jonnekin.
//toisaalta se ei ehdi laskea minne se pitäisi ampua.
public class NappaimistoKuuntelija implements KeyListener {

    private JTextField komentoalue;
    private KomentoTulkki komentotulkki;
    private KomennonJakaja jakaja;

    public NappaimistoKuuntelija(JTextField t, KomentoTulkki k, KomennonJakaja j) {
        this.komentoalue = t;
        this.komentotulkki = k;
        this.jakaja = j;
        jakaja.setKohde(this.komentotulkki);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String komento = komentoalue.getText();
            //komentotulkki.kasitteleKomento(komento);
            komentoalue.setText("");
            jakaja.jaaKomento(komento);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            jakaja.jaaKomento("painettiinOikealle");
            
        }//tämän takia tämä ei ole enää enterin kuuntelija.
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setKohde(KomennonKohde k) {
        this.jakaja.setKohde(k);
    }
    //ei kannata tehdä mitään mikä menisi muokkaamaan jotain jonka pitäisi tapahtua keyeventissä,
    //ilmeisesti siitä tulee jokin queue error.

    public void resetKohde() {
        this.jakaja.setKohde(komentotulkki);
    }
}
