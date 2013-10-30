/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Envy 6-1010
 */
public class KomennonJakaja {
    private KomennonKohde kohde;
    
    //tämä on kaksihaarainen putki
    public void jaaKomento(String komento) {               
        kohde.kasitteleKomento(komento);
    }
    
    public void setKohde(KomennonKohde k) {
        this.kohde = k;
    }
}
