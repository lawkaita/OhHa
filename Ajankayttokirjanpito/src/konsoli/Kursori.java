/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Kursori näyttää konsolissa komentorivi-rivin paikan. 
 * Kursori on animoitu \|/- -merkeillä, joita luetaan tedostosta "kursori.txt".
 * @author lawkaita
 */
public class Kursori extends Timer implements ActionListener{

    private File spritet;
    private Scanner lukija;
    private JTextField ilmentymisalue;

    public Kursori(JTextField ilmentymisalue) {
        super(500, null);
        this.spritet = new File("kursori.txt");
        this.lukija = annaLukija();
        this.ilmentymisalue = ilmentymisalue;
        
        addActionListener(this);
        setInitialDelay(500);
    }
    
    /**
     * lukee kursorin seuraavan näytettävän merkin.
     * @return 
     */
    public String lueSpritea() {
        if (this.lukija.hasNext()) {
            return this.lukija.nextLine();
        } else {
            this.lukija = annaLukija();
            return this.lukija.nextLine();
        }
    }
    
    /**
     * Palauttaa spriten lukemisessa käytettävän scanner-olion.
     * @return spriten lukemisessa käytettävä scanner, tai null jos luettavaa
     * tiedostoa ei ole.
     */
    public Scanner annaLukija() {
        try {
            return new Scanner(this.spritet);
        } catch (FileNotFoundException ex) {
            //tiedosto on kuitenkin olemassa
            return null;
        }
    }
     
    /**
     * Antaa seuraavan kursorin merkin.
     * @return kursorin seuraava merkki.
     */
    public String annaMerkki() {
        return lueSpritea();
    }
    
    /**
     * Päivittää kursorin esiintymiskohdassa kursorin vaihtamalla sen paikalle
     * seuraavan luettavan merkin.
     * @param ae tapahtuma joka käynnistää muutoksen.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.ilmentymisalue.setText(lueSpritea());
    }
}
