package ajankayttokirjanpito;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import tietokantasysteemi.OmaTiedostonkasittelija;
import sovelluslogiikka.Dekooderi;
import javax.swing.SwingUtilities;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.Tulostaja;
import konsoli.OmaKonsoli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.KomentoLogiikka;
import sovelluslogiikka.MerkinnanKasittelija;
import tietokantasysteemi.Kellonaika;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.Paivays;
import tietokantasysteemi.Tapahtuma;

/**
 *
 * @author lawkaita
 */
public class AjankayttokirjanpitoTest {

    private OmaKonsoli konsoli;
    private Kayttoliittyma kali;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija meka;
    private OmaTiedostonkasittelija tika;
    private Tulostaja tulostaja;
    private KontekstinHaltija koha;
    private KomentoLogiikka kolo;
    private Komentotulkki kotu;
    
    private String alkuteksti;
    private String kayttajanTulosteMerkki = "> ";

    @Before
    public void setUp() {
        konsoli = new OmaKonsoli(true);
        kali = new Kayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        meka = new MerkinnanKasittelija(dekooderi);
        tika = new OmaTiedostonkasittelija(dekooderi, meka);
        tulostaja = new Tulostaja(konsoli, dekooderi);
        koha = new KontekstinHaltija();
        kolo = new KomentoLogiikka(tulostaja, tika, konsoli, koha, kali, meka);
        kotu = new Komentotulkki(konsoli, koha, kolo, dekooderi);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        alkuteksti = konsoli.getTulosteAlue().getText();
        
        SwingUtilities.invokeLater(kali);
    }
    
    @Test
    public void alkuTestiOnOikein() {
        assertEquals(" :Komenna apua saadaksesi apua", alkuteksti);
    }
    
    @Test
    public void tyhjaKomentoTulostaaTyhjanRivin() {
        kotu.otaKomento();
        
        String tuloste = konsoli.getTulosteAlue().getText();
        String saatu = tuloste.substring(alkuteksti.length() + 2); // +2 koska \n ja pyörivä kursori.
        String odotettu = "> ";
        
        System.out.println(alkuteksti);
        System.out.println(tuloste);
        
        assertEquals(odotettu, saatu);
    }

    @Test
    public void vihreellinenKomentoTulostaaKomennonJaVirheuilmoituksen() {
        kali.getKonsoli().kirjoitaKomentoriville("Jeessys");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = tuloste.substring(alkuteksti.length() +2, tuloste.length());
        //jätetään alusta komentorivin tervehdys ja pyörivä kursori.

        assertEquals("> Jeessys\n :Ei ole komento", odotettu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaOnKirjaimia() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville("jeessys");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> merk" + "\n :Päiväys:" + "\n-> jeessys" + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä", saatu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaLiianVahanPisteellaEroteltujaNumeroSekvensseja() {
        String rikkinainenPaivays = ".13";
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville(rikkinainenPaivays);
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> merk" + "\n :Päiväys:" + "\n-> " + rikkinainenPaivays + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä", saatu);
    }
    
    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaLiikaaPisteellaEroteltujaNumeroSekvensseja() {
        String rikkinainenPaivays = "13.13.1313.13";
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville(rikkinainenPaivays);
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> merk" + "\n :Päiväys:" + "\n-> " + rikkinainenPaivays + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä", saatu);
    }
    
    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunAnnetaanTyhjaSyote() {
        String rikkinainenPaivays = "";
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville(rikkinainenPaivays);
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> merk" + "\n :Päiväys:" + "\n-> " + rikkinainenPaivays + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Ei ole päivä", saatu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaOikeanPaivanJaOhjlemaSiirtyyKysymaanAloitusAikaa() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        //tassa kohtaa komentoriville on hakeutunut oikea paiva
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> merk" + "\n :Päiväys:" + "\n-> dd.mm.yyyy" + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.

        assertEquals(" :Aloitusaika:", saatu);
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

        assertEquals(odotettu, aktuaali);
    }
    
    @Test
    public void onkoAikaTestiTunnistaaTyhjanAjan() {
        kali.getKonsoli().kirjoitaKomentoriville("merk");
        kotu.otaKomento();
        kotu.otaKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville("");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " :Ei ole aika";
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());

        assertEquals(odotettu, aktuaali);
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

        assertEquals(odotettu, aktuaali);
    }

    @Test
    public void haeKomentoTulostaaEiOsumiaViestinKunTietokantaEiOleTyhjaMuttaHakusanallaEiLoydyMitaan() {
        Merkinta lisattava = new Merkinta(new Paivays(1, 1, 1000),
                new Tapahtuma(new Kellonaika(0, 0), new Kellonaika(0, 1), "Kaiken alku"));

        this.kolo.getTietokantaValimuisti().lisaaMerkinta(lisattava);
        kali.getKonsoli().kirjoitaKomentoriville("tallenna");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville("hae");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville("mutaxoxoxo");
        kotu.otaKomento();

        String odotettu = " :Ei osumia";
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
        boolean tulosteOnSamaViestiKuinEiOsumia = (aktuaali.equals(odotettu));

        System.out.println(aktuaali);

        assertEquals(true, tulosteOnSamaViestiKuinEiOsumia);
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