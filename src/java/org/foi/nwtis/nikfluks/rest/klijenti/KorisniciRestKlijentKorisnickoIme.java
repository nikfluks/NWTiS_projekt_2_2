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
 * Jersey REST client generated for REST resource:we [korisnici/{korisnickoIme}]<br>
 * USAGE:
 * <pre>
 *        KorisniciRestKlijentKorisnickoIme client = new KorisniciRestKlijentKorisnickoIme();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Nikola
 */
public class KorisniciRestKlijentKorisnickoIme {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/nikfluks_aplikacija_3_1/webresources/";

    public KorisniciRestKlijentKorisnickoIme(String korisnickoIme) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        String resourcePath = java.text.MessageFormat.format("korisnici/{0}", new Object[]{korisnickoIme});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    public void setResourcePath(String korisnickoIme) {
        String resourcePath = java.text.MessageFormat.format("korisnici/{0}", new Object[]{korisnickoIme});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    /**
     * @param responseType Class representing the response
     * @param lozinka query parameter
     * @return response object (instance of responseType class)
     */
    public <T> T autenticirajKorisnika(Class<T> responseType, String lozinka) throws ClientErrorException {
        String[] queryParamNames = new String[]{"lozinka"};
        String[] queryParamValues = new String[]{lozinka};
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

    /**
     * @param responseType Class representing the response
     * @param korIme query parameter
     * @param lozinka query parameter
     * @return response object (instance of responseType class)
     */
    public <T> T preuzmiJednogKorisnika(Class<T> responseType, String korIme, String lozinka) throws ClientErrorException {
        String[] queryParamNames = new String[]{"korIme", "lozinka"};
        String[] queryParamValues = new String[]{korIme, lozinka};
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

    /**
     * @param responseType Class representing the response
     * @param lozinka query parameter
     * @param requestEntity request data@return response object (instance of responseType class)
     */
    public <T> T dodajJednogKorisnika(Object requestEntity, Class<T> responseType, String lozinka) throws ClientErrorException {
        String[] queryParamNames = new String[]{"lozinka"};
        String[] queryParamValues = new String[]{lozinka};
        ;
        javax.ws.rs.core.Form form = getQueryOrFormParams(queryParamNames, queryParamValues);
        javax.ws.rs.core.MultivaluedMap<String, String> map = form.asMap();
        for (java.util.Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
            java.util.List<String> list = entry.getValue();
            String[] values = list.toArray(new String[list.size()]);
            webTarget = webTarget.queryParam(entry.getKey(), (Object[]) values);
        }
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
    }

    /**
     * @param responseType Class representing the response
     * @param lozinka query parameter
     * @param requestEntity request data@return response object (instance of responseType class)
     */
    public <T> T azurirajJednogKorisnika(Object requestEntity, Class<T> responseType, String lozinka) throws ClientErrorException {
        String[] queryParamNames = new String[]{"lozinka"};
        String[] queryParamValues = new String[]{lozinka};
        ;
        javax.ws.rs.core.Form form = getQueryOrFormParams(queryParamNames, queryParamValues);
        javax.ws.rs.core.MultivaluedMap<String, String> map = form.asMap();
        for (java.util.Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
            java.util.List<String> list = entry.getValue();
            String[] values = list.toArray(new String[list.size()]);
            webTarget = webTarget.queryParam(entry.getKey(), (Object[]) values);
        }
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
    }

    /**
     * @param responseType Class representing the response
     * @return response object (instance of responseType class)
     */
    public <T> T brisi(Class<T> responseType) throws ClientErrorException {
        return webTarget.request().delete(responseType);
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
