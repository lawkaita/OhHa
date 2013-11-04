/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import Tietokantasysteemi.Tiedostonkasittelija;
import java.io.IOException;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import Konsoli.Konsoli;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.Nappaimistonkuuntelija;

/**
 *
 * @author lawkaita
 */
public class Ajankayttokirjanpito {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Konsoli konsoli = new Konsoli();
        Komentotulkki komentotulkki = new Komentotulkki(konsoli);        
        Nappaimistonkuuntelija kuuntelija = new Nappaimistonkuuntelija(komentotulkki);
        
        Kayttoliittyma kali = new Kayttoliittyma(konsoli, kuuntelija);
        Tiedostonkasittelija tika = new Tiedostonkasittelija();       
        
        
        try {
            tika.lisaaTietokantaan("112");
        } catch (IOException ex) {
            
        }
        
        SwingUtilities.invokeLater(kali);
        // TODO code application logic here
    }
}
