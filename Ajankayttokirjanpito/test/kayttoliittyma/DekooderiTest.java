/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

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
public class DekooderiTest {
    
    public DekooderiTest() {
    }
       
    @Test
    public void dekooderiLajitteleeStringOlionOikein() {
        String s = "hei olen pekka";

        Dekooderi d = new Dekooderi();

        String[] dekoodi = d.dekoodaa(s, null);

        assertEquals(dekoodi[0], "hei");
        assertEquals(dekoodi[1], "olen");
        assertEquals(dekoodi[2], "pekka");
    }

    
}