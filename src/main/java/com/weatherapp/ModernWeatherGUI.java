package com.weatherapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModernWeatherGUI extends JFrame {
    private JTextField cityField;
    private JLabel locationLabel, temperatureLabel, descriptionLabel, feelsLikeLabel, 
                   humidityLabel, windLabel, pressureLabel, sunriseLabel, sunsetLabel;
    private JPanel forecastPanel;
    private WeatherService weatherService;
    private LocationService locationService;
    private JButton currentLocationBtn;
    
    // Custom colors
    private Color primaryColor = new Color(66, 133, 244);
    private Color secondaryColor = new Color(232, 240, 254);
    private Color backgroundColor = new Color(245, 247, 250);
    private Color cardColor = Color.WHITE;
    private Color textColor = new Color(51, 51, 51);
    private Color accentColor = new Color(234, 67, 53);

    public ModernWeatherGUI() {
        weatherService = new WeatherService();
        locationService = new LocationService();
        initializeComponents();
        setupLayout();
        setupStyles();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Modern Weather App");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setVisible(true);
        
        // Load current location weather on startup
        loadCurrentLocationWeather();
    }

    private void initializeComponents() {
        cityField = new JTextField(20);
        cityField.setPreferredSize(new Dimension(200, 35));
        
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 35));
        searchButton.setBackground(primaryColor);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        
        currentLocationBtn = new JButton("My Location");
        currentLocationBtn.setPreferredSize(new Dimension(120, 35));
        currentLocationBtn.setBackground(secondaryColor);
        currentLocationBtn.setForeground(primaryColor);
        currentLocationBtn.setFocusPainted(false);
        
        // Weather information labels
        locationLabel = new JLabel("Weather App", JLabel.CENTER);
        temperatureLabel = new JLabel("--°C", JLabel.CENTER);
        descriptionLabel = new JLabel("Enter a city or use My Location", JLabel.CENTER);
        feelsLikeLabel = new JLabel("Feels like: --°C");
        humidityLabel = new JLabel("Humidity: --%");
        windLabel = new JLabel("Wind: -- m/s");
        pressureLabel = new JLabel("Pressure: -- hPa");
        sunriseLabel = new JLabel("Sunrise: --:--");
        sunsetLabel = new JLabel("Sunset: --:--");
        
        forecastPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        forecastPanel.setBackground(backgroundColor);
        forecastPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add action listeners
        searchButton.addActionListener(e -> fetchWeather());
        currentLocationBtn.addActionListener(e -> loadCurrentLocationWeather());
        cityField.addActionListener(e -> fetchWeather());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel with close button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        
        JLabel titleLabel = new JLabel("  Modern Weather App");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JButton closeButton = new JButton("✕");
        closeButton.setPreferredSize(new Dimension(50, 50));
        closeButton.setBackground(primaryColor);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.addActionListener(e -> System.exit(0));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        
        // Top panel with search controls
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        topPanel.setBackground(backgroundColor);
        topPanel.add(cityField);
        topPanel.add(new JButton("Search") {{
            addActionListener(e -> fetchWeather());
            setBackground(primaryColor);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setPreferredSize(new Dimension(100, 35));
        }});
        topPanel.add(currentLocationBtn);
        
        // Main weather panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Current weather card
        JPanel weatherCard = new JPanel();
        weatherCard.setLayout(new BoxLayout(weatherCard, BoxLayout.Y_AXIS));
        weatherCard.setBackground(cardColor);
        weatherCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        locationLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        locationLabel.setForeground(textColor);
        locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        temperatureLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        temperatureLabel.setForeground(textColor);
        temperatureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        descriptionLabel.setForeground(textColor);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(2, 3, 15, 10));
        detailsPanel.setBackground(cardColor);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        detailsPanel.add(feelsLikeLabel);
        detailsPanel.add(humidityLabel);
        detailsPanel.add(windLabel);
        detailsPanel.add(pressureLabel);
        detailsPanel.add(sunriseLabel);
        detailsPanel.add(sunsetLabel);
        
        weatherCard.add(locationLabel);
        weatherCard.add(Box.createRigidArea(new Dimension(0, 10)));
        weatherCard.add(temperatureLabel);
        weatherCard.add(Box.createRigidArea(new Dimension(0, 5)));
        weatherCard.add(descriptionLabel);
        weatherCard.add(Box.createRigidArea(new Dimension(0, 20)));
        weatherCard.add(detailsPanel);
        
        // Forecast section title
        JLabel forecastTitle = new JLabel("5-Day Forecast");
        forecastTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        forecastTitle.setForeground(textColor);
        forecastTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        forecastTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(weatherCard);
        mainPanel.add(forecastTitle);
        mainPanel.add(forecastPanel);
        
        add(headerPanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.SOUTH);
    }

    private void setupStyles() {
        // Style labels
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        feelsLikeLabel.setFont(labelFont);
        humidityLabel.setFont(labelFont);
        windLabel.setFont(labelFont);
        pressureLabel.setFont(labelFont);
        sunriseLabel.setFont(labelFont);
        sunsetLabel.setFont(labelFont);
        
        // Set text colors
        feelsLikeLabel.setForeground(textColor);
        humidityLabel.setForeground(textColor);
        windLabel.setForeground(textColor);
        pressureLabel.setForeground(textColor);
        sunriseLabel.setForeground(textColor);
        sunsetLabel.setForeground(textColor);
    }

    private void fetchWeather() {
    String city = cityField.getText().trim();
    if (city.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a city name");
        return;
    }
    
    try {
        WeatherData data = weatherService.getCurrentWeather(city);
        List<ForecastData> forecast = weatherService.getForecast(city);
        updateUI(data, forecast);
    } catch (Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        if (message.contains("City not found")) {
            JOptionPane.showMessageDialog(this, "City not found. Please check the spelling and try again.");
        } else {
            JOptionPane.showMessageDialog(this, "Error fetching weather: " + message);
        }
    }
}
    
    private void loadCurrentLocationWeather() {
    try {
        String location = locationService.getCurrentLocation();
        cityField.setText(location.split(",")[0]); // Just set the city part
        fetchWeather();
    } catch (Exception e) {
        e.printStackTrace();
        // Set a default city if location detection fails
        cityField.setText("London");
        fetchWeather();
    }
}

    private void updateUI(WeatherData data, List<ForecastData> forecast) {
    locationLabel.setText(data.getCityName() + ", " + data.getCountry());
    temperatureLabel.setText(String.format("%.0f°C", data.getTemperature()));
    descriptionLabel.setText(data.getDescription());
    feelsLikeLabel.setText(String.format("Feels like: %.0f°C", data.getFeelsLike()));
    humidityLabel.setText("Humidity: " + data.getHumidity() + "%");
    windLabel.setText(String.format("Wind: %.1f m/s", data.getWindSpeed()));
    pressureLabel.setText("Pressure: " + data.getPressure() + " hPa");
    
    // Format sunrise and sunset times
    LocalDateTime sunriseTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(data.getSunrise()), 
        ZoneId.ofOffset("UTC", java.time.ZoneOffset.ofTotalSeconds((int)data.getTimezone()))
    );
    LocalDateTime sunsetTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(data.getSunset()), 
        ZoneId.ofOffset("UTC", java.time.ZoneOffset.ofTotalSeconds((int)data.getTimezone()))
    );
    
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    sunriseLabel.setText("Sunrise: " + sunriseTime.format(timeFormatter));
    sunsetLabel.setText("Sunset: " + sunsetTime.format(timeFormatter));
    
    // Update forecast panel
    updateForecastPanel(forecast);
}
    
    private void updateForecastPanel(List<ForecastData> forecast) {
        forecastPanel.removeAll();
        
        for (ForecastData item : forecast) {
            JPanel forecastCard = createForecastCard(item);
            forecastPanel.add(forecastCard);
        }
        
        forecastPanel.revalidate();
        forecastPanel.repaint();
    }
    
    private JPanel createForecastCard(ForecastData data) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        card.setPreferredSize(new Dimension(150, 200));
        
        // Format time
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(data.getTimestamp()), 
            ZoneId.systemDefault()
        );
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        JLabel dayLabel = new JLabel(dateTime.format(dayFormatter), JLabel.CENTER);
        JLabel timeLabel = new JLabel(dateTime.format(timeFormatter), JLabel.CENTER);
        JLabel tempLabel = new JLabel(String.format("%.0f°C", data.getTemperature()), JLabel.CENTER);
        JLabel descLabel = new JLabel(data.getDescription(), JLabel.CENTER);
        
        // Style labels
        dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tempLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        dayLabel.setForeground(textColor);
        timeLabel.setForeground(textColor);
        tempLabel.setForeground(primaryColor);
        descLabel.setForeground(textColor);
        
        // Add components
        card.add(dayLabel);
        card.add(timeLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(tempLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(descLabel);
        
        return card;
    }
}