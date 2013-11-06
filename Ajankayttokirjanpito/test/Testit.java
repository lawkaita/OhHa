/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Tietokantasysteemi.Tiedostonkasittelija;
import kayttoliittyma.Dekooderi;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lawkaita
 */
public class Testit {
    
    private Kayttoliittyma kali;
    private Tiedostonkasittelija tika;
    
    @Before
    public void setUp() {        
        kali = new Kayttoliittyma();     
        tika = new Tiedostonkasittelija();
        
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
    public void tiedostonKasittelijaPystyyLoytamaanTiedostostaHaettavaaAsiaa(){
        
    }
    
    @Test
    public void tiedostonKasittelijaPystyyKirjoittamaanTiedostoonJaLukemaanSita() {
        try {
            tika.lisaaTietokantaan("testiteksti");
            tika.alustaTietokannanLukija();
            
            String vikaRivi = "";
            
            while(tika.lukijallaSeuraavaRivi()) {                
                vikaRivi = tika.lukijanSeuraavaRivi();
            }
                        
            assertEquals("testiteksti", vikaRivi);
        } catch (IOException ex) {
            assertEquals(1,2);
            //mitä tähän pitäisi kirjoittaa?
        }
    }
    
    @Test
    public void tiedostonTulostaminenKonsoliinTulostaaTiedostonTiedotOikein() {
        
    }
    
    @Test
    public void dekooderiLajitteleeStringOlionOikein(){
        String s = "hei olen pekka";
        
        Dekooderi d = new Dekooderi();
        
        String[] dekoodi = d.dekoodaa(s, null);
        
        assertEquals(dekoodi[0], "hei");
        assertEquals(dekoodi[1], "olen");
        assertEquals(dekoodi[2], "pekka");
    }
    
    
    //onkoPaivaTesti toimii
    //onko Aika testi toimii, testejä 7
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}