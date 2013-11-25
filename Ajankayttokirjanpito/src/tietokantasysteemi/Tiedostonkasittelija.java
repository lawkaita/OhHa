/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sovelluslogiikka.Dekooderi;

/**
 * Huolehtii ohjelman tietokanta-tiedoston lukemisesta ja muusta käsittelystä.
 *
 * @author Envy 6-1010
 */
public class Tiedostonkasittelija {

    private File tietokanta;
    private Scanner lukija;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija meka;

    public Tiedostonkasittelija(Dekooderi dekooderi) {
        tietokanta = new File("kirjaukset.txt");
        try {
            tietokanta.createNewFile();
        } catch (IOException ex) {
            System.out.println("TiedostonkasittelijaIOException");
        }
        try {
            lukija = new Scanner(tietokanta);
        } catch (FileNotFoundException ex) {
            System.out.println("TiedostonkastittelijaFileNotFoundException");
        }
        this.dekooderi = dekooderi;
        this.meka = new MerkinnanKasittelija(this.dekooderi);
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

    public Dekooderi getDekooderi() {
        return this.dekooderi;
    }

    /**
     * Alustaa tietokannan lukemiseen käytettävän Scanner-olion.
     * @throws FileNotFoundException jos tietokantatiedostoa ei löydy.
     */
    public void alustaTietokannanLukija() throws FileNotFoundException {
        this.lukija.close();
        this.lukija = new Scanner(tietokanta);
        this.lukija.reset();
    }

    /**
     * Kertoo, onko tietokantatiedostoa lukevalla Scanner-oliolla seuraavaa riviä.
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
     * Nollaa ohjelman tietokantatiedoston.
     * Sulkee ensin tiedostoa lukevan Scanner-olion, ja luo nollauksen jälkeen uuden
     * Scanner-olion.
     * @throws IOException 
     */
    public void nollaaTiedosto() throws IOException {
        this.suljeLukija();
        kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("", false);
        alustaTietokannanLukija();
    }

    public MerkinnanKasittelija getMerkinnanKasittelija() {
        return this.meka;
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
     * @param paiva annettu päivä
     * @return true, jos kannasta löytyy merkintä annetulla päivällä, muuten false
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
     * Hakee annetun päiväyksen rivi-indeksin. Tämä indeksi kertoo, millä rivillä
     * tietokantatiedostossa annettu päiväys on.
     * @param paiva haettava päiväys
     * @return päiväyksen sijainti tietokantatiedostossa. Jos tiedostoa ei ole, palauttaa
     * -2, jos päiväystä ei löydy, palauttaa -3.
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
     * @param riviIndeksi poistamisen aloitusrivi
     * @param length kuinka monta riviä positetaan
     */
    private void poistaKannastaRivit(int riviIndeksi, int length) {
        ArrayList<String> tietokantaTauluna = getTietokantaTekstiTauluna();

        poistaTaulustaRivit(riviIndeksi, length, tietokantaTauluna);

        kirjoitaKannanYli(tietokantaTauluna);
    }

    /**
     * Poistaa tietokannasta väliaikaista käsittelyä varten luodusta taulu-kuvasta
     * annetulta paikalta annetun määrän rivejä.
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
     * @param tekstitaulu annettu tekstitaulu
     */
    public void kirjoitaKannanYli(ArrayList<String> tekstitaulu) {
        try {
            kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(kirjoitaKantaTekstitauluStringiksiRivittaen(tekstitaulu), false);
        } catch (IOException ex) {
            //
        }
    }

    /**
     * Poistaa kannasta annetun rivin ja kirjoittaa kannan sitten uudelleen,
     * jotta muutos tulee voimaan
     * @param riviIndeksi poistettavan rivin indeksi
     */
    private void poistaKannastaRivi(int riviIndeksi) {
        ArrayList<String> tekstitaulu = getTietokantaTekstiTauluna();
        tekstitaulu.remove(riviIndeksi);
        String tekstitauluStringina = kirjoitaKantaTekstitauluStringiksiRivittaen(tekstitaulu);

        try {
            kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(tekstitauluStringina, false);
        } catch (IOException ex) {
            //
        }
    }

    /**
     * Kirjoittaa annetun tekstitaulun yhdeksi String-olioksi, joka sisältää rivinvaihdot.
     * @param tekstitaulu annettu tekstitaulu
     * @return tekstitaulu kirjoitettuna String-olioksi.
     */
    public String kirjoitaKantaTekstitauluStringiksiRivittaen(ArrayList<String> tekstitaulu) {
        String tekstitauluStringina = "";

        for (String rivi : tekstitaulu) {
            tekstitauluStringina += rivi + "\r\n";
        }

        return tekstitauluStringina;
    }

    /**
     * Kirjoittaa väliaikaiseen tietokantatiedoston taulu-kuvaan kirjoitettavan tekstin
     * annetusta rivi-indeksistä eteenpäin.
     * Metodi poistaVanhaMerkintaJaLisaaUUsiYhdistettyMerkintaJaKirjaaMuutosTietokantaan
     * käyttää tätä metodia
     * @param kirjoitettava tauluun kirjoitettava teksti
     * @param indeksi kirjoituksen aloitusrivin indeksi
     * @param tekstitaulu muokattava tekstitaulu
     * 
     * @see poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(int indeksi, int vanhanMerkinnanPituus, Merkinta uusiMerkinta)
     */
    private void kirjoitaTauluunAnnetustaRiviNumerostaEteenpäin(String kirjoitettava, int indeksi, ArrayList<String> tekstitaulu) {

        if (indeksi < tekstitaulu.size()) {
            tekstitaulu.add(indeksi, kirjoitettava);
        } else {
            tekstitaulu.add(kirjoitettava);
        }
    }

    /**
     * Poistaa tietokantatiedostosta vanhan merkinnän annetusta indeksistä
     * ja kirjoittaa sen kohdalle uuden annetun Merkinta-olion. 
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
     * Luo taulukuvan tietokantatiedostosta jonka alkioina on kaikki kannan merkinnät.
     * @return merkintätaulu-kuva tietokantatiedostosta.
     */
    public ArrayList<Merkinta> merkinnatTaulussa() {
        ArrayList<Merkinta> merkinnat = new ArrayList<Merkinta>();
        ArrayList<String> tietokantaTauluna = getTietokantaTekstiTauluna();

        for (String rivi : tietokantaTauluna) {
            if (!rivi.isEmpty()) {
                if ((rivi.charAt(0) != ' ')) {
                    String[] merkintaTauluna = haeStringtaulunaTietoKannastaMerkintaPaivalla(rivi);
                    Merkinta lisattava = meka.luoMerkintaAnnetustaTaulusta(merkintaTauluna);
                    merkinnat.add(lisattava);
                }
            }
        }

        Collections.sort(merkinnat);
        return merkinnat;
    }

    /**
     * Kirjoittaa kannan uudelleen järjestäen merkinnät uudelleen päiväyksen perusteella.
     * Järjestys tapahtuu merkinnatTaulussa-metodissa.
     */
    public void kirjoitaMerkinnatJarjestettynaKannanYli() {
        ArrayList<Merkinta> merkinnat = merkinnatTaulussa();
        try {
            nollaaTiedosto();
            for (Merkinta merkinta : merkinnat) {
                kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(merkinta.toString(), true);
            }
        } catch (IOException ex) {
            //
        }

    }
}
//Tiedostonkasittelijalla on yksi tiedosto johon se tallettaa ja kirjoittaa tietoja.