package org.foi.nwtis.nikfluks.zrna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.nikfluks.helperi.KorisnikListaj;
import org.foi.nwtis.nikfluks.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.nikfluks.slusaci.SlusacAplikacije;

/**
 *
 * @author Nikola
 */
@Named(value = "pig")
@SessionScoped
public class PosluziteljIGrupa implements Serializable {

    String komandaZaSlanje;
    String adresaPosluzitelja;
    int portPosluzitelja;
    String komanda;
    String korisnickoIme;
    String lozinka;
    String greskaPosluzitelj = "";
    String uspjehPosluzitelj = "";
    String greskaGrupa = "";
    String uspjehGrupa = "";

    public PosluziteljIGrupa() {
    }

    private boolean dohvatiPodatkeIzKonfiguracije() {
        try {
            BP_Konfiguracija bpk = (BP_Konfiguracija) SlusacAplikacije.getServletContext().getAttribute("BP_Konfig");
            portPosluzitelja = Integer.parseInt(bpk.getPortServera_());
            adresaPosluzitelja = bpk.getAdresaServera_();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean prijavljenKorisnik() {
        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        KorisnikListaj prijavljen = (KorisnikListaj) sesija.getAttribute("korisnik");
        if (prijavljen != null) {
            korisnickoIme = prijavljen.getKi();
            lozinka = prijavljen.getLozinka();
            return true;
        } else {
            uspjehGrupa = "";
            greskaGrupa = "Niste prijavljeni!";
            return false;
        }
    }

    public String dohvatiStanjePosluzitelja() {
        if (prijavljenKorisnik()) {
            komanda = "STANJE";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; STANJE;";
            posaljiKomandu();
        }
        return "";
    }

    public String pauzirajPosluzitelja() {
        if (prijavljenKorisnik()) {
            komanda = "PAUZA";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; PAUZA;";
            posaljiKomandu();
        }
        return "";
    }

    public String pokreniPosluzitelja() {
        if (prijavljenKorisnik()) {
            komanda = "KRENI";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; KRENI;";
            posaljiKomandu();
        }
        return "";
    }

    public String pasivirajPosluzitelja() {
        if (prijavljenKorisnik()) {
            komanda = "PASIVNO";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; PASIVNO;";
            posaljiKomandu();
        }
        return "";
    }

    public String aktivirajPosluzitelja() {
        if (prijavljenKorisnik()) {
            komanda = "AKTIVNO";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; AKTIVNO;";
            posaljiKomandu();
        }
        return "";
    }

    public String zaustaviPosluzitelja() {
        if (prijavljenKorisnik()) {
            komanda = "STANI";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; STANI;";
            posaljiKomandu();
        }
        return "";
    }

    public String dohvatiStanjeGrupe() {
        if (prijavljenKorisnik()) {
            komanda = "GRUPA STANJE";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; GRUPA STANJE;";
            posaljiKomandu();
        }
        return "";
    }

    public String registrirajGrupu() {
        if (prijavljenKorisnik()) {
            komanda = "GRUPA DODAJ";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; GRUPA DODAJ;";
            posaljiKomandu();
        }
        return "";
    }

    public String deregistrirajGrupu() {
        if (prijavljenKorisnik()) {
            komanda = "GRUPA PREKID";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; GRUPA PREKID;";
            posaljiKomandu();
        }
        return "";
    }

    public String aktivirajGrupu() {
        if (prijavljenKorisnik()) {
            komanda = "GRUPA KRENI";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; GRUPA KRENI;";
            posaljiKomandu();
        }
        return "";
    }

    public String blokirajGrupu() {
        if (prijavljenKorisnik()) {
            komanda = "GRUPA PAUZA";
            komandaZaSlanje = "KORISNIK " + korisnickoIme + "; LOZINKA " + lozinka + "; GRUPA PAUZA;";
            posaljiKomandu();
        }
        return "";
    }

    private void posaljiKomandu() {
        System.out.println("komandaZaSlanje: " + komandaZaSlanje);
        greskaPosluzitelj = "";
        uspjehPosluzitelj = "";
        greskaGrupa = "";
        uspjehGrupa = "";
        try {
            if (dohvatiPodatkeIzKonfiguracije()) {
                Socket socket = new Socket(adresaPosluzitelja, portPosluzitelja);
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                os.write(komandaZaSlanje.getBytes());
                os.flush();
                socket.shutdownOutput();

                StringBuilder odgovor = new StringBuilder();
                int znak;
                BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8.displayName()));
                while ((znak = in.read()) != -1) {
                    odgovor.append((char) znak);
                }
                System.out.println("Odgovor: " + odgovor);
                in.close();
                is.close();
                analizirajOdgovor(odgovor.toString());
            } else {
                System.out.println("Greška kod dohvaćanja podataka iz konf dat!");
            }
        } catch (IOException ex) {
            greskaPosluzitelj = "Pogreška kod spajanja na poslužitelj!";
            uspjehPosluzitelj = "";
            System.err.println("Greska kod slanja komande kroz socket: " + ex.getLocalizedMessage());
        }
    }

