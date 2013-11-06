/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Tietokantasysteemi.Tiedostonkasittelija;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
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
public class Testit {
    
    private Kayttoliittyma kali;
    
    @Before
    public void setUp() {        
        kali = new Kayttoliittyma();     
             
        SwingUtilities.invokeLater(kali);
    }
    
    @Test
    public void vihreellinenKomentoTulostaaKomennonJaVirheuilmoituksen() {
        kali.getKonsoli().kirjoitaKomentoriville("Jeessys");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(5, tuloste.length());
        //jätetään alusta komentorivin tervehdys ja pyörivä kursori.
        
        assertEquals("> Jeessys\n :Ei ole komento",
                odotettu);
    }
    
    @Test
    public void n() {
        
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