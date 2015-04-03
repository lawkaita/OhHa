/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.util.Scanner;
import legacykayttoliittyma.MainThread;
import legacykayttoliittyma.Komentotulkki;
import legacykayttoliittyma.KontekstinHaltija;
import legacykayttoliittyma.LegacyKonsolinKorvaajaRajapinta;
import legacykayttoliittyma.LegacyKonsolinKorvaaja;
import legacykayttoliittyma.Tulostaja;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;
import tietokantasysteemi.OmaTiedostonkasittelija;

/**
 *
 * @author lawkaita
 */
public class Ajankayttokirjanpito {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //boolean ubuntulla = false;
        
        //LegacyKonsoliRajapinta konsoli = new OmaKonsoli(ubuntulla);        
        //Kayttoliittyma kayttoliittyma = new Kayttoliittyma(konsoli);
        
        MainThread mt = new MainThread();
        mt.run();
        //kayttoliittyma.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(null, komentotulkki));
        
        //kayttoliittyma.run();
        
        //SwingUtilities.invokeLater(kayttoliittyma);
        // TODO code application logic here
    }
}
