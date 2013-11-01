/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.text.DecimalFormat;

/**
 *
 * @author Envy 6-1010
 */
public class TaistelijaTulostaja {

    public TaistelijaTulostaja() {
    }

    public String teeTuloste(Taistelija t) {
        String tuloste = vuoroOsoitin(t.getVuoro())
                + initFormat(t.getInit()) + " | " + "  "
                + nimiFormat(t.getNimi()) + " | HP: "
                + hpFormat(t) + " : "
                + hpProsentti(t) + " | " 
                + t.getHuom();

        return tuloste;
    }

    //kirjoittaa inikan tasaten vasemmalle
    public String initFormat(int init) {
        //oletus: init on korkeintaan kaksinumeroinen luku.
        String tuloste = "" + init;
        int pituus = tuloste.length();

        if (pituus > 1) {
            return tuloste;
        } else {
            tuloste = " " + tuloste;
            return tuloste;
            // tässä init on kaksinueroinen luku.
        }
    }

    private String nimiFormat(String nimi) {
        if (nimi.length() < 11) {
            int kuinkaLyhyt = nimi.length();
            int kuinkaPaljon = 10 - kuinkaLyhyt;

            int i = kuinkaPaljon;
            while (i > 0) {
                nimi = nimi + " ";
                i--;
            }

            return nimi;
        } else {
            nimi = nimi.substring(0, 7);
            return nimi + "...";
        }
    }

    //tukee kolminumeroisia hp:eitä
    public static String hpFormat(Taistelija t) {
        Monsu testiMonsu = new Monsu("Testi", 1, 1);
        
        if (t.getClass() != testiMonsu.getClass()) {
            return "    -    "; //tämä riippuu merkkivarauksesta
        } else {
            Monsu m = (Monsu) t;

            int hp = m.getHp();
            int maxHp = m.getMaxHp();

            //tästä eteenpäin voisi tehdä abstarctFormatilla
            String kunto = "" + hp;
            String potentiaali = "" + maxHp;

            int kuntoPituus = kunto.length();
            int potentiaaliPituus = potentiaali.length();
            
            //olkoon merkkivaraus 4 niin formaatti ei hajoa -192 hplla.
            int merkkiVaraus = 4;  // pitäsi kehittää jotakin joka laskee pisimmän merkkijonon kaikilta pelaajilta.

            int kpKuinkaPaljon = merkkiVaraus - kuntoPituus;
            int ppKuinkaPaljon = merkkiVaraus - potentiaaliPituus;

            int i = kpKuinkaPaljon;
            int j = ppKuinkaPaljon;

            while (i > 0) {
                kunto = " " + kunto;
                i--;
            }

            while (j > 0) {
                potentiaali = potentiaali + " ";
                j--;
            }

            String tuloste = kunto + "/" + potentiaali;

            return tuloste;
        }
    }

    public static String hpProsentti(Taistelija t) {
        Monsu testiMonsu = new Monsu("Testi", 1, 1);

        if (t.getClass() != testiMonsu.getClass()) {
            return "    -    "; // tämä riippuu merkkivarauksesta
        } else {
            Monsu m = (Monsu) t;

            double osamaara = (double) m.getHp() / m.getMaxHp();
            DecimalFormat df = new DecimalFormat("##0.0##");
            String tuloste = df.format(osamaara);

            int merkkiIndeksi = tuloste.indexOf(",");

            tuloste = abstarctFormat(tuloste.substring(0, merkkiIndeksi), ",", tuloste.substring(merkkiIndeksi + 1));

            return tuloste;
        }
    }

    //kolmella; eikun 4 merkillä, tämä voisi ottaa vastaan sen kaikkien pelaajien pisimmän merkkijonon pituuden.
    public static String abstarctFormat(String a, String merkki, String b) {

        int aPituus = a.length();
        int bPituus = b.length();

        //tässä oli ennen 3
        int merkkiVaraus = 4;  // pitäsi kehittää jotakin joka laskee pisimmän merkkijonon kaikilta pelaajilta.

        int aKuinkaPaljon = merkkiVaraus - aPituus;
        int bKuinkaPaljon = merkkiVaraus - bPituus;

        int i = aKuinkaPaljon;
        int j = bKuinkaPaljon;

        while (i > 0) {
            a = " " + a;
            i--;
        }

        while (j > 0) {
            b = b + " ";
            j--;
        }

        String tuloste = a + merkki + b;

        return tuloste;
    }

    private String vuoroOsoitin(boolean vuoro) {
        if (vuoro == true) {
            return "  >> ";
        }
        
        return "     ";
    }
}
