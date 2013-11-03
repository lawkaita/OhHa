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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import kayttoliittyma.Paivitettava;

/**
 *
 * @author Envy 6-1010
 */
public class Konsoli extends JPanel implements Paivitettava {

    private JTextArea tuloste;
    private JTextField komentoalue;
    private JTextField komentoriviMerkki;
    private Kursori kursori;
    private JTextArea tyhja;

    public Konsoli() {
        tuloste = new JTextArea("hei");
        komentoalue = new JTextField();
        komentoriviMerkki = new JTextField(">< ");
        kursori = new Kursori(komentoriviMerkki);
        kursori.start();
        tyhja = new JTextArea();

        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());

        Font f = new Font("Monospaced", Font.PLAIN, 12);

        tuloste.setFont(f);
        tuloste.setEditable(false);
        tuloste.setFocusable(false);
        tuloste.setBackground(Color.black);
        tuloste.setForeground(Color.white);
        tuloste.setPreferredSize(new Dimension(400, 17));

        komentoalue.setFont(f);
        komentoalue.setBackground(Color.black);
        komentoalue.setForeground(Color.white);
        komentoalue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        komentoalue.setPreferredSize(new Dimension(385, 17));
        
        komentoriviMerkki.setFont(f);
        komentoriviMerkki.setEditable(false);
        komentoriviMerkki.setFocusable(false);
        komentoriviMerkki.setBackground(Color.black);
        komentoriviMerkki.setForeground(Color.white);
        komentoriviMerkki.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        komentoriviMerkki.setPreferredSize(new Dimension(15, 17));
        
        JPanel komentorivi = new JPanel(new BorderLayout());
        komentorivi.setPreferredSize(new Dimension(400, 17));
        komentorivi.add(komentoriviMerkki, BorderLayout.WEST);
        komentorivi.add(komentoalue, BorderLayout.EAST);

        tyhja.setBackground(Color.black);
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
        String teksti = komentoalue.getText();
        String dialogi = tuloste.getText();

        tuloste.setPreferredSize(new Dimension(400,
                (tuloste.getPreferredSize().height + 17)));
        
        tyhja.setPreferredSize(new Dimension(400, 
                Math.max(tyhja.getPreferredSize().height - 17, 18)));

        tuloste.setText(dialogi + "\n" + kursori.annaMerkki() + "> " + teksti);
        komentoalue.setText("");

    }
    
    public void lisaaRivinVaihto() {
        String sisalto = komentoalue.getText();
        sisalto = sisalto + "\n";
        komentoalue.setText("");
        komentoalue.setText(sisalto);
    }

    public JTextField getKomentoalue() {
        return this.komentoalue;
    }
}
