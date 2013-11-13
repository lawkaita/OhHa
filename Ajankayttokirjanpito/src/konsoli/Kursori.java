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
 *
 * @author Envy 6-1010
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

    public String lueSpritea() {
        if (this.lukija.hasNext()) {
            return this.lukija.nextLine();
        } else {
            this.lukija = annaLukija();
            return this.lukija.nextLine();
        }
    }

    public Scanner annaLukija() {
        try {
            return new Scanner(this.spritet);
        } catch (FileNotFoundException ex) {
            //tiedosto on kuitenkin olemassa
            return null;
        }
    }
    
    public String annaMerkki() {
        return lueSpritea();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.ilmentymisalue.setText(lueSpritea());
    }
}
