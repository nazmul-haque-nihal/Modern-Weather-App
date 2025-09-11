package com.weatherapp;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class WeatherApp {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new ModernWeatherGUI());
    }
}