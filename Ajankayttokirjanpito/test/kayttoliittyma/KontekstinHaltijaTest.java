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
 * @author lawkaita
 */
public class KontekstinHaltijaTest {
    
    private KontekstinHaltija koha;
    
    
    @Before
    public void setUp() {
        this.koha = new KontekstinHaltija();
    }
    
    @Test
    public void uusiKontekstinHaltijaEiOleNull() {
        assertTrue(this.koha != null);
    }
    
    @Test
    public void uusiKontekstinHaltijaEiOleMissaanKontekstissa() {
        assertFalse(this.koha.onKontekstissa());
    }
    
    @Test
    public void onKontekstissaMetodiPalauttaaTrueJosJokinKontekstinHaltijaOnJossakinKontekstissa() {
        this.koha.setMerkintaanPaiva(true);
        
        assertTrue(this.koha.onKontekstissa());
    }
    
    @Test
    public void onKontekstissaOnFalseKunPoistutaanKaikistaKontekstteista() {
        this.koha.setMerkintaanLopetusaika(true);
        this.koha.poistuKaikistaKonteksteista();
        
        assertFalse(this.koha.onKontekstissa());
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