/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import tool.Ohjelma;
import tool.TaistelijatLista;

/**
 *
 * @author Lauri
 */
//tämä on komentokuuntelija.
public class KomentoTulkki implements KomennonKohde {
    //tämä pitää kirjoittaa uusiksi

    private Ohjelma ohjelma;
    private boolean lopetusKaynnissa;
    private boolean uusiTaisteluKaynnissa;

    public KomentoTulkki() {
        this.ohjelma = null;
        this.lopetusKaynnissa = false;
        this.uusiTaisteluKaynnissa = false;    }

    public void annaTyokalut(Ohjelma o) {
        this.ohjelma = o;
    }

    @Override
    public void kasitteleKomento(String komento) {
        //kirjoitetaan komento ikkunaan
        this.ohjelma.kirjoitaKomento(komento);
        
        if (komento.equals("")) {
            return; 
        }
        //tähän tyyliin VOISI kovakoodata kaikki dialogit mutta se 
        //olisi työlästä ja sitten se on siinä eikä sitä parane muuttaa.
        if (lopetusKaynnissa) {
            if ((komento.equals("xy") || komento.equals("y") || komento.equals("kyllä"))) {
                this.ohjelma.lopeta();
            }
            this.lopetusKaynnissa = false;
        } else if (uusiTaisteluKaynnissa) {
            if ((komento.equals("kyllä") || komento.equals("y"))) {
                this.ohjelma.taistelijaListaNollaus();
            }
            this.uusiTaisteluKaynnissa = false;
        } else {

            if (komento.equals("v") || komento.equals("help")) {
                this.ohjelma.tulostaKomennot();
            }

            if (komento.equals("t") || komento.equals("tilanne")) {
                this.ohjelma.tulostaTilanne(0);
            }

            if (komento.equals("xy") || komento.equals("lopeta")|| komento.equals("exit")) {
                this.ohjelma.lopeta();
            }

            if (komento.equals("x")) {
                this.ohjelma.lopetusKeskustelu();
                this.lopetusKaynnissa = true;
            }

            if (komento.equals("uusi taistelu") || komento.equals("uusi") 
                    || komento.equals("taistelu")) {
                this.ohjelma.uusitaisteluKeskustelu();
                this.uusiTaisteluKaynnissa = true;

                //kesken
            }
            
            if (komento.equals("b")) {
                this.ohjelma.lyhenteet();
            }

            if (komento.equals("painettiinOikealle")) {
                this.ohjelma.getKirjoittaja().korvaa("");
                this.ohjelma.getTaistelijaLista().inikkaOsoitinLiike();
                this.ohjelma.tulostaTilanne(1);
            }
//            while (syote.equals("x")) {
//                System.out.println("Haluato poistua? (y/n)");
//                //syote = lukija.nextLine();
//            }
//
//            if (syote.equals("y") || syote.equals("xy")) {
//                this.ohjelma.lopeta();
//            }


            //tätä ei välttämättä aina tarvitse ajaa jos ylläolevat komennot on suoritettu.
            if (tarkistaKomentojenMaara(komento) > 1) {
                kasitteleKomentoSarja(komento);
            }
        }
    }

    public void kasitteleKomentoSarja(String komento) {
        Dekooderi d = new Dekooderi();
        String[] komentosarja = d.dekoodaa(komento);

        String alku = komentosarja[0];

        if (alku.equals("pelaaja") || alku.equals("p")) {
            //stringsiirto
            if (komentosarja.length > 2) {
                String[] uusiSarja = siirraSarjaa(komentosarja);
                this.ohjelma.lisaaPelaaja(uusiSarja);
            }
        }

        if (alku.equals("monsu") || alku.equals("mobi") || alku.equals("m")) {
            if (komentosarja.length > 3) {
                String[] uusiSarja = siirraSarjaa(komentosarja);
                this.ohjelma.lisaaMonsu(uusiSarja);
            }
        }

        if (alku.equals("lisää")) {
            if (tarkistaKomentojenMaara(komento) > 3) {
                komentoLisaa(komentosarja);
            }
        } // poista lisää-vaihe. se on turha.

        if (alku.equals("poista") || (alku.equals("r"))){
            String nimi = komentosarja[1];
            this.ohjelma.poistaTaistelija(nimi);
        }

        if (alku.equals("muuta")) {
            if (komentosarja[1].equals("inikkaa")) {
                if (tarkistaKomentojenMaara(komento) > 3) {
                    this.ohjelma.muutaInikkaa(komentosarja);
                }
            }
        }
        
        if (alku.equals("i")) {
            if (tarkistaKomentojenMaara(komento) > 2) {
                    this.ohjelma.muutaInikkaa(siirraSarjaa(komentosarja));
                }
        }

        if (alku.equals("vahingoita") || alku.equals("dmg") || alku.equals("d")) {
            if (tarkistaKomentojenMaara(komento) > 2) {
                this.ohjelma.vahingoita(komentosarja);
            }
        }
        
        if (alku.equals("kommentti") || alku.equals("kommentoi") || alku.equals("k")) {
            if (tarkistaKomentojenMaara(komento) > 2 ) {
                this.ohjelma.kommentoi(komentosarja);
            }
        }
        
        if (alku.equals("summa") || alku.equals("s")) {
            this.ohjelma.summain(komentosarja);
        }
        
        if (alku.equals("pyyhi") ||alku.equals("w") ) {
            this.ohjelma.poistaKommentit(komentosarja[1]);
        }
    }

    private void komentoLisaa(String[] komentosarja) {
        if (komentosarja[1].equals("pelaaja") || komentosarja[1].equals("p")) {
            this.ohjelma.lisaaPelaaja(komentosarja);
        }

        if (komentosarja[1].equals("monsu") || komentosarja[1].equals("mobi") || komentosarja[1].equals("m")) {
            if (komentosarja.length > 4) {
                this.ohjelma.lisaaMonsu(komentosarja);
            }
        }
    }

    private void kasitteleKomento_vanha(String komento, TaistelijatLista t) {

        //
        if (komento.equals("ap")) {
            //this.ohjelma.lisaaPelaaja(t);
        }
        //
        if (komento.equals("am")) {
            //this.ohjelma.lisaaMonsu(t, lukija);
        }
        //
        if (komento.equals("cp")) {
            //this.ohjelma.poistaTaistelija(t, lukija);
        }

        if (komento.equals("ci")) {
            //this.ohjelma.muutaInikkaa(t, lukija);
        }

        if (komento.equals("cd")) {
            //this.ohjelma.vahingoita(t, lukija);
        }

        if (komento.equals("ck")) {
            // this.ohjelma.kommentoi(t, lukija);
        }

        if (komento.equals("s")) {
            // this.ohjelma.summain(lukija);
        }



        //ylla vanha tapa, alla uusi
    }

    public int tarkistaKomentojenMaara(String komento) {
        Dekooderi d = new Dekooderi();
        int k = d.laskeKomentojenMaara(komento);
        return k;
    }

    private String[] siirraSarjaa(String[] komentosarja) {
        String[] uusiSarja = new String[komentosarja.length + 1]; // length on 3:lla 3
        uusiSarja[0] = "";

        for (int i = 1; i < komentosarja.length + 1; i++) {
            uusiSarja[i] = komentosarja[i - 1];
        }

        return uusiSarja;
    }
    
    public Ohjelma getOhjelma() {
        return this.ohjelma;
    }
}
