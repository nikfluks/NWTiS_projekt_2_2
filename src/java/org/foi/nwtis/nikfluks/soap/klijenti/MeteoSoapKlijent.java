package org.foi.nwtis.nikfluks.soap.klijenti;

/**
 *
 * @author Nikola
 */
public class MeteoSoapKlijent {

    public static MeteoPodaci dajVazeceMeteoPodatke(java.lang.String arg0, java.lang.String arg1, int arg2) {
        org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoap_Service service = new org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoap_Service();
        org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoap port = service.getMeteoSoapPort();
        return port.dajVazeceMeteoPodatke(arg0, arg1, arg2);
    }

    public static MeteoPodaci dajZadnjeMeteoPodatke(java.lang.String arg0, java.lang.String arg1, int arg2) {
        org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoap_Service service = new org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoap_Service();
        org.foi.nwtis.nikfluks.soap.klijenti.MeteoSoap port = service.getMeteoSoapPort();
        return port.dajZadnjeMeteoPodatke(arg0, arg1, arg2);
    }

}
