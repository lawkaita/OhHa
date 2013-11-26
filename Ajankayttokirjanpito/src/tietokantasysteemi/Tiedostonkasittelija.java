/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.io.IOException;
import java.util.ArrayList;
import sovelluslogiikka.Dekooderi;

/**
 *
 * @author Envy 6-1010
 */
public interface Tiedostonkasittelija {
    ArrayList<Merkinta> lataaTietokanta();
    void yliKirjoitaTietokantatiedosto(ArrayList<Merkinta> merkinnat);
    void nollaaTietokantaTiedosto() throws IOException;
    MerkinnanKasittelija getMerkinnanKasittelija();
    Dekooderi getDekooderi();
    
}
