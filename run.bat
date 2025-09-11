@echo off
echo Compiling Java files...
mkdir target\classes 2>nul
javac -d target/classes -cp "lib/gson-2.10.1.jar;src/main/java" src/main/java/com/weatherapp/*.java

if %errorlevel% == 0 (
    echo Starting Weather App...
    java -cp "target/classes;lib/gson-2.10.1.jar" com.weatherapp.WeatherApp
) else (
    echo Compilation failed!
    pause
)