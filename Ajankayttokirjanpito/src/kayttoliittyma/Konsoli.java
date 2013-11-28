/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Envy 6-1010
 */
public interface Konsoli {
    void paivita(String s, boolean b);

    public JTextField getVarsinainenKomentoRivi();

    public void tulostaViesti(String s);

    public JTextArea getTyhjanTilanTyhja();

    public void kirjoitaKomentoriville(String paiva);

    public void tulostaKomento();

    public void estaLiianPitkaKomento();

    public JTextArea getTulosteAlue();
    
}
