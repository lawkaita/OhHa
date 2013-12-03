/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import sovelluslogiikka.MerkinnanKasittelija;
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
public class TapahtumaTest {

    private MerkinnanKasittelija meka;
    private Tapahtuma erasTapahtuma;
    private Tapahtuma toinenTapahtuma;
    private Kellonaika nollaAika;

    @Before
    public void setUp() {
        this.meka = new MerkinnanKasittelija(new Dekooderi());
        Kellonaika[] erasKellonaikaPari = this.meka.luoAloitusaikajaLopetusaika("00.00-12.03");
        Kellonaika[] toinenKellonaikaPari = this.meka.luoAloitusaikajaLopetusaika("13.05-17.8");

        this.erasTapahtuma = new Tapahtuma(erasKellonaikaPari[0], erasKellonaikaPari[1], "Kokeiltu mekan palveluita");
        this.toinenTapahtuma = new Tapahtuma(toinenKellonaikaPari[0], toinenKellonaikaPari[1],
                "Todettu ettei tama paljoa lyhyempi ole");
        
        nollaAika = new Kellonaika(00,00);
    }
    
    @Test
    public void compareToTunnistaaSamansuuruisetTapahtumat() {
        Tapahtuma erasToinenTapahtuma = new Tapahtuma(new Kellonaika(0, 0), new Kellonaika(12, 3), "jokin tapahtuma");
        assertEquals(erasTapahtuma.compareTo(erasToinenTapahtuma), 0);
    }
    
    @Test
    public void compareToTunnistaaErisuuruisetTapahtumat() {
        assertTrue(erasTapahtuma.compareTo(toinenTapahtuma) != 0);
    }
    
    @Test
    public void toStringTulostaaTapahtumanOikein() {
        assertEquals("    00.00-12.03: Kokeiltu mekan palveluita", erasTapahtuma.toString());
    }
    
    @Test
    public void kestoPalauttaaOikeanKellonajan() {
        assertEquals(new Kellonaika(4, 3), toinenTapahtuma.kesto());
    }
    
    @Test
    public void kestoPalauttaaOikeanKellonajanKunAikaaEiOleTapahtumassaKulunut() {        
        Tapahtuma toinenErasTapahtuma = new Tapahtuma(nollaAika, nollaAika, "Plancin aika");
        Kellonaika kesto = toinenErasTapahtuma.kesto();
        assertEquals(nollaAika, kesto);
    }
    
    @Test
    public void kellonaikaOsuuTapahtumaanKunSeOsuuSiihen() {
        boolean tosi = erasTapahtuma.kellonaikaOsuuTapahtumaan(new Kellonaika(10, 30));
        assertTrue(tosi);
    }
    
    @Test
    public void kellonaikaEiOsuTapahtumaanKunSeOsuuTapahtumanJalkeiseenAikaan() {
        boolean tosi = erasTapahtuma.kellonaikaOsuuTapahtumaan(new Kellonaika(12, 04));
        assertFalse(tosi);
    }
    
    @Test
    public void kellonaikaEiOsuTapahtumaanKunSeOsuuTapahtumaaEdeltaneeseenAikaan() {
        boolean tosi = toinenTapahtuma.kellonaikaOsuuTapahtumaan(new Kellonaika(3, 55));
        assertFalse(tosi);
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