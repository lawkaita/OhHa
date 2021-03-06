/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.util.ArrayList;
import java.util.Collections;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.Paivays;
import tietokantasysteemi.Tapahtuma;

/**
 * Huolehtii Merkintä-olioiden tarkemmasta käsittelystä.
 * @author Envy 6-1010
 */
public class MerkinnanKasittelija {

    private Dekooderi dekooderi;

    public MerkinnanKasittelija(Dekooderi dekooderi) {
        this.dekooderi = dekooderi;
    }

    /**
     * Muuntaa merkinnänluomistapahtumassa luodun yksitapahtumaisen merkinnän 
     * Stringistä merkinnäksi.
     * @param merkinta annettu merkintä String-muodossa
     * @return String-muodosta muutettu uusi Merkinta
     */
    //tällä muunnetaan kolmirivinen käyttöliittymän antama merkintä
    public Merkinta muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(String merkinta) {
        Character rivinvaihto = "!".charAt(0);
        String[] merkintarivit = dekooderi.dekoodaa(merkinta, rivinvaihto);

        String paivamaara = merkintarivit[0];
        Paivays paivays = luoPaivays(paivamaara);

        String kellonajat = merkintarivit[1];
        String[] kellonajatOsina = dekooderi.dekoodaa(kellonajat, "-".charAt(0));
        Kellonaika[] ajat = luoAloitusaikajaLopetusaika(kellonajat);

        String seloste = merkintarivit[2];
        Tapahtuma tapahtuma = new Tapahtuma(ajat[0], ajat[1], seloste);
        Merkinta valmisMerkinta = new Merkinta(paivays, tapahtuma);
        return valmisMerkinta;
    }

    /**
     * Hoitaa kahden merkinnän yhdistämisen
     * @param eka ensimmäinen merkintä, johon liitetään toisen merkinnän tapahtumat
     * @param toka toinen merkintä
     */
    public void yhdista(Merkinta eka, Merkinta toka) {
        for (Tapahtuma t : toka.getTapahtumat()) {
            eka.lisaaTapahtuma(t);
        }
        
        Collections.sort(eka.getTapahtumat());
    }

    /**
     * Luo paivays-olion annetusta Stringista.
     * @param paivamaara annettu string
     * @return paivays-olio
     */
    public Paivays luoPaivays(String paivamaara) {
        String[] paivamaaraOsina = dekooderi.dekoodaa(paivamaara, ".".charAt(0));
        int paiva = Integer.parseInt(paivamaaraOsina[0]);
        int kuukausi = Integer.parseInt(paivamaaraOsina[1]);
        int vuosi = Integer.parseInt(paivamaaraOsina[2]);
        Paivays paivays = new Paivays(paiva, kuukausi, vuosi);
        return paivays;
    }
    
    /**
     * Luo kaksi kellonaikaa annetusta Stringistä ja palauttaa ne kaksipaikkaisessa taulussa.
     * @param kellonajat
     * @return kaksipaikkainen taulu joka sisältää annetut kellonajat.
     */
    public Kellonaika[] luoAloitusaikajaLopetusaika(String kellonajat) {
        String[] kellonajatOsina = dekooderi.dekoodaa(kellonajat, "-".charAt(0));
        String[] aloitusaikaOsina = dekooderi.dekoodaa(kellonajatOsina[0], ".".charAt(0));
        Kellonaika aloitusaika = luoKellonaika(aloitusaikaOsina);
        String[] lopetusaikaOsina = dekooderi.dekoodaa(kellonajatOsina[1], ".".charAt(0));
        Kellonaika lopetusaika = luoKellonaika(lopetusaikaOsina);
        
        Kellonaika[] palautettava = new Kellonaika[2];
        palautettava[0] = aloitusaika;
        palautettava[1] = lopetusaika;
        
        return palautettava;
    }

    /**
     * luo kellonajan annetusta String-taulusta
     * @param aikaOsina aika lajiteltuna tauluun minuutteina ja tunteina.
     * @return Kellonaika luotuna annetusta String-taulusta.
     */
    public Kellonaika luoKellonaika(String[] aikaOsina) {
        String tuntiString = aikaOsina[0].trim();
        String minuuttiString = aikaOsina[1].trim();
        int aikaTunti = Integer.parseInt(tuntiString);
        int aikaMinuutti = Integer.parseInt(minuuttiString);
        Kellonaika aika = new Kellonaika(aikaTunti, aikaMinuutti);
        return aika;
    }

    
    
    //oletetaan, että osuma ei ole null
    
    /**
     * Rakentaa merkinnan annetusta String-taulusta.
     * @param osuma annettu string-taulu
     * @return rakennettu merkinta.
     */
    public Merkinta luoMerkintaAnnetustaTaulusta(String[] osuma) {
        Paivays paivays = luoPaivays(osuma[0]);
        ArrayList<Tapahtuma> tapahtumat = new ArrayList<>();
        
        for (int i = 1; i < osuma.length; i++) {
            Tapahtuma t = luoTapahtuma(osuma[i]);
            tapahtumat.add(t);
        }
        
        Merkinta merkinta = new Merkinta(paivays, tapahtumat);
        return merkinta;
    }

    /**
     * Luo tapahtuma-olion annetusta Stringistä.
     * @param tapahtumaString annettu Tapahtuma-olio String-muodossa
     * @return luotu Tapahtuma
     */
    private Tapahtuma luoTapahtuma(String tapahtumaString) {
        String[] tapahtumaAlkiot = dekooderi.dekoodaa(tapahtumaString, ':');
        Kellonaika[] kellonajat = luoAloitusaikajaLopetusaika(tapahtumaAlkiot[0]);
        Kellonaika aloitusaika = kellonajat[0];
        Kellonaika lopetusaika = kellonajat[1];
        String seloste = tapahtumaAlkiot[1].substring(1); //otetaan erottava välilyönti
        
        Tapahtuma tapahtuma = new Tapahtuma(aloitusaika, lopetusaika, seloste);
        return tapahtuma;
    }

    public String luoKellonaikaStringina(String komento) {
        String[] dekoodi = dekooderi.dekoodaa(komento, '.');
        return luoKellonaika(dekoodi).toString();
    }
}
