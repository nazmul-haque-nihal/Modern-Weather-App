package com.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LocationService {
    private static final String IP_API_URL = "http://ip-api.com/json/";
    
    public String getCurrentLocation() throws Exception {
        try {
            URL url = new URL(IP_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Failed to get location: HTTP " + responseCode);
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            
            // Check if the response has the required fields
            if (jsonObject.has("city") && jsonObject.has("countryCode")) {
                String city = jsonObject.get("city").getAsString();
                String countryCode = jsonObject.get("countryCode").getAsString();
                
                // Remove special characters and spaces
                city = city.replaceAll("[^a-zA-Z0-9\\s\\-]", "").trim();
                
                // If city name is empty or too short, use default
                if (city.isEmpty() || city.length() < 2) {
                    return "London,UK";
                }
                
                return city + "," + countryCode;
            } else {
                // Return a default city if location detection fails
                return "London,UK";
            }
        } catch (Exception e) {
            // Return a default city if any error occurs
            return "London,UK";
        }
    }
}