/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import tietokantasysteemi.OmaTiedostonkasittelija;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.OmaKonsoli;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.OmaAjanAntaja;
import sovelluslogiikka.KomentoLogiikka;

/**
 *
 * @author lawkaita
 */
public class Ajankayttokirjanpito {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean ubuntulla = false;
        //tämän pitää näkyä huomenna tässä
        
        OmaKonsoli konsoli = new OmaKonsoli(ubuntulla);        
        Kayttoliittyma kali = new Kayttoliittyma(konsoli, null);            
        Dekooderi dekooderi = new  Dekooderi();
        OmaTiedostonkasittelija tika = new OmaTiedostonkasittelija(dekooderi);
        KontekstinHaltija koha = new KontekstinHaltija();
        Tulostaja tulostaja = new Tulostaja(konsoli, tika, dekooderi);
        KomentoLogiikka kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha, kali);
        Komentotulkki kotu = new Komentotulkki(konsoli, koha, kolo, dekooderi);
        
        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        SwingUtilities.invokeLater(kali);
        // TODO code application logic here
    }
}
