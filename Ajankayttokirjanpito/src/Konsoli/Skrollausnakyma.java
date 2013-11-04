/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Konsoli;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author lawkaita
 */
public class Skrollausnakyma {
    private JScrollPane skrollausalue;
    private int dimensioLuku1;

    public Skrollausnakyma(JScrollPane skrollausalue, int dimensioLuku1) {
        this.skrollausalue = skrollausalue;
        this.dimensioLuku1 = dimensioLuku1;

        skrollausalue.setPreferredSize(new Dimension(200, dimensioLuku1));
        //skrollausalue.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        skrollausalue.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        skrollausalue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }
    
    public JScrollPane getSkrollausalue() {
        return this.skrollausalue;
    }

    public void skrollaaAlas() {
        //JScrollBar vertical = skrollausalue.getVerticalScrollBar();
        //vertical.setValue(vertical.getMaximum());
    }
}
