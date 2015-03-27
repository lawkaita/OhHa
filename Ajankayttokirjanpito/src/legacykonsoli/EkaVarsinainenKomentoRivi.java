/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legacykonsoli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * Alue, johon käyttäjä varsinaisesti kirjoittaa komennot.
 * @author Envy 6-1010
 */
public class EkaVarsinainenKomentoRivi extends JTextField{
    
    public EkaVarsinainenKomentoRivi(Font f, int dimensioLuku1, int dimensioLuku2) {
        setFont(f);
        setBackground(Color.black);
        setForeground(Color.white);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(400 - dimensioLuku2, dimensioLuku1));
    }
    
}
