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

    private OmaTiedostonkasittelija tika;

    @Before
    public void setUp() {
        this.tika = new OmaTiedostonkasittelija(new Dekooderi()); 
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("TietokantaTestiTeksti\r\n", true);
        } catch (IOException ex) {
            System.out.println("Testtin valmistelu ei onnistu: " + ex.getMessage());
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
    public void tikanTiedostonKasittelijaEiOleNull() {
        assertTrue(tika.getTietokannanLukija() != null);
    }
    
    @Test
    public void alustaTietokannanLukijaEiAiheutaFileNotFoundExceptionia() {
        try {
            tika.alustaTietokannanLukija();
        } catch (FileNotFoundException ex) {
            testiEiOnnistunut(ex);
       }   
    }
    
    @Test
    public void kirjoitaTietokantaanLisatenEiAiheutaIOExceptionia() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("alustaTietokantaTestiTeksti\r\n", true);
        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
    }
    
    @Test
    public void alustaTietokannanLukijanJalkeenLukijallaOnNext() {
        try {   
            tika.alustaTietokannanLukija();
            boolean onSeuraava = tika.getTietokannanLukija().hasNextLine();
            assertEquals(true, onSeuraava);
        } catch (FileNotFoundException ex) {
            testiEiOnnistunut(ex);
       }   
    }
 
    @Test
    public void tiedostonKasittelijaPystyyKirjoittamaanTiedostoonJaLukemaanSita() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("testiteksti", true);
            tika.alustaTietokannanLukija();

            String vikaRivi = "";

            while (tika.lukijallaSeuraavaRivi()) {
                vikaRivi = tika.lukijanSeuraavaRivi();
            }

            assertEquals("testiteksti", vikaRivi);
        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
    }
    
    @Test 
    public void tiedostonKasittelijaOsaaNollataTiedoston() {
        this.tika.suljeLukija();
        try {
            this.tika.nollaaTietokantaTiedosto();
        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
        try {
            this.tika.alustaTietokannanLukija();
        } catch (FileNotFoundException ex) {
            testiEiOnnistunut(ex);
        }
        
        assertEquals(false, tika.getTietokannanLukija().hasNext());        
    }
    
    @Test
    public void tiedostonKasittelijaOsaaSanoaEttaMerkintaJokaOnKannassaOnKannassa() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("haeMinut", true);
            boolean onKannassa = tika.kannassaOnMerkintaPaivalla("haeMinut");
            
            assertEquals(true, onKannassa);
            
        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
    }
    
    @Test
    public void tiedostonKasittelijaOsaaSanoaEttaMerkintaJokaEiOleKannassaEiTosiaanOleKannassa() {
        boolean onKannassa = tika.kannassaOnMerkintaPaivalla("meezormoxEliMinuaEiSaaLoytya");
        
        assertEquals(false, onKannassa);        
    }
    
    @Test
    public void tiedostonKasittelijaLaskeeMerkintojenMaaranNollaksiKunEiMerkintoja() {
        try {
            tika.nollaaTietokantaTiedosto();
            int merkintojenMaara = tika.laskeMerkittyjenPaivienMaara();
            
            assertEquals(0, merkintojenMaara);
        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
    }
    
    @Test
    public void merkintojenMaaraKasvaaKunLisataanMerkinta(){
        try {
            tika.nollaaTietokantaTiedosto();
            
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("1.1.2000", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    00.00-01.00: testitekstientesti", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\n", true);
            
            int merkintojenMaara = tika.laskeMerkittyjenPaivienMaara();
            
            assertEquals(1, merkintojenMaara);
        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
    }


    @After
    public void tearDown() {
        try {
            tika.nollaaTietokantaTiedosto();
        } catch (IOException ex) {
            System.out.println("Tietokantatiedoston nollaus testien lopussa ei onnistu: " + ex.getMessage());
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    private void testiEiOnnistunut(IOException ex) {
        System.out.println("Testi ei onnistu!" + ex.getMessage());
    }
}