/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author Envy 6-1010
 */
public class Ajantestaaja {
    
    public boolean onPaiva(String komento) {
        Dekooderi d = new Dekooderi();
        Character piste = ".".charAt(0);

        if (d.laskeKomentojenMaara(komento, piste) != 3) {
            return false;
        }

        String[] osat = d.dekoodaa(komento, piste);

        int[] lukuina = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                lukuina[i] = Integer.parseInt(osat[i]);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        if (!(0 < lukuina[0] && lukuina[0] < 32)) {
            return false;
        }

        if (!(0 < lukuina[1] && lukuina[1] < 13)) {
            return false;
    }

        return true;

    }

    public boolean onAika(String komento) {
        Dekooderi d = new Dekooderi();
        Character piste = ".".charAt(0);

        if (d.laskeKomentojenMaara(komento, piste) != 2) {
            return false;
        }

        String[] osat = d.dekoodaa(komento, piste);

        int[] lukuina = new int[2];
        for (int i = 0; i < 2; i++) {
            try {
                lukuina[i] = Integer.parseInt(osat[i]);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        if (!(0 <= lukuina[0] && lukuina[0] < 24)) {
            return false;
        }

        if (!(0 <= lukuina[1] && lukuina[1] < 60)) {
            return false;
        }

        return true;
    }
    
}
