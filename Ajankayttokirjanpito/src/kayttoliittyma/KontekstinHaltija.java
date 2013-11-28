/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.util.ArrayList;

/**
 *KontekstinHaltijan tehtävä on seurata, missä asiayhteydessä ohjelmaan syötetään komentoja.
 * @author Envy 6-1010
 */
public class KontekstinHaltija {
    public boolean merkintaanPaiva;
    public boolean merkintaanAloitusAika;
    public boolean merkintaanLopetusAika;
    public boolean merkintaanSelostus;
    public boolean hakuKaynnissa;
    public boolean poistetaanMerkintaa;
    
    public KontekstinHaltija() {
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;
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
     * Asettaa kaikki kontekstinHaltijan kontekstia vastaavat boolean-muuttujat epätosiksi.
     */
    public void poistuKaikistaKonteksteista() {
        merkintaanPaiva = false;
        merkintaanAloitusAika = false;
        merkintaanLopetusAika = false;
        merkintaanSelostus = false;
        hakuKaynnissa = false;
        poistetaanMerkintaa = false;
    }
       
    /**
     * Selvittää, onko ohjelmassa jokin konteksti päällä.
     * @return true, jos jokin konteksti on käynnissä, muuten false.
     */
    public boolean onKontekstissa() {
        boolean[] kontekstit = kaikkiKontekstit();
        
        for(boolean b : kontekstit) {
            if (b == true) {
                return true;
            }
        }
        
        return false;        
    }
    
    /**
     * Luo kaikista konteksteista taulun tarkastelua varten.
     * @return kaikki kontekstiHaltijan boolean-muuttujat taulussa.
     */    
    public boolean[] kaikkiKontekstit() {
        boolean[] kaikkiKontekstit = new boolean[6];
        kaikkiKontekstit[0] = merkintaanPaiva;
        kaikkiKontekstit[1] = merkintaanAloitusAika;
        kaikkiKontekstit[2] = merkintaanLopetusAika;
        kaikkiKontekstit[3] = merkintaanSelostus;
        kaikkiKontekstit[4] = hakuKaynnissa;
        kaikkiKontekstit[5] = poistetaanMerkintaa;
        
        return kaikkiKontekstit;
    }
    
}
