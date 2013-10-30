/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import gui.NappaimistoKuuntelija;
import gui.KomentoTulkki;
import gui.Kayttoliittyma;
import gui.Kello;
import gui.Kirjoittaja;
import gui.KomennonJakaja;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Envy 6-1010
 */
public class Ohjelma {
    //private Scanner lukija;

    private TaistelijatLista Taistelijalista;
    private KomentoTulkki komentoTulkki;
    private Kirjoittaja kirjoittaja;
    //private boolean kaynnissa;
    private Kayttoliittyma kali;
    private JTextField komentoalue;
    private NappaimistoKuuntelija enterinKuuntelija;
    private KomennonJakaja jakaja;
    private JTextArea area;
    private Kello k;

    Ohjelma(TaistelijatLista t, KomentoTulkki k, Kirjoittaja kirjoittaja, Kayttoliittyma kali,
            JTextField rivi, NappaimistoKuuntelija kuuntelija, KomennonJakaja jakaja, JTextArea area) {
        //this.lukija = l;
        this.Taistelijalista = t;
        this.komentoTulkki = k;
        this.kirjoittaja = kirjoittaja;
        //this.kaynnissa = false;
        this.kali = kali;
        this.komentoalue = rivi;

        this.enterinKuuntelija = kuuntelija;
        this.jakaja = jakaja;
        this.area = area;
        this.k = new Kello(this.kirjoittaja);

    }

    public TaistelijatLista annaLista() {
        return this.Taistelijalista;
    }

    //Tätä ei välttämättä tarvitse enää.
    public void kaynnista() {

        //Tämän pitää tapahtua ihan ensimmäisenä.        
        //this.kali.run();
        //this.kaynnissa = true;

        //tervehdys
        //nyt vain ei sanota että sout, tai kaannaWin7 jonka sisalla on sout, vaan kustutaan Kirjoittajan kirjoituskomentoa.
        //kaannaWin7("\n  Ohjelma käynnissä. \n  Syötä v niin tulostuu valikko. \n");
        this.kirjoittaja.korvaa("  Syötä v niin tulostuu lista komennoista. \n\n");


        //mainloop
//        while (this.kaynnissa == true) {
//            tulostaTilanne(this.Taistelijalista);
//            //System.out.println("GM@Tool:");
//            //String syote = lukija.nextLine();                        
//        }        

    }

    public void lopeta() {
        //this.kaynnissa = false;
        this.kali.tapa();
    }

    public void taistelijaListaNollaus() {
        //tässä on uuden taistelun suoritus
        this.Taistelijalista.nollaaLista();
        this.k.stopKokonaan();
        ok();
    }

    public void tulostaTilanne(int i) {
        if (this.Taistelijalista.getArray().isEmpty()) {
            this.kirjoittaja.lisaanl("Alussa oli gridi, nopat ja dm...");
        } else {
            this.Taistelijalista.jarjesta();
            if (i == 0) {
                this.kirjoittaja.lisaanl(this.Taistelijalista.tilanne());
            } else {
                k.start();
                this.kirjoittaja.lisaanl(this.Taistelijalista.tilanne());
            }
        }
    }

    public void tulostaKomennot() {
        String komennot =
                "                         = Komennot = \n"
                + "v, help \n"
                //+ "  den här listen \n"
                + "x, exit, lopeta \n"
                + "t, tilanne \n"
                //+ "  tulostaa taistelun tilanteen\n"
                + "uusi, uusi taistelu, taistelu \n"
                + "lisää pelaaja/monsu/mobi \n"
                //+ "  käyttö: lisää <taistelija> <nimi> <hp> <inikka>\n"
                //+ "  'lisää' ei tarvitse kirjoittaa.\n"
                //+ "  taistelijan kohdalle ilmoitetaan tehdäänkö pelaaja vai monsu\n"
                //+ "  hp annetaan vain monsuille \n"
                + "poista <nimi> \n"
                + "muuta inikkaa <nimi> <uusi inikka>\n"
                + "vahingoita, dmg <nimi> <määrä>\n"
                + "kommentti/kommentoi <nimi> <kommentit> \n"
                + "pyyhi <taistelija>\n"
                + "summa <luku1> <luku2> ... \n"
                //+ "  laskee lukujen summan \n"
                + "Nuoli oikealle: aloittaa ja jatkaa inikan seuraamista \n"
                //+ "  aloittaa ja jatkaa inikan seuraamista.\n"
                //+ "TAB \n"
                //+ "  siirry teksti- ja komentoalueen välillä \n"
                //+ "PAGE UP/DOWN \n"
                //+ "  selaa tekstialuetta \n"
                + "                          = Loppu =";

        this.kirjoittaja.lisaanl(komennot);
    }

