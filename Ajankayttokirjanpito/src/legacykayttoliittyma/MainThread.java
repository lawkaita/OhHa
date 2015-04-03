/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legacykayttoliittyma;

import java.util.HashMap;
import java.util.Scanner;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;
import tietokantasysteemi.OmaTiedostonkasittelija;

/**
 *
 * @author Envy 6-1010
 */
public class MainThread {    
    public void run() {
        String lastInput = "null";
        HashMap parsed = null;        
        
        LegacyKonsolinKorvaajaRajapinta lkkr = new LegacyKonsolinKorvaaja();
        Dekooderi dekooderi = new  Dekooderi();
        Tulostaja tulostaja = new Tulostaja(lkkr, dekooderi); 
        MerkinnanKasittelija merkinnankasittelija = new MerkinnanKasittelija(dekooderi);
        OmaTiedostonkasittelija tiedostonkasittelija = new OmaTiedostonkasittelija(dekooderi, merkinnankasittelija, tulostaja);
        KontekstinHaltija kontekstinHaltija = new KontekstinHaltija();               
        KomentoLogiikka komentologiikka = new KomentoLogiikka(tulostaja, tiedostonkasittelija, lkkr, kontekstinHaltija, merkinnankasittelija);
        Komentotulkki komentotulkki = new Komentotulkki(lkkr, kontekstinHaltija, komentologiikka, dekooderi);
        
        Parser parser = new Parser();
        
        Scanner lukija = new Scanner(System.in);
        System.out.println(" # Komenna apua saadaksesi apua");
        
        while(true) {
            lastInput = lukija.nextLine();
            parsed = parser.parse();
            //komentotulkki.otaKomento(komento);
        }
    }
    
}
