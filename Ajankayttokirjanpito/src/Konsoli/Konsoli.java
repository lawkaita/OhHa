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
    private EnsimmainenRivi rivi;    
    private Komentorivi komentorivi;
    private JTextArea tyhja;

    public Konsoli() {
        Font f = new Font("Monospaced", Font.PLAIN, 12);
        
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        
        tuloste = new Tulostealue(f);        
        rivi = new EnsimmainenRivi(f);
        komentorivi = new Komentorivi(f, rivi);
        
        tyhja = new JTextArea();

        tyhja.setBackground(Color.green);
        tyhja.setEditable(false);
        tyhja.setFocusable(false);
        tyhja.setPreferredSize(new Dimension(400, (400 - 2*17)));

        JPanel tekstialue = new JPanel(new BorderLayout());
        tekstialue.add(tuloste, BorderLayout.NORTH);
        tekstialue.add(komentorivi, BorderLayout.CENTER);
        tekstialue.add(tyhja, BorderLayout.SOUTH);

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
        
        tyhja.setPreferredSize(new Dimension(400, 
                Math.max(tyhja.getPreferredSize().height - 17, 18)));

        tuloste.setText(dialogi + "\n" + komentorivi.getKursori().annaMerkki() + "> " + teksti);
        rivi.setText("");

    }
    
    public void tulostaViesti(String viesti) {
        
    }
    
    public void estaMerkki() {
        try {
            rivi.setText(rivi.getText(0, rivi.getText().length() - 1));
        } catch (BadLocationException ex) {
            
        }
    }

    public JTextField getKomentoalue() {
        return this.rivi;
    }
}
