/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.Dekooderi;
import sovelluslogiikka.MerkinnanKasittelija;

/**
 *
 * @author Envy 6-1010
 */
public class TiedostonkasittelijaHakuTest {

    private OmaTiedostonkasittelija tika;

    @Before
    public void setUp() {
        this.tika = new OmaTiedostonkasittelija(new Dekooderi());
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\nTiedostonkasittelijaHakuTesti\n", true);
        } catch (IOException ex) {
            System.out.println("TiedostonkasittelijaTestIOException");
        }
    }

    @Test
    public void tiedostonKasittelijaEiPalautaNullTauluaKunHaunOsumaOnTietokannassa() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("1.1.1990", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    02.00-03.00: nulltest", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\n", true);

            String[] merkintaTaulu = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla("1.1.1990");

            boolean tauluEiOleNolla = !(merkintaTaulu == null);

            assertEquals(true, tauluEiOleNolla);

        } catch (IOException ex) {
            assertEquals(true, false);
            System.out.println("IOException");
        }
    }

    @Test
    public void tiedostonKasittelijaPalauttaaNullTaulunKunHaunKohdeEiOleTietokannassa() {

        String[] merkintaTaulu = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla("xbörfmeezormox");

        boolean tauluOnNull = (merkintaTaulu == null);

        assertEquals(true, tauluOnNull);
    }

    @Test
    public void tiedostonKasittelijaLoytaaTiedostostaSenPaivanMikaSinneJuuriKirjoitettiin() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("2.3.4567", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    00.01-00.03: juuri-kirjoitettu-testiteksti", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\n", true);

            String[] osumat = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla("2.3.4567");

            String odotettu = "2.3.4567";

            assertEquals(odotettu, osumat[0]);

        } catch (IOException ex) {
            assertEquals(true, false);
            System.out.println("IOException");
        }
    }

    @Test
    public void tiedostonKasittelijaLoytaaTiedostostaSenSatunnaisenTekstinMikaSinneKirjoitettiin() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("abcTesti", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("bbcRadiokanava", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\n", true);

            String[] osumat = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla("abcTesti");

            String odotettu = "abcTesti";

            assertEquals(odotettu, osumat[0]);

        } catch (IOException ex) {
            assertEquals(true, false);
            System.out.println("IOException");
        }
    }

    @Test
    public void tiedostonKasittelijaPystyyLoytamaanTiedostostaHaettavaaAsiaaKunHalutaanHakusananAllaOlevaAsia() {
        try {
            //Täällä, onko "1.1.2000" jälkeen \n vai \r\n vaikuttaa windowsissa, 
            //tuleeko rivinvaihtoa.
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("1.1.2000", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    00.00-01.00: testitekstientesti", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\n", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("1.2.2001", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    01.00-01.30: testitekstienteksti", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("\n", true);

            String[] osumat = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla("1.1.2000");
            String odotettu = "    00.00-01.00: testitekstientesti";

            assertEquals(odotettu, osumat[1]);

        } catch (IOException ex) {
            assertEquals(1, 2);
            System.out.println("IOException");
        }
    }

    @Test
    public void pystyyHahmottamaanKokonaisenHaettavanMerkinnanJaAntamaanSen() {
        try {
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("1.1.1666", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    16.00-21.00: merkinta1", true);
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun("    21.30-22.30: merkinta2", true);
            
            String[] osuma = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla("1.1.1666");
            
            boolean osumaOnOikea = true;
            
            if(osuma.length != 3) {
                osumaOnOikea = false;
            }
            
            if(!osuma[0].equals("1.1.1666")) {
                osumaOnOikea = false;
            }
            
            if(!osuma[1].equals("    16.00-21.00: merkinta1")) {
                osumaOnOikea = false;
            }
            
            if(!osuma[2].equals("    21.30-22.30: merkinta2")) {
                osumaOnOikea = false;
            }
            
            assertEquals(true, osumaOnOikea);
            
            
        } catch (IOException ex) {
            assertEquals(1, 2);
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