/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import sovelluslogiikka.MerkinnanKasittelija;
import java.awt.Paint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.Dekooderi;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.Paivays;
import tietokantasysteemi.Tapahtuma;

/**
 *
 * @author Envy 6-1010
 */
public class MerkinnanKasittelijaTest {
    
    private MerkinnanKasittelija meka;
    private Merkinta eilinen;
    private Merkinta toinenEilinen;
    private String kellonajat;
        
    @Before
    public void setUp() {
        this.meka = new MerkinnanKasittelija(new Dekooderi());
        
        Paivays eilisPaiva = new Paivays(26, 11, 2013);
        
        eilinen = new Merkinta(eilisPaiva, 
                new Tapahtuma(new Kellonaika(12, 45),
                new Kellonaika(13, 00), "Nopea ruokailu"));
        
        toinenEilinen = new Merkinta(eilisPaiva,
                new Tapahtuma(new Kellonaika(13, 00),
                new Kellonaika(13,15), "Akateeminen vartti"));
        
        kellonajat = "00.00-0.1";
    }
    
    @Test
    public void uusiMerkinnanKasittelijaEiOleNull() {
        assertTrue(this.meka != null);
    }
    
    @Test
    public void muuttaaKayttajanAntamanMerkinnanStringMuodostaOikeanMuotoiseksi() {
        String syote = "20.10.2013!13.45-15.00!testikirjaus"; //! on toistaiseksi erottajasymboli tässä
        
        String mekanMuuttama = meka.muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(syote).toString();
        
        // \r\n vaaditaan windowssissa, jotta tieto kirjoitetaan tiedostoon oikein
        String odotettu = "20.10.2013\r\n    13.45-15.00: testikirjaus\r\n";
   
        assertEquals(odotettu, mekanMuuttama);
    }
    
    @Test
    public void yhdistaminenKasvattaaTapahtumienKokoa() {
        this.meka.yhdista(eilinen, toinenEilinen);
        
        assertEquals(2, eilinen.getTapahtumienMaara());
    }
    
    @Test
    public void luodunPaivayksenStringEsitysOnOikein() {
        String paivays = "30.1.2013";
        Paivays luotuPaivays = this.meka.luoPaivays(paivays);
        assertEquals("30.01.2013", luotuPaivays.toString());
    }
    
    @Test
    public void luoPaivaysLuoPaivayksenOikein() {
        String paivays = "1.1.2013";
        Paivays luotuPaivays = this.meka.luoPaivays(paivays);
        assertEquals(luotuPaivays.compareTo(new Paivays(1, 1, 2013)), 0);
    }
    
    @Test
    public void luoKellonajatMetodiLuoAloitusajanOikein() {     
        Kellonaika aloitusaika = this.meka.luoAloitusaikajaLopetusaika(kellonajat)[0];
        
        assertEquals(new Kellonaika(0, 0), aloitusaika);
    }
    
    @Test
    public void luoKellonajatMetodiLuoLopetusAjanOikein() {
        Kellonaika lopetusaika = this.meka.luoAloitusaikajaLopetusaika(kellonajat)[1];
        
        assertEquals(new Kellonaika(0,1), lopetusaika);
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