package com.firealarm.helpers;

import javax.json.JsonObject;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

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


    public static StringBuffer patch(String url, JsonObject params, String token) throws IOException {
        String parsedParams = JsonHelper.parseJsonToString(params);
        URL postUrl = new URL(url);

        HttpURLConnection con  = (HttpURLConnection)postUrl.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("X-HTTP-Method-Override", "PATCH");

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

    public static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}

















