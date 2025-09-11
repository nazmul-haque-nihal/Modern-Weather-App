package com.weatherapp;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherGUI extends JFrame {
    private JTextField cityField;
    private JLabel weatherIcon, temperatureLabel, descriptionLabel, humidityLabel, windLabel;
    private WeatherService weatherService;

    public WeatherGUI() {
        weatherService = new WeatherService();
        initializeComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Weather App");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        cityField = new JTextField(20);
        JButton searchButton = new JButton("Get Weather");
        
        weatherIcon = new JLabel();
        temperatureLabel = new JLabel("--°C", JLabel.CENTER);
        descriptionLabel = new JLabel("Enter a city and click Get Weather", JLabel.CENTER);
        humidityLabel = new JLabel("Humidity: --%");
        windLabel = new JLabel("Wind: -- m/s");
        
        // Style labels
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 36));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        windLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Add action listener to the button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchWeather();
            }
        });
        
        // Add search on Enter key
        cityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchWeather();
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel with button properly added
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("City: "));
        topPanel.add(cityField);
        topPanel.add(new JButton("Get Weather") {{
            addActionListener(e -> fetchWeather());
        }});
        
        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(weatherIcon);
        centerPanel.add(temperatureLabel);
        centerPanel.add(descriptionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(humidityLabel);
        centerPanel.add(windLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
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
        JOptionPane.showMessageDialog(this, "Error fetching weather: " + e.getMessage());
    }
}

    private void updateUI(WeatherData data) {
        temperatureLabel.setText(String.format("%.1f°C", data.getTemperature()));
        descriptionLabel.setText(data.getDescription());
        humidityLabel.setText("Humidity: " + data.getHumidity() + "%");
        windLabel.setText("Wind: " + data.getWindSpeed() + " m/s");
    }
}