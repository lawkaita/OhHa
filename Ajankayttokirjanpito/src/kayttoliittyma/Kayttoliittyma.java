/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import Konsoli.Konsoli;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Envy 6-1010
 */
public class Kayttoliittyma implements Runnable{
    private JFrame frame;
    private Konsoli konsoli;
    private Nappaimistonkuuntelija naku;
    
    public Kayttoliittyma(){
        this.konsoli = new Konsoli(this);
        this.naku = new Nappaimistonkuuntelija(this.konsoli);        
    }
    
    @Override
    public void run() {        
        frame = new JFrame();
        Dimension d = new Dimension(400,400);
        frame.setPreferredSize(d);
        frame.setMaximumSize(d);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane(), d);
        
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocation(300, 100);
        
        konsoli.getVarsinainenKomentoRivi().requestFocus();
        
    }

    private void luoKomponentit(Container container, Dimension d) {        
        container.setLayout(new BorderLayout());
                
        container.add(this.konsoli, BorderLayout.CENTER);
        konsoli.getVarsinainenKomentoRivi().addKeyListener(naku);
        konsoli.getTyhjanTilanTyhja().addKeyListener(naku);
        
    }
    
    public Nappaimistonkuuntelija getNappaimistonkuuntelija() {
        return this.naku;
    }
    
    public Komentotulkki getKomentotulkki() {
        return this.naku.getKomentotulkki();
    }
    
    public Konsoli getKonsoli() {
        return this.konsoli;
    }
    
    public void tapa() {
        WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        frame.dispatchEvent(wev);
    }
    
}
