/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Pilkkoo String-muodossa olevia komentoja osiin käsiteltäväksi erikseen.
 * @author Envy 6-1010
 */
public class Dekooderi {
    
    
    /**
     * Dekoodauksen rekursiivinen metodi, joka askel kerrallaan määrittää palautettavan dekoodaustaulun
     * @param komento jäljellä oleva dekoodattava osa
     * @param dekoodi Tähän asti suoritettu dekoodaus
     * @param i vaiheindeksi
     * @param valimerkki dekoodaukseen valittu osat toisistaan erottava merkki
     * @return palauttaa luodun dekoodaus taulun
     */
    private String[] hahmota(String komento, String[] dekoodi, int i, Character valimerkki) {
        Character vali;

        if (valimerkki == null) {
            vali = " ".charAt(0);// vali on " ";        
        } else {
            vali = valimerkki;
        }

        int valiIndeksi = komento.indexOf(vali);
        //valin paikka, tai aina ensimmäisen valin paikka uuden iteraation jälkeen.        
        //palautettava taulukko

        if (valiIndeksi == -1) { // indexof antaa -1 jos Stringissä ei ole haettua merkkiä.
            //System.out.println(komento + ".");            
            dekoodi[i] = komento;
            return dekoodi;
        } else {
            String osakomento = komento.substring(0, valiIndeksi);

            String jatkettava = komento.substring(valiIndeksi + 1);

            //System.out.println(tulostettava + ",");

            dekoodi[i] = osakomento;

            i++;
            hahmota(jatkettava, dekoodi, i, vali);

        }
        return dekoodi;
    }
    
    /**
     * Laskee eroteltavien osien määrän.
     * @param lajiteltava lajiteltava String
     * @param valimerkki lajittelussa käytettävä välimerkki.
     * @return 
     */
    public int laskeOsienMaara(String lajiteltava, Character valimerkki) {
        if (lajiteltava.isEmpty()) {
            return 0;
        }

        Character vali;
        int komentojenMaara = 0;
        if (valimerkki == null) {
            vali = " ".charAt(0);
        } else {
            vali = valimerkki;
        }
        int komennonPituus = lajiteltava.length();

        int i = 0;
        while (i < komennonPituus) {
            if (lajiteltava.charAt(i) == vali) {
                komentojenMaara++;
            }
            i++;
        }

        return komentojenMaara + 1; //jos valeja on 2, komentoja on 3.
        //määrää kuinka pitkä integer-taulukko tulee luoda kun dekoodataan komento-stringiä komennoiksi.
    }
    
    /**
     * Muuttaa laijteltavan Stringin tauluksi Stringejä erotellen lajiteltavan
     * annetun valimerkin avulla.
     * @param lajiteltava laijiteltava String
     * @param valimerkki lajitteluun käytettävä välimerkki
     * @return 
     */
    public String[] dekoodaa(String lajiteltava, Character valimerkki) {
        int komentojenMaara = laskeOsienMaara(lajiteltava, valimerkki);
        String dekoodi[] = new String[komentojenMaara];
        
        if (dekoodi.length == 0) {
            return new String[0];
        }

        return hahmota(lajiteltava, dekoodi, 0, valimerkki);
    }
    
    /**
     * Muuttaa dekoodauksessa saadun tulostaulun joukoksi rivejä.
     * @param dekoodi dekoodauksessa saatu taulu
     * @return taulu String-oliona eroteltavat osat omilla riveillään
     */
    public String listaaSisalto(String[] dekoodi) {
        String tuloste = "";

        for (String s : dekoodi) {
            tuloste = tuloste + "> " + s + "\n";
        }

        return tuloste;

    }
}
