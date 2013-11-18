/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.io.IOException;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.Tulostaja;
import konsoli.Konsoli;
import tietokantasysteemi.Merkinta;
import tietokantasysteemi.Tiedostonkasittelija;

/**
 *
 * @author Envy 6-1010
 */
public class KomentoLogiikka {

    private Komentotulkki komentotulkki;
    private Tulostaja tulostaja;
    private Ajantestaaja ajantestaaja;
    private Tiedostonkasittelija tika;
    private Konsoli konsoli;

    public KomentoLogiikka(Komentotulkki komentotlukki, Tulostaja tulostaja, Ajantestaaja ajantestaaja, Tiedostonkasittelija tika, Konsoli konsoli) {
        this.komentotulkki = komentotlukki;
        this.tulostaja = tulostaja;
        this.ajantestaaja = ajantestaaja;
        this.tika = tika;
        this.konsoli = konsoli;
    }

    public void merkinnanAloitus() {
        tulostaja.pyydaPaivaa();
        String paiva = tulostaja.getAjan().annaTamaPaiva();
        tulostaja.getKali().getKonsoli().kirjoitaKomentoriville(paiva);
    }

    public void nollaaTiedosto() {
        try {
            tika.nollaaTiedosto();
        } catch (IOException ex) {
            //
        }
    }

    public void haku(String komento) {
        String[] osuma = tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(komento);
        //Merkinta merkinta = tika.getMerkinnanKasittelija().luoMerkintaHaunTuloksesta(osuma);
        tulostaja.tulostaHaunOsumat(osuma);
        //tulostaja.listaaKonsoliin(merkinta.toString());
    }

    public void luodaanMerkinta(String komento) {
        tulostaja.kerroLisayksesta();
        tulostaja.listaaKonsoliin(komento);

        try {
            Merkinta uusiMerkinta = tika.getMerkinnanKasittelija().muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(komento);
            String paivaysMuistettavaStringista = tika.getDekooderi().dekoodaa(komento, '!')[0];

            boolean kannassaOnMerkintaSamallaPaivalla =
                    tika.kannassaOnMerkintaPaivalla(paivaysMuistettavaStringista);

            if (kannassaOnMerkintaSamallaPaivalla) {
                String[] vanhaMerkintaUudenPaivallaTauluna =
                        tika.haeStringtaulunaTietoKannastaMerkintaPaivalla(paivaysMuistettavaStringista);

                Merkinta samallaPaivallaValmisMerkinta = tika.getMerkinnanKasittelija()
                        .luoMerkintaHaunTuloksesta(vanhaMerkintaUudenPaivallaTauluna);

                //+2 sillä 1 päiväykselle ja 1 viimeiselle rivinvaihdolle.
                int vanhanMerkinnanPituus = samallaPaivallaValmisMerkinta.getTapahtumienMaara() + 2;
                int vanhanMerkinnanPaikkaIndeksi =
                        tika.haeKannastaMerkinnanPaivayksenPaikkaPaivayksella(paivaysMuistettavaStringista);

                tika.getMerkinnanKasittelija().yhdista(uusiMerkinta, samallaPaivallaValmisMerkinta);

                tika.poistaVanhaMerkintaJaLisaaUusiYhdistettyMerkintaJaKirjaaMuutosTietokantaan(vanhanMerkinnanPaikkaIndeksi, vanhanMerkinnanPituus, uusiMerkinta);

            } else {
                tika.kirjoitaTietokantaanLisatenRivinvaihtoLoppuun(uusiMerkinta.toString(), true);
            }

        } catch (IOException ex) {
            //mitä tähän tulisi lisätä?
        }
    }
}
