/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import kayttoliittyma.Konsoli;

/**
 * Ohjelman graafisen käyttöliittymän pääkomponentti.
 * Koostuu monesta eri Swing-komponentista, joilla yritetään saada ohjelma 
 * muistuttamaan tavallista terminaalia. Tulostealueella näkyy ohjelman viestit,
 * Komentorivi on alue, jonka VarsinainenKomentoRivi-olioon komennot kirjoitetaan, ja TyhjaTila on vain
 * kosmeettinen kokoaan muuttava alue, jotta Konsoli muistuttaisi oikeaa konsolia.
 * Skrollausnäkymä sisältää Tulostealueen, jota voi selata ylös ja alas page-up ja -down näppäimillä. 
 * DimensioLuku1 on luku, jolla säädetään kirjasinkokoa. Swing-komponenttien fontti vaihtelee eri 
 * esimerkiksi ubuntun ja windowssin kanssa.
 * @author Envy 6-1010
 */
public class OmaKonsoli extends JPanel implements Konsoli {

    private Tulostealue tulosteAlue;
    private EkaVarsinainenKomentoRivi rivi;
    private Komentorivi komentorivi;
    private TyhjaTila tyhjaTila;
    private Skrollausnakyma skrollausnakyma;
    
    private int dimensioLuku1;
    
    public OmaKonsoli(boolean ubuntulla) {
        Font f = new Font("Monospaced", Font.PLAIN, 12);

        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        
        dimensioLuku1 = 17; //kotikoneella 17, koulun ubuntulla 15.
        if(ubuntulla) {
            dimensioLuku1 = 15;
        }
        
        int dimensioLuku2 = 21; //kotikoneella toistaiseksi 15, koulun ubuntulla 21

        tulosteAlue = new Tulostealue(f, dimensioLuku1);
        rivi = new EkaVarsinainenKomentoRivi(f, dimensioLuku1, dimensioLuku2);
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
    
    /**
     * Päivittää konsolin kun siihen lisätään tekstiä.
     * KirjoittajaOnKayttaja määrää mikä etumerkki tulostetaan. 
     * Näin ohjelman ja käyttäjän viestit erottuvat konsolissa.
     * 
     * @param teksti lisättävä teksti
     * @param kirjoittajaOnKayttaja kertoo onko kirjoittaja käyttäjä vai ei
     */
    @Override
    public void paivita(String teksti, boolean kirjoittajaOnKayttaja) {
        skrollausnakyma.setPaivitetaan(true);    
        String dialogi = tulosteAlue.getText();
        String etumerkki = komentorivi.getKursori().annaMerkki() + "> ";

        if (!kirjoittajaOnKayttaja) {
            etumerkki = " # ";
        }        

        tulosteAlue.setPreferredSize(new Dimension(400,
                (tulosteAlue.getPreferredSize().height + dimensioLuku1)));

        tulosteAlue.setText(dialogi + "\n" + etumerkki + teksti);
        tyhjaTila.madallaKorkeutta();
    }

    /**
     * Tulostaa ohjelman viestin konsoliin.
     * @param viesti ohjelman viesti.
     */
    @Override
    public void tulostaViesti(String viesti) {
        paivita(viesti, false);
    }
    
    @Override
    public void tulostaKomento() {
        String komento = rivi.getText() + tyhjaTila.getTyhja().getText();
        paivita(komento, true);
        rivi.setText("");

        nollaaTyhjaTila();
    }

    @Override
    public void jatkaKirjoitustaTyhjaanKenttaan() {
        tyhjaTila.getTyhja().setEditable(true);
        tyhjaTila.getTyhja().setFocusable(true);
        tyhjaTila.getTyhja().requestFocus();
    }
    
    /**
     * Estää liian pitkän komennon kirjoittamisen poistamalla komennon viimeisen merkin.
     */
    @Override
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

    @Override
    public JTextField getVarsinainenKomentoRivi() {
        return this.rivi;
    }

    @Override
    public JTextArea getTyhjanTilanTyhja() {
        return this.tyhjaTila.getTyhja();
    }
    
    @Override
    public Tulostealue getTulosteAlue() {
        return this.tulosteAlue;
    }
    
    /**
     * Kirjoittaa komentoriville annetun tekstin.
     * @param kirjoitettava kirjoitettava teksti.
     */
    @Override
    public void kirjoitaKomentoriville(String kirjoitettava) {
        this.rivi.setText(kirjoitettava);
    }

    /**
     * Tyhjentaa komentorivin.
     */
    public void tyhjennaKomentorivi() {
        kirjoitaKomentoriville("");
    }

    @Override
    public void setDimensioLuku() {
        
    }

}
