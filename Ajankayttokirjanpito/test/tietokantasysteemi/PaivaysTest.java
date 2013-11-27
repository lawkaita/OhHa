/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lawkaita
 */
public class PaivaysTest {

    private Paivays erasPaiva;

    @Before
    public void setUp() {
        erasPaiva = new Paivays(12, 12, 13);

    }
    
    @Test
    public void luotuPaivaysEiOleNull() {
        assertTrue(erasPaiva != null);
    }
    
    @Test
    public void toStringTulostaaPaivanOikein() {
        assertEquals("12.12.13", erasPaiva.toString());
    }
    
    @Test
    public void toStringKirjoittaaTarvittaessaEtunollat() {
        Paivays p = new Paivays(3, 7, 2013);
        assertEquals("03.07.2013", p.toString());
    }
    
    @Test
    public void tunnistaaSamanPaivanSamaksi() {
        Paivays b = new Paivays(12, 12, 13);

        boolean onSamaPaiva = erasPaiva.equals(b);

        assertTrue(onSamaPaiva);
    }

    @Test
    public void tunnistaaEriPaivatEriPaiviksi() {
        Paivays b = new Paivays(13, 12, 13);

        boolean onSamaPaiva = erasPaiva.equals(b);

        assertFalse(onSamaPaiva);
    }
    
    @Test
    public void compareToPalauttaaNollaJosPaivatOvatSamat() {
        Paivays b = new Paivays(12, 12, 13);
        
        assertEquals(erasPaiva.compareTo(b), 0);
    }

    @Test
    public void osaaJarjestaaPaiviaSuhteessaToisiinsaKoskaOnOverridattuComparableMetodit() {
        ArrayList<Paivays> paivaykset = new ArrayList<Paivays>();
        Paivays[] paivayksetOikeassaJarjestyksessa = new Paivays[4];

        Paivays b = new Paivays(1, 2, 3);
        Paivays c = new Paivays(4, 6, 12);
        Paivays d = new Paivays(24, 1, 12);

        paivayksetOikeassaJarjestyksessa[0] = b;
        paivayksetOikeassaJarjestyksessa[1] = d;
        paivayksetOikeassaJarjestyksessa[2] = c;
        paivayksetOikeassaJarjestyksessa[3] = erasPaiva;

        paivaykset.add(erasPaiva);
        paivaykset.add(b);
        paivaykset.add(c);
        paivaykset.add(d);

        Collections.sort(paivaykset);

        for (int i = 0; i < 4; i++) {
            //System.out.println(paivayksetOikeassaJarjestyksessa[i].toString() + "=" + paivaykset.get(i).toString());
            assertEquals(paivayksetOikeassaJarjestyksessa[i], paivaykset.get(i));
        }
    }
}