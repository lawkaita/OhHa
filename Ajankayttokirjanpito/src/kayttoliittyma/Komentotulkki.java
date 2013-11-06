/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import Tietokantasysteemi.Tiedostonkasittelija;
import ajankayttokirjanpito.Ohjelma;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Envy 6-1010
 */
public class Komentotulkki {
    private Ohjelma ohjelma;
    
    private boolean merkintaanPaiva;
    private boolean merkintaanAloitusAika;
    private boolean merkintaanLopetusAika;
    private boolean merkintaanSelostus;
    private String muistettavaString;

    public Komentotulkki(Ohjelma ohjelma) {
        this.ohjelma = ohjelma;
        
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        muistettavaString = "";
    }

    public void enter(String komento) {
        haarauta(komento);
    }
    
    public void haarauta(String komento) {
        
        if (merkintaanPaiva == true) {
            //testaaOnkoPaiva            
            muistettavaString = komento;             
            merkintaanAloitusAika();
            merkintaanPaiva = false;            
            
            return;
        }
        
        if (merkintaanAloitusAika == true) {
            //testaaOnkoAika
            muistettavaString += ": " + komento;        
            merkintaanLopetusAika();
            merkintaanAloitusAika = false;
            
            return;
        }
        
        if (merkintaanLopetusAika == true) {
            muistettavaString += "-" + komento;
            merkintaanSelostus();
            merkintaanLopetusAika = false;
            
            return;            
        }
        
        if (merkintaanSelostus == true) {
            muistettavaString += "\n" + komento;
            ohjelma.kerroLisayksesta();
            ohjelma.listaaKonsoliin(muistettavaString);
            
            tiedostoKasittely();
            
            merkintaanSelostus = false;
            muistettavaString = "";
            return;
        }
                
        tulkitse(komento);
    }
    
    public void merkintaanAloitusAika() {
        ohjelma.pyydaAloitusAikaa();
        ohjelma.getKali().getKonsoli().kirjoitaKomentoriville("hh.mm");
        merkintaanAloitusAika = true;
    }
    
    public void merkintaanLopetusAika() {
        ohjelma.pyydaLopetusAikaa();
        ohjelma.getKali().getKonsoli().kirjoitaKomentoriville(ohjelma.aika());
        merkintaanLopetusAika = true;
    }
    
    public void merkintaanSelostus() {
        ohjelma.pyydaSelostus();
        merkintaanSelostus = true;
    }

    public void tulkitse(String komento) {

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            this.ohjelma.getKali().tapa();
        }
       
        if (komento.equals("nyt")) {
            ohjelma.nyt();
            return;
        }
        
        if (komento.equals("paiva")) {
            ohjelma.paiva();
            return;
        }
        
        if (komento.equals("aika")) {
            ohjelma.aika();
            return;
        }

        if (komento.equals("apua")) {
            ohjelma.apua();
            return;
        }
        
        if (komento.equals("tulosta")) {
            ohjelma.tulostaTiedosto();
            return;
        }
        
        if (komento.equals("looppi")) {
            
        }
        
        if (komento.equals("merk")) {
            ohjelma.pyydaPaivaa();
            String paiva = ohjelma.paiva();
            ohjelma.getKali().getKonsoli().kirjoitaKomentoriville(paiva);
            this.merkintaanPaiva = true;
            return;
        }
        
        ohjelma.tulostaVirhe();
    }    

    private void tiedostoKasittely() {
        Tiedostonkasittelija tika = new Tiedostonkasittelija(); 
        try {
            tika.lisaaTietokantaan(muistettavaString);
        } catch (IOException ex) {
            //
        }
        
    }
}
