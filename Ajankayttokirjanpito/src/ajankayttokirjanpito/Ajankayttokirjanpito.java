/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import Tietokantasysteemi.Tiedostonkasittelija;
import java.io.IOException;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;

/**
 *
 * @author lawkaita
 */
public class Ajankayttokirjanpito {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        Kayttoliittyma kali = new Kayttoliittyma();
        Tiedostonkasittelija tika = new Tiedostonkasittelija();       
        
        
        try {
            tika.lisaaTietokantaan("112");
        } catch (IOException ex) {
            
        }
        
        SwingUtilities.invokeLater(kali);
        // TODO code application logic here
    }
}
