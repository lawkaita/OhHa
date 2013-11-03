/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Konsoli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import kayttoliittyma.Paivitettava;

/**
 *
 * @author Envy 6-1010
 */
public class Konsoli extends JPanel implements Paivitettava {

    private Tulostealue tuloste;
    private VarsinainenKomentoRivi rivi;
    private Komentorivi komentorivi;
    private TyhjaTila tyhjaTila;
   
    public Konsoli() {
        Font f = new Font("Monospaced", Font.PLAIN, 12);

        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());

        tuloste = new Tulostealue(f);
        rivi = new VarsinainenKomentoRivi(f);
        komentorivi = new Komentorivi(f, rivi);
        tyhjaTila = new TyhjaTila(f);

        JPanel tekstialue = new JPanel(new BorderLayout());
        tekstialue.add(tuloste, BorderLayout.NORTH);
        tekstialue.add(komentorivi, BorderLayout.CENTER);
        tekstialue.add(tyhjaTila, BorderLayout.SOUTH);

        JScrollPane skrollausAlue = new JScrollPane(tekstialue);
        skrollausAlue.setPreferredSize(new Dimension(400, 400));
        //skrollausAlue.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        skrollausAlue.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(skrollausAlue, BorderLayout.CENTER);
    }

    @Override
    public void paivita() {
        String teksti = rivi.getText();
        String dialogi = tuloste.getText();

        tuloste.setPreferredSize(new Dimension(400,
                (tuloste.getPreferredSize().height + 17)));

        tyhjaTila.asetaUusiDimensio();

        tuloste.setText(dialogi + "\n" + komentorivi.getKursori().annaMerkki() + "> " + teksti);
        rivi.setText("");

    }

    public void tulostaViesti(String viesti) {
    }

    public void jatkaKirjoitustaTyhjaanKenttaan() {
        tyhjaTila.getTyhja().setEditable(true);
        tyhjaTila.getTyhja().setFocusable(true);
        tyhjaTila.getTyhja().requestFocus();
        
    }

    public JTextField getVarsinainenKomentoRivi() {
        return this.rivi;
    }
}
