/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import tietokantasysteemi.Tiedostonkasittelija;
import ajankayttokirjanpito.Ohjelma;
import java.io.IOException;

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
    private boolean hakuKaynnissa;
    private String muistettavaString;

    public Komentotulkki(Kayttoliittyma kali) {
        this.ohjelma = new Ohjelma(kali);
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        muistettavaString = "";
    }

    public void enter(String komento) {
        haarauta(komento);
    }

    public void haarauta(String komento) {

        if (hakuKaynnissa == true) {            
            ohjelma.haku(komento);            
            hakuKaynnissa = false;
            return;
        }

        if (merkintaanPaiva == true) {
            if (onPaiva(komento)) {
                muistettavaString = komento + "\n";
                merkintaanAloitusAika();
            } else {
                ohjelma.tulostaEiOlePaiva();
                String paiva = ohjelma.annaTamaPaiva();
                ohjelma.getKali().getKonsoli().kirjoitaKomentoriville(paiva);
                return;
            }

            merkintaanPaiva = false;
            return;
        }

        if (merkintaanAloitusAika == true) {
            if (onAika(komento)) {
                muistettavaString += komento;
                merkintaanLopetusAika();
            } else {
                ohjelma.tulostaKonsoliin("Ei ole aika");
                ohjelma.getKali().getKonsoli().kirjoitaKomentoriville("hh.mm");
                return;
            }

            merkintaanAloitusAika = false;

            return;
        }

        if (merkintaanLopetusAika == true) {
            muistettavaString += "-" + komento + "\n";
            merkintaanSelostus();
            merkintaanLopetusAika = false;

            return;
        }

        if (merkintaanSelostus == true) {
            muistettavaString += komento;
            ohjelma.kerroLisayksesta();
            ohjelma.listaaKonsoliin(muistettavaString);

            lisaaOhjelmanTiedostoonMuistettavaString();

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
        ohjelma.getKali().getKonsoli().kirjoitaKomentoriville(ohjelma.annaTamaAika());
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
            this.ohjelma.tapaKali();
        }

        if (komento.equals("nyt")) {
            ohjelma.sanoMikaAikaNytOn();
            return;
        }

        if (komento.equals("paiva")) {
            ohjelma.annaTamaPaiva();
            return;
        }

        if (komento.equals("aika")) {
            ohjelma.annaTamaAika();
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

        if (komento.equals("hae")) {
            ohjelma.pyydaHakusana();
            this.hakuKaynnissa = true;
            return;
        }

        if (komento.equals("merk")) {
            merk();
            return;
        }
        
        if (komento.equals("nollaa muisti")) {
            ohjelma.nollaaTietoKanta();
            ohjelma.ilmoitaNollaamisesta();
            return;
        }

        ohjelma.tulostaVirhe();
    }

    private void merk() {
        ohjelma.pyydaPaivaa();
        String paiva = ohjelma.annaTamaPaiva();
        ohjelma.getKali().getKonsoli().kirjoitaKomentoriville(paiva);
        this.merkintaanPaiva = true;
    }

    private void lisaaOhjelmanTiedostoonMuistettavaString() {
        ohjelma.lisaaTiedostoonMuistettavaString(muistettavaString);
    }

    private boolean onPaiva(String komento) {
        Dekooderi d = new Dekooderi();
        Character piste = ".".charAt(0);

        if (d.laskeKomentojenMaara(komento, piste) != 3) {
            return false;
        }

        String[] osat = d.dekoodaa(komento, piste);

        int[] lukuina = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                lukuina[i] = Integer.parseInt(osat[i]);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        if (!(0 < lukuina[0] && lukuina[0] < 32)) {
            return false;
        }

        if (!(0 < lukuina[1] && lukuina[1] < 13)) {
            return false;
        }

        return true;

    }

    private boolean onAika(String komento) {
        Dekooderi d = new Dekooderi();
        Character piste = ".".charAt(0);

        if (d.laskeKomentojenMaara(komento, piste) != 2) {
            return false;
        }

        String[] osat = d.dekoodaa(komento, piste);

        int[] lukuina = new int[2];
        for (int i = 0; i < 2; i++) {
            try {
                lukuina[i] = Integer.parseInt(osat[i]);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        if (!(0 <= lukuina[0] && lukuina[0] < 24)) {
            return false;
        }

        if (!(0 <= lukuina[1] && lukuina[1] < 60)) {
            return false;
        }

        return true;
    }
}
