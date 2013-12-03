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
public class KellonaikaTest {
    private Kellonaika a;
    private Kellonaika b;
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void toStringToimiiOikein() {
        a = new Kellonaika(1, 03);
        assertEquals("01.03", a.toString());
    }
    
    @Test
    public void aikaEroMetodiLaskeeAikaEronOikeinKunEiJouduaMinuuttienTakiaVahentaaTunteja() {
        a = new Kellonaika(10, 30);
        b = new Kellonaika(9, 15);
        
        Kellonaika ero = a.aikaEro(b);
        
        assertEquals("01.15", ero.toString());
    }
    
    @Test
    public void aikaEroMetodiLaskeeAikaEronOikeinKunMinuuttienTakiaVahennetaanTunteja() {
        a = new Kellonaika(8, 36);
        b = new Kellonaika(7, 42);
        
        Kellonaika ero = a.aikaEro(b);
        
        assertEquals("00.54", ero.toString());
    }
    
    @Test
    public void aikaEroMetodiLaskeeAikaEronRiippumattaAikojenJarjestyksesta() {
        a = new Kellonaika(16, 42);
        b = new Kellonaika(19, 12);
        
        Kellonaika ero = a.aikaEro(b);
        
        assertEquals("02.30", ero.toString());
    }
    
    @Test
    public void aikaEroMetodiLaskeeAikaEronRiippumattaAikojenJarjestyksestaJaAjatOvatSamat() {
        a = new Kellonaika(16, 42);
        b = new Kellonaika(19, 12);
        
        Kellonaika ero = a.aikaEro(b);
        
        assertEquals(b.aikaEro(a), ero);
    }
    
    @Test
    public void aikaEroMetodiEiHajoaSamanSuuruistenAikojenErottamisessa() {
        a = new Kellonaika(13, 00);
        
        Kellonaika ero = a.aikaEro(a);
        
        assertEquals(new Kellonaika(0, 0), ero);
    }
    
    @Test
    public void aikaSummaMetodiLaskeeKaksiAikaaYhteenOikein() {
        a = new Kellonaika(13, 45);
        b = new Kellonaika(4, 52);
        
        int summa = a.aikaSummaMinuutteina(b);
        
        assertEquals(1117, summa);
    }
    
    @Test public void aikaVertailuToimiiOikein() {
        a = new Kellonaika(12, 12);
        b = new Kellonaika(15, 0);
        
        assertEquals(-1, a.compareTo(b));
    }
    
    @Test
    public void equalsMetodiTunnistaaSamatKellonajat() {
        a = new Kellonaika(00, 00);
        b = new Kellonaika(00, 00);
        
        assertEquals(a, b);
    }
    
    @Test
    public void equalsMetodiTunnistaaEriKellonajat() {
        a = new Kellonaika(10,00);
        b = new Kellonaika(00,10);
        
        assertFalse(a.equals(b));
    }
    
    @Test
    public void equalsMetodiTunnistaaKellonajanToisestaOliosta() {
        a = new Kellonaika(23, 59);
        Paivays p = new Paivays(12, 12, 2012);
        
        assertFalse(a.equals(p));
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