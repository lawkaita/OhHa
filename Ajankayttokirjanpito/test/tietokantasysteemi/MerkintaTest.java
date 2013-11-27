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
public class MerkintaTest {
    
    private Merkinta merkinta;
    private Merkinta toinenMerkinta;
    private Merkinta tulevaisuudenMerkinta;
    private Merkinta eilisenMerkinta;
    
    private Tapahtuma nokoset;
    
    private Paivays eilinen;

    @Before
    public void setUp() {
        Paivays paivays = new Paivays(11, 11, 2011);
        Paivays tulevaisuus = new Paivays(1, 1, 2014);
        eilinen = new Paivays(10, 11, 2011);
        
        Kellonaika ruokaAika = new Kellonaika(12, 45);
        Kellonaika nokosAika =  new Kellonaika(13, 15);
        Kellonaika tekemisAika = new Kellonaika(13, 30);
        
        Tapahtuma ruokailu = new Tapahtuma(ruokaAika, nokosAika, "Ruoka-aika");
        merkinta = new Merkinta(paivays, ruokailu);
        
        nokoset = new Tapahtuma(nokosAika, tekemisAika, "nokoset");        
        toinenMerkinta = new Merkinta(paivays, nokoset);
        
        Tapahtuma nokosetTulevaisuudessa = new Tapahtuma(nokosAika, tekemisAika, "nokoset tulevaisuudessa");
        tulevaisuudenMerkinta = new Merkinta(tulevaisuus, nokosetTulevaisuudessa);
        
        Tapahtuma eilisenRuokailu = new Tapahtuma(ruokaAika, nokosAika, "eilispäivän ruoka");
        eilisenMerkinta = new Merkinta(eilinen, eilisenRuokailu);
        
    }
    
    @Test
    public void luotuTapahtumaEiOleNull() {
        assertTrue(merkinta != null);
    }
    
    @Test
    public void merkintaVertaaItseaanSamanKokoiseenMerkintaanOikein() {
        assertEquals(merkinta.compareTo(toinenMerkinta), 0);
    }
    
    @Test
    public void merkintaVertaaItesaanSuurempaanMerkintaanOikein() {
        assertTrue(merkinta.compareTo(tulevaisuudenMerkinta) < 0);
    }
    
    @Test
    public void merkintaVertaaItseaanPienempaanMerkintaanOikein() {
        assertTrue(merkinta.compareTo(eilisenMerkinta) > 0);
    }
    
    @Test
    public void tapahtumienMaaraMerkinnassaOnAluksiYksi() {
        assertEquals(merkinta.getTapahtumienMaara(), 1);
    }
    
    @Test
    public void tapahtumienMaaraKasvaaKunMerkintaanLisataanTapahtuma() {
        merkinta.lisaaTapahtuma(nokoset);
        assertEquals(merkinta.getTapahtumienMaara(), 2);
    }
    
    @Test
    public void tapahtumiinKaytettyAikaLaskeeAjanOikeinKunTapahtumiaonYksi(){
        int minuutit = toinenMerkinta.tapahtumiinKaytettyAikaMinuutteina();
        assertEquals(15, minuutit);
    }
    
    @Test
    public void tapahtumiinKaytettyAikaKasvaaOikienKunMerkintaanLisataanTapahtuma() {
        Kellonaika lenkkiAika = new Kellonaika(16, 00);
        Kellonaika suihkuAika = new Kellonaika(16, 45);
        
        Tapahtuma eilisenLenkki = new Tapahtuma(lenkkiAika, suihkuAika, "Lenkki");
        
        eilisenMerkinta.lisaaTapahtuma(eilisenLenkki);
        
        int minuutit = eilisenMerkinta.tapahtumiinKaytettyAikaMinuutteina();
        
        assertEquals(75, minuutit);
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