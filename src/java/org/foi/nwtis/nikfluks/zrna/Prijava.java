package org.foi.nwtis.nikfluks.zrna;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.nikfluks.helperi.KorisnikListaj;
import org.foi.nwtis.nikfluks.rest.klijenti.KorisniciRestKlijentKorisnickoIme;

/**
 *
 * @author Nikola
 */
@Named(value = "prijava")
@SessionScoped
public class Prijava implements Serializable {

    String korisnickoIme;
    String lozinka;
    String greska;

    public Prijava() {
    }

    public String prijaviSe() {
        if (korisnickoIme.equals("") || lozinka.equals("")) {
            greska = "Korisničko ime i lozinka ne smiju biti prazni!";
        } else {
            KorisniciRestKlijentKorisnickoIme korisniciRest = new KorisniciRestKlijentKorisnickoIme(korisnickoIme);
            String restOdg = korisniciRest.autenticirajKorisnika(String.class, lozinka);
            if (restOdg.contains("OK")) {
                JsonObject jsonSadrzaj = new JsonParser().parse(restOdg).getAsJsonObject();
                KorisnikListaj k = new Gson().fromJson(jsonSadrzaj.getAsJsonObject("odgovor"), KorisnikListaj.class);
                HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                k.setLozinka(lozinka);
                sesija.setAttribute("korisnik", k);
                greska = "";
                return "pig";
            } else {
                greska = "Pogrešni podaci!";
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

    public String getGreska() {
        return greska;
    }

    public void setGreska(String greska) {
        this.greska = greska;
    }


}