    private void analizirajOdgovor(String odgovor) {
        if (komanda.equals("STANJE")) {
            obradiStanje(odgovor);
        } else if (komanda.equals("PAUZA")) {
            obradiPauzu(odgovor);
        } else if (komanda.equals("KRENI")) {
            obradiKreni(odgovor);
        } else if (komanda.equals("PASIVNO")) {
            obradiPasivno(odgovor);
        } else if (komanda.equals("AKTIVNO")) {
            obradiAktivno(odgovor);
        } else if (komanda.equals("STANI")) {
            obradiStani(odgovor);
        } else if (komanda.equals("GRUPA STANJE")) {
            obradiGrupaStanje(odgovor);
        } else if (komanda.equals("GRUPA DODAJ")) {
            obradiGrupaDodaj(odgovor);
        } else if (komanda.equals("GRUPA PREKID")) {
            obradiGrupaPrekid(odgovor);
        } else if (komanda.equals("GRUPA KRENI")) {
            obradiGrupaKreni(odgovor);
        } else if (komanda.equals("GRUPA PAUZA")) {
            obradiGrupaPauza(odgovor);
        }
    }

    private void obradiStanje(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaPosluzitelj = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("OK 11")) {
            uspjehPosluzitelj = "Poslužitelj je pokrenut i aktivan";
        } else if (odgovor.contains("OK 12")) {
            uspjehPosluzitelj = "Poslužitelj je pokrenut i pasivan";
        } else if (odgovor.contains("OK 13")) {
            uspjehPosluzitelj = "Poslužitelj je pauziran i aktivan";
        } else if (odgovor.contains("OK 14")) {
            uspjehPosluzitelj = "Poslužitelj je pauziran i pasivan";
        }
    }

    private void obradiPauzu(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaPosluzitelj = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 12")) {
            greskaPosluzitelj = "Poslužitelj je već pauziran!";
        } else if (odgovor.contains("OK 10")) {
            uspjehPosluzitelj = "Poslužitelj uspješno pauziran!";
        }
    }

    private void obradiKreni(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaPosluzitelj = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 13")) {
            greskaPosluzitelj = "Poslužitelj je već pokrenut!";
        } else if (odgovor.contains("OK 10")) {
            uspjehPosluzitelj = "Poslužitelj uspješno pokrenut!";
        }
    }

    private void obradiPasivno(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaPosluzitelj = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 14")) {
            greskaPosluzitelj = "Poslužitelj je već pasivan!";
        } else if (odgovor.contains("OK 10")) {
            uspjehPosluzitelj = "Poslužitelj uspješno pasiviran!";
        }
    }

    private void obradiAktivno(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaPosluzitelj = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 1")) {
            greskaPosluzitelj = "Poslužitelj je već aktivan!";
        } else if (odgovor.contains("OK 10")) {
            uspjehPosluzitelj = "Poslužitelj uspješno aktiviran!";
        }
    }

    private void obradiStani(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaPosluzitelj = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 16")) {
            greskaPosluzitelj = "Poslužitelj je već zaustavljen!";
        } else if (odgovor.contains("OK 10")) {
            uspjehPosluzitelj = "Poslužitelj uspješno zaustavljen!";
        }
    }

    private void obradiGrupaStanje(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaGrupa = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 12")) {
            greskaGrupa = "Poslužitelj je pauziran, nisu dopuštene komande za grupu!";
        } else if (odgovor.contains("ERR 21")) {
            greskaGrupa = "Grupa ne postoji!";
        } else if (odgovor.contains("ERR 22")) {
            greskaGrupa = "Grupa je deregistrirana";
        } else if (odgovor.contains("ERR 23")) {
            greskaGrupa = "Grupa je pasivna";
        } else if (odgovor.contains("ERR 24")) {
            greskaGrupa = "Grupa je neaktivna";
        } else if (odgovor.contains("OK 21")) {
            uspjehGrupa = "Grupa je aktivna";
        } else if (odgovor.contains("OK 22")) {
            uspjehGrupa = "Grupa je blokirana";
        } else if (odgovor.contains("OK 23")) {
            uspjehGrupa = "Grupa je registrirana";
        }
    }

    private void obradiGrupaDodaj(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaGrupa = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 12")) {
            greskaGrupa = "Poslužitelj je pauziran, nisu dopuštene komande za grupu!";
        } else if (odgovor.contains("ERR 20")) {
            greskaGrupa = "Greška kod registriranja grupe!";
        } else if (odgovor.contains("OK 20")) {
            uspjehGrupa = "Grupa uspješno registrirana!";
        }
    }

    private void obradiGrupaPrekid(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaGrupa = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 12")) {
            greskaGrupa = "Poslužitelj je pauziran, nisu dopuštene komande za grupu!";
        } else if (odgovor.contains("ERR 21")) {
            greskaGrupa = "Greška kod deregistriranja grupe!";
        } else if (odgovor.contains("OK 20")) {
            uspjehGrupa = "Grupa uspješno deregistrirana!";
        }
    }

    private void obradiGrupaKreni(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaGrupa = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 12")) {
            greskaGrupa = "Poslužitelj je pauziran, nisu dopuštene komande za grupu!";
        } else if (odgovor.contains("ERR")) {
            greskaGrupa = "Greška kod aktiviranja grupe!";
        } else if (odgovor.contains("OK 20")) {
            uspjehGrupa = "Grupa uspješno aktivirana!";
        }
    }

    private void obradiGrupaPauza(String odgovor) {
        if (odgovor.contains("ERR 11")) {
            greskaGrupa = "Pogrešno korisničko ime ili lozinka!";
        } else if (odgovor.contains("ERR 12")) {
            greskaGrupa = "Poslužitelj je pauziran, nisu dopuštene komande za grupu!";
        } else if (odgovor.contains("ERR")) {
            greskaGrupa = "Greška kod blokiranja grupe!";
        } else if (odgovor.contains("OK 20")) {
            uspjehGrupa = "Grupa uspješno blokirana!";
        }
    }

    public String getGreskaPosluzitelj() {
        return greskaPosluzitelj;
    }

    public void setGreskaPosluzitelj(String greskaPosluzitelj) {
        this.greskaPosluzitelj = greskaPosluzitelj;
    }

    public String getUspjehPosluzitelj() {
        return uspjehPosluzitelj;
    }

    public void setUspjehPosluzitelj(String uspjehPosluzitelj) {
        this.uspjehPosluzitelj = uspjehPosluzitelj;
    }

    public String getGreskaGrupa() {
        return greskaGrupa;
    }

    public void setGreskaGrupa(String greskaGrupa) {
        this.greskaGrupa = greskaGrupa;
    }

    public String getUspjehGrupa() {
        return uspjehGrupa;
    }

    public void setUspjehGrupa(String uspjehGrupa) {
        this.uspjehGrupa = uspjehGrupa;
    }

}
