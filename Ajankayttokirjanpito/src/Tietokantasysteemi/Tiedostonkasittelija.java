/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tietokantasysteemi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Envy 6-1010
 */
public class Tiedostonkasittelija {

    private File tietokanta;
    private Scanner lukija;

    public Tiedostonkasittelija() {
        tietokanta = new File("kirjaukset.txt");
        
        try {
            tietokanta.createNewFile();
        } catch (IOException ex) {
           //ei tehdä mitään jos tiedosto on olemassa.
        }
    }
    
    public File getTietokanta() {
        return tietokanta;
    }
    
    public void lisaaTietokantaan(String lisattavat) throws IOException {
        FileWriter kirjoittaja = new FileWriter(tietokanta, true);
        kirjoittaja.write("\r\n" + lisattavat);
        kirjoittaja.close();
    }
    
    public Scanner getTietokannanLukija() throws FileNotFoundException {
        this.lukija = new Scanner(new File("kirjaukset.txt")); 
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
    
    
}
//Tiedostonkasittelijalla on yksi tiedosto johon se tallettaa ja kirjoittaa tietoja.