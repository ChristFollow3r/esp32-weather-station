#include "DHT.h"
#define DHT22_PIN 4

DHT dht22(DHT22_PIN, DHT22);

void setup() {
  Serial.begin(9600);
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

  Serial.println("Temperature: ");
    Serial.println(cTemperature);
    Serial.println("Humidity: ");
    Serial.println(humidity);

}
