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

    private boolean ollaanPoistumassaOhjelmasta;
    private boolean kysytaanPoistumisenYhteydessaTallennuksesta;
    private boolean merkintaanPaiva;
    private boolean merkintaanAloitusAika;
    private boolean merkintaanLopetusAika;
    private boolean merkintaanSelostus;
    private boolean hakuKaynnissa;
    private boolean poistetaanMerkintaa;
    private boolean kysytaanLisataankoSeurattava;
    private boolean lisataanSeurattava;
    private boolean poistetaanSeurattava;

    public KontekstinHaltija() {

        ollaanPoistumassaOhjelmasta = false;
        kysytaanPoistumisenYhteydessaTallennuksesta = false;
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;
        kysytaanLisataankoSeurattava = false;
        poistetaanSeurattava = false;
    }

    public boolean getKysytaanPoistumisenYhteydessaTallennuksesta() {
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
    
    public boolean getKysytaanLisataankoSeurattava() {
        return this.kysytaanLisataankoSeurattava;
    }
    
    public boolean getLisataanseurattava() {
        return this.lisataanSeurattava;
    }
    
    public boolean getPoistetaanSeurattava() {
        return this.poistetaanSeurattava;
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
    
    public void setKysytaanLisataankoSeurattava(boolean b) {
        kysytaanLisataankoSeurattava = b;
    }
    
    public void setLisataanSeurattava(boolean b) {
        lisataanSeurattava = b;
    }
    
        public void setPoistetaanSeurattava(boolean b) {
            poistetaanSeurattava = b;
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
        kysytaanLisataankoSeurattava = false;
        lisataanSeurattava = false;
        poistetaanSeurattava = false;
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
        boolean[] kaikkiKontekstit = new boolean[11];

        kaikkiKontekstit[0] = ollaanPoistumassaOhjelmasta;
        kaikkiKontekstit[1] = kysytaanPoistumisenYhteydessaTallennuksesta;
        kaikkiKontekstit[2] = merkintaanPaiva;
        kaikkiKontekstit[3] = merkintaanAloitusAika;
        kaikkiKontekstit[4] = merkintaanLopetusAika;
        kaikkiKontekstit[5] = merkintaanSelostus;
        kaikkiKontekstit[6] = hakuKaynnissa;
        kaikkiKontekstit[7] = poistetaanMerkintaa;
        kaikkiKontekstit[8] = kysytaanLisataankoSeurattava;
        kaikkiKontekstit[9] = lisataanSeurattava;
        kaikkiKontekstit[10] = poistetaanSeurattava;

        return kaikkiKontekstit;
    }


    
}
