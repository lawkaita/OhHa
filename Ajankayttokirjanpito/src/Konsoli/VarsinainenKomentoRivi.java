/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Konsoli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

/**
 *
 * @author Envy 6-1010
 */
public class VarsinainenKomentoRivi extends JTextField{
    
    public VarsinainenKomentoRivi(Font f) {
        setFont(f);
        setBackground(Color.red);
        setForeground(Color.white);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(385, 17));
    }
    
}
