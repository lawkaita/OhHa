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
    private JTextArea tyhjaAlue;
    private JTextArea marginaali;
    private int dimensioLuku1;

    public TyhjaTila(Font f, int dimensioLuku1) {
        this.dimensioLuku1 = dimensioLuku1;
        
        this.setPreferredSize(new Dimension (400, (400 - 4 * dimensioLuku1)));
        
        tyhjaAlue = new JTextArea();
        tyhjaAlue.setFont(f);
        tyhjaAlue.setBackground(Color.black);
        tyhjaAlue.setForeground(Color.white);
        tyhjaAlue.setEditable(false);
        tyhjaAlue.setFocusable(false);
        tyhjaAlue.setPreferredSize(new Dimension(350, (400 - 4 * dimensioLuku1)));
        tyhjaAlue.setLineWrap(true);
        tyhjaAlue.setWrapStyleWord(true);
        
        marginaali = new JTextArea();
        marginaali.setBackground(Color.black);
        marginaali.setEditable(false);
        marginaali.setFocusable(false);
        marginaali.setPreferredSize(new Dimension(50, 400 - 4 * dimensioLuku1));
        
        this.setLayout(new BorderLayout());
        this.add(tyhjaAlue, BorderLayout.WEST);
        this.add(marginaali, BorderLayout.EAST);
    }
    
    public JTextArea getTyhja() {
        return this.tyhjaAlue;
    }
    
    /**
     * muuttaa tyhjän tilan kokoa.
     */
    public void madallaKorkeutta() {
        this.setPreferredSize(new Dimension (400,
                Math.max(tyhjaAlue.getPreferredSize().height - dimensioLuku1, 17)));        
        tyhjaAlue.setPreferredSize(new Dimension(350,
                Math.max(tyhjaAlue.getPreferredSize().height - dimensioLuku1, 17)));
        marginaali.setPreferredSize(new Dimension(50,
                Math.max(tyhjaAlue.getPreferredSize().height - dimensioLuku1, 17)));
        
    }

    void kasvataKorkeutta() {
        this.setPreferredSize(new Dimension (400,
                tyhjaAlue.getPreferredSize().height + 2 * dimensioLuku1));        
        tyhjaAlue.setPreferredSize(new Dimension(350,
                Math.max(tyhjaAlue.getPreferredSize().height - dimensioLuku1, 17)));
        marginaali.setPreferredSize(new Dimension(50,
                Math.max(tyhjaAlue.getPreferredSize().height - dimensioLuku1, 17)));
    }
}
