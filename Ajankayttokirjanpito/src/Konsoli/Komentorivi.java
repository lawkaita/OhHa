/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Konsoli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Envy 6-1010
 */
public class Komentorivi extends JPanel {

    private JTextField komentoriviMerkki;
    private Kursori kursori;

    public Komentorivi(Font f, VarsinainenKomentoRivi rivi) {
        komentoriviMerkki = new JTextField(">< ");
        komentoriviMerkki.setFont(f);
        komentoriviMerkki.setEditable(false);
        komentoriviMerkki.setFocusable(false);
        komentoriviMerkki.setBackground(Color.blue);
        komentoriviMerkki.setForeground(Color.white);
        komentoriviMerkki.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        komentoriviMerkki.setPreferredSize(new Dimension(15, 17));

        kursori = new Kursori(komentoriviMerkki);
        kursori.start();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(400, 17));
        this.add(komentoriviMerkki, BorderLayout.WEST);
        this.add(rivi, BorderLayout.EAST);
    }
    
    public Kursori getKursori() {
        return this.kursori;
    } 
    
}
