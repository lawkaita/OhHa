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
        this.meka = new MerkinnanKasittelija();
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
    public String[] haeTietoKannasta(String hakusana) {
        try {
            alustaTietokannanLukija();

            //tässä pitää ottaa kantaa siihen miten haun tulokset esitetään
            //ja mitä sanotaan jos mitään ei löydy            
            String osumat = "";            
            while (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                
                if (rivi.equals(hakusana)) {
                    osumat = osumat + rivi + "\n" + lukija.nextLine() + "\n";
                    //tässä oletust tapahtuu.
                    //käytännössä tietoa tallennetaan päiväyksen alle muodossa kellonajat + mitä tehtiin.
                    //ainakin toistaiseksi päässäni on näin.
                }                
            }

            //oletetaan että tietokantaan tallennetaan tietoa niin että päätietoalkio,
            //esim päiväys, alkaa merkillä !.           
            String[] tuloksetLajiteltu = dekooderi.dekoodaa(osumat, "!".charAt(0));            
            
            return tuloksetLajiteltu;            
        } catch (FileNotFoundException ex) {
            return null;
        }        
    }
    
    public Scanner getTietokannanLukija() {      
        return lukija;
    }
    
    public void alustaTietokannanLukija() throws FileNotFoundException {
        this.lukija = new Scanner(new File("kirjaukset.txt"));        
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
    
}
//Tiedostonkasittelijalla on yksi tiedosto johon se tallettaa ja kirjoittaa tietoja.