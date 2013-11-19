/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package konsoli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Konsolin kosmeettinen kokoaan muuttava alue.
 * @author Envy 6-1010
 */
public class TyhjaTila extends JPanel {
    private JTextArea tyhja;
    private JTextArea marginaali;
    private int dimensioLuku1;

    public TyhjaTila(Font f, int dimensioLuku1) {
        this.dimensioLuku1 = dimensioLuku1;
        
        this.setPreferredSize(new Dimension (400, (400 - 4 * dimensioLuku1)));
        
        tyhja = new JTextArea();
        tyhja.setFont(f);
        tyhja.setBackground(Color.black);
        tyhja.setForeground(Color.white);
        tyhja.setEditable(false);
        tyhja.setFocusable(false);
        tyhja.setPreferredSize(new Dimension(350, (400 - 4 * dimensioLuku1)));
        tyhja.setLineWrap(true);
        tyhja.setWrapStyleWord(true);
        
        marginaali = new JTextArea();
        marginaali.setBackground(Color.black);
        marginaali.setEditable(false);
        marginaali.setFocusable(false);
        marginaali.setPreferredSize(new Dimension(50, 400 - 4 * dimensioLuku1));
        
        this.setLayout(new BorderLayout());
        this.add(tyhja, BorderLayout.WEST);
        this.add(marginaali, BorderLayout.EAST);
    }
    
    public JTextArea getTyhja() {
        return this.tyhja;
    }
    
    public void madallaKorkeutta() {
        this.setPreferredSize(new Dimension (400,
                Math.max(tyhja.getPreferredSize().height - dimensioLuku1, 17)));        
        tyhja.setPreferredSize(new Dimension(350,
                Math.max(tyhja.getPreferredSize().height - dimensioLuku1, 17)));
        marginaali.setPreferredSize(new Dimension(50,
                Math.max(tyhja.getPreferredSize().height - dimensioLuku1, 17)));
        
    }

    void kasvataKorkeutta() {
        this.setPreferredSize(new Dimension (400,
                tyhja.getPreferredSize().height + 2 * dimensioLuku1));        
        tyhja.setPreferredSize(new Dimension(350,
                Math.max(tyhja.getPreferredSize().height - dimensioLuku1, 17)));
        marginaali.setPreferredSize(new Dimension(50,
                Math.max(tyhja.getPreferredSize().height - dimensioLuku1, 17)));
    }
}
