/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

/**
 *
 * @author Envy 6-1010
 */
public class Monsu extends Taistelija{
     private int hp;
     private int maxHp;
     
     //tätä käytetään lisäämään terve Mobi tappeluun.
     public Monsu(String nimi, int init, int hp) {
         super(nimi, init);
         this.hp = hp;
         this.maxHp = hp;
     }
     
     public int getHp() {
         return this.hp;
     }
     
     public int getMaxHp() {
         return this.maxHp;
     }
     
     public void dmg(int dmg) {
         this.hp = this.hp - dmg;
     }
}
