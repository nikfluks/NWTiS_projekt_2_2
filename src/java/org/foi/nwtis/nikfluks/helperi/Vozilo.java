package org.foi.nwtis.nikfluks.helperi;

/**
 *
 * @author Nikola
 */
public class Vozilo {

    String akcija;
    int parkiraliste;//id parkiralista
    String registracija;

    public Vozilo() {
    }

    public Vozilo(String akcija, int parkiraliste, String registracija) {
        this.akcija = akcija;
        this.parkiraliste = parkiraliste;
        this.registracija = registracija;
    }

    public String getAkcija() {
        return akcija;
    }

    public void setAkcija(String akcija) {
        this.akcija = akcija;
    }

    public int getParkiraliste() {
        return parkiraliste;
    }

    public void setParkiraliste(int parkiraliste) {
        this.parkiraliste = parkiraliste;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }


}
