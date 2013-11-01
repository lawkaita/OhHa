/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JTextArea;

/**
 *
 * @author Lauri
 */
public class Kirjoittaja{
    private JTextArea tekstiAlue;
    
    public Kirjoittaja( JTextArea t) {
        this.tekstiAlue = t;
    }
    
    public void lisaanl(String s) {
        this.tekstiAlue.append(s + "\n");   
        this.tekstiAlue.setCaretPosition(this.tekstiAlue.getDocument().getLength());
    }
    
    //ei ole käytetty vielä
    public void lueKomento() {
        this.tekstiAlue.getText();
    }
    
    public void korvaa(String s) {
        this.tekstiAlue.setText(s);
    }
}
