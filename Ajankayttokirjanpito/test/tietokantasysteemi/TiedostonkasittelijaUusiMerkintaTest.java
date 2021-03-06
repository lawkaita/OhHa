/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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

    private OmaTiedostonkasittelija tika;
    private Merkinta vanhaMerkinta;
    private Merkinta merkinta;
    private Merkinta uudempiMerkinta;

    @Before
    public void setUp() {
        this.tika = new OmaTiedostonkasittelija(new Dekooderi());

        vanhaMerkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(0, 1), new Kellonaika(1, 3), "vanhaMerkinta"));
        merkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(2, 2), new Kellonaika(4, 8), "lisattyMerkinta"));
        uudempiMerkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(3, 3), new Kellonaika(15, 15), "JuuriSeMerkintä"));

    }

    @Test
    public void tietokantaanVoiTehdaMerkinnanVaikkaUudenMerkinnanPaivallaOnKannassaJoVanhaMerkinta() {

        try {
            tika.nollaaTietokantaTiedosto();
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(vanhaMerkinta.toString(), true);
            tika.getMerkinnanKasittelija().yhdista(merkinta, vanhaMerkinta);
            //tika.getMerkinnanKasittelija().yhdista(uudempiMerkinta, merkinta);
            int paikkaindeksi = tika.haeKannastaMerkinnanPaivayksenPaikkaPaivayksella("01.01.1990"); //TEE JOTAIN NOLLILLE!
            int vanhanMerkinnanPituus = vanhaMerkinta.getTapahtumienMaara() + 2; //+1 päiväyksestä ja + 1 viimeisestä rivinvaihdosta.

            tika.poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(paikkaindeksi, vanhanMerkinnanPituus, merkinta);

            String odotettu =
                    "01.01.1990\r\n"
                    + "    00.01-01.03: vanhaMerkinta\r\n"
                    + "    02.02-04.08: lisattyMerkinta";

            String aktuaali = tika.kirjoitaTekstitauluStringiksiRivittaen(tika.getTietokantaTekstiTauluna());
            //kirjoitKantaTekstitauluStringiksiRivittaenLisaaYlimaaraisenRivinvaihdon koska haetussa tekstitaulussa on jo yksi rivinvaihto

            System.out.println(odotettu);
            System.out.println("---");
            System.out.println(aktuaali);
            System.out.println("---");

            assertEquals(odotettu, aktuaali);

        } catch (IOException ex) {
            testiEiOnnistunut(ex);
        }
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

    private void testiEiOnnistunut(IOException ex) {
        System.out.println("Testi ei onnistu!" + ex.getMessage());
    }
}