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
    
    public KellonaikaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
    public void aikaSummaMetodiLaskeeKaksiAikaaYhteenOikein() {
        a = new Kellonaika(13, 45);
        b = new Kellonaika(4, 52);
        
        int summa = a.aikaSummaMinuutteina(b);
        
        assertEquals(1117, summa);
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