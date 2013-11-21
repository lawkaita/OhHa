/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajankayttokirjanpito;

import java.io.IOException;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.Konsoli;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.AjanAntaja;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.KomentoLogiikka;
import tietokantasysteemi.Tiedostonkasittelija;

/**
 *
 * @author Envy 6-1010
 */
public class AjankayttokirjanitoTiedostonTulostusTest {

    private Konsoli konsoli;
    private Kayttoliittyma kali;
    private Dekooderi dekooderi;
    private Tiedostonkasittelija tika;
    private Tulostaja tulostaja;
    private KontekstinHaltija koha;
    private KomentoLogiikka kolo;
    private Komentotulkki kotu;

    @Before
    public void setUp() {
        konsoli = new Konsoli(true);
        kali = new Kayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        tika = new Tiedostonkasittelija(dekooderi);
        tulostaja = new Tulostaja(konsoli, tika, dekooderi);
        koha = new KontekstinHaltija();
        kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha);
        kotu = new Komentotulkki(konsoli, koha, kolo);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));

        SwingUtilities.invokeLater(kali);
    }
    
    @Test
    public void tulostaKomentoTulostaaEimerkintojaViestinKunTietokantaOnTyhja() {
        try {
            tika.nollaaTiedosto();
            kali.getKonsoli().kirjoitaKomentoriville("tulosta");
            kotu.otaKomento();

            String odotettu = " :Ei merkintöjä\n :::::"; //tulostus loppuu aina rivinvaihtoon ja neljään: ':'
            String tuloste = kali.getKonsoli().getTulosteAlue().getText();
            String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
            boolean tulosteOnSamaViestiKuinEimerkintoja = (aktuaali.equals(odotettu));

            System.out.println(aktuaali);

            assertEquals(true, tulosteOnSamaViestiKuinEimerkintoja);
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }
    

    @Test
    public void tulostaKomentoEiTulostaEimerkintojaViestiaKunTietokantaEiOleTyhja() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("lisataanMeluaMuistiin", true);
            kali.getKonsoli().kirjoitaKomentoriville("tulosta");
            kotu.otaKomento();

            String eiOotettu = " :Ei merkintöjä\n :::::";
            String tuloste = kali.getKonsoli().getTulosteAlue().getText();
            String aktuaali = tuloste.substring(tuloste.length() - eiOotettu.length(), tuloste.length());
            boolean tulosteOnEriViestiKuinEimerkintoja = (!aktuaali.equals(eiOotettu));

            System.out.println(aktuaali);

            assertEquals(true, tulosteOnEriViestiKuinEimerkintoja);
        } catch (IOException ex) {
            System.out.println("IOException");
        }
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