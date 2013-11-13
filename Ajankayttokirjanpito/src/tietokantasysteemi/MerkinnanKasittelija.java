/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantasysteemi;

import java.text.DateFormat;
import java.util.ArrayList;
import kayttoliittyma.Dekooderi;

/**
 *
 * @author Envy 6-1010
 */
public class MerkinnanKasittelija {
  
  //tällä muunnetaan kolmirivinen käyttöliittymän antama merkintä
  public String muutaKayttajanAntamaMerkintaTietokannanMerkinnaksi(String merkinta) {
      Dekooderi d = new Dekooderi();
      Character rivinvaihto = "\n".charAt(0);
      
      String[] merkintarivit = d.dekoodaa(merkinta, rivinvaihto);      
      String paiva = merkintarivit[0];      
      String kellonajat = merkintarivit[1];
      String seloste = merkintarivit[2];
      
      String valmisMerkinta = paiva + "\n  " + kellonajat + ": " + seloste; 
      return valmisMerkinta;
  }
  
  public ArrayList<String> muutaTietokannanMerkintaKasiteltavaksi(String merkinta) {
      Dekooderi d = new Dekooderi();
      Character rivinvaihto = "\n".charAt(0);
      
      String[] merkintarivit = d.dekoodaa(merkinta, rivinvaihto);      
      String paiva = merkintarivit[0];
      ArrayList<String> tapahtumat = new ArrayList<>();
      
      for(int i = 1; i < merkintarivit.length; i++) {
          tapahtumat.add(merkintarivit[i]);
      }
      
      return null;
  }
}
