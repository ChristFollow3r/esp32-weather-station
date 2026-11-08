#include <Arduino.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include "DHT.h"
#include "secrets.h"

#define DHT22_PIN 4
DHT dht22(DHT22_PIN, DHT22);

Credentials credentials;

const std::string WIFI_SSID = credentials.getWifiId();
const std::string WIFI_PASSWORD = credentials.getWifiPassword();
const std::string API_PASSWORD = credentials.getApiPassword();

String PATH_NAME = "http://192.168.68.117:8080/api/report";

void setup() {
  
  Serial.begin(9600);
  WiFi.begin(WIFI_SSID.c_str(), WIFI_PASSWORD.c_str());
  dht22.begin();
  
}

void loop() {
  
  delay(2000);

  float humidity = dht22.readHumidity();
  float cTemperature = dht22.readTemperature();

  if (isnan(humidity) || isnan(cTemperature)) {

    Serial.println("Failed to read from dht22 sensor!\n");
    return;

  }

    String body = "{\"temperature\":" + String(cTemperature, 1) +
                  ",\"humidity\":" + String(humidity, 1) + "}";

    HTTPClient http;
    http.begin(PATH_NAME);
    http.addHeader("Content-Type", "application/json");
    http.addHeader("STATION-API-KEY", API_PASSWORD.c_str());

    int httpCode = http.POST(body);

    if (httpCode > 0) {

        Serial.printf("[HTTP] POST code: %d\n", httpCode);
        if (httpCode >= 200 && httpCode < 300) Serial.println(http.getString());
    
    }  
  
    else Serial.printf("[HTTP] POST failed: %s\n", http.errorToString(httpCode).c_str());


    http.end();
    


}

