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

/**
 *
 * @author Envy 6-1010
 */
public class TiedostonkasittelijaTest {

    private Tiedostonkasittelija tika;

    @Before
    public void setUp() {
        this.tika = new Tiedostonkasittelija();
        try {
            tika.kirjoitaTietokantaanLisaten("\nTestimerkintöjä\n", true);
        } catch (IOException ex) {
            
        }
        
    }

    @Test
    public void tiedostonKasittelijaPystyyLoytamaanTiedostostaHaettavaaAsiaa() {
        try {
            

            tika.kirjoitaTietokantaanLisaten("!testiteksti2:\n"
                    + "testitekstientesti", true);
            tika.kirjoitaTietokantaanLisaten("!testiteksti3:\n"
                    + "testitekstienteksti", true);
            tika.alustaTietokannanLukija();
            //oletetaan että tietokantaan tallennetaan tietoa niin että päätietoalkio,
            //esim päiväys, alkaa merkillä !.

            //tässä tehdään oletus haun tuloksen muodosta.
            String[] osumat = tika.haeTietoKannasta("!testiteksti2:");
            String odotettu = "testiteksti2:\n"
                    + "testitekstientesti\n";

            assertEquals(odotettu, osumat[1]);

            //oletetaan, että tietokantaan ei ole lisätty otsikolla '!testiteksti' muita tietoalkioita.           

        } catch (IOException ex) {
            assertEquals(1, 2);
            //mitä tähän pitäisi kirjoittaa?
        }
    }

    @Test
    public void tiedostonTulostaminenKonsoliinTulostaaTiedostonTiedotOikein() {
    }

    @Test
    public void tiedostonKasittelijaPystyyKirjoittamaanTiedostoonJaLukemaanSita() {
        try {
            tika.kirjoitaTietokantaanLisaten("testiteksti", true);
            tika.alustaTietokannanLukija();

            String vikaRivi = "";

            while (tika.lukijallaSeuraavaRivi()) {
                vikaRivi = tika.lukijanSeuraavaRivi();
            }

            assertEquals("testiteksti", vikaRivi);
        } catch (IOException ex) {
            assertEquals(1, 2);
            //mitä tähän pitäisi kirjoittaa?
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