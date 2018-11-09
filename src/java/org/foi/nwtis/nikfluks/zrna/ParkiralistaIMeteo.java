package org.foi.nwtis.nikfluks.zrna;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.nikfluks.helperi.Izbornik;
import org.foi.nwtis.nikfluks.helperi.KorisnikListaj;
import org.foi.nwtis.nikfluks.helperi.Parkiraliste;
import org.foi.nwtis.nikfluks.helperi.ParkiralisteZaRest;
import org.foi.nwtis.nikfluks.helperi.Vozilo;
import org.foi.nwtis.nikfluks.rest.klijenti.ParkiralisteRestKlijent;
import org.foi.nwtis.nikfluks.rest.klijenti.ParkiralisteRestKlijentId;
import org.foi.nwtis.nikfluks.rest.klijenti.ParkiralisteRestKlijentIdPromjeniStatus;
import org.foi.nwtis.nikfluks.rest.klijenti.ParkiralisteRestKlijentIdVozila;
import org.foi.nwtis.nikfluks.rest.klijenti.ParkiralisteRestKlijentIdVratiStatus;
import org.foi.nwtis.nikfluks.soap.klijenti.MeteoPodaci;
import org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoapKlijent;

/**
 *
 * @author Nikola
 */
@Named(value = "pim")
@SessionScoped
public class ParkiralistaIMeteo implements Serializable {

    String korisnickoIme;
    String lozinka;
    int id = 32;
    List<Parkiraliste> svaParkiralista = new ArrayList<>();
    List<String> odabranaParkiralista = new ArrayList<>();
    List<Izbornik> parkiralistaKorisnika = new ArrayList<>();
    List<Vozilo> vozilaParkiralista = new ArrayList<>();
    boolean prikazGumbova = false;
    String greska = "";
    String uspjeh = "";
    String nazivParkiralista;
    String adresaParkiralista;
    String brojParkirnihMjesta;
    int bpm;
    boolean prikazVozila = false;
    boolean prikazGumbaAzuriraj = false;
    Parkiraliste parkZaAzuriranje = null;
    List<MeteoPodaci> listaMeteoPodataka = new ArrayList<>();
    boolean prikazMeteoPodataka = false;

    public ParkiralistaIMeteo() {
    }

    private boolean prijavljenKorisnik() {
        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        KorisnikListaj prijavljen = (KorisnikListaj) sesija.getAttribute("korisnik");
        if (prijavljen != null) {
            korisnickoIme = prijavljen.getKi();
            lozinka = prijavljen.getLozinka();
            return true;
        } else {
            uspjeh = "";
            greska = "Niste prijavljeni!";
            return false;
        }
    }

