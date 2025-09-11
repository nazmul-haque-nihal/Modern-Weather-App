# 🌤️ Modern Weather App

[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![OpenWeatherMap](https://img.shields.io/badge/OpenWeatherMap-API-orange.svg)](https://openweathermap.org/api)

A feature-rich, modern weather application built with Java that provides real-time weather data, forecasts, and location-based services with an elegant user interface.
<img width="902" height="699" alt="Screenshot_20250911_074014" src="https://github.com/user-attachments/assets/a2c9d114-47f1-4747-91da-dc10bcad79f4" />
![Weather App Demo](https://user-images.githubusercontent.com/placeholder/weather-app-demo.png)

## ✨ Features

### 🎨 Modern UI Design
- **Sleek Interface**: Custom window with rounded corners and modern aesthetics
- **Responsive Layout**: Adapts to different screen sizes
- **Card-Based Design**: Organized information presentation
- **Dark/Light Theme**: Eye-friendly color schemes

### 🌍 Location Services
- **Auto-Detection**: Automatically finds your current location
- **Manual Search**: Search by city name or coordinates
- **Geolocation API**: Uses IP-based location services

### 📊 Comprehensive Weather Data
- **Current Conditions**: Temperature, humidity, wind, pressure
- **"Feels Like" Temperature**: Perceived temperature calculation
- **Sunrise/Sunset Times**: Localized astronomical data
- **5-Day Forecast**: Extended weather predictions
- **Weather Icons**: Visual representation of conditions

### 🔧 Technical Features
- **Real-time API Integration**: OpenWeatherMap data fetching
- **JSON Parsing**: Efficient data processing with Gson
- **Error Handling**: Graceful error management
- **Multi-threading**: Responsive UI with background processing

## 🚀 Getting Started

### Prerequisites
- **Java JDK 17 or higher**
- **Internet Connection**
- **OpenWeatherMap API Key** (Free tier available)

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/nazmul-haque-nihal/modern-weather-app.git
   cd modern-weather-app
   ```
## 🔑 Get Your API Key  

1. **Sign up** at [OpenWeatherMap](https://openweathermap.org/)  
2. Navigate to your **account dashboard**  
3. Copy your **API key**  

### Configure API Key  
Edit the `WeatherService.java` file:  

```java
private static final String API_KEY = "your_api_key_here";
```

---

## 🚀 Running the Application  

### On Linux/MacOS:
```bash
chmod +x run.sh
./run.sh
```

### On Windows:
```bash
# Compile
mkdir target\classes
javac -d target/classes -cp "lib/gson-2.10.1.jar;src/main/java" src/main/java/com/weatherapp/*.java

# Run
java -cp "target/classes;lib/gson-2.10.1.jar" com.weatherapp.WeatherApp
```

---

## 📁 Project Structure  

```
WeatherApp/
├── src/
│   ├── main/
│   │   ├── java/com/weatherapp/
│   │   │   ├── WeatherApp.java        # Main application entry
│   │   │   ├── ModernWeatherGUI.java  # Modern UI implementation
│   │   │   ├── WeatherService.java    # API communication
│   │   │   ├── WeatherData.java       # Current weather model
│   │   │   ├── ForecastData.java      # Forecast data model
│   │   │   └── LocationService.java   # Location detection
│   │   └── resources/
│   │       └── icons/                 # Weather condition icons
├── lib/
│   └── gson-2.10.1.jar                # JSON parsing library
├── run.sh                             # Build and run script
└── README.md
```

---

## 🎯 Usage  

- **Launch the Application**: Run the script or compiled JAR  
- **Auto-Location**: Detects your location automatically  
- **Manual Search**: Enter any city name in the search field  
- **View Data**: Displays current weather + 5-day forecast  
- **Navigation**: Use "My Location" button for quick location switch  

---

## 🛠️ Development  

### Adding New Features
- **Extend WeatherData**: Add new fields for additional metrics  
- **Update UI**: Modify `ModernWeatherGUI` for new components  
- **API Integration**: Enhance `WeatherService` for new endpoints  
- **Testing**: Verify functionality with unit + integration tests  

---

## 🧪 API Endpoints Used  

- **Current Weather**: `https://api.openweathermap.org/data/2.5/weather`  
- **5-Day Forecast**: `https://api.openweathermap.org/data/2.5/forecast`  
- **Geolocation**: `http://ip-api.com/json/`  

---

## 🤝 Contributing  

We welcome contributions! 🚀  

1. **Fork** the repository  
2. **Create a feature branch**  
```bash
git checkout -b feature/AmazingFeature
```
3. **Commit changes**  
```bash
git commit -m 'Add some AmazingFeature'
```
4. **Push to branch**  
```bash
git push origin feature/AmazingFeature
```
5. **Open a Pull Request**  

### Areas for Improvement
- Weather maps visualization  
- Unit conversion (Celsius/Fahrenheit)  
- Severe weather alerts  
- Air quality data  
- Favorites/locations list  
- Offline mode with cached data  

---

## 📄 License  

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.  

---

## 🙏 Acknowledgments  

- **OpenWeatherMap** – Free weather API  
- **Google Gson** – JSON parsing  
- **IP-API** – Geolocation services  
- **All contributors & testers** ❤️  

---

## 🆘 Support  

If you encounter issues:  
- Check the **FAQ**  
- Review error messages in the console  
- Ensure your API key is valid and activated  
- Submit an issue on GitHub  

---

<p align="center">
  Made with ❤️ and ☀️ by Weather Enthusiasts
</p>
