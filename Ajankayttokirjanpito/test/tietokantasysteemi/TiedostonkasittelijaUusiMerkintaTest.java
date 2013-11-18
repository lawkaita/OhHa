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

/**
 *
 * @author lawkaita
 */
public class TiedostonkasittelijaUusiMerkintaTest {

    private Tiedostonkasittelija tika;

    @Before
    public void setUp() {
        this.tika = new Tiedostonkasittelija(new Dekooderi());

        try {
            tika.nollaaTiedosto();
        } catch (IOException ex) {
            System.out.println("TiedostonkasittelijaTestIOException");
        }
    }

    @Test
    public void tietokantaanVoiTehdaMerkinnanVaikkaUudenMerkinnanPaivallaOnKannassaJoVanhaMerkinta() {
        
        Merkinta vanhaMerkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(0, 1), new Kellonaika(1, 3), "vanhaMerkinta"));
        Merkinta merkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(2, 2), new Kellonaika(4, 8), "lisattyMerkinta"));

        try {
            tika.kirjoitaTietokantaanLisaten(vanhaMerkinta.toString(), true);
            tika.getMerkinnanKasittelija().yhdista(merkinta, vanhaMerkinta);            
            int paikkaindeksi = tika.haeKannastaMerkinnanPaivayksenPaikkaPaivayksella("01.01.1990"); //TEE JOTAIN NOLILLE!
            System.out.println(paikkaindeksi);
            tika.poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(paikkaindeksi, 2, merkinta);
        } catch (IOException ex) {
            assertEquals(true, false);
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