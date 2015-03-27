/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.io.IOException;
import javax.swing.SwingUtilities;
import kayttoliittyma.LegacyKayttoliittyma;
import kayttoliittyma.LegacyKomentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.LegacyTulostaja;
import legacykonsoli.OmaKonsoli;
import org.junit.After;
import org.junit.Before;
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
public class AjankayttokirjanitoValimuistinTulostusTest {

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

        String odotettu = " # 11.11.2013\n"
                + " #     12.12-13.45: testiteksti\n"
                + " # \n"
                + " # 11.12.2013\n"
                + " #     13.12-13.45: testiteksti2\n"
                + " # ";


        String tuloste = konsoli.getTulosteAlue().getText();
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());

        System.out.println("---");
        System.out.println(odotettu);
        System.out.println("---");
        System.out.println(aktuaali);
        System.out.println("---");

        assertEquals(odotettu, aktuaali);

    }

    @Test
    public void tulostaKomentoKomentoTulostaValimuistinOikeinKunKannassaOnMerkintoja() {

        konsoli.kirjoitaKomentoriville("tallenna");
        kotu.otaKomento();
        konsoli.kirjoitaKomentoriville("nollaa");
        kotu.otaKomento();
        konsoli.kirjoitaKomentoriville("tulosta");
        kotu.otaKomento();

        String odotettu = " # Ei merkintöjä";
        String tuloste = konsoli.getTulosteAlue().getText();
        String saatu = konsoli.getTulosteAlue().getText().substring(tuloste.length() - odotettu.length());

        assertEquals(odotettu, saatu);

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