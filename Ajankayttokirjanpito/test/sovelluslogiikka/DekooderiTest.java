/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Envy 6-1010
 */
public class DekooderiTest {
    
    private Dekooderi d;
    
    @Before
    public void setUp() {
        d = new Dekooderi();
    }
    
       
    @Test
    public void dekooderiLajitteleeStringOlionOikein() {
        String s = "hei olen pekka";

        String[] dekoodi = d.dekoodaa(s, null);

        assertEquals("hei", dekoodi[0]);
        assertEquals("olen", dekoodi[1]);
        assertEquals("pekka", dekoodi[2]);    
    }
    
    @Test
    public void dekooderiLajitteleeStringOlionOikeinKunKaytetaanValinnaistaErotusMerkkia() {
        String s = "hei!olen!pekka!";
        String[] dekoodi = d.dekoodaa(s, '!');
        
        assertEquals("hei", dekoodi[0]);
        assertEquals("olen", dekoodi[1]);
        assertEquals("pekka", dekoodi[2]);       
        
    }
    
    @Test
    public void dekooderiPalauttaaYhdenAlkionTaulunKunLajiteltavassaStringissaEiOleDekoodausMerkkia() {
        String s = "hei!olen!pekka!";
        
        String[] dekoodi = d.dekoodaa(s, null);
        
        assertEquals(1, dekoodi.length);  
    }
    
    @Test
    public void dekooderinPalauttamaYhdenAlkionTauluSisaltaaAnnetunStringin() {
        String s = "hei!olen!pekka!";
        
        String[] dekoodi = d.dekoodaa(s, null);
        
        assertEquals("hei!olen!pekka!", dekoodi[0]);
    }
    

    
}