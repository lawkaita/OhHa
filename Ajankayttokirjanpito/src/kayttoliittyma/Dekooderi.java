/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Envy 6-1010
 */
public class Dekooderi {

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

    public int laskeKomentojenMaara(String komento) {
        if (komento.isEmpty()) {
            return 0;
        }

        int komentojenMaara = 0;
        Character vali = " ".charAt(0);
        int komennonPituus = komento.length();

        int i = 0;
        while (i < komennonPituus) {
            if (komento.charAt(i) == vali) {
                komentojenMaara++;
            }
            i++;
        }

        return komentojenMaara + 1; //jos valeja on 2, komentoja on 3.
        //määrää kuinka pitkä integer-taulukko tulee luoda kun dekoodataan komento-stringiä komennoiksi.
    }

    public String[] dekoodaa(String komento, Character valimerkki) {
        int komentojenMaara = laskeKomentojenMaara(komento);
        String dekoodi[] = new String[komentojenMaara];

        return hahmota(komento, dekoodi, 0, valimerkki);
    }

    public String listaaSisalto(String[] dekoodi) {
        String tuloste = "";

        for (String s : dekoodi) {
            tuloste = tuloste  + "> " + s + "\n";
        }

        return tuloste;

    }
}
