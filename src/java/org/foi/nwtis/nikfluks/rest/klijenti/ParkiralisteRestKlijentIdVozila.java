/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.nikfluks.rest.klijenti;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

/**
 * Jersey REST client generated for REST resource:we [parkiralista/{id}/vozila/]<br>
 * USAGE:
 * <pre>
 *        ParkiralisteRestKlijentIdVozila client = new ParkiralisteRestKlijentIdVozila();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Nikola
 */
public class ParkiralisteRestKlijentIdVozila {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8084/nikfluks_aplikacija_1/webresources/";

    public ParkiralisteRestKlijentIdVozila(String id) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        String resourcePath = java.text.MessageFormat.format("parkiralista/{0}/vozila", new Object[]{id});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    public void setResourcePath(String id) {
        String resourcePath = java.text.MessageFormat.format("parkiralista/{0}/vozila", new Object[]{id});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    /**
     * @param responseType Class representing the response
     * @param korisnickoIme query parameter
     * @param lozinka query parameter
     * @return response object (instance of responseType class)
     */
    public <T> T preuzmiSvaVozilaOdabranogParkiralista(Class<T> responseType, String korisnickoIme, String lozinka) throws ClientErrorException {
        String[] queryParamNames = new String[]{"korisnickoIme", "lozinka"};
        String[] queryParamValues = new String[]{korisnickoIme, lozinka};
        ;
        javax.ws.rs.core.Form form = getQueryOrFormParams(queryParamNames, queryParamValues);
        javax.ws.rs.core.MultivaluedMap<String, String> map = form.asMap();
        for (java.util.Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
            java.util.List<String> list = entry.getValue();
            String[] values = list.toArray(new String[list.size()]);
            webTarget = webTarget.queryParam(entry.getKey(), (Object[]) values);
        }
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    private Form getQueryOrFormParams(String[] paramNames, String[] paramValues) {
        Form form = new javax.ws.rs.core.Form();
        for (int i = 0; i < paramNames.length; i++) {
            if (paramValues[i] != null) {
                form = form.param(paramNames[i], paramValues[i]);
            }
        }
        return form;
    }

    public void close() {
        client.close();
    }

}
