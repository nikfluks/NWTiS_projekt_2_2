package org.foi.nwtis.nikfluks.helperi;

/**
 *
 * @author Nikola
 */
public class KorisnikListaj {

    private int id;
    private String ki;
    private String prezime;
    private String ime;
    private String lozinka;

    public KorisnikListaj() {
    }

    public KorisnikListaj(String ki, String prezime, String ime, String lozinka) {
        this.ki = ki;
        this.prezime = prezime;
        this.ime = ime;
        this.lozinka = lozinka;
    }

    public KorisnikListaj(int id, String korisnickoIme, String prezime, String ime) {
        this.id = id;
        this.ki = korisnickoIme;
        this.prezime = prezime;
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKi() {
        return ki;
    }

    public void setKi(String ki) {
        this.ki = ki;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

}
