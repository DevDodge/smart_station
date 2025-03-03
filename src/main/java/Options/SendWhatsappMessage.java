/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Options;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DodgeMasr
 */
public class SendWhatsappMessage {

    public static String SendWhatsappMessage(String to, String body, String token) throws UnsupportedEncodingException, MalformedURLException, IOException {
        String baseUrl = "https://api.ultramsg.com/instance92677/messages/chat";

        int priority = 10;
        body = body + "\n\n مع تحيات #دراع_المدرس_اليمين";

        // Encode the body parameter
        String encodedBody = URLEncoder.encode(body, "UTF-8");

        // Construct the URL with parameters
        String urlWithParams = String.format("%s?token=%s&to=%s&body=%s&priority=%d",
                baseUrl, token, to, encodedBody, priority);

        // Create URL object
        URL url = new URL(urlWithParams);

        // Open connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set request properties
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Set request method
        conn.setRequestMethod("GET");

        // Get the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());

        // Extract data into a Map
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sent", jsonResponse.getString("sent"));
        resultMap.put("message", jsonResponse.getString("message"));
        resultMap.put("id", jsonResponse.getInt("id"));

        // Print the extracted data
        System.out.println("Response Map:");
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Disconnect the HttpURLConnection
        conn.disconnect();
        return (String) resultMap.get("sent");
    }

}
