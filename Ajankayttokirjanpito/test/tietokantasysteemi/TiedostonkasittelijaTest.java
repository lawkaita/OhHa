/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.FileNotFoundException;
import java.io.IOException;
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
 * @author Envy 6-1010
 */
public class TiedostonkasittelijaTest {

    private Tiedostonkasittelija tika;

    @Before
    public void setUp() {
        this.tika = new Tiedostonkasittelija(new Dekooderi());
        try {
            tika.kirjoitaTietokantaanLisaten("\nTestimerkintöjä\n", true);
        } catch (IOException ex) {
            System.out.println("TiedostonkasittelijaTestIOException");
        }
        
    }
    
    @Test
    public void tikallaOnTiedostoLuomisensaJalkeen () {
        boolean tikallaOnteidostoLuomisensaJalkeen = (tika.getTietokanta() != null);
            
        assertEquals(tikallaOnteidostoLuomisensaJalkeen, true);
    }
    
    @Test
    public void tikanTiedostonNimiOnOikein () {
        String tiedostonNimi = this.tika.getTietokanta().toString();
            
        assertEquals(tiedostonNimi, "kirjaukset.txt");
    }
    
    @Test
    public void alustaTietokannanLukijaEiAiheutaFileNotFoundExceptionia() {
        try {
            tika.alustaTietokannanLukija();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
            assertEquals(true, false);
       }   
    }
    
    @Test
    public void kirjoitaTietokantaanLisatenEiAiheutaIOExceptionia() {
        try {
            tika.kirjoitaTietokantaanLisaten("alustaTietokantaTestiTeksti\r\n", true);
        } catch (IOException ex) {
            System.out.println("IOException");
            assertEquals(true, false);
        }
    }
    
    @Test
    public void alustaTietokannanLukijanJalkeenLukijallaOnNext() {
        try {   
            tika.alustaTietokannanLukija();
            boolean onSeuraava = tika.getTietokannanLukija().hasNextLine();
            assertEquals(true, onSeuraava);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
            assertEquals(true, false);
       }   
    }
 
    @Test
    public void tiedostonKasittelijaPystyyKirjoittamaanTiedostoonJaLukemaanSita() {
        try {
            tika.kirjoitaTietokantaanLisaten("testiteksti", true);
            tika.alustaTietokannanLukija();

            String vikaRivi = "";

            while (tika.lukijallaSeuraavaRivi()) {
                vikaRivi = tika.lukijanSeuraavaRivi();
            }

            assertEquals("testiteksti", vikaRivi);
        } catch (IOException ex) {
            assertEquals(1, 2);
            System.out.println("tika ei onnistunut kirjoittamaan tiedostoon ja lukemaan tiedostoa");
        }
    }
    
    @Test 
    public void tiedostonKasittelijaOsaaNollataTiedoston() {
        this.tika.suljeLukija();
        try {
            this.tika.nollaaTiedosto();
        } catch (IOException ex) {
            System.out.println("Tiedoston nollaustestissä nollaaminen ei onnistu");
        }
        try {
            this.tika.alustaTietokannanLukija();
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedoston nollaamistestissä tiedostoa ei löydy");
        }
        
        assertEquals(false, tika.getTietokannanLukija().hasNext());        
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