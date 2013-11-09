/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Tietokantasysteemi.Tiedostonkasittelija;
import kayttoliittyma.Dekooderi;
import java.io.IOException;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lawkaita
 */
public class Testit {

    private Kayttoliittyma kali;
    private Tiedostonkasittelija tika;

    @Before
    public void setUp() {
        kali = new Kayttoliittyma();
        tika = new Tiedostonkasittelija();

        SwingUtilities.invokeLater(kali);
    }

    @Test
    public void vihreellinenKomentoTulostaaKomennonJaVirheuilmoituksen() {
        kali.getKonsoli().kirjoitaKomentoriville("Jeessys");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(5, tuloste.length());
        //jätetään alusta komentorivin tervehdys ja pyörivä kursori.

        assertEquals("> Jeessys\n :Ei ole komento",
                odotettu);
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

    @Test
    public void tiedostonTulostaminenKonsoliinTulostaaTiedostonTiedotOikein() {
    }

    @Test
    public void dekooderiLajitteleeStringOlionOikein() {
        String s = "hei olen pekka";

        Dekooderi d = new Dekooderi();

        String[] dekoodi = d.dekoodaa(s, null);

        assertEquals(dekoodi[0], "hei");
        assertEquals(dekoodi[1], "olen");
        assertEquals(dekoodi[2], "pekka");
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaOnKirjaimia() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        kali.getKonsoli().kirjoitaKomentoriville("jeessys");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(34, tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä",
                odotettu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaLiikaaPisteellaEroteltujaNumeroSekvensseja() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        kali.getKonsoli().kirjoitaKomentoriville(".13");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(30, tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä",
                odotettu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaOikeanPaivanJaOhjlemaSiirtyyKysymaanAloitusAikaa() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(37, tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Aloitusaika:",
                odotettu);
    }
    
    @Test
    public void onkoAikaTestiTUnnistaaVirheelisenAjan() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville("jeessys");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " :Ei ole aika";
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
        
        assertEquals(odotettu,
                aktuaali);        
    } 
    
    @Test
    public void onkoAikaTestiTunnistaaOikeanAjan() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville("12.12");
        kali.getKonsoli().tulostaJaSuoritaKayttajanKomento();
        
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " :Lopetusaika:";
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
        
        assertEquals(odotettu,
                aktuaali);        
    } 
    //päiväykset ja kellonajat 0 eteen., testejä 7
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}