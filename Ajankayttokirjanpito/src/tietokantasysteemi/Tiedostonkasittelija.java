/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    
    public Tiedostonkasittelija(Dekooderi dekooderi){
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
                rakennaMerkintaString(lukija, osuma);
                osuma = osuma + "!" + lukijanSeuraava;                
            }               
        }
        return dekooderi.dekoodaa(osuma, "!".charAt(0));
    }    
}
//Tiedostonkasittelijalla on yksi tiedosto johon se tallettaa ja kirjoittaa tietoja.