    public void tulostaValikko_vanha() {
        String valikko = "  Valikko:    \n"
                + "v, help - valikko    \n"
                + "x  - lopeta           \n"
                + "t  - tilanne          \n"
                + "n  - uusi taistelu       \n"
                + "ap - lisää pelaaja    \n"
                + "am - lisää mobi \n"
                + "cp - poista taistelija   \n"
                + "ci - muuta inikkaa       \n"
                + "cd - vahingoita \n"
                + "ck - aseta taistelijalle kommentti \n"
                + "s  - laske summa \n"
                + "e  - aloittaa ja jatkaa inikan seuraamista \n";

        this.kirjoittaja.lisaanl(valikko);
    }

    public String otaNimi() {
        return null;
    }

    public void lisaaPelaaja_kyselyversioyritys(TaistelijatLista t) {
        String teksti = "Lisätään pelaaja. \nNimi:";
        this.kirjoittaja.lisaanl(teksti);

        //this.komentoTulkki.kasitteleKomento("g");
        //  k.suorita();


    }

    public void lisaaPelaaja(String[] komentosarja) {
        //String teksti = "Lisätään pelaaja. \nNimi:";
        //this.kirjoittaja.lisaanl(teksti);

        String nimi = komentosarja[2];
        //enterinkuuntelija ei mene loppuun! -korjattu
        //jakaja on jakanut enterikuuntelijan stringpalauttimelle ja se odottaa, mutta tämä threadi ei odota.

        //tässä ohjelma odottanee seuraavaa komentoa, jonka se laittaa nimeksi:
        //sillä komennonjakaja osoittaa nyt nimeen.

        if (this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi) == true) {
            this.kirjoittaja.lisaanl("Samanniminen taistelija on jo taistelemassa! \n");
        } else {

            //String ehdokas = lukija.nextLine();
            String ehdokas = komentosarja[3];
            if (onkoMuunnettavissaInt(ehdokas) == true) {
                int init = Integer.parseInt(ehdokas);

                Taistelija u = new Pelaaja(nimi, init);
                this.Taistelijalista.lisaa(u);
                ok();
            } else {
                tyyppiVirhe();
            }
        }
    }

    public void lisaaMonsu(String[] komentosarja) {
        String nimi = komentosarja[2];
        if (this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi) == true) {
            this.kirjoittaja.lisaanl("Samanniminen taistelija on jo taistelemassa! \n");
        } else {

            String ehdokas = komentosarja[4];
            if (onkoMuunnettavissaInt(ehdokas) == true) {
                int init = Integer.parseInt(ehdokas);

                //4 ja 3: halusin että hp ilmoitetaan ennen inikkaa.
                ehdokas = komentosarja[3];
                if (onkoMuunnettavissaInt(ehdokas) == true) {
                    int hp = Integer.parseInt(ehdokas);

                    if (!(hp == 0)) {
                        Taistelija u = new Monsu(nimi, init, hp);
                        this.Taistelijalista.lisaa(u);
                        ok();
                    } else {
                        this.kirjoittaja.lisaanl("Hp ei voi olla nolla!");
                    }

                } else {
                    tyyppiVirhe();
                }
            } else {
                tyyppiVirhe();
            }
        }
    }

    public void poistaTaistelija(String nimi) {
        if (this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi) == true) {
            this.Taistelijalista.poista(nimi);
            this.kirjoittaja.lisaanl("Sinne meni!");
        } else {
            eiOle();
        }
    }

    public void muutaInikkaa(String komentosarja[]) {
        String nimi = komentosarja[2];
        boolean kokeillaanko = this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi);

        if (kokeillaanko == true) {
            String init = komentosarja[3];

            if (onkoMuunnettavissaInt(init) == true) {
                this.Taistelijalista.etsi(nimi).setInit(Integer.parseInt(init));
                ok();
            } else {
                tyyppiVirhe();
            }

        } else {
            eiOle();
        }
    }

    public boolean onkoMuunnettavissaInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    public void vahingoita(String[] komentosarja) {
        String nimi = komentosarja[1];

        if (this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi)) {
            Taistelija p = this.Taistelijalista.etsi(nimi);

            if (this.Taistelijalista.onkoMonsu(p) == true) {
                Monsu m = (Monsu) p;

                String dmg = komentosarja[2];

                if (onkoMuunnettavissaInt(dmg)) {

                    m.dmg(Integer.parseInt(dmg));
                    ok();
                }

            } else {
                eiOle();
            }

        }
    }

    public void poistaKommentit(String nimi) {
        if (this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi) == true) {
            this.Taistelijalista.etsi(nimi).setHuom("");
        }

        ok();
    }

    public void kommentoi(String[] komentosarja) {
        String nimi = komentosarja[1];

        if (this.Taistelijalista.onkoTaistelussaTaistelijaaNimella(nimi) == true) {

            String kommentit = "";

            for (int i = 2; i < komentosarja.length; i++) {
                kommentit = kommentit + " -" + komentosarja[i];
            }

            this.Taistelijalista.etsi(nimi).jatkaHuom(kommentit);
            ok();

        } else {
            eiOle();
        }
    }

    public void summain(String[] komentosarja) {
        int summa = 0;

        int i = 1;
        while (true) {
            if (i == komentosarja.length) {
                String tuloste = "" + summa;
                this.kirjoittaja.lisaanl(tuloste);
                break;
            } else {
                if (!(onkoMuunnettavissaInt(komentosarja[i]))) {
                    this.kirjoittaja.lisaanl("Virheellinen syöte");
                    break;
                }
                summa = summa + Integer.parseInt(komentosarja[i]);
                i++;
            }
        }
    }

    public void summain_vanha(Scanner lukija) {
        System.out.println("Anna lukuja, s laskee summan");

        int summa = 0;
        while (true) {
            String komento = lukija.nextLine();

            if (komento.equals("s") || komento.equals("x")) {
                System.out.println("= " + summa);
                break;
            }

            if (onkoMuunnettavissaInt(komento)) {
                summa = summa + Integer.parseInt(komento);
            } else {
                System.out.println("Ei ole luku.");
            }
        }
    }

    public void kaannaWin7(String teksti) {
        Win7cmd_kaantaja kaantaja = new Win7cmd_kaantaja();

        byte[] bitit = kaantaja.kaanna(teksti);

        System.out.write(bitit, 0, bitit.length);
    }

    public void eiOle() {
        this.kirjoittaja.lisaanl("Taistelijaa ei löydy.");
    }

    public void ok() {
        this.kirjoittaja.lisaanl("Onnistui. \n");
    }

    public void tyyppiVirhe() {
        this.kirjoittaja.lisaanl("Tyyppivirhe.");
    }

    public void lopetusKeskustelu() {
        this.kirjoittaja.lisaanl("Haluato poistua? (y/n)");

        //this.komentoTulkki.kasitteleKomento(KOMENTO);
        //Tässä ohjelma ei odota komentoa! Enterkuuntelija ampuu komennot tulkille aina. SIIS EN SAA TÄTÄ KOMENTOA TÄHÄN MISTÄÄN!


//                    while (syote.equals("x")) {
//                System.out.println("Haluato poistua? (y/n)");
//                //syote = lukija.nextLine();
//            }
//
//            if (syote.equals("y") || syote.equals("xy")) {
//                this.ohjelma.lopeta();
//            }
    }

    public void uusitaisteluKeskustelu() {
        this.kirjoittaja.lisaanl("Uusi taistelu? Vanha pyyhkiytyy pois! (y/n)");
    }

    public NappaimistoKuuntelija getEnterinKuuntelija() {
        return this.enterinKuuntelija;
    }

    public Kirjoittaja getKirjoittaja() {
        return this.kirjoittaja;
    }

    public KomennonJakaja getJakaja() {
        return this.jakaja;
    }

    public void kirjoitaKomento(String syote) {
        if (!(syote.isEmpty()) && !(syote.equals("painettiinOikealle"))) {
            //tässä oli ennen korvaa
            this.kirjoittaja.lisaanl("> " + syote); //tässä oli rivinvaihto
        }
    }

    public TaistelijatLista getTaistelijaLista() {
        return this.Taistelijalista;
    }

    public void lyhenteet() {
        String s = "vbtx pmri dksw";
        this.kirjoittaja.lisaanl(s);
    
    }
}
