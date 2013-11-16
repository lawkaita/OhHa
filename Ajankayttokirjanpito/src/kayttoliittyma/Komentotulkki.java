/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.Dekooderi;
import tietokantasysteemi.Tiedostonkasittelija;
import java.io.IOException;
import konsoli.Konsoli;
import sovelluslogiikka.Ajantestaaja;
import tietokantasysteemi.Merkinta;

/**
 *
 * @author Envy 6-1010
 */
public class Komentotulkki {

    private Tulostaja tulostaja;
    private Ajantestaaja ajantestaaja;
    private Tiedostonkasittelija tika;
    private Konsoli konsoli;
    
    private boolean merkintaanPaiva;
    private boolean merkintaanAloitusAika;
    private boolean merkintaanLopetusAika;
    private boolean merkintaanSelostus;
    private boolean hakuKaynnissa;
    private char dekoodausMerkki;
    
    private String muistettavaString;

    public Komentotulkki(Tulostaja tulostaja, Tiedostonkasittelija tika, Konsoli konsoli) {
        this.tulostaja = tulostaja;
        this.ajantestaaja = new Ajantestaaja();
        this.tika = tika;
        this.konsoli = konsoli;

        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        dekoodausMerkki = "!".charAt(0);

        muistettavaString = "";

    }

    public void enter(String komento) {
        haarauta(komento);
    }

    public void haarauta(String komento) {

        if (hakuKaynnissa == true) {
            String[] osumaString = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(komento);
            tulostaja.tulostaHaunOsumat(osumaString);
            hakuKaynnissa = false;
            return;
        }

        if (merkintaanPaiva == true) {
            if (ajantestaaja.onPaiva(komento)) {
                muistettavaString = komento + dekoodausMerkki;
                tulostaja.pyydaAloitusAikaa();
                tulostaja.getKali().getKonsoli().kirjoitaKomentoriville("hh.mm");
                merkintaanAloitusAika = true;
            } else {
                tulostaja.tulostaEiOlePaiva();
                String paiva = tulostaja.getAjan().annaTamaPaiva();
                tulostaja.getKali().getKonsoli().kirjoitaKomentoriville(paiva);
                return;
            }
            merkintaanPaiva = false;
            return;
        }

        if (merkintaanAloitusAika == true) {
            if (ajantestaaja.onAika(komento)) {
                muistettavaString += komento;
                tulostaja.pyydaLopetusAikaa();
                tulostaja.getKali().getKonsoli().kirjoitaKomentoriville(tulostaja.getAjan().annaTamaAika());
                merkintaanLopetusAika = true;

            } else {
                tulostaja.tulostaKonsoliin("Ei ole aika");
                tulostaja.getKali().getKonsoli().kirjoitaKomentoriville("hh.mm");
                return;
            }

            merkintaanAloitusAika = false;

            return;
        }

        if (merkintaanLopetusAika == true) {
            muistettavaString += "-" + komento + dekoodausMerkki;
            tulostaja.pyydaSelostus();
            merkintaanSelostus = true;
            merkintaanLopetusAika = false;

            return;
        }

        if (merkintaanSelostus == true) {
            muistettavaString += komento;
            tulostaja.kerroLisayksesta();
            tulostaja.listaaKonsoliin(muistettavaString);
            
            try {                
                Merkinta merkinta = tika.getMerkinnanKasittelija().muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(muistettavaString);
                tika.kirjoitaTietokantaanLisaten(merkinta.toString(), true);
            } catch (IOException ex) {
                //mitä tähän tulisi lisätä?
            }

            merkintaanSelostus = false;
            muistettavaString = "";
            return;
        }

        tulkitse(komento);
    }

    public void tulkitse(String komento) {

        if (komento.equals("")) {
            return;
        }

        if (komento.equals("exit")) {
            this.tulostaja.getKali().tapa();
        }

        if (komento.equals("nyt")) {
            tulostaja.sanoMikaAikaNytOn();
            return;
        }

        if (komento.equals("paiva")) {
            tulostaja.getAjan().annaTamaPaiva();
            return;
        }

        if (komento.equals("aika")) {
            tulostaja.getAjan().annaTamaAika();
            return;
        }

        if (komento.equals("apua")) {
            tulostaja.apua();
            return;
        }

        if (komento.equals("tulosta")) {
            tulostaja.tulostaTiedosto();
            return;
        }

        if (komento.equals("hae")) {
            tulostaja.pyydaHakusana();
            this.hakuKaynnissa = true;
            return;
        }

        if (komento.equals("merk")) {
            merk();
            return;
        }

        if (komento.equals("nollaa muisti")) {
            try {
                tika.nollaaTiedosto();
            } catch (IOException ex) {
                //
            }
            tulostaja.ilmoitaNollaamisesta();
            return;
        }

        tulostaja.tulostaVirhe();
    }

    private void merk() {
        tulostaja.pyydaPaivaa();
        String paiva = tulostaja.getAjan().annaTamaPaiva();
        tulostaja.getKali().getKonsoli().kirjoitaKomentoriville(paiva);
        this.merkintaanPaiva = true;
    }
    
    public void otaKomento() {
        String komento = konsoli.getVarsinainenKomentoRivi().getText();
        konsoli.tulostaKomento();
        
        enter(komento);
    }
    
}
