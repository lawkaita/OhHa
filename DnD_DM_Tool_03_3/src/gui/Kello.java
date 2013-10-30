/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.Timer;
import tool.TaistelijatLista;

/**
 *
 * @author Envy 6-1010
 */
public class Kello {

    private int sek;
    int min;
    private Kirjoittaja kirjoittaja;
    private Timer r;
    private boolean ekaKutsu;

    public Kello(Kirjoittaja kirjoittaja) {
        this.sek = 0;
        this.min = 0;
        this.kirjoittaja = kirjoittaja;
        this.r = new Timer(1000, new Viisari(this));
        this.ekaKutsu = true;
    }

    public void start() {
        if (!ekaKutsu) {
            stop();
        }

        sek = 0;
        min = 0;

        r.stop();
        r.start();



        ekaKutsu = false;
    }

    void etene() {
        sek++;

        if (sek == 60) {
            sek = 0;
            min++;

            this.kirjoittaja.lisaanl("Minuutteja mennyt: " + min + " min");
        }

    }
    
    public void stopKokonaan() {
        this.r.stop();
    }

    public void stop() {
        String aika = aikaFormat();

        this.kirjoittaja.lisaanl("Aika: " + aika);
    }

    private String aikaFormat() {
        double sekunteja = (double) sek / 100;
        double minuutteja = (double) min;
        double aika = minuutteja + sekunteja;

        DecimalFormat df = new DecimalFormat("#0.0#");
        String tuloste = df.format(aika);

        char c = ",".charAt(0);
        char c1 = ":".charAt(0);

        String uusituloste = tuloste.replace(c, c1);

        return uusituloste;
    }
}
