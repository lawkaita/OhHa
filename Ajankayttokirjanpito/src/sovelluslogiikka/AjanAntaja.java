/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Antaa tämän hetken erimuotoisina String-oliona.
 * @author lawkaita
 */
public class AjanAntaja {
    //hoitaa operaatioita jotka ovat nyt sekasin komentotulkissa ja kayttoliittymassa
    
    
    /**
     * 
     * @return tämä ajanhetki
     */
    public String mikaAikaNytOn() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        return ft.format(dNow);
    }

    public String annaTamaPaiva() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("dd.MM.YYYY");

        return ft.format(dNow);
    }
        public String annaTamaAika() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("HH.mm");

        return ft.format(dNow);
    }
}
