/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import konsoli.OmaKonsoli;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Ohjelman käyttöliittymä joka koostuu Konsoli-oliosta 
 * ja Nappaimistokuuntelija-oliosta.
 * Konsoli-olio on vain ohjelman tarkoitukseen koottu JPanel.
 * @author Envy 6-1010
 */
public class Kayttoliittyma implements Runnable{
    private JFrame frame;
    private OmaKonsoli konsoli;
    private Nappaimistonkuuntelija naku;
    
    public Kayttoliittyma(OmaKonsoli konsoli, Nappaimistonkuuntelija nappaimistonkuuntelija){
        this.konsoli = konsoli;
        this.naku = nappaimistonkuuntelija;        
    }
    
    /**
     * Asettaa kayttolittyman nappaimistonKuuntelijaksi annetun nappaimistonkuuntelijan.
     * @param nappaimistonkuuntelija Annettava nappaimistonkuuntelija.
     */    
    public void otaNappaimistonkuuntelija(Nappaimistonkuuntelija nappaimistonkuuntelija) {
        this.naku = nappaimistonkuuntelija;
    }
    
    /**
     * Käynnistää kayttoliittyman.
     */
    @Override
    public void run() {        
        frame = new JFrame();
        Dimension d = new Dimension(400,400);
        frame.setPreferredSize(d);
        frame.setMaximumSize(d);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocation(300, 100);
        
        konsoli.getVarsinainenKomentoRivi().requestFocus();
        
    }
    
    /**
     * Luo kayttoliittyman komponentit.
     * @param container kayttoliittyman framen container-olio.
     */
    private void luoKomponentit(Container container) {        
        container.setLayout(new BorderLayout());
                
        container.add(this.konsoli, BorderLayout.CENTER);
        konsoli.getVarsinainenKomentoRivi().addKeyListener(naku);
        konsoli.getTyhjanTilanTyhja().addKeyListener(naku);
        
    }
    
    /**
     * Palauttaa ohjelman nappaimistonkuuntellijan.
     * @return ohjelman nappaimistonkuuntelija.
     */
    public Nappaimistonkuuntelija getNappaimistonkuuntelija() {
        return this.naku;
    }
    
    /**
     * Palauttaa ohjelman konsoli-olion.
     * @return ohjelman konsoli-olio.
     */    
    public OmaKonsoli getKonsoli() {
        return this.konsoli;
    }
    
    /**
     * Tappaa ohjelman kayttoliittyma-olion.
     */
    public void tapa() {
        WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        frame.dispatchEvent(wev);
    }
    
}
