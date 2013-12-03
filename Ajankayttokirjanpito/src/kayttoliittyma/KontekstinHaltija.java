/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 * KontekstinHaltijan tehtävä on seurata, missä asiayhteydessä ohjelmaan
 * syötetään komentoja.
 *
 * @author Envy 6-1010
 */
public class KontekstinHaltija {

    public boolean ollaanPoistumassaOhjelmasta;
    public boolean kysytaanPoistumisenYhteydessaTallennuksesta;
    public boolean merkintaanPaiva;
    public boolean merkintaanAloitusAika;
    public boolean merkintaanLopetusAika;
    public boolean merkintaanSelostus;
    public boolean hakuKaynnissa;
    public boolean poistetaanMerkintaa;

    public KontekstinHaltija() {

        ollaanPoistumassaOhjelmasta = false;
        kysytaanPoistumisenYhteydessaTallennuksesta = false;
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;
    }

    public boolean getkysytaanPoistumisenYhteydessaTallennuksesta() {
        return this.kysytaanPoistumisenYhteydessaTallennuksesta;
    }
    
    public boolean getOllaanPoistumassaOhjelmasta() {
        return this.ollaanPoistumassaOhjelmasta;
    }

    public boolean getMerkintaanPaiva() {
        return merkintaanPaiva;
    }

    public boolean getMerkintaanAloitusAika() {
        return merkintaanAloitusAika;
    }

    public boolean getMerkintaanLopetusAika() {
        return merkintaanLopetusAika;
    }

    public boolean getMerkintaanSelostus() {
        return merkintaanSelostus;
    }

    public boolean getHakuKaynnissa() {
        return this.hakuKaynnissa;
    }

    public boolean getPoistetaanMerkintaa() {
        return this.poistetaanMerkintaa;
    }
    
    public void setKysytaanPoistumisenYhteydessaTallennuksesta(boolean b) {
        kysytaanPoistumisenYhteydessaTallennuksesta = b;
    }

    public void setOllaanPoistumassaOhjelmasta(boolean b) {
        ollaanPoistumassaOhjelmasta = b;
    }

    public void setMerkintaanPaiva(boolean b) {
        merkintaanPaiva = b;
    }

    public void setMerkintaanAloitusAika(boolean b) {
        merkintaanAloitusAika = b;
    }

    public void setMerkintaanLopetusaika(boolean b) {
        merkintaanLopetusAika = b;
    }

    public void setMerkintaanSelostus(boolean b) {
        merkintaanSelostus = b;
    }

    public void setHakuKaynnissa(boolean b) {
        hakuKaynnissa = b;
    }

    public void setPoistetaanMerkintaa(boolean b) {
        poistetaanMerkintaa = b;
    }

    /**
     * Asettaa kaikki kontekstinHaltijan kontekstia vastaavat boolean-muuttujat
     * epätosiksi.
     */
    public void poistuKaikistaKonteksteista() {

        ollaanPoistumassaOhjelmasta = false;
        kysytaanPoistumisenYhteydessaTallennuksesta = false;
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;
    }

    /**
     * Selvittää, onko ohjelmassa jokin konteksti päällä.
     *
     * @return true, jos jokin konteksti on käynnissä, muuten false.
     */
    public boolean onKontekstissa() {
        boolean[] kontekstit = kaikkiKontekstit();

        for (boolean b : kontekstit) {
            if (b == true) {
                return true;
            }
        }

        return false;
    }

    /**
     * Luo kaikista konteksteista taulun tarkastelua varten.
     *
     * @return kaikki kontekstiHaltijan boolean-muuttujat taulussa.
     */
    public boolean[] kaikkiKontekstit() {
        boolean[] kaikkiKontekstit = new boolean[8];

        kaikkiKontekstit[0] = ollaanPoistumassaOhjelmasta;
        kaikkiKontekstit[1] = kysytaanPoistumisenYhteydessaTallennuksesta;
        kaikkiKontekstit[2] = merkintaanPaiva;
        kaikkiKontekstit[3] = merkintaanAloitusAika;
        kaikkiKontekstit[4] = merkintaanLopetusAika;
        kaikkiKontekstit[5] = merkintaanSelostus;
        kaikkiKontekstit[6] = hakuKaynnissa;
        kaikkiKontekstit[7] = poistetaanMerkintaa;

        return kaikkiKontekstit;
    }
}
