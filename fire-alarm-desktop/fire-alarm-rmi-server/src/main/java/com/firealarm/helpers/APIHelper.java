package com.firealarm.helpers;

import javax.json.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHelper {

    /**
     * Perform a get requets
     * @param url url of the request
     * @return string representation of the response
     * @throws IOException
     */
    public static StringBuffer get(String url) throws IOException {
        // build url
        URL getUrl = new URL(url);

        // build connection
        HttpURLConnection con = (HttpURLConnection) getUrl.openConnection();
        // set method
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        String readLine = null;
        // read the response
        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
            StringBuffer response = new StringBuffer();
            while((readLine = in.readLine()) != null){
                response.append(readLine);
            }
            in.close();
            return response;
        }
        return null;
    }

    /**
     * Perform a post request
     * @param url url of the request
     * @param params parameter data to send with the request
     * @param token An authentication token, this will be added to the header of
     *              the request
     * @return
     * @throws IOException
     */
    public static StringBuffer post(String url, JsonObject params, String token) throws IOException {
        // get param string
        String parsedParams = JsonHelper.parseJsonToString(params);
        // parse url
        URL postUrl = new URL(url);

        // build connection
        HttpURLConnection con  = (HttpURLConnection)postUrl.openConnection();

        con.setRequestMethod("POST");
        // set token if there is any
        if(token != null){
            con.setRequestProperty("Authorization","Bearer " + token);
        }

        return fetchResponse(con, parsedParams);

    }


    /**
     * Performs a put request
     * @param url url of the request
     * @param params parameter data to send with the request
     * @param token an authentication token
     * @return a string representation of the response
     * @throws IOException
     */
    public static StringBuffer put(String url, JsonObject params, String token) throws IOException {
        String parsedParams = JsonHelper.parseJsonToString(params);
        URL postUrl = new URL(url);

        HttpURLConnection con  = (HttpURLConnection)postUrl.openConnection();

        con.setRequestMethod("PUT");

        if(token != null){
            con.setRequestProperty("Authorization","Bearer " + token);
        }

        return fetchResponse(con, parsedParams);

    }

    /**
     * performs a delete request
     * @param url url of the request
     * @param params parameters to send with the request
     * @param token an authentication token
     * @return a string representation of the response
     * @throws IOException
     */
    public static StringBuffer delete(String url, JsonObject params, String token) throws IOException {
        String parsedParams = JsonHelper.parseJsonToString(params);
        URL postUrl = new URL(url);

        HttpURLConnection con  = (HttpURLConnection)postUrl.openConnection();

        con.setRequestMethod("DELETE");

        if(token != null){
            con.setRequestProperty("Authorization","Bearer " + token);
        }

        return fetchResponse(con, parsedParams);

    }

    /**
     * builds a StringBuffer with the response stream
     * @param stream an input stream
     * @return a StringBuffer filled with the stream data
     * @throws IOException
     */
    private static StringBuffer getResponseFromStream(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        // read the stream
        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    /**
     * Extract response from the connection
     * @param con
     * @param params
     * @return
     * @throws IOException
     */
    private static StringBuffer fetchResponse(HttpURLConnection con, String params) throws IOException {
        con.setRequestProperty("Content-type", "application/json");

        // read from the stream
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return getResponseFromStream(con.getInputStream());
        }
        return null;
    }


}

















