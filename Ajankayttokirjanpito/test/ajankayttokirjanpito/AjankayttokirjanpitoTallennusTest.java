/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.OmaKonsoli;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.OmaTiedostonkasittelija;
import tietokantasysteemi.Paivays;
import tietokantasysteemi.Tapahtuma;

/**
 *
 * @author Envy 6-1010
 */
public class AjankayttokirjanpitoTallennusTest {

    private OmaKonsoli konsoli;
    private Kayttoliittyma kali;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija meka;
    private OmaTiedostonkasittelija tika;
    private Tulostaja tulostaja;
    private KontekstinHaltija koha;
    private KomentoLogiikka kolo;
    private Komentotulkki kotu;

    @Before
    public void setUp() {
        konsoli = new OmaKonsoli(true);
        kali = new Kayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        meka = new MerkinnanKasittelija(dekooderi);
        tika = new OmaTiedostonkasittelija(dekooderi, meka);
        tulostaja = new Tulostaja(konsoli, dekooderi);
        koha = new KontekstinHaltija();
        kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha, kali, meka);
        kotu = new Komentotulkki(konsoli, koha, kolo, dekooderi);

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
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}