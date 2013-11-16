/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import sovelluslogiikka.Dekooderi;

/**
 *
 * @author Envy 6-1010
 */
public class MerkinnanKasittelija {

    private Dekooderi dekooderi;

    public MerkinnanKasittelija() {
        dekooderi = new Dekooderi();
    }

    //tällä muunnetaan kolmirivinen käyttöliittymän antama merkintä
    public Merkinta muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(String merkinta) {
        Character rivinvaihto = "!".charAt(0);
        String[] merkintarivit = dekooderi.dekoodaa(merkinta, rivinvaihto);

        String paivamaara = merkintarivit[0];
        Paivays paivays = luoPaivays(paivamaara);

        String kellonajat = merkintarivit[1];
        String[] kellonajatOsina = dekooderi.dekoodaa(kellonajat, "-".charAt(0));
        String[] aloitusaikaOsina = dekooderi.dekoodaa(kellonajatOsina[0], ".".charAt(0));
        Kellonaika aloitusaika = luoKellonaika(aloitusaikaOsina);
        String[] lopetusaikaOsina = dekooderi.dekoodaa(kellonajatOsina[1], ".".charAt(0));
        Kellonaika lopetusaika = luoKellonaika(lopetusaikaOsina);

        String seloste = merkintarivit[2];
        Tapahtuma tapahtuma = new Tapahtuma(aloitusaika, lopetusaika, seloste);
        Merkinta valmisMerkinta = new Merkinta(paivays, tapahtuma);
        return valmisMerkinta;
    }

    public Merkinta yhdista(Merkinta eka, Merkinta toka) {
        for (Tapahtuma t : toka.getTapahtumat()) {
            eka.lisaaTapahtuma(t);
        }

        return eka;
    }

    public Merkinta muutaTietokannanTapahtumatKasiteltaviksi(String merkintaString) {
        Character rivinvaihto = "\n".charAt(0);

        String[] merkintarivit = dekooderi.dekoodaa(merkintaString, rivinvaihto);
        String paiva = merkintarivit[0];
        ArrayList<String> tapahtumat = new ArrayList<>();

        for (int i = 1; i < merkintarivit.length; i++) {
            tapahtumat.add(merkintarivit[i]);
        }

        return null;
    }
    //paikan voi hakea osoittimen paiaksta.

    private Paivays luoPaivays(String paivamaara) {
        String[] paivamaaraOsina = dekooderi.dekoodaa(paivamaara, ".".charAt(0));
        int paiva = Integer.parseInt(paivamaaraOsina[0]);
        int kuukausi = Integer.parseInt(paivamaaraOsina[1]);
        int vuosi = Integer.parseInt(paivamaaraOsina[2]);
        Paivays paivays = new Paivays(paiva, kuukausi, vuosi);
        return paivays;
    }

    private Kellonaika luoKellonaika(String[] aikaOsina) {                
        int aikaTunti = Integer.parseInt(aikaOsina[0]);
        int aikaMinuutti = Integer.parseInt(aikaOsina[1]);
        Kellonaika aika = new Kellonaika(aikaTunti, aikaMinuutti);
        return aika;
    }
}
