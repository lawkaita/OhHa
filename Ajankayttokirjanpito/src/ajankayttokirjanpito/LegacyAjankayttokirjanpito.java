/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import javax.swing.SwingUtilities;
import kayttoliittyma.LegacyKayttoliittyma;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.LegacyKomentotulkki;
import kayttoliittyma.LegacyKonsoliRajapinta;
import kayttoliittyma.LegacyTulostaja;
import kayttoliittyma.Nappaimistonkuuntelija;
import legacykonsoli.OmaKonsoli;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.LegacyKomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;
import tietokantasysteemi.LegacyTiedostonkasittelija;

/**
 *
 * @author lawkaita
 */
public class LegacyAjankayttokirjanpito {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean ubuntulla = false;
        
        LegacyKonsoliRajapinta konsoli = new OmaKonsoli(ubuntulla);        
        LegacyKayttoliittyma kayttoliittyma = new LegacyKayttoliittyma(konsoli);            
        Dekooderi dekooderi = new  Dekooderi();
        LegacyTulostaja tulostaja = new LegacyTulostaja(konsoli, dekooderi); 
        MerkinnanKasittelija merkinnankasittelija = new MerkinnanKasittelija(dekooderi);
        LegacyTiedostonkasittelija tiedostonkasittelija = new LegacyTiedostonkasittelija(dekooderi, merkinnankasittelija, tulostaja);
        KontekstinHaltija kontekstinHaltija = new KontekstinHaltija();               
        LegacyKomentoLogiikka komentologiikka = new LegacyKomentoLogiikka(tulostaja, tiedostonkasittelija, konsoli, kontekstinHaltija, kayttoliittyma, merkinnankasittelija);
        LegacyKomentotulkki komentotulkki = new LegacyKomentotulkki(konsoli, kontekstinHaltija, komentologiikka, dekooderi);
        
        kayttoliittyma.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, komentotulkki));
        
        //kayttoliittyma.run();
        
        SwingUtilities.invokeLater(kayttoliittyma);
        // TODO code application logic here
    }
}
