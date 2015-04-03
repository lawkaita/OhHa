/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legacykayttoliittyma;

/**
 *
 * @author Envy 6-1010
 */
public class LegacyKonsolinKorvaaja implements LegacyKonsolinKorvaajaRajapinta {

    @Override
    public void tulostaViesti(String s) {
        System.out.println(s);
    }

    @Override
    public void kirjoitaKomentoriville(String s) {
        tulostaViesti(s);
    }
    
}
