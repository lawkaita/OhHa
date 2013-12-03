/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.util.ArrayList;

/**
 *
 * @author Envy 6-1010
 */
public interface TietokantaValimuisti {
    public int laskeMerkintojenMaara();
    public Merkinta haeMuististaMerkintaPaivayksella(String paivays);
    public boolean kannassaOnMerkintaPaivalla(String paivaysMuistettavaStringista);
    public boolean poistaMerkintaPaivanPerusteella(String komento);
    public ArrayList<Merkinta> annaMuisti();
    public ArrayList<String> annaSeurattavatToiminnot();
    public void lisaaMerkinta(Merkinta uusiMerkinta);
    public void lisaaMerkintaYhdistaen(Merkinta uusiMerkinta);
    public void nollaaMuisti();
    public void lisaaSeurattava(String string);
    public boolean kannassaOnSeurattavaToiminta(String komento);

    public int laskeSeurattavienMaara();
    
}
