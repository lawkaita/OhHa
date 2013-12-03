/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Konsolin osa, jossa voi selata käyttäjän ja ohjelman dialogia.
 * @author lawkaita
 */
public class Skrollausnakyma {

    private JScrollPane skrollausalue;
    private boolean paivitetaan;

    public Skrollausnakyma(JScrollPane skrollausalue, int dimensioLuku1) {
        this.skrollausalue = skrollausalue;
        this.paivitetaan = false;

        skrollausalue.setPreferredSize(new Dimension(400, dimensioLuku1));
        skrollausalue.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        skrollausalue.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        skrollausalue.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        skrollausalue.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (paivitetaan == true) {                
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());                
                }       
                
                paivitetaan = false;                
            }
        });

    }

    public JScrollPane getSkrollausalue() {
        return this.skrollausalue;
    }
    
    /**
     * Vie komponentin näkymän alas.
     */
    public void skrollaaAlas() {
        JScrollBar vertical = skrollausalue.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
    
    public void setPaivitetaan(boolean b) {
        this.paivitetaan = b;
    }
}
