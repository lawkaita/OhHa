/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tietokantasysteemi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Envy 6-1010
 */
public class Tiedostonkasittelija {

    private File tietokanta;

    public Tiedostonkasittelija() {
        tietokanta = new File("kirjaukset.txt");
        
        try {
            tietokanta.createNewFile();
        } catch (IOException ex) {
           //ei tehdä mitään
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
}
//Tiedostonkasittelijalla on yksi tiedosto johon se tallettaa ja kirjoittaa tietoja.