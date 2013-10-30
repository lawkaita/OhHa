/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import gui.NappaimistoKuuntelija;
import gui.KomentoTulkki;
import gui.Kayttoliittyma;
import gui.Kirjoittaja;
import gui.KomennonJakaja;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Envy 6-1010
 */
public class Main {
    //tässä versiossa luovun kyselysysteemistä koska se on liian vaikea tehdä;
    //Täytyy luoda komentoparseridekooderi

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Scanner lukija = new Scanner(System.in);
        TaistelijatLista lista = new TaistelijatLista();
        
        KomentoTulkki tulkki = new KomentoTulkki();
        
        JTextArea tekstialue = new JTextArea("Teksti");
        JTextField komentoalue = new JTextField();
        
        KomennonJakaja jakaja = new KomennonJakaja();
        NappaimistoKuuntelija enterinkuuntelija = new NappaimistoKuuntelija(komentoalue, tulkki, jakaja);
        
        
        //tätä työstetään
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma(tekstialue, komentoalue, enterinkuuntelija);   
                
        Kirjoittaja kirjoittaja = new Kirjoittaja(tekstialue);
        
        //ohjelmalle on annettava kayttoliittyma että se voi sulkea sen.
        Ohjelma ohjelma = new Ohjelma(lista, tulkki, kirjoittaja, kayttoliittyma, komentoalue, enterinkuuntelija, jakaja, tekstialue); 
        tulkki.annaTyokalut(ohjelma); 
        kayttoliittyma.annaOhjelma(ohjelma);
         
        //invokelater käynnistää
        SwingUtilities.invokeLater(kayttoliittyma);
        //ohjelma.kaynnista();
        
    }
}
