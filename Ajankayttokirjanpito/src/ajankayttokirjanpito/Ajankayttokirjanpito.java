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
        
        Konsoli konsoli = new OmaKonsoli(ubuntulla);        
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma(konsoli);            
        Dekooderi dekooderi = new  Dekooderi();
        Tulostaja tulostaja = new Tulostaja(konsoli, dekooderi); 
        MerkinnanKasittelija merkinnankasittelija = new MerkinnanKasittelija(dekooderi);
        OmaTiedostonkasittelija tiedostonkasittelija = new OmaTiedostonkasittelija(dekooderi, merkinnankasittelija, tulostaja);
        KontekstinHaltija kontekstinHaltija = new KontekstinHaltija();               
        KomentoLogiikka komentologiikka = new KomentoLogiikka(tulostaja, tiedostonkasittelija, konsoli, kontekstinHaltija, kayttoliittyma, merkinnankasittelija);
        Komentotulkki komentotulkki = new Komentotulkki(konsoli, kontekstinHaltija, komentologiikka, dekooderi);
        
        kayttoliittyma.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, komentotulkki));
        
        //kayttoliittyma.run();
        
        SwingUtilities.invokeLater(kayttoliittyma);
        // TODO code application logic here
    }
}
