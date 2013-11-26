/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import sovelluslogiikka.OmaAjanAntaja;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.OmaTiedostonkasittelija;
import tietokantasysteemi.Paivays;
import tietokantasysteemi.Tapahtuma;

/**
 *
 * @author Envy 6-1010
 */
public class AjankayttokirjanitoValimuistinTulostusTest {

    private OmaKonsoli konsoli;
    private Kayttoliittyma kali;
    private Dekooderi dekooderi;
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
        tika = new OmaTiedostonkasittelija(dekooderi);
        tulostaja = new Tulostaja(konsoli, tika, dekooderi);
        koha = new KontekstinHaltija();
        kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha, kali);
        kotu = new Komentotulkki(konsoli, koha, kolo);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));

        SwingUtilities.invokeLater(kali);
        
        kolo.getTietokantaValimuisti().nollaaMuisti();
        
        Merkinta eka = new Merkinta(new Paivays(11, 11, 2013),
                new Tapahtuma(new Kellonaika(12, 12), new Kellonaika(13, 45), "testiteksti"));

        Merkinta toka = new Merkinta(new Paivays(11, 12, 2013),
                new Tapahtuma(new Kellonaika(13, 12), new Kellonaika(13, 45), "testiteksti2"));

        kolo.getTietokantaValimuisti().lisaaMerkinta(eka);
        kolo.getTietokantaValimuisti().lisaaMerkinta(toka);

    }

    @Test
    public void tulostaKomentoTulostaaValimuistinOikeinKunValimuistissaOnMerkintoja() {

        konsoli.kirjoitaKomentoriville("tulosta");
        kotu.otaKomento();

        String odotettu = " :11.11.2013\n"
                + " :    12.12-13.45: testiteksti\n"
                + " :\n"
                + " :11.12.2013\n"
                + " :    13.12-13.45: testiteksti2\n"
                + " :";
                

        String tuloste = konsoli.getTulosteAlue().getText();
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());

        System.out.println("---");
        System.out.println(odotettu);
        System.out.println("---");
        System.out.println(aktuaali);
        System.out.println("---");

        assertEquals(odotettu, aktuaali);

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