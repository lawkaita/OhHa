/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Envy 6-1010
 */
public class Viisari implements ActionListener{
    private Kello k;
    
    public Viisari(Kello k) {
        this.k = k;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.k.etene();
    }
    
}
