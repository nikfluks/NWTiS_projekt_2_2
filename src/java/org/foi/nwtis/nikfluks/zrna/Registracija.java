package org.foi.nwtis.nikfluks.zrna;

import com.google.gson.Gson;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.foi.nwtis.nikfluks.helperi.KorisnikListaj;
import org.foi.nwtis.nikfluks.rest.klijenti.KorisniciRestKlijentKorisnickoIme;

/**
 *
 * @author Nikola
 */
@Named(value = "registracija")
@SessionScoped
public class Registracija implements Serializable {

    String korisnickoIme;
    String lozinka;
    String lozinkaPon;
    String greska;
    String ime;
    String prezime;
    String uspjeh;

    public Registracija() {
    }

    public String registrirajSe() {
        if (korisnickoIme.equals("") || lozinka.equals("") || lozinkaPon.equals("") || ime.equals("") || prezime.equals("")) {
            greska = "Podaci ne smiju biti prazni!";
        } else {
            if (lozinka.equals(lozinkaPon)) {
                KorisniciRestKlijentKorisnickoIme korisniciRest = new KorisniciRestKlijentKorisnickoIme(korisnickoIme);
                KorisnikListaj k = new KorisnikListaj(korisnickoIme, prezime, ime, lozinka);
                String korisnikJson = new Gson().toJson(k);
                String restOdg = korisniciRest.dodajJednogKorisnika(korisnikJson, String.class, lozinka);
                if (restOdg.contains("OK")) {
                    greska = "";
                    return "prijava";
                } else {
                    greska = "Registracija nije uspjela!";
                }
            } else {
                greska = "Lozinke nisu iste!";
            }
        }
        return "";
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getLozinkaPon() {
        return lozinkaPon;
    }

    public void setLozinkaPon(String lozinkaPon) {
        this.lozinkaPon = lozinkaPon;
    }

    public String getGreska() {
        return greska;
    }

    public void setGreska(String greska) {
        this.greska = greska;
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

    public String getUspjeh() {
        return uspjeh;
    }

    public void setUspjeh(String uspjeh) {
        this.uspjeh = uspjeh;
    }


}
