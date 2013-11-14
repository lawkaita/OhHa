/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import tietokantasysteemi.Tiedostonkasittelija;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.Konsoli;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.AjanAntaja;

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
        Kayttoliittyma kali = new Kayttoliittyma(konsoli, null);            
        Dekooderi dekooderi = new  Dekooderi();
        AjanAntaja ajan = new AjanAntaja();
        Tiedostonkasittelija tika = new Tiedostonkasittelija(dekooderi);
        Tulostaja tulostaja = new Tulostaja(kali, tika, ajan, dekooderi);
        Komentotulkki kotu = new Komentotulkki(tulostaja, tika, konsoli);
        
        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        SwingUtilities.invokeLater(kali);
        // TODO code application logic here
    }
}
