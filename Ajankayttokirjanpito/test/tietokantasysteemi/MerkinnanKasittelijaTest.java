/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

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
public class MerkinnanKasittelijaTest {
    
    private MerkinnanKasittelija meka;
        
    @Before
    public void setUp() {
        this.meka = new MerkinnanKasittelija(new Dekooderi());
    }
    
    @Test
    public void muuttaaKayttajanAntamanMerkinnanOikeanMuotoiseksi() {
        String syote = "20.10.2013!13.45-15.00!testikirjaus"; //! on toistaiseksi erottajasymboli tässä
        
        String mekanMuuttama = meka.muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(syote).toString();
        
        //\r\n vaaditaan windowssissa, jotta tieto kirjoitetaan tiedostoon oikein
        String odotettu = "20.10.2013\r\n    13.45-15.00: testikirjaus\r\n";
   
        assertEquals(odotettu, mekanMuuttama);
    }
    
    //testi merkinnan hakemiselle kannasta!
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}