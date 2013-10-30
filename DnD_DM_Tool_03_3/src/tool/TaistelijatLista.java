/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Envy 6-1010
 */
public class TaistelijatLista {
    private ArrayList<Taistelija> taistelijat;
    private boolean kaynnissa;

    public TaistelijatLista() {
        this.taistelijat = new ArrayList<>();
        this.kaynnissa = false;
    }
    
    public void nollaaLista() {
        this.taistelijat = new ArrayList<>();
        this.kaynnissa = false;
    }

    public ArrayList<Taistelija> getArray() {
        return this.taistelijat;
    }

    public void lisaa(Taistelija t) {
        this.taistelijat.add(t);
    }

    public void poista(String nimi) {
        Taistelija p = this.etsi(nimi);

        if (!(p == null)) {
            if (p.getVuoro()) {
                inikkaOsoitinLiike();
            }
            
            this.taistelijat.remove(p);
        }
    }

    public boolean onkoTaistelussaTaistelijaaNimella(String nimi) {

        for (Taistelija t : this.taistelijat) {
            if (t.getNimi().equals(nimi)) {
                return true;
            }
        }

        return false;
    }

    public Taistelija etsi(String nimi) {
        Taistelija p = null;

        for (Taistelija t : this.taistelijat) {
            if (t.getNimi().equals(nimi)) {
                p = t;
            }
        }

        return p;
    }

    public void jarjesta() {
        Collections.sort(this.taistelijat);
    }

    public String tilanne() {
        jarjesta();
        String tuloste = "";
        for (Taistelija t : this.taistelijat) {
            tuloste = tuloste + t.toString() + "\n";
        }

        return tuloste;
    }

    public boolean onkoMonsu(Taistelija t) {
        if (t instanceof Monsu) {
            return true;
        }

        return false;
    }

    public void inikkaOsoitinLiike() {
        if(this.taistelijat.isEmpty()) {
            return;
        }
        
        jarjesta();
        Iterator<Taistelija> osoitin = this.taistelijat.iterator();
       // boolean tehtiinkoJotain = false; //tätä tarvitaan ensimmäisellä kierroksella kun ei ole kenenkään vuoro.
        if (this.kaynnissa == false) { //ensimmäinen kutsu
            //this.taistelijat.get(0).setVuoro(true);
            // 0 pienin?
            
            this.taistelijat.get(this.taistelijat.size()-1).setVuoro(true);
            this.kaynnissa = true;
        }
        
        while (osoitin.hasNext()) {
            Taistelija t = osoitin.next();
            if (t.getVuoro() == true) {//jos kutsulla tämän vuoro, sitten sen vuoro pois
                t.setVuoro(false);

                if (osoitin.hasNext()) {//käydään läpi listaa ja annetaan seuraavalle vuoro kunnes on aloitettava alusta
                    osoitin.next().setVuoro(true);
                } else {
                    this.taistelijat.get(0).setVuoro(true);
                }
                
                break;
            }
        }

        //tämä ei toimi koska boolean luodaan aina tässä kutsussa. Tämän pitää olla taistelijalistan boolean.
        
    }
}
