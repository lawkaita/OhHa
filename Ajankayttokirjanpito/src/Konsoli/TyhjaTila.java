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
import javax.swing.JTextArea;

/**
 *
 * @author Envy 6-1010
 */
public class TyhjaTila extends JPanel {
    private JTextArea tyhja;
    private JTextArea marginaali;

    public TyhjaTila(Font f) {
        this.setPreferredSize(new Dimension (400, (400 - 2* 17)));
        
        tyhja = new JTextArea();
        tyhja.setFont(f);
        tyhja.setBackground(Color.green);
        tyhja.setForeground(Color.white);
        tyhja.setEditable(false);
        tyhja.setFocusable(false);
        tyhja.setPreferredSize(new Dimension(350, (400 - 2 * 17)));
        tyhja.setLineWrap(true);
        tyhja.setWrapStyleWord(true);
        
        marginaali = new JTextArea();
        marginaali.setBackground(Color.black);
        marginaali.setEditable(false);
        marginaali.setFocusable(false);
        marginaali.setPreferredSize(new Dimension(50, 400 - 2 * 17));
        
        this.setLayout(new BorderLayout());
        this.add(tyhja, BorderLayout.WEST);
        this.add(marginaali, BorderLayout.EAST);
    }
    
    public JTextArea getTyhja() {
        return this.tyhja;
    }
    
    public void asetaUusiDimensio() {
        this.setPreferredSize(new Dimension (400,
                Math.max(tyhja.getPreferredSize().height - 17, 18)));        
        tyhja.setPreferredSize(new Dimension(350,
                Math.max(tyhja.getPreferredSize().height - 17, 18)));
        marginaali.setPreferredSize(new Dimension(50,
                Math.max(tyhja.getPreferredSize().height - 17, 18)));
    }
}
