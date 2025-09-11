package com.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    private static final String API_KEY = "68373f0fba059cd10128a896a699bf52"; // Your working API key
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    
    public WeatherData getCurrentWeather(String city) throws Exception {
        // Handle city,country format
        String queryCity = city;
        if (city.contains(",")) {
            String[] parts = city.split(",");
            queryCity = parts[0];
        }
        
        String urlString = BASE_URL + "weather?q=" + queryCity + "&appid=" + API_KEY + "&units=metric";
        System.out.println("Requesting: " + urlString);
        
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        if (responseCode != 200) {
            if (responseCode == 404) {
                throw new Exception("City not found: " + queryCity);
            } else if (responseCode == 401) {
                throw new Exception("Invalid API key");
            } else {
                throw new Exception("HTTP Error: " + responseCode);
            }
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        
        WeatherData weather = new WeatherData();
        weather.setCityName(jsonObject.get("name").getAsString());
        weather.setCountry(jsonObject.getAsJsonObject("sys").get("country").getAsString());
        weather.setTemperature(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
        weather.setFeelsLike(jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble());
        weather.setTempMin(jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble());
        weather.setTempMax(jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble());
        weather.setHumidity(jsonObject.getAsJsonObject("main").get("humidity").getAsInt());
        weather.setPressure(jsonObject.getAsJsonObject("main").get("pressure").getAsInt());
        weather.setWindSpeed(jsonObject.getAsJsonObject("wind").get("speed").getAsDouble());
        if (jsonObject.getAsJsonObject("wind").has("deg")) {
            weather.setWindDirection(jsonObject.getAsJsonObject("wind").get("deg").getAsInt());
        }
        weather.setSunrise(jsonObject.getAsJsonObject("sys").get("sunrise").getAsLong());
        weather.setSunset(jsonObject.getAsJsonObject("sys").get("sunset").getAsLong());
        weather.setTimezone(jsonObject.get("timezone").getAsLong());
        
        JsonObject weatherObj = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        weather.setDescription(capitalize(weatherObj.get("description").getAsString()));
        weather.setIconCode(weatherObj.get("icon").getAsString());
        
        return weather;
    }
    
    public List<ForecastData> getForecast(String city) throws Exception {
        String queryCity = city;
        if (city.contains(",")) {
            String[] parts = city.split(",");
            queryCity = parts[0];
        }
        
        String urlString = BASE_URL + "forecast?q=" + queryCity + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(urlString);
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Forecast HTTP Error: " + responseCode);
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray list = jsonObject.getAsJsonArray("list");
        
        List<ForecastData> forecastList = new ArrayList<>();
        // Get forecasts for next 5 entries (every 3 hours)
        for (int i = 0; i < Math.min(5, list.size()); i++) {
            JsonObject item = list.get(i).getAsJsonObject();
            ForecastData forecast = new ForecastData();
            forecast.setTimestamp(item.get("dt").getAsLong());
            forecast.setTemperature(item.getAsJsonObject("main").get("temp").getAsDouble());
            forecast.setHumidity(item.getAsJsonObject("main").get("humidity").getAsInt());
            forecast.setWindSpeed(item.getAsJsonObject("wind").get("speed").getAsDouble());
            
            JsonObject weatherObj = item.getAsJsonArray("weather").get(0).getAsJsonObject();
            forecast.setDescription(capitalize(weatherObj.get("description").getAsString()));
            forecast.setIconCode(weatherObj.get("icon").getAsString());
            
            forecastList.add(forecast);
        }
        
        return forecastList;
    }
    
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}