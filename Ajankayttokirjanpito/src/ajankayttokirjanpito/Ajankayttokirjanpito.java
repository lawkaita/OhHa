/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import tietokantasysteemi.OmaTiedostonkasittelija;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.Konsoli;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.OmaKonsoli;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.OmaAjanAntaja;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;

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
        
        Konsoli konsoli = new OmaKonsoli(ubuntulla);        
        Kayttoliittyma kali = new Kayttoliittyma(konsoli);            
        Dekooderi dekooderi = new  Dekooderi();
        MerkinnanKasittelija meka = new MerkinnanKasittelija(dekooderi);
        OmaTiedostonkasittelija tika = new OmaTiedostonkasittelija(dekooderi, meka);
        KontekstinHaltija koha = new KontekstinHaltija();
        Tulostaja tulostaja = new Tulostaja(konsoli, dekooderi);        
        KomentoLogiikka kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha, kali, meka);
        Komentotulkki kotu = new Komentotulkki(konsoli, koha, kolo, dekooderi);
        
        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        SwingUtilities.invokeLater(kali);
        // TODO code application logic here
    }
}
