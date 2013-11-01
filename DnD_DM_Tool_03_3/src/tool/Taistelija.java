/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

/**
 *
 * @author Envy 6-1010
 */

public class Taistelija implements Comparable<Taistelija>{
    private String nimi;
    private int init;
    private String huom;
    private boolean vuoro;
    private TaistelijaTulostaja j;
    
    public Taistelija (String nimi, int init) {
        this.nimi = nimi;
        this.init = init;
        this.huom = "";
        this.vuoro = false;
        this.j = new TaistelijaTulostaja();
    }
        
    public String getNimi() {
        return this.nimi;
    }
    
    public int getInit() {
        return this.init;
    }    
    
    public void setInit(int init) {
        this.init = init;
    }
 
    @Override
    public int compareTo(Taistelija t) {
        return t.init - this.init;
    }
    
    //2-rivinen
    public String getHuom() {
        if (this.huom.isEmpty()) {
            return "";
        }
        String huomiot = "\n        Huom:" + this.huom;
        
        return huomiot;
    }
    
    public void setHuom(String huom){
        this.huom = huom;
    }
    
    public void jatkaHuom(String huomio){
        this.huom = this.huom + huomio;
    }
    
    public boolean getVuoro() {
        return this.vuoro;
    }
    
    public void setVuoro(boolean b) {
        this.vuoro = b;
    }
    
    @Override
    public String toString() {
        String tuloste = this.j.teeTuloste(this);
        return tuloste;
    }
   
    public boolean equals(Taistelija t) {
        if (this.nimi.equals(t.nimi)) {
            return true;
        }
        
        return false;
    } 
    
}


