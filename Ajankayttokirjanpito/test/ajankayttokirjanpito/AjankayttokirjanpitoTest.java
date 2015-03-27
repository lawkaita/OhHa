package ajankayttokirjanpito;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tietokantasysteemi.LegacyTiedostonkasittelija;
import sovelluslogiikka.Dekooderi;
import javax.swing.SwingUtilities;
import kayttoliittyma.LegacyKayttoliittyma;
import kayttoliittyma.LegacyKomentotulkki;
import kayttoliittyma.KontekstinHaltija;
import kayttoliittyma.Nappaimistonkuuntelija;
import kayttoliittyma.LegacyTulostaja;
import konsoli.OmaKonsoli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.LegacyKomentoLogiikka;
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
    private LegacyKayttoliittyma kali;
    private Dekooderi dekooderi;
    private MerkinnanKasittelija meka;
    private LegacyTiedostonkasittelija tika;
    private LegacyTulostaja tulostaja;
    private KontekstinHaltija koha;
    private LegacyKomentoLogiikka kolo;
    private LegacyKomentotulkki kotu;
    
    private String alkuteksti;
    private String odotettuVirheIlmoitusVirheellisenPaivanYhteydessa;

    @Before
    public void setUp() {
        konsoli = new OmaKonsoli(true);
        kali = new LegacyKayttoliittyma(konsoli, null);
        dekooderi = new Dekooderi();
        meka = new MerkinnanKasittelija(dekooderi);
        tulostaja = new LegacyTulostaja(konsoli, dekooderi);
        tika = new LegacyTiedostonkasittelija(dekooderi, meka, tulostaja);
        koha = new KontekstinHaltija();
        kolo = new LegacyKomentoLogiikka(tulostaja, tika, konsoli, koha, kali, meka);
        kotu = new LegacyKomentotulkki(konsoli, koha, kolo, dekooderi);

        kali.otaNappaimistonkuuntelija(new Nappaimistonkuuntelija(konsoli, kotu));
        
        alkuteksti = konsoli.getTulosteAlue().getText();
        this.odotettuVirheIlmoitusVirheellisenPaivanYhteydessa
                = " # Ei ole päivä:\n # Anna päivä muodossa 'pp.kk.vvvv'";
        
        SwingUtilities.invokeLater(kali);
    }
    
    @Test
    public void alkuTestiOnOikein() {
        assertEquals(" # Komenna apua saadaksesi apua", alkuteksti);
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

        assertEquals("> Jeessys\n # Ei ole komento", odotettu);
    }
    
    private String onkoPaivaTesti(String string) {
        kali.getKonsoli().kirjoitaKomentoriville("lisää");
        kotu.otaKomento();
        kali.getKonsoli().kirjoitaKomentoriville(string);
        kotu.otaKomento();
        
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> lisää" + "\n # Luodaan uusi merkintä" + "\n # Päiväys:" + "\n-> " +  string + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.
        
        return saatu;
    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaOnKirjaimia() {
        String saatu = onkoPaivaTesti("jeessys");
        assertEquals(odotettuVirheIlmoitusVirheellisenPaivanYhteydessa, saatu);
    }
    
    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivayksessaJokinOsaOnTyhja() {
        String saatu = onkoPaivaTesti("12..");
        assertEquals(odotettuVirheIlmoitusVirheellisenPaivanYhteydessa, saatu);        
    }
    
    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunVuosiOnMiinusMerkkinen() {
        String saatu = onkoPaivaTesti("12.12.-2013");
        assertEquals(odotettuVirheIlmoitusVirheellisenPaivanYhteydessa, saatu);

    }

    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaLiianVahanPisteellaEroteltujaNumeroSekvensseja() {
        String saatu = onkoPaivaTesti(".13");
        assertEquals(odotettuVirheIlmoitusVirheellisenPaivanYhteydessa, saatu);
    }
    
    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunPaivassaLiikaaPisteellaEroteltujaNumeroSekvensseja() {
        String saatu = onkoPaivaTesti("13.13.1313.13");
        assertEquals(odotettuVirheIlmoitusVirheellisenPaivanYhteydessa, saatu);
    }
    
    @Test
    public void onkoPaivaTestiTunnistaaVirheellisenPaivanKunAnnetaanTyhjaSyote() {
        String saatu = onkoPaivaTesti("");
        assertEquals(odotettuVirheIlmoitusVirheellisenPaivanYhteydessa, saatu);
    }

    @Test
    public void onkoPaivaTestiTunnistaaOikeanPaivanJaOhjlemaSiirtyyKysymaanAloitusAikaa() {
        kali.getKonsoli().kirjoitaKomentoriville("lisää");
        kotu.otaKomento();
        //tassa kohtaa komentoriville on hakeutunut oikea paiva
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String alkuosa = alkuteksti + "\n-> lisää" + "\n # Luodaan uusi merkintä" + "\n # Päiväys:" + "\n-> dd.mm.yyyy" + "\n";
        String saatu = tuloste.substring(alkuosa.length(), tuloste.length());
        //jätetään alusta komentorivin tervehdys, pyörivä kursori ja aluksi annettu komento.
        
        System.out.println(tuloste);
        System.out.println(alkuosa);
        System.out.println(saatu);

        assertEquals(" # Aloitusaika:", saatu);
    }
    
    private String onkoAikaTesti(String aika) {
        kali.getKonsoli().kirjoitaKomentoriville("lisää");
        kotu.otaKomento();
        kotu.otaKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville(aika);
        kotu.otaKomento();
        
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " # Ei ole aika:\n # Anna aika muodossa 'tt.mm'";
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
        
        return aktuaali;
    }

    @Test
    public void onkoAikaTestiTunnistaaVirheelisenAjan() {
        String aktuaali = onkoAikaTesti("jeessys");

        assertEquals(" # Ei ole aika:\n # Anna aika muodossa 'tt.mm'", aktuaali);
    }
    
    @Test
    public void onkoAikaTestiTunnistaaTyhjanAjan() {
        String aktuaali = onkoAikaTesti("");
        assertEquals(" # Ei ole aika:\n # Anna aika muodossa 'tt.mm'", aktuaali);
    }

    @Test
    public void onkoAikaTestiTunnistaaOikeanAjan() {
        kali.getKonsoli().kirjoitaKomentoriville("lisää");
        kotu.otaKomento();
        kotu.otaKomento();//tassa kohtaa komentoriville on hakeutunut oikea paiva
        kali.getKonsoli().kirjoitaKomentoriville("12.12");
        kotu.otaKomento();

        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String odotettu = " # Lopetusaika:";
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

        String odotettu = " # Ei osumia";
        String tuloste = kali.getKonsoli().getTulosteAlue().getText();
        String aktuaali = tuloste.substring(tuloste.length() - odotettu.length(), tuloste.length());
        boolean tulosteOnSamaViestiKuinEiOsumia = (aktuaali.equals(odotettu));

        System.out.println(aktuaali);

        assertEquals(true, tulosteOnSamaViestiKuinEiOsumia);
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
}