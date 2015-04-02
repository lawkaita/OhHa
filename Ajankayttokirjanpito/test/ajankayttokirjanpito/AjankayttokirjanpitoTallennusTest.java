/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import legacykayttoliittyma.LegacyKayttoliittyma;
import legacykayttoliittyma.LegacyKomentotulkki;
import legacykayttoliittyma.KontekstinHaltija;
import legacykayttoliittyma.Nappaimistonkuuntelija;
import legacykayttoliittyma.LegacyTulostaja;
import legacykonsoli.OmaKonsoli;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.LegacyKomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.LegacyTiedostonkasittelija;
import tietokantasysteemi.Paivays;
import tietokantasysteemi.Tapahtuma;

/**
 *
 * @author Envy 6-1010
 */
public class AjankayttokirjanpitoTallennusTest {

    private OmaKonsoli konsoli;
    private LegacyKayttoliittyma kali;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija meka;
    private LegacyTiedostonkasittelija tika;
    private LegacyTulostaja tulostaja;
    private KontekstinHaltija koha;
    private LegacyKomentoLogiikka kolo;
    private LegacyKomentotulkki kotu;

    @Before
    public void setUp() {
        konsoli = new OmaKonsoli(true);
        kali = new LegacyKayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        meka = new MerkinnanKasittelija(dekooderi);
        tulostaja = new LegacyTulostaja(konsoli, dekooderi);
        tika = new LegacyTiedostonkasittelija(dekooderi, meka, tulostaja);
        koha = new KontekstinHaltija();
        kolo = new LegacyKomentoLogiikka(tulostaja, tika, konsoli, koha, kali, meka);
        kotu = new LegacyKomentotulkki(konsoli, koha, kolo, dekooderi);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        Merkinta merkinta = new Merkinta(new Paivays(1, 1, 1),
                new Tapahtuma(new Kellonaika(0, 0), new Kellonaika(0, 1), "Alkuhetki"));
        
        kolo.getTietokantaValimuisti().lisaaMerkinta(merkinta);
        kolo.tallenna();

        SwingUtilities.invokeLater(kali);
        
    }
    
    @Test
    public void tiedostossaOnAluksiJotakin() {
        assertTrue(tika.getTietokannanLukija().hasNext());
    }

    @Test
    public void nollaaKomentoJaTallennaKomentoTyhjentaaTiedoston() {
        konsoli.kirjoitaKomentoriville("nollaa");
        kotu.otaKomento();

        konsoli.kirjoitaKomentoriville("tallenna");
        kotu.otaKomento();

        assertFalse(tika.getTietokannanLukija().hasNext());
    }

    @After
    public void tearDown() {
        try {
            tika.nollaaTietokantaTiedosto();
        } catch (IOException ex) {
            System.out.println("Tietokantatiedoston nollaus testien lopussa ei onnistu: " + ex.getMessage());
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}