/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextArea;

/**
 * Konsolin osa, johon kirjoitetaan käyttäjän ja ohjelman dialogi.
 * @author Envy 6-1010
 */
public class Tulostealue extends JTextArea {
    
    public Tulostealue(Font f, int dimensioLuku1) {
        super(" # Komenna apua saadaksesi apua");
        
        setFont(f);
        setEditable(false);
        setFocusable(false);
        setBackground(Color.black);
        setForeground(Color.white);
        setPreferredSize(new Dimension(400, dimensioLuku1));
    }
    
}
