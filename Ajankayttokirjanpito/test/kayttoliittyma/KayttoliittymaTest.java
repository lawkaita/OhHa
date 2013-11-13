package kayttoliittyma;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import tietokantasysteemi.Tiedostonkasittelija;
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
public class KayttoliittymaTest {

    private Kayttoliittyma kali;

    @Before
    public void setUp() {
        kali = new Kayttoliittyma();

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
    
    @Test
    public void testi0(){
        System.out.println("hei");
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