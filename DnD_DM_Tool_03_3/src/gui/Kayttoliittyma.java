/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import tool.Ohjelma;


/**
 *
 * @author Lauri
 */
public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private JTextArea tekstialue;
    private JTextField komentoalue;
    private NappaimistoKuuntelija enterinKuuntelija;
    
    private Ohjelma o;

    public Kayttoliittyma(JTextArea tekstialue, JTextField komentoalue, NappaimistoKuuntelija enterinkuuntelija) {
        this.tekstialue = tekstialue;
        this.komentoalue = komentoalue;
        this.enterinKuuntelija = enterinkuuntelija;
        
        this.o = null; 
    }
    
    public void annaOhjelma(Ohjelma o) {
        this.o = o;
    }

    @Override
    public void run() {
        frame = new JFrame("HXNX");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true); 
        
        frame.setLocation(300, 100);
        
        this.tekstialue.setFocusable(false);
        komentoalue.requestFocus();
        
        this.o.kaynnista();
    }

    private void luoKomponentit(Container container) {
        container.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(600, 400));

       // JLabel teksti = new JLabel("Tähän tulee ehkä menu");
        //teksti.setPreferredSize(new Dimension(600, 40));
        //container.add(teksti, BorderLayout.NORTH);
        
        Font f = new Font("Monospaced", Font.BOLD, 15);//Font.decode(Font.MONOSPACED);
        tekstialue.setFont(f);
        tekstialue.setEditable(false);
        tekstialue.setBackground(Color.BLACK);
        tekstialue.setForeground(Color.RED);
        JScrollPane skrollausAlue = new JScrollPane(tekstialue);
        skrollausAlue.setPreferredSize(new Dimension(600, 370));
        skrollausAlue.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        skrollausAlue.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //container.add(tekstialue, BorderLayout.CENTER);     
        
        komentoalue.setFont(f);
        komentoalue.setForeground(Color.GREEN);
        komentoalue.setBackground(Color.black);
        //komentoalue.addKeyListener(new EnterinKuuntelija(komentoalue, this.komentotulkki));
        komentoalue.addKeyListener(enterinKuuntelija);
        komentoalue.setPreferredSize(new Dimension(600, 30));
        //container.add(komentoalue, BorderLayout.SOUTH);
        
        //mainPanel.add(teksti, BorderLayout.NORTH);
        mainPanel.add(skrollausAlue, BorderLayout.CENTER);
        //mainPanel.add(tekstialue, BorderLayout.CENTER);
        mainPanel.add(komentoalue, BorderLayout.SOUTH);
        
        container.add(mainPanel, BorderLayout.CENTER);
    }
    
    public void tapa() {
        WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        frame.dispatchEvent(wev);
    }

    public JFrame getFrame() {
        return frame;
    }
    
    public NappaimistoKuuntelija getEnterinKuuntelija(){
        return this.enterinKuuntelija;
    }
}
