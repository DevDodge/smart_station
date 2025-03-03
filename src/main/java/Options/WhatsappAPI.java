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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DodgeMasr
 */
public class WhatsappAPI {

    public static Map<String, Object> sendWhatsappMessage(String toNumber, String message) {
        try {
            // URL to send the message
            String urlString = "https://hisocial.in/api/send";

            // Encode message parameter
            String encodedMessage = URLEncoder.encode(message, "UTF-8");

            // Construct the URL with encoded query parameters
            String urlWithParams = String.format("%s?number=%s&type=text&message=%s&instance_id=66829ACE1227E&access_token=6682785198a16",
                    urlString, toNumber, encodedMessage);

            URL url = new URL(urlWithParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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
            resultMap.put("status", jsonResponse.getString("status"));

            JSONObject messageObj = jsonResponse.getJSONObject("message");
            JSONObject keyObj = messageObj.getJSONObject("key");
            resultMap.put("remoteJid", keyObj.getString("remoteJid"));
            resultMap.put("fromMe", keyObj.getBoolean("fromMe"));
            resultMap.put("id", keyObj.getString("id"));

            JSONObject extendedTextMessageObj = messageObj.getJSONObject("message").getJSONObject("extendedTextMessage");
            resultMap.put("text", extendedTextMessageObj.getString("text"));

            resultMap.put("messageTimestamp", messageObj.getString("messageTimestamp"));

            // Disconnect the HttpURLConnection
            conn.disconnect();

            return resultMap;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WhatsappAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WhatsappAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WhatsappAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
