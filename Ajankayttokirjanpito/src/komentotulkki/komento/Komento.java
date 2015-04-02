/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komentotulkki.komento;

import komentotulkki.msg.Msg;

/**
 *
 * @author Envy 6-1010
 */
public interface Komento {
    public String help();
    public Msg suorita(String[] params);
}
