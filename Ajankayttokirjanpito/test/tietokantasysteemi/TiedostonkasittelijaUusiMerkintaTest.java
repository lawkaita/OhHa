/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.IOException;
import java.util.ArrayList;
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
    }

    @Test
    public void tietokantaanVoiTehdaMerkinnanVaikkaUudenMerkinnanPaivallaOnKannassaJoVanhaMerkinta() {
        
        Merkinta vanhaMerkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(0, 1), new Kellonaika(1, 3), "vanhaMerkinta"));
        Merkinta merkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(2, 2), new Kellonaika(4, 8), "lisattyMerkinta"));
        //Merkinta uudempiMerkinta = new Merkinta(new Paivays(1, 1, 1990), new Tapahtuma(new Kellonaika(3, 3), new Kellonaika(15, 15), "JuuriSeMerkintä"));

        try {
            tika.nollaaTiedosto();
            tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(vanhaMerkinta.toString(), true);
            tika.getMerkinnanKasittelija().yhdista(merkinta, vanhaMerkinta);
            //tika.getMerkinnanKasittelija().yhdista(uudempiMerkinta, merkinta);
            int paikkaindeksi = tika.haeKannastaMerkinnanPaivayksenPaikkaPaivayksella("01.01.1990"); //TEE JOTAIN NOLLILLE!
            int vanhanMerkinnanPituus = vanhaMerkinta.getTapahtumienMaara() + 2; //+1 päiväyksestä ja + 1 viimeisestä rivinvaihdosta.

            tika.poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(paikkaindeksi, vanhanMerkinnanPituus, merkinta);
            
            String odotettu =
                    "01.01.1990\r\n"
                    + "    00.01-01.03: vanhaMerkinta\r\n"
                    + "    02.02-04.08: lisattyMerkinta\r\n"
                    + "\r\n";
            
            String aktuaali = tika.kirjoitaKantaTekstitauluStringiksiRivittaen(tika.getTietokantaTekstiTauluna());
            //kirjoitKantaTekstitauluStringiksiRivittaenLisaaYlimaaraisenRivinvaihdon koska haetussa tekstitaulussa on jo yksi rivinvaihto
            
            System.out.println(odotettu);
            System.out.println("---");
            System.out.println(aktuaali.substring(0, aktuaali.length())); 
            System.out.println("---");
            
            assertEquals(odotettu, aktuaali);
            
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