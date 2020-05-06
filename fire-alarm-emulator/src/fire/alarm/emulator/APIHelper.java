package fire.alarm.emulator;

import javax.json.JsonObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHelper {

    /**
     * Perform a get requets
     *
     * @param url url of the request
     * @return string representation of the response
     * @throws IOException
     */
    public static StringBuffer get(String url) throws IOException {
        URL getUrl = new URL(url);

        HttpURLConnection con = (HttpURLConnection) getUrl.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        String readLine = null;

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            return response;
        }
        return null;
    }

    /**
     * Performs a patch request
     *
     * @param url url of the request
     * @param params parameter data to send with the request
     * @return a string representation of the response
     * @throws IOException
     */
    public static StringBuffer patch(String url, JsonObject params) throws IOException {
        String parsedParams = JsonHelper.parseJsonToString(params);
        URL postUrl = new URL(url);

        HttpURLConnection con = (HttpURLConnection) postUrl.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("X-HTTP-Method-Override", "PATCH");

        return fetchResponse(con, parsedParams);

    }

    /**
     * builds a StringBuffer with the response stream
     *
     * @param stream an input stream
     * @return a StringBuffer filled with the stream data
     * @throws IOException
     */
    private static StringBuffer getResponseFromStream(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    /**
     * Extract response from the connection
     *
     * @param con
     * @param params
     * @return
     * @throws IOException
     */
    private static StringBuffer fetchResponse(HttpURLConnection con, String params) throws IOException {
        con.setRequestProperty("Content-type", "application/json");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return getResponseFromStream(con.getInputStream());
        }
        return null;
    }

}
