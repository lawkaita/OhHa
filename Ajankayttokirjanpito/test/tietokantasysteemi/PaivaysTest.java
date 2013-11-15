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

    public PaivaysTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void tunnistaaSamanPaivanSamaksi() {
        Paivays a = new Paivays(12, 12, 13);
        Paivays b = new Paivays(12, 12, 13);
        
        boolean onSamaPaiva = a.equals(b);
        
        assertEquals(true, onSamaPaiva);
    }
    
    @Test
    public void tunnistaaEriPaivatEriPaiviksi(){
        Paivays a = new Paivays(12, 12, 13);
        Paivays b = new Paivays(13, 12, 13);
        
        boolean onSamaPaiva = a.equals(b);
        
        assertEquals(false, onSamaPaiva);        
    }
    
    @Test
    public void osaaJarjestaaPaiviaSuhteessaToisiinsa() {
        ArrayList<Paivays> paivaykset = new ArrayList<Paivays>();
        Paivays[] paivayksetOikeassaJarjestyksessa = new Paivays[4];
        
        Paivays a = new Paivays(12, 12, 13);
        Paivays b = new Paivays(1, 2, 3);
        Paivays c = new Paivays(4, 6, 12);
        Paivays d = new Paivays(24, 1, 12);
        
        paivayksetOikeassaJarjestyksessa[0] = b;
        paivayksetOikeassaJarjestyksessa[1] = d;
        paivayksetOikeassaJarjestyksessa[2] = c;
        paivayksetOikeassaJarjestyksessa[3] = a;       
           
        paivaykset.add(a);
        paivaykset.add(b);
        paivaykset.add(c);
        paivaykset.add(d);
        
        Collections.sort(paivaykset);
            
        for (int i = 0; i < 4; i++) {
            //System.out.println(paivayksetOikeassaJarjestyksessa[i].toString() + "=" + paivaykset.get(i).toString());
            assertEquals(paivayksetOikeassaJarjestyksessa[i], paivaykset.get(i));            
        }
        
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
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