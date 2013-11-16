package ajankayttokirjanpito;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import tietokantasysteemi.Tiedostonkasittelija;
import sovelluslogiikka.Dekooderi;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.Konsoli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.AjanAntaja;

/**
 *
 * @author lawkaita
 */
public class AjankayttokirjanpitoTest {

    private Konsoli konsoli;
    private Kayttoliittyma kali;
    private Dekooderi dekooderi;
    private AjanAntaja ajan;
    private Tiedostonkasittelija tika;
    private Tulostaja tulostaja;
    private Komentotulkki kotu;

    @Before
    public void setUp() {
        konsoli = new Konsoli(true);
        kali = new Kayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        ajan = new AjanAntaja();
        tika = new Tiedostonkasittelija(dekooderi);
        tulostaja = new Tulostaja(kali, tika, ajan, dekooderi);
        kotu = new Komentotulkki(tulostaja, tika, konsoli);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));

        SwingUtilities.invokeLater(kali);
    }

    @Test
    public void vihreellinenKomentoTulostaaKomennonJaVirheuilmoituksen() {
        kali.getKonsoli().kirjoitaKomentoriville("Jeessys");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(5, tuloste.length());
        //jätetään alusta komentorivin tervehdys ja pyörivä kursori.

        assertEquals("> Jeessys\n :Ei ole komento",
                odotettu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaOnKirjaimia() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville("jeessys");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(34, tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä",
                odotettu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaLiikaaPisteellaEroteltujaNumeroSekvensseja() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville(".13");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(30, tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä",
                odotettu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaOikeanPaivanJaOhjlemaSiirtyyKysymaanAloitusAikaa() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kotu.otaKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(37, tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Aloitusaika:",
                odotettu);
    }

    @Test
    public void onkoAikaTestiTUnnistaaVirheelisenAjan() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kotu.otaKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville("jeessys");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " :Ei ole aika";
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());

        assertEquals(odotettu,
                aktuaali);
    }

    @Test
    public void onkoAikaTestiTunnistaaOikeanAjan() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kotu.otaKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville("12.12");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " :Lopetusaika:";
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());

        assertEquals(odotettu,
                aktuaali);
    }

    @Test
    public void tulostaKomentoEiTulostaEiMerkintojaViestiaKunTietokantaEiOleTyhja() {
        try {
            tika.kirjoitaTietokantaanLisaten("lisataanMeluaMuistiin", true);
            kali.getKonsoli().kirjoitaKomentoriville("hae");
            kotu.otaKomento();
            kali.getKonsoli().kirjoitaKomentoriville("muta");
            kotu.otaKomento();
            
            String eiOdotettu = " :Ei merkintöjä";
            String tuloste = kali.getKonsoli().getTulosteAlue().getText();
            String aktuaali = tuloste.substring(tuloste.length() - eiOdotettu.length(), tuloste.length());
            boolean tulosteOnEriViestiKuinEiMerkintoja = (!aktuaali.equals(eiOdotettu));
            
            assertEquals(true, tulosteOnEriViestiKuinEiMerkintoja);
        } catch (IOException ex) {
            System.out.println("IOException");
        }
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