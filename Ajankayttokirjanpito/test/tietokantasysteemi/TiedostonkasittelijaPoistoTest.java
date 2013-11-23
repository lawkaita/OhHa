/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.Dekooderi;

/**
 *
 * @author lawkaita
 */
public class TiedostonkasittelijaPoistoTest {
    
    private Tiedostonkasittelija tika;

    @Before
    public void setUp() {
        this.tika = new Tiedostonkasittelija(new Dekooderi());
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\nTiedostonkasittelijaPoistoTesti\n", true);
        } catch (IOException ex) {
            System.out.println("TiedostonkasittelijaTestIOException");
        }
        
    }
    
    @Test
    public void taulumuotoisestaTietokantaKopiostaPoistaminenVahentaaSenRivienMaaraa() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("eka!", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("poistaminut", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("alapoistaminua", true);
            
            ArrayList<String> tietokantaTauluna = tika.getTietokantaTekstiTauluna();
            int tietokannanAlkuperainenKokoRiveina = tietokantaTauluna.size();            
            int poistoIndeksi = tietokannanAlkuperainenKokoRiveina - 2;          
            
            tietokantaTauluna.remove(poistoIndeksi);
            
            int uusiKannanKoko = tietokantaTauluna.size();
            
            assertEquals(tietokannanAlkuperainenKokoRiveina - 1, uusiKannanKoko);
            
        } catch (IOException ex) {
            System.out.println("IOException");
        }        
    }
    
    @Test
    public void tauluMuotoisestaKantaKopiostaPoistaminenPoistaaOdotetunRivin() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("eka!", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("poistaminut", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("alapoistaminua", true);
            
            ArrayList<String> tietokantaTauluna = tika.getTietokantaTekstiTauluna();
            int tietokannanAlkuperainenKokoRiveina = tietokantaTauluna.size();            
            int poistoIndeksi = tietokannanAlkuperainenKokoRiveina - 2; 
            // -1 poistaisi viimeisen alkon, sillä indeksointi alkaa nollasta.         
            
            tietokantaTauluna.remove(poistoIndeksi);
                        
            assertEquals("alapoistaminua", tietokantaTauluna.get(tietokantaTauluna.size() - 1));
            
        } catch (IOException ex) {
            System.out.println("IOException");
        }        
    }
    
    
    @Test
    public void poistoKomentoHahmottaaJaPoistaaKokonaisenMerkinnan() {
        //Tämä testi on rikki
        
        try {
            tika.nollaaTiedosto();
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("eka!", true);
            
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("7.7.1777", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    16.00-21.00: poistaMinut1", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    21.30-22.30: poistaMinut2", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("alapoistaminua", true);
            
            ArrayList<String> tekstiTaulu = tika.getTietokantaTekstiTauluna();
            
            assertEquals(6, tekstiTaulu.size());
            
            tika.poistaMerkintaPaivanPerusteella("7.7.1777");
            //Virheen täytyy tapahtua tässä
            
            tekstiTaulu = tika.getTietokantaTekstiTauluna();
            
            int uusiSize = tekstiTaulu.size();
            
            assertEquals(3, uusiSize);
            
            
            
        } catch (IOException ex) {
            System.out.println("IOException");
        }        
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}