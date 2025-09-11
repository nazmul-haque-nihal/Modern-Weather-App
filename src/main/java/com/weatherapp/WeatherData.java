package com.weatherapp;

public class WeatherData {
    private String cityName;
    private String country;
    private double temperature;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private String description;
    private String iconCode;
    private int humidity;
    private double windSpeed;
    private int windDirection;
    private int pressure;
    private long sunrise;
    private long sunset;
    private long timezone;

    // Getters and setters
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    
    public double getFeelsLike() { return feelsLike; }
    public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
    
    public double getTempMin() { return tempMin; }
    public void setTempMin(double tempMin) { this.tempMin = tempMin; }
    
    public double getTempMax() { return tempMax; }
    public void setTempMax(double tempMax) { this.tempMax = tempMax; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getIconCode() { return iconCode; }
    public void setIconCode(String iconCode) { this.iconCode = iconCode; }
    
    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    
    public int getWindDirection() { return windDirection; }
    public void setWindDirection(int windDirection) { this.windDirection = windDirection; }
    
    public int getPressure() { return pressure; }
    public void setPressure(int pressure) { this.pressure = pressure; }
    
    public long getSunrise() { return sunrise; }
    public void setSunrise(long sunrise) { this.sunrise = sunrise; }
    
    public long getSunset() { return sunset; }
    public void setSunset(long sunset) { this.sunset = sunset; }
    
    public long getTimezone() { return timezone; }
    public void setTimezone(long timezone) { this.timezone = timezone; }
}