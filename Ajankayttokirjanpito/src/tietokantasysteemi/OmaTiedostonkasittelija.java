/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import sovelluslogiikka.MerkinnanKasittelija;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import kayttoliittyma.Tulostaja;
import sovelluslogiikka.Dekooderi;

/**
 * Huolehtii ohjelman tietokanta-tiedoston lukemisesta ja muusta käsittelystä.
 *
 * @author Envy 6-1010
 */
public class OmaTiedostonkasittelija implements Tiedostonkasittelija {

    private File tietokanta;
    private File seurattavatToiminnot;
    private Scanner lukija;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija merkinnanKasittelija;
    private Tulostaja tulostaja;

    public OmaTiedostonkasittelija(Dekooderi dekooderi, MerkinnanKasittelija merkinnanKasittelija, Tulostaja tulostaja) {
        tietokanta = new File("kirjaukset.txt");
        seurattavatToiminnot = new File("seurattavatToiminnot.txt");

        this.tulostaja = tulostaja;

        try {
            tietokanta.createNewFile();
            seurattavatToiminnot.createNewFile();
        } catch (IOException ex) {
            this.tulostaja.tulostaKonsoliin("Tiedostonkasittelijalla IOException: " + ex.getMessage());
        }
        try {
            lukija = new Scanner(tietokanta);
        } catch (FileNotFoundException ex) {
            this.tulostaja.tulostaKonsoliin("Tietokantatiedostoa ei löydy: " + ex.getMessage());
        }
        this.dekooderi = dekooderi;
        this.merkinnanKasittelija = merkinnanKasittelija;

    }
    
    public OmaTiedostonkasittelija(Dekooderi dekooderi) {
        tietokanta = new File("kirjaukset.txt");
        seurattavatToiminnot = new File("seurattavatToiminnot.txt");

        this.tulostaja = null;

        try {
            tietokanta.createNewFile();
            seurattavatToiminnot.createNewFile();
        } catch (IOException ex) {
            this.tulostaja.tulostaKonsoliin("Tiedostonkasittelijalla IOException: " + ex.getMessage());
        }
        try {
            lukija = new Scanner(tietokanta);
        } catch (FileNotFoundException ex) {
            this.tulostaja.tulostaKonsoliin("Tietokantatiedostoa ei löydy: " + ex.getMessage());
        }
        this.dekooderi = dekooderi;
        this.merkinnanKasittelija = new MerkinnanKasittelija(dekooderi);

    }

    public File getTietokanta() {
        return tietokanta;
    }

    /**
     * Luo tietokannasta ArrayListin käsittelyä varten.
     *
     * @return
     */
    public ArrayList<String> getTietokantaTekstiTauluna() {
        ArrayList<String> tekstitaulu = new ArrayList<String>();

        try {
            alustaTietokannanLukija();
            while (lukija.hasNextLine()) {
                tekstitaulu.add(lukija.nextLine());
            }
            return tekstitaulu;
        } catch (FileNotFoundException ex) {
            return null;
        }

    }

    /**
     * Kirjoittaa annetun String-olion lisäten rivin vaihdon loppuun, jos sitä vaaditaan.
     * @param lisattava lisättävä String
     * @param kirjoitetaanLisaten boolean muuttuja ilmaisemaan, lisätäänkö rivinvaihto vai ei
     * @throws IOException 
     */
    public void kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(String lisattava, boolean kirjoitetaanLisaten) throws IOException {
        FileWriter kirjoittaja = new FileWriter(tietokanta, kirjoitetaanLisaten);

        if (kirjoitetaanLisaten == true) {
            kirjoittaja.write(lisattava + "\r\n"); //tämä voi aiheuttaa ylimääräisiä rivinvaihtoja kun käsitellään merkintöjä
        } else {
            kirjoittaja.write(lisattava);
        }
        kirjoittaja.close();
    }

