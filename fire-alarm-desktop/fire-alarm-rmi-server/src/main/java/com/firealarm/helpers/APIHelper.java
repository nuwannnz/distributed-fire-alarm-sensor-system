package com.firealarm.helpers;

import javax.json.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHelper {

    public static StringBuffer get(String url) throws IOException {
        URL getUrl = new URL(url);

        HttpURLConnection con = (HttpURLConnection) getUrl.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        String readLine = null;

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

    public static StringBuffer post(String url, JsonObject params, String token) throws IOException {
        String parsedParams = JsonHelper.parseJsonToString(params);
        URL postUrl = new URL(url);

        HttpURLConnection con  = (HttpURLConnection)postUrl.openConnection();

        con.setRequestMethod("POST");

        if(token != null){
            con.setRequestProperty("Authorization","Bearer " + token);
        }

        return fetchResponse(con, parsedParams);

    }


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

    private static StringBuffer getResponseFromStream(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private static StringBuffer fetchResponse(HttpURLConnection con, String params) throws IOException {
        con.setRequestProperty("Content-type", "application/json");


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

















