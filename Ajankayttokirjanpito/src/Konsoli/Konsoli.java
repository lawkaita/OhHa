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
    private int dimensioLuku1;
   
    public Konsoli() {
        Font f = new Font("Monospaced", Font.PLAIN, 12);

        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        
        dimensioLuku1 = 15; //kotikoneella 17, koulun ubuntulla 15.
        int dimensioLuku2 = 21; //kotikoneella 15, koulun ubuntulla 21
        tuloste = new Tulostealue(f, dimensioLuku1);
        rivi = new VarsinainenKomentoRivi(f, dimensioLuku1, dimensioLuku2);
        komentorivi = new Komentorivi(f, rivi, dimensioLuku1, dimensioLuku2);
        tyhjaTila = new TyhjaTila(f, dimensioLuku1);

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
    public void paivita(String teksti) {;
        String dialogi = tuloste.getText();

        tuloste.setPreferredSize(new Dimension(400,
                (tuloste.getPreferredSize().height + dimensioLuku1)));

        tyhjaTila.madallaKorkeutta();

        tuloste.setText(dialogi + "\n" + komentorivi.getKursori().annaMerkki() + "> " + teksti);

    }

    public void tulostaViesti() {
        String viesti = "viesti";
        paivita(viesti);
    }
    
    public void tulostaKayttajanKomento() {
        paivita(rivi.getText() + tyhjaTila.getTyhja().getText());
        rivi.setText("");
        tyhjaTila.getTyhja().setText("");
        
    }

    public void jatkaKirjoitustaTyhjaanKenttaan() {
        tyhjaTila.getTyhja().setEditable(true);
        tyhjaTila.getTyhja().setFocusable(true);
        tyhjaTila.getTyhja().requestFocus();
        
    }

    public JTextField getVarsinainenKomentoRivi() {
        return this.rivi;
    }
    
    public JTextArea getTyhjanTilanTyhja(){
        return this.tyhjaTila.getTyhja();
    }
}
