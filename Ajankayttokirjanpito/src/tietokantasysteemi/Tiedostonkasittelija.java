/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sovelluslogiikka.Dekooderi;

/**
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

    public void kirjoitaTietokantaanLisaten(String lisattavat, boolean kirjoitetaanLisaten) throws IOException {
        FileWriter kirjoittaja = new FileWriter(tietokanta, kirjoitetaanLisaten);

        if (kirjoitetaanLisaten == true) {
            kirjoittaja.write(lisattavat + "\r\n");
        } else {
            kirjoittaja.write(lisattavat);
        }
        kirjoittaja.close();
    }

    //metodi ottaa kantaa siihen missa muodossa tieto on tallennettu
    //eli tietoa on tallennettu avain sanan alle seuraavalle riville.
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

    public void alustaTietokannanLukija() throws FileNotFoundException {
        this.lukija.close();
        this.lukija = new Scanner(tietokanta);
        this.lukija.reset();
    }

    public boolean lukijallaSeuraavaRivi() {
        if (lukija == null) {
            return false;
        }

        return lukija.hasNextLine();
    }

    public String lukijanSeuraavaRivi() {
        return this.lukija.nextLine();
    }

    public void suljeLukija() {
        this.lukija.close();
    }

    public void nollaaTiedosto() throws IOException {
        this.suljeLukija();
        kirjoitaTietokantaanLisaten("", false);
        alustaTietokannanLukija();
    }

    public MerkinnanKasittelija getMerkinnanKasittelija() {
        return this.meka;
    }

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

    public int poistaMerkintaPaivanPerusteella(String paiva) {
        int riviIndexi = -1;
        try {
            alustaTietokannanLukija();
            while (lukija.hasNextLine()) {
                riviIndexi++;
                if (lukija.nextLine().equals(paiva)) {
                    poistaKannastaRivit(riviIndexi, haeStringtaulunaTietoKannastaMerkintaPaivalla(paiva).length);
                    return riviIndexi;
                }
            }

            return riviIndexi;
        } catch (FileNotFoundException ex) {
            return -2;
        }
    }

    private void poistaKannastaRivit(int riviIndexi, int length) {
        poistaKannastaRivi(riviIndexi);

        if (length > 2) {
            poistaKannastaRivit(riviIndexi, length - 1);
        }
    }

    private void poistaKannastaRivi(int riviIndexi) {
        ArrayList<String> tekstitaulu = getTietokantaTekstiTauluna();
        tekstitaulu.remove(riviIndexi);
        
        String tekstitauluStringina = "";
        
        for (String rivi : tekstitaulu) {
            tekstitauluStringina += rivi;
        }
        try {
            kirjoitaTietokantaanLisaten(tekstitauluStringina, false);
        } catch (IOException ex) {
            //
        }
    }
}
//Tiedostonkasittelijalla on yksi tiedosto johon se tallettaa ja kirjoittaa tietoja.