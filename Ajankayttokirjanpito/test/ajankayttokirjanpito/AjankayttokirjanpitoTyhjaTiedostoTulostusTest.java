/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AjankayttokirjanpitoTyhjaTiedostoTulostusTest {

    private OmaKonsoli konsoli;
    private LegacyKayttoliittyma kali;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija meka;
    private LegacyTiedostonkasittelija tika;
    private LegacyTulostaja tulostaja;
    private KontekstinHaltija koha;
    private LegacyKomentoLogiikka kolo;
    private LegacyKomentotulkki kotu;
    
    private String eiMerkintoja;

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
        
        eiMerkintoja = " # Ei merkintöjä";

        SwingUtilities.invokeLater(kali);
        
        try {
            tika.nollaaTietokantaTiedosto();
            kolo.getTietokantaValimuisti().nollaaMuisti();
        } catch (IOException ex) {
            System.out.println("Tiedoston nollaaminen testien alussa ei onnistu: " + ex.getMessage());
        }
        
    }

    @Test
    public void tulostaKomentoTulostaaEimerkintojaViestinKunValimuistiOnTyhja() {
        
        konsoli.kirjoitaKomentoriville("tulosta");
        kotu.otaKomento();

        String odotettu = eiMerkintoja;
        String tuloste = konsoli.getTulosteAlue().getText();
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
        boolean tulosteOnSamaViestiKuinEimerkintoja = (aktuaali.equals(odotettu));

        System.out.println(aktuaali);

        assertEquals(true, tulosteOnSamaViestiKuinEimerkintoja);
    }
    
    @Test
    public void tulostaKomentoEiTulostaEimerkintojaViestiaKunValimuistiEiOleTyhja() {
        Merkinta lisattava = new Merkinta(new Paivays(1, 1, 1990),
                new Tapahtuma(new Kellonaika(10, 15),
                new Kellonaika(11, 30),
                "Muistimelua"));
        this.kolo.getTietokantaValimuisti().lisaaMerkinta(lisattava);
        konsoli.kirjoitaKomentoriville("tulosta");
        kotu.otaKomento();

        String eiOdotettu = eiMerkintoja;
        String tuloste = konsoli.getTulosteAlue().getText();
        String aktuaali = tuloste.substring(tuloste.length() - eiOdotettu.length(), tuloste.length());
        boolean tulosteOnEriViestiKuinEimerkintoja = (!aktuaali.equals(eiOdotettu));

        System.out.println(aktuaali);

        assertEquals(true, tulosteOnEriViestiKuinEimerkintoja);
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