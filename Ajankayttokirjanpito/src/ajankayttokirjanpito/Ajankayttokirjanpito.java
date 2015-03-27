/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.util.Scanner;
import tietokantasysteemi.LegacyTiedostonkasittelija;
import javax.swing.SwingUtilities;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.LegacyKayttoliittyma;
import kayttoliittyma.LegacyKomentotulkki;
import kayttoliittyma.LegacyKonsoliRajapinta;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.LegacyKonsolinKorvaajaRajapinta;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.LegacyTulostaja;
import kayttoliittyma.LegacyKonsolinKorvaaja;
import kayttoliittyma.Tulostaja;
import legacykonsoli.OmaKonsoli;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.LegacyKomentoLogiikka;
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
        LegacyKonsolinKorvaajaRajapinta lkkr = new LegacyKonsolinKorvaaja();
        Dekooderi dekooderi = new  Dekooderi();
        Tulostaja tulostaja = new Tulostaja(lkkr, dekooderi); 
        MerkinnanKasittelija merkinnankasittelija = new MerkinnanKasittelija(dekooderi);
        OmaTiedostonkasittelija tiedostonkasittelija = new OmaTiedostonkasittelija(dekooderi, merkinnankasittelija, tulostaja);
        KontekstinHaltija kontekstinHaltija = new KontekstinHaltija();               
        KomentoLogiikka komentologiikka = new KomentoLogiikka(tulostaja, tiedostonkasittelija, lkkr, kontekstinHaltija, merkinnankasittelija);
        Komentotulkki komentotulkki = new Komentotulkki(lkkr, kontekstinHaltija, komentologiikka, dekooderi);
        
        Scanner lukija = new Scanner(System.in);
        System.out.println(" # Komenna apua saadaksesi apua");
        while(kontekstinHaltija.kaynnissa) {
            String komento = lukija.nextLine();
            komentotulkki.otaKomento(komento);
        }
        //kayttoliittyma.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(null, komentotulkki));
        
        //kayttoliittyma.run();
        
        //SwingUtilities.invokeLater(kayttoliittyma);
        // TODO code application logic here
    }
}
