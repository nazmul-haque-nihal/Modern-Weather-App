#!/bin/bash

# Download Gson if not present
if [ ! -f "lib/gson-2.10.1.jar" ]; then
    echo "Downloading Gson library..."
    mkdir -p lib
    wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar -O lib/gson-2.10.1.jar
fi

# Create icons if they don't exist
if [ ! -d "src/main/resources/icons" ]; then
    echo "Creating icons directory..."
    mkdir -p src/main/resources/icons
fi

# Compile
echo "Compiling Java files..."
mkdir -p target/classes
javac -d target/classes -cp "lib/gson-2.10.1.jar:src/main/java" src/main/java/com/weatherapp/*.java

# Copy resources
echo "Copying resources..."
cp -r src/main/resources/icons target/classes/ 2>/dev/null || echo "Icons copied"

if [ $? -eq 0 ]; then
    echo "Starting Modern Weather App..."
    java -cp "target/classes:lib/gson-2.10.1.jar" com.weatherapp.WeatherApp
else
    echo "Compilation failed!"
fi