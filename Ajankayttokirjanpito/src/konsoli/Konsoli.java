/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import kayttoliittyma.Tulostaja;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import kayttoliittyma.Kayttoliittyma;
import kayttoliittyma.Komentotulkki;
import kayttoliittyma.Paivitettava;

/**
 *
 * @author Envy 6-1010
 */
public class Konsoli extends JPanel implements Paivitettava {

    private Tulostealue tulosteAlue;
    private VarsinainenKomentoRivi rivi;
    private Komentorivi komentorivi;
    private TyhjaTila tyhjaTila;
    private Skrollausnakyma skrollausnakyma;
    
    private int dimensioLuku1;
    
    private Komentotulkki komentotulkki;

    public Konsoli(Kayttoliittyma kali) {
        komentotulkki = new Komentotulkki(kali);
        Font f = new Font("Monospaced", Font.PLAIN, 12);

        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());

        dimensioLuku1 = 15; //kotikoneella 17, koulun ubuntulla 15.
        int dimensioLuku2 = 21; //kotikoneella toistaiseksi 15, koulun ubuntulla 21

        tulosteAlue = new Tulostealue(f, dimensioLuku1);
        rivi = new VarsinainenKomentoRivi(f, dimensioLuku1, dimensioLuku2);
        komentorivi = new Komentorivi(f, rivi, dimensioLuku1, dimensioLuku2);
        tyhjaTila = new TyhjaTila(f, dimensioLuku1);

        JPanel tekstialue = new JPanel(new BorderLayout());
        tekstialue.add(tulosteAlue, BorderLayout.NORTH);
        tekstialue.add(komentorivi, BorderLayout.CENTER);
        tekstialue.add(tyhjaTila, BorderLayout.SOUTH);

        JScrollPane skrollausalue = new JScrollPane(tekstialue);
        this.skrollausnakyma = new Skrollausnakyma(skrollausalue, dimensioLuku1);

        add(skrollausalue, BorderLayout.CENTER);
        
    }

    @Override
    public void paivita(String teksti, boolean kirjoittajaOnKayttaja) {;
        skrollausnakyma.setPaivitetaan(true);
    
        String dialogi = tulosteAlue.getText();

        String etumerkki = komentorivi.getKursori().annaMerkki() + "> ";

        if (!kirjoittajaOnKayttaja) {
            etumerkki = " :";
        }

        tulosteAlue.setPreferredSize(new Dimension(400,
                (tulosteAlue.getPreferredSize().height + dimensioLuku1)));

        tulosteAlue.setText(dialogi + "\n" + etumerkki + teksti);

        tyhjaTila.madallaKorkeutta();

        //tyhjaTila.kasvataKorkeutta();

    }
    //kirjoittajaOnKauttaja asettaa sen, mika etumerkki tulostetaan,
    //kun paivitetaan tulostuskenttaa.

    public void tulostaViesti(String viesti) {
        paivita(viesti, false);
    }

    public void tulostaJaSuoritaKayttajanKomento() {
        String komento = rivi.getText() + tyhjaTila.getTyhja().getText();
        paivita(komento, true);
        rivi.setText("");

        nollaaTyhjaTila();
        komentotulkki.enter(komento);
    }

    public void jatkaKirjoitustaTyhjaanKenttaan() {
        tyhjaTila.getTyhja().setEditable(true);
        tyhjaTila.getTyhja().setFocusable(true);
        tyhjaTila.getTyhja().requestFocus();
    }

    public void estaLiianPitkaKomento() {
        try {
            rivi.setText(rivi.getText(0, rivi.getText().length() - 1));
        } catch (BadLocationException ex) {
            //ei tehda mitaan - tata ei ikina saavuteta.
        }
    }

    public void nollaaTyhjaTila() {
        tyhjaTila.getTyhja().setEditable(false);
        tyhjaTila.getTyhja().setFocusable(false);
        rivi.requestFocus();
        tyhjaTila.getTyhja().setText("");
    }

    public JTextField getVarsinainenKomentoRivi() {
        return this.rivi;
    }

    public JTextArea getTyhjanTilanTyhja() {
        return this.tyhjaTila.getTyhja();
    }

    public String annaKomento(String komento) {
        return komento;
    }

    public Komentotulkki getKomentotulkki() {
        return this.komentotulkki;
    }
    
    public VarsinainenKomentoRivi getKomentoRivi() {
        return this.rivi;
    }
    
    public Tulostealue getTulosteAlue() {
        return this.tulosteAlue;
    }
    
    public void kirjoitaKomentoriville(String S) {
        this.rivi.setText(S);
    }

}
