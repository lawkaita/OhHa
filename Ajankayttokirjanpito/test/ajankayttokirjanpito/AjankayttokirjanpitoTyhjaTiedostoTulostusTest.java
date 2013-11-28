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
public class AjankayttokirjanpitoTyhjaTiedostoTulostusTest {

    private OmaKonsoli konsoli;
    private Kayttoliittyma kali;
    private Dekooderi dekooderi;
    private OmaTiedostonkasittelija tika;
    private Tulostaja tulostaja;
    private KontekstinHaltija koha;
    private KomentoLogiikka kolo;
    private Komentotulkki kotu;
    
    private String eiMerkintoja;

    @Before
    public void setUp() {
        konsoli = new OmaKonsoli(true);
        kali = new Kayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        tika = new OmaTiedostonkasittelija(dekooderi);
        tulostaja = new Tulostaja(konsoli, tika, dekooderi);
        koha = new KontekstinHaltija();
        kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha, kali);
        kotu = new Komentotulkki(konsoli, koha, kolo, dekooderi);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        eiMerkintoja = " :Ei merkintöjä";

        SwingUtilities.invokeLater(kali);
        try {
            tika.nollaaTietokantaTiedosto();
            kolo.getTietokantaValimuisti().nollaaMuisti();
        } catch (IOException ex) {
            System.out.println("IOException");
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
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}