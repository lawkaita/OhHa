/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Envy 6-1010
 */
public class Win7cmd_kaantaja {
    
    public Win7cmd_kaantaja() {
        
    }
    
    public byte[] kaanna(String teksti) {        
        int askeleita = teksti.length(); //tekstin pituus on 1, antaa luvuksi 1, mutta merkin indeksi on 0.
        byte[] tuloste = new byte[askeleita];
        
        int i = 0;
        while ( askeleita > i ) {
            tuloste[i] = aakkostaja("" + teksti.charAt(i));
            i++;
        }
        
        return tuloste;
    }
    
    public byte aakkostaja(String s) {
        try {
            byte [] ba = s.getBytes("IBM850");
            return ba[0];
        } catch (Exception e) {
            
            try {
                s = " ";
                
                byte[] b;
                b = s.getBytes("IBM850");
                
                return b[0];
                            } catch (UnsupportedEncodingException ex) {
                return new Byte(" ");
            }           
            
        }            
    }
}
