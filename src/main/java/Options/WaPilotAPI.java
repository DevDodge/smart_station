package Options;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaPilotAPI {

    public static Map<String, Object> sendWaPilotMessage(String instanceId, String token, String chatId, String textMessage) {
        try {
            // Construct the API URL using the provided instance ID
            String urlString = String.format("https://wapilot.net/api/v1/%s/send-message", instanceId);
            URL url = new URL(urlString);

            // Open HTTP connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); // Specify UTF-8
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Request body parameters
            JSONObject requestBody = new JSONObject();
            requestBody.put("token", token);
            requestBody.put("chat_id", chatId);
            requestBody.put("text", textMessage); // Text with UTF-8 encoding

            // Send POST request
            try (DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream())) {
                dataOutputStream.write(requestBody.toString().getBytes(StandardCharsets.UTF_8)); // Force UTF-8 encoding
                dataOutputStream.flush();
            }

            // Read the response
            int responseCode = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    responseCode < HttpURLConnection.HTTP_BAD_REQUEST
                            ? conn.getInputStream()
                            : conn.getErrorStream(),
                    StandardCharsets.UTF_8 // Read response as UTF-8
            ));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Print the full response body
            System.out.println("Response Body: " + response.toString());

            // Parse response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Extract data into a Map to return
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("status", jsonResponse.optString("status"));
            resultMap.put("message", jsonResponse.optString("message"));

            conn.disconnect();

            return resultMap;

        } catch (IOException ex) {
            Logger.getLogger(WaPilotAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}