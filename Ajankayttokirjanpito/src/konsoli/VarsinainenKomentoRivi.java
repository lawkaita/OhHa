/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * Se alue, johon käyttäjä kirjoittaa komennot.
 * @author Envy 6-1010
 */
public class VarsinainenKomentoRivi extends JTextField{
    
    public VarsinainenKomentoRivi(Font f, int dimensioLuku1, int dimensioLuku2) {
        setFont(f);
        setBackground(Color.black);
        setForeground(Color.white);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(400 - dimensioLuku2, dimensioLuku1));
    }
    
}
