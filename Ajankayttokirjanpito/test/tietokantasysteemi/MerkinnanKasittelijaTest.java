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

/**
 *
 * @author Envy 6-1010
 */
public class MerkinnanKasittelijaTest {
    
    private MerkinnanKasittelija meka;
        
    @Before
    public void setUp() {
        this.meka = new MerkinnanKasittelija();
    }
    
    @Test
    public void muuttaaKayttajanAntamanMerkinnanOikeanMuotoiseksi() {
        String syote = "20.10.2013\n13.45-15.00\ntestikirjaus";
        
        String mekanMuuttama = meka.muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(syote);
        
        String odotettu = "20.10.2013\n  13.45-15.00: testikirjaus";
   
        assertEquals(odotettu, mekanMuuttama);
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