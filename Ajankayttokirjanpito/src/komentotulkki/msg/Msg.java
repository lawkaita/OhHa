/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komentotulkki.msg;

/**
 *
 * @author Envy 6-1010
 */
public class Msg {
    private final String label;
    private final String text;
    
    public Msg(String label, String text) {
        this.label = label;
        this.text = text;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public String getText() {
        return this.text;
    }
}