    /**
     * Hakee tietokannasta hakusanalla merkintää, jonka päiväys täsmää hakusanan
     * kanssa. Palauttaa vain merkinnän sinänsä tauluna, eli ei sisällä
     * merkintöjä tietokannassa erottavaa rivinvaihtoa.
     *
     * @param hakusana String jolla haetaan kannasta merkinää.
     * @return hakusanalla löydetty merkintä taulumuodossa tai muuten null.
     */
    public String[] haeStringtaulunaTietoKannastaMerkintaPaivalla(String hakusana) {
        try {
            alustaTietokannanLukija();
            String[] merkintaTaulu = null;
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();

                if (rivi.equals(hakusana)) {
                    merkintaTaulu = rakennaMerkintaString(lukija, rivi);
                    break;
                }
            }

            return merkintaTaulu;
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    public Scanner getTietokannanLukija() {
        return lukija;
    }

    @Override
    public Dekooderi getDekooderi() {
        return this.dekooderi;
    }

    /**
     * Alustaa tietokannan lukemiseen käytettävän Scanner-olion.
     *
     * @throws FileNotFoundException jos tietokantatiedostoa ei löydy.
     */
    public void alustaTietokannanLukija() throws FileNotFoundException {
        this.lukija.close();
        this.lukija = new Scanner(tietokanta);
        this.lukija.reset();
    }

    /**
     * Kertoo, onko tietokantatiedostoa lukevalla Scanner-oliolla seuraavaa
     * riviä.
     *
     * @return true jos lukijalla on seuraava rivi, muuten false.
     */
    public boolean lukijallaSeuraavaRivi() {
        if (lukija == null) {
            return false;
        }
        return lukija.hasNextLine();
    }

    /**
     * Hakee tietokantatiedostoa lukevan Scanner-olion seuraavan rivin.
     *
     * @return tietokantatiedostoa lukevan Scannerin seuraava rivi
     */
    public String lukijanSeuraavaRivi() {
        return this.lukija.nextLine();
    }

    /**
     * Sulkee tietokantatiedostoa lukevan Scanner-olion.
     */
    public void suljeLukija() {
        this.lukija.close();
    }

    /**
     * Nollaa ohjelman tietokantatiedoston. Sulkee ensin tiedostoa lukevan
     * Scanner-olion, ja luo nollauksen jälkeen uuden Scanner-olion.
     *
     * @throws IOException
     */
    @Override
    public void nollaaTietokantaTiedosto() throws IOException {
        this.suljeLukija();
        kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("", false);
        alustaTietokannanLukija();
    }

    @Override
    public MerkinnanKasittelija getMerkinnanKasittelija() {
        return this.merkinnanKasittelija;
    }

    /**
     * metodi rakentaa sille annetusta Scannerista merkinnan taulumuodossa.
     *
     * @param lukija tiedostoa lukeva Scanner-olio
     * @param osuma merkinnan rakentamiseen kaytetty String
     * @return
     */
    private String[] rakennaMerkintaString(Scanner lukija, String osuma) {
        if (lukija.hasNextLine()) {
            String lukijanSeuraava = lukija.nextLine();
            if (!lukijanSeuraava.isEmpty()) {
                osuma = osuma + "!" + lukijanSeuraava;
                return rakennaMerkintaString(lukija, osuma);
            }
        }
        return dekooderi.dekoodaa(osuma, "!".charAt(0));
    }

    /**
     * Tutkii, onko tietokantatiedostossa päiväystä annetulla päivällä.
     *
     * @param paiva annettu päivä
     * @return true, jos kannasta löytyy merkintä annetulla päivällä, muuten
     * false
     */
    public boolean kannassaOnMerkintaPaivalla(String paiva) {
        boolean onMerkinta = false;
        try {
            alustaTietokannanLukija();
            while (lukija.hasNextLine()) {
                if (lukija.nextLine().equals(paiva)) {
                    onMerkinta = true;
                }
            }
            return onMerkinta;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }

    /**
     * Hakee annetun päiväyksen rivi-indeksin. Tämä indeksi kertoo, millä
     * rivillä tietokantatiedostossa annettu päiväys on.
     *
     * @param paiva haettava päiväys
     * @return päiväyksen sijainti tietokantatiedostossa. Jos tiedostoa ei ole,
     * palauttaa -2, jos päiväystä ei löydy, palauttaa -3.
     */
    public int haeKannastaMerkinnanPaivayksenPaikkaPaivayksella(String paiva) {
        int riviIndeksi = -1;
        try {
            alustaTietokannanLukija();
            while (lukija.hasNextLine()) {
                riviIndeksi++;
                if (lukija.nextLine().equals(paiva)) {
                    return riviIndeksi;
                }
            }

            return -3;
        } catch (FileNotFoundException ex) {
            return -2;
        }
    }

    /**
     * Poistaa kannasta merkintä-olion annetun päiväyksen perusteella.
     *
     * @param paiva annettu päiväys.
     * @return rivi, jolta päiväys poistettiin.
     */
    public int poistaMerkintaPaivanPerusteella(String paiva) {
        int riviIndeksi = -1;
        try {
            alustaTietokannanLukija();
            while (lukija.hasNextLine()) {
                riviIndeksi++;
                if (lukija.nextLine().equals(paiva)) {
                    poistaKannastaRivit(riviIndeksi,
                            haeStringtaulunaTietoKannastaMerkintaPaivalla(paiva).length + 1);
                    //huomioidaan se että haeStringtauluna...
                    //ei laske mukaan merkinnät erottavaa rivinvaihtoa.
                    return riviIndeksi;
                }
            }

            return riviIndeksi;
        } catch (FileNotFoundException ex) {
            return -2;
        }
    }

    /**
     * Poistaa tietokantatiedostosta annetulta paikalta ja pituudelta rivit.
     *
     * @param riviIndeksi poistamisen aloitusrivi
     * @param length kuinka monta riviä positetaan
     */
    private void poistaKannastaRivit(int riviIndeksi, int length) {
        ArrayList<String> tietokantaTauluna = getTietokantaTekstiTauluna();

        poistaTaulustaRivit(riviIndeksi, length, tietokantaTauluna);

        kirjoitaKannanYli(tietokantaTauluna);
    }

    /**
     * Poistaa tietokannasta väliaikaista käsittelyä varten luodusta
     * taulu-kuvasta annetulta paikalta annetun määrän rivejä.
     *
     * @param riviIndeksi poistamisen aloitusrivi
     * @param length poistettavien rivien määrä
     * @param tietokantaTauluna väliaikainen kannan taulu-kuva
     */
    private void poistaTaulustaRivit(int riviIndeksi, int length, ArrayList<String> tietokantaTauluna) {
        //otetaan nolla mukaann niin poistuuylimääräinen rivinvaihto.
        while (length > 0) {
            tietokantaTauluna.remove(riviIndeksi);
            length--;
        }
    }

    /**
     * kirjoittaa kannan yli annetun tekstitaulun sisällön.
     *
     * @param tekstitaulu annettu tekstitaulu
     */
    public void kirjoitaKannanYli(ArrayList<String> tekstitaulu) {
        try {
            kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(kirjoitaTekstitauluStringiksiRivittaen(tekstitaulu), false);
        } catch (IOException ex) {
            //
        }
    }

    /**
     * Poistaa kannasta annetun rivin ja kirjoittaa kannan sitten uudelleen,
     * jotta muutos tulee voimaan
     *
     * @param riviIndeksi poistettavan rivin indeksi
     */
    private void poistaKannastaRivi(int riviIndeksi) {
        ArrayList<String> tekstitaulu = getTietokantaTekstiTauluna();
        tekstitaulu.remove(riviIndeksi);
        String tekstitauluStringina = kirjoitaTekstitauluStringiksiRivittaen(tekstitaulu);

        try {
            kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(tekstitauluStringina, false);
        } catch (IOException ex) {
            //
        }
    }

    /**
     * Kirjoittaa annetun tekstitaulun yhdeksi String-olioksi, joka sisältää
     * rivinvaihdot, paitsi viimeisen rivinvaihdon.
     *
     * @param tekstitaulu annettu tekstitaulu
     * @return tekstitaulu kirjoitettuna String-olioksi.
     */
    public String kirjoitaTekstitauluStringiksiRivittaen(ArrayList<String> tekstitaulu) {
        String tekstitauluStringina = "";
        
        if (tekstitaulu.isEmpty()) {
            return tekstitauluStringina;
        }
        
        if (tekstitaulu.size() == 1) {
            return tekstitaulu.get(0) + "\r\n";
        }        

        for (String rivi : tekstitaulu) {
            tekstitauluStringina += rivi + "\r\n";
        }

        //poistetaan viimeinen ylimääräinen \r\n
        tekstitauluStringina = tekstitauluStringina.substring(0, tekstitauluStringina.length() - 2);
        //poistaa molemmat \r\n
        return tekstitauluStringina;
    }

    /**
     * Kirjoittaa väliaikaiseen tietokantatiedoston taulu-kuvaan kirjoitettavan
     * tekstin annetusta rivi-indeksistä eteenpäin. Metodi
     * poistaVanhaMerkintaJaLisaaUUsiYhdistettyMerkintaJaKirjaaMuutosTietokantaan
     * käyttää tätä metodia
     *
     * @param kirjoitettava tauluun kirjoitettava teksti
     * @param indeksi kirjoituksen aloitusrivin indeksi
     * @param tekstitaulu muokattava tekstitaulu
     *
     * @see
     * poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(int
     * indeksi, int vanhanMerkinnanPituus, Merkinta uusiMerkinta)
     */
    private void kirjoitaTauluunAnnetustaRiviNumerostaEteenpäin(String kirjoitettava, int indeksi, ArrayList<String> tekstitaulu) {

        if (indeksi < tekstitaulu.size()) {
            tekstitaulu.add(indeksi, kirjoitettava);
        } else {
            tekstitaulu.add(kirjoitettava);
        }
    }

    /**
     * Poistaa tietokantatiedostosta vanhan merkinnän annetusta indeksistä ja
     * kirjoittaa sen kohdalle uuden annetun Merkinta-olion.
     *
     * @param indeksi vanhan merkinnän paikka
     * @param vanhanMerkinnanPituus poistettavien rivien määrä
     * @param uusiMerkinta kantaan kirjoitettava uusi merkintä
     */
    public void poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(int indeksi, int vanhanMerkinnanPituus, Merkinta uusiMerkinta) {
        ArrayList<String> tietokantaTauluna = getTietokantaTekstiTauluna();

        poistaTaulustaRivit(indeksi, vanhanMerkinnanPituus, tietokantaTauluna);

        kirjoitaTauluunAnnetustaRiviNumerostaEteenpäin(uusiMerkinta.toString(), indeksi, tietokantaTauluna);
        //uusiMerkinta.toString tuo ylimääräisen rivinvaihdon kantaan.

        kirjoitaKannanYli(tietokantaTauluna);
    }

    /**
     * Laskee kuinka monta päivää tietokantaan on merkitty.
     *
     * @return merkittyjen päivien määrä.
     */
    public int laskeMerkittyjenPaivienMaara() {
        int merkintojenMaara = 0;
        ArrayList<String> tietokantaTauluna = getTietokantaTekstiTauluna();
        for (String rivi : tietokantaTauluna) {
            if (!rivi.isEmpty()) {
                if ((rivi.charAt(0) != ' ')) {
                    merkintojenMaara++;
                }
            }
        }

        return merkintojenMaara;
    }

    /**
     * Luo taulukuvan tietokantatiedostosta jonka alkioina on kaikki kannan
     * merkinnät.
     *
     * @return merkintätaulu-kuva tietokantatiedostosta.
     */
    private ArrayList<Merkinta> merkinnatTaulussa() {
        ArrayList<Merkinta> merkinnat = new ArrayList<Merkinta>();
        ArrayList<String> tietokantaTauluna = getTietokantaTekstiTauluna();

        for (String rivi : tietokantaTauluna) {
            if (!rivi.isEmpty()) {
                if ((rivi.charAt(0) != ' ')) {
                    String[] merkintaTauluna = haeStringtaulunaTietoKannastaMerkintaPaivalla(rivi);
                    Merkinta lisattava = merkinnanKasittelija.luoMerkintaAnnetustaTaulusta(merkintaTauluna);
                    merkinnat.add(lisattava);
                }
            }
        }

        Collections.sort(merkinnat);
        return merkinnat;
    }

    /**
     * Kirjoittaa tietokannan yli annetun merkintalistan. Järjestys tapahtuu
     * merkinnatTaulussa-metodissa.
     */
    private void ylikirjoitaMerkintalistaTietokantaan(ArrayList<Merkinta> merkinnat) {
        try {
            nollaaTietokantaTiedosto();
            for (Merkinta merkinta : merkinnat) {
                kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(merkinta.toString(), true);
            }
        } catch (IOException ex) {
            //
        }

    }
    
    /**
     * Antaa ohjelman käynnistyksen yhteydessä muistista ladatun tietokannan.
     * @return muistista ladattu tietokanta.
     */
    @Override
    public ArrayList<Merkinta> lataaTietokanta() {
        return merkinnatTaulussa();
    }

    /**
     * Kirjoittaa tietokannan yli annentun merkintätaulun tietokantaan.
     * @param merkinnat annettu merkintätaulu
     */
    @Override
    public void yliKirjoitaTietokantatiedosto(ArrayList<Merkinta> merkinnat) {
        ylikirjoitaMerkintalistaTietokantaan(merkinnat);
    }

    /**
     * Lataa tiedostostosta seurattavien toimintojen listan.
     * @return seurattavien toimintojen lista
     */
    @Override
    public ArrayList<String> lataaSeurattavatToiminnot() {
        ArrayList<String> toiminnot = new ArrayList<String>();
        
        try {
            Scanner toimintojenLukija = new Scanner(seurattavatToiminnot);
            while (toimintojenLukija.hasNextLine()) {
                toiminnot.add(toimintojenLukija.nextLine());
            }

        } catch (FileNotFoundException ex) {
            tulostaja.tulostaKonsoliin("Tiedostoa ei löydy: " + ex.getMessage());
        }

        return toiminnot;
    }
    
    /**
     * Kirjoittaa seurattavien toimintojen tiedoston yli annetun listan seurattavia toimintoja.
     * @param annaSeurattavatToiminnot annettu lista seurattavia toimintoja
     */
    @Override
    public void ylikirjoitaSeurattavatToiminnotTiedosto(ArrayList<String> annaSeurattavatToiminnot) {
        FileWriter kirjoittaja;
        try {
            kirjoittaja = new FileWriter(seurattavatToiminnot);
            kirjoittaja.write(kirjoitaTekstitauluStringiksiRivittaen(annaSeurattavatToiminnot));
            kirjoittaja.close();
        } catch (IOException ex) {
            tulostaja.tulostaKonsoliin("Seurattavien toimintojen tallettaminen ei onnistu: " + ex.getMessage());
        }
    }

}