    public String obrisiParkiraliste() {
        if (prijavljenKorisnik()) {
            ParkiralisteRestKlijentId prkId = new ParkiralisteRestKlijentId(odabranaParkiralista.get(0));
            String json = prkId.obrisiJednoParkiraliste(String.class, korisnickoIme, lozinka);
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
            } else {
                uspjeh = "Parkirašte uspješno obrisano!";
                greska = "";
            }
            dohvatiParkiralista();
        }
        return "";
    }

    public String aktivirajParkiraliste() {
        if (prijavljenKorisnik()) {
            ParkiralisteRestKlijentIdPromjeniStatus prkIdPromjeniStatus
                    = new ParkiralisteRestKlijentIdPromjeniStatus(odabranaParkiralista.get(0));
            String json = prkIdPromjeniStatus.promjeniStatusParkiralista(String.class, korisnickoIme, lozinka, "aktivan");
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
            } else {
                uspjeh = "Parkiralište uspješno aktivirano!";
                greska = "";
            }
        }
        return "";
    }

    public String blokirajParkiraliste() {
        if (prijavljenKorisnik()) {
            ParkiralisteRestKlijentIdPromjeniStatus prkIdPromjeniStatus
                    = new ParkiralisteRestKlijentIdPromjeniStatus(odabranaParkiralista.get(0));
            String json = prkIdPromjeniStatus.promjeniStatusParkiralista(String.class, korisnickoIme, lozinka, "blokiran");
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
            } else {
                uspjeh = "Parkiralište uspješno blokirano!";
                greska = "";
            }
        }
        return "";
    }

    public String pregledStatusaParkiralista() {
        if (prijavljenKorisnik()) {
            ParkiralisteRestKlijentIdVratiStatus prkIdVratiStatus
                    = new ParkiralisteRestKlijentIdVratiStatus(odabranaParkiralista.get(0));
            String json = prkIdVratiStatus.dohvatiStatusParkiralista(String.class, korisnickoIme, lozinka);
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
            } else {
                uspjeh = "Status parkirališta: " + jsonSadrzaj.get("odgovor").getAsString();
                greska = "";
            }
        }
        return "";
    }

    public String pregledVozilaParkiralista() {
        if (prijavljenKorisnik()) {
            vozilaParkiralista.clear();
            ParkiralisteRestKlijentIdVozila prkIdVozila = new ParkiralisteRestKlijentIdVozila(odabranaParkiralista.get(0));
            String json = prkIdVozila.preuzmiSvaVozilaOdabranogParkiralista(String.class, korisnickoIme, lozinka);
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
            } else {
                vozilaParkiralista = new Gson().fromJson(jsonSadrzaj.getAsJsonArray("odgovor"), new TypeToken<List<Vozilo>>() {
                }.getType());
                if (vozilaParkiralista.isEmpty()) {
                    uspjeh = "Parkiralište trenutno nema ni jedno vozilo!";
                } else {
                    uspjeh = "Vozila uspješno dohvaćena!";
                }
                greska = "";
            }
            prikaziVozila();
        }
        return "";
    }

    public String dodajParkiraliste() {
        if (prijavljenKorisnik()) {
            if (provjeriPodatke()) {
                ParkiralisteZaRest park = new ParkiralisteZaRest(nazivParkiralista, adresaParkiralista, bpm);
                ParkiralisteRestKlijent prkDodaj = new ParkiralisteRestKlijent();
                String jsonPark = new Gson().toJson(park);
                String json = prkDodaj.dodajJednoParkiraliste(jsonPark, String.class, korisnickoIme, lozinka);
                JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
                if (jsonSadrzaj.get("poruka") != null) {
                    greska = jsonSadrzaj.get("poruka").getAsString();
                    uspjeh = "";
                } else {
                    uspjeh = "Parkiralište uspješno dodano!";
                    greska = "";
                }
            }
            dohvatiParkiralista();
            isprazniPolja();
        }
        return "";
    }

    public String dohvatiZadnjeMeteoPodatke() {
        if (prijavljenKorisnik()) {
            listaMeteoPodataka.clear();
            MeteoPodaci mp = MeteoSoapKlijent.dajZadnjeMeteoPodatke(korisnickoIme, lozinka, Integer.parseInt(odabranaParkiralista.get(0)));
            if (mp != null) {
                uspjeh = "Meteo podaci uspješno dohvaćeni!";
                greska = "";
                prikazMeteoPodataka = true;
                listaMeteoPodataka.add(mp);

            } else {
                greska = "Greška kod dohvata meteo podataka!";
                uspjeh = "";
                prikazMeteoPodataka = false;
            }
        }
        return "";
    }

    public String dohvatiVazeceMeteoPodatke() {
        if (prijavljenKorisnik()) {
            listaMeteoPodataka.clear();
            MeteoPodaci mp = MeteoSoapKlijent.dajVazeceMeteoPodatke(korisnickoIme, lozinka, Integer.parseInt(odabranaParkiralista.get(0)));
            if (mp != null) {
                uspjeh = "Meteo podaci uspješno dohvaćeni!";
                greska = "";
                prikazMeteoPodataka = true;
                listaMeteoPodataka.add(mp);
            } else {
                greska = "Greška kod dohvata meteo podataka!";
                uspjeh = "";
                prikazMeteoPodataka = false;
            }
        }
        return "";
    }

    public String prebaciZaAzuriranje() {
        if (prijavljenKorisnik()) {
            ParkiralisteRestKlijentId prkId = new ParkiralisteRestKlijentId(odabranaParkiralista.get(0));
            String json = prkId.preuzmiJednoParkiraliste(String.class, korisnickoIme, lozinka);
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
                prikazGumbaAzuriraj = false;
            } else {
                parkZaAzuriranje = new Gson().fromJson(jsonSadrzaj.getAsJsonArray("odgovor").get(0).getAsJsonObject(), Parkiraliste.class);
                nazivParkiralista = parkZaAzuriranje.getNaziv();
                adresaParkiralista = parkZaAzuriranje.getAdresa();
                brojParkirnihMjesta = String.valueOf(parkZaAzuriranje.getBrojParkirnihMjesta());
                prikazGumbaAzuriraj = true;
                uspjeh = "";
                greska = "";
            }
        }
        return "";
    }

    public String azurirajParkiraliste() {
        if (prijavljenKorisnik()) {
            if (provjeriPodatke()) {
                ParkiralisteZaRest park = new ParkiralisteZaRest(nazivParkiralista, adresaParkiralista, bpm);
                ParkiralisteRestKlijentId prkIdAzuriraj = new ParkiralisteRestKlijentId(odabranaParkiralista.get(0));
                String jsonPark = new Gson().toJson(park);
                String json = prkIdAzuriraj.azurirajJednoParkiraliste(jsonPark, String.class, korisnickoIme, lozinka);
                JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
                if (jsonSadrzaj.get("poruka") != null) {
                    greska = jsonSadrzaj.get("poruka").getAsString();
                    uspjeh = "";
                    prikazGumbaAzuriraj = true;
                } else {
                    uspjeh = "Parkiralište uspješno ažurirano!";
                    greska = "";
                    prikazGumbaAzuriraj = false;
                }
            }
            dohvatiParkiralista();
            isprazniPolja();
        }
        return "";
    }

    private void isprazniPolja() {
        nazivParkiralista = "";
        adresaParkiralista = "";
        brojParkirnihMjesta = "";
    }

    @PostConstruct
    private void dohvatiParkiralista() {
        if (prijavljenKorisnik()) {
            svaParkiralista.clear();
            ParkiralisteRestKlijent prk = new ParkiralisteRestKlijent();
            String json = prk.preuzmiSvaParkiralista(String.class, korisnickoIme, lozinka);
            JsonObject jsonSadrzaj = new JsonParser().parse(json).getAsJsonObject();
            if (jsonSadrzaj.get("poruka") != null) {
                greska = jsonSadrzaj.get("poruka").getAsString();
                uspjeh = "";
            } else {
                svaParkiralista = new Gson().fromJson(jsonSadrzaj.getAsJsonArray("odgovor"), new TypeToken<List<Parkiraliste>>() {
                }.getType());
            }
            odrediParkiralistaKorisnika();
        }
    }

    private void odrediParkiralistaKorisnika() {
        parkiralistaKorisnika.clear();
        for (Parkiraliste p : svaParkiralista) {
            if (p.getKorisnikId() == id) {
                Izbornik i = new Izbornik(p.getNaziv(), String.valueOf(p.getId()));
                parkiralistaKorisnika.add(i);
            }
        }
    }

    private boolean provjeriPodatke() {
        if (nazivParkiralista.equals("") || adresaParkiralista.equals("") || brojParkirnihMjesta.equals("")) {
            greska = "Nisu uneseni svi podaci!";
            uspjeh = "";
            return false;
        } else {
            try {
                bpm = Integer.parseInt(brojParkirnihMjesta);
            } catch (NumberFormatException e) {
                greska = "Broj parkirnih mjesta nije (cijeli) broj!";
                uspjeh = "";
                return false;
            }
            if (bpm <= 0) {
                greska = "Broj parkirnih mjesta nije pozitivan broj!";
                uspjeh = "";
                return false;
            }
        }
        return true;
    }

    public void prikaziGumbove() {
        if (odabranaParkiralista.size() == 1) {
            prikazGumbova = true;
        } else {
            prikazGumbova = false;
        }
    }

    public void prikaziVozila() {
        if (vozilaParkiralista.isEmpty()) {
            prikazVozila = false;
        } else {
            prikazVozila = true;
        }
    }

    public List<Parkiraliste> getSvaParkiralista() {
        return svaParkiralista;
    }

    public void setSvaParkiralista(List<Parkiraliste> svaParkiralista) {
        this.svaParkiralista = svaParkiralista;
    }

    public List<String> getOdabranaParkiralista() {
        return odabranaParkiralista;
    }

    public void setOdabranaParkiralista(List<String> odabranaParkiralista) {
        this.odabranaParkiralista = odabranaParkiralista;
    }

    public List<Izbornik> getParkiralistaKorisnika() {

        return parkiralistaKorisnika;
    }

    public void setParkiralistaKorisnika(List<Izbornik> parkiralistaKorisnika) {
        this.parkiralistaKorisnika = parkiralistaKorisnika;
    }

    public boolean isPrikazGumbova() {
        return prikazGumbova;
    }

    public void setPrikazGumbova(boolean prikazGumbova) {
        this.prikazGumbova = prikazGumbova;
    }

    public String getGreska() {
        return greska;
    }

    public void setGreska(String greska) {
        this.greska = greska;
    }

    public String getUspjeh() {
        return uspjeh;
    }

    public void setUspjeh(String uspjeh) {
        this.uspjeh = uspjeh;
    }

    public List<Vozilo> getVozilaParkiralista() {
        return vozilaParkiralista;
    }

    public void setVozilaParkiralista(List<Vozilo> vozilaParkiralista) {
        this.vozilaParkiralista = vozilaParkiralista;
    }

    public String getNazivParkiralista() {
        return nazivParkiralista;
    }

    public void setNazivParkiralista(String nazivParkiralista) {
        this.nazivParkiralista = nazivParkiralista;
    }

    public String getAdresaParkiralista() {
        return adresaParkiralista;
    }

    public void setAdresaParkiralista(String adresaParkiralista) {
        this.adresaParkiralista = adresaParkiralista;
    }

    public String getBrojParkirnihMjesta() {
        return brojParkirnihMjesta;
    }

    public void setBrojParkirnihMjesta(String brojParkirnihMjesta) {
        this.brojParkirnihMjesta = brojParkirnihMjesta;
    }

    public boolean isPrikazVozila() {
        return prikazVozila;
    }

    public void setPrikazVozila(boolean prikazVozila) {
        this.prikazVozila = prikazVozila;
    }

    public boolean isPrikazGumbaAzuriraj() {
        return prikazGumbaAzuriraj;
    }

    public void setPrikazGumbaAzuriraj(boolean prikazGumbaAzuriraj) {
        this.prikazGumbaAzuriraj = prikazGumbaAzuriraj;
    }

    public List<MeteoPodaci> getListaMeteoPodataka() {
        return listaMeteoPodataka;
    }

    public void setListaMeteoPodataka(List<MeteoPodaci> listaMeteoPodataka) {
        this.listaMeteoPodataka = listaMeteoPodataka;
    }

    public boolean isPrikazMeteoPodataka() {
        return prikazMeteoPodataka;
    }

    public void setPrikazMeteoPodataka(boolean prikazMeteoPodataka) {
        this.prikazMeteoPodataka = prikazMeteoPodataka;
    }

}
