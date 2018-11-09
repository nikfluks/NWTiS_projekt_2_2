package org.foi.nwtis.nikfluks.helperi;

/**
 *
 * @author Nikola
 */
public class ParkiralisteZaRest {
    String naziv;
    String adresa;
    int brojParkirnihMjesta;

    public ParkiralisteZaRest() {
    }

    public ParkiralisteZaRest(String naziv, String adresa, int brojParkirnihMjesta) {
        this.naziv = naziv;
        this.adresa = adresa;
        this.brojParkirnihMjesta = brojParkirnihMjesta;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getBrojParkirnihMjesta() {
        return brojParkirnihMjesta;
    }

    public void setBrojParkirnihMjesta(int brojParkirnihMjesta) {
        this.brojParkirnihMjesta = brojParkirnihMjesta;
    }

}
