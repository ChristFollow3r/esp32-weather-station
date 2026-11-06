# ESP32 Weather Station

Modular ESP32 weather station with a Kotlin/Ktor backend, REST API and web dashboard for live and historical environmental data.

> **Status:** work in progress. This README is a placeholder and will be expanded as the project grows.

## How it works

```
ESP32 + DHT22 --HTTP POST (JSON)--> Ktor server --> database
                                         |
                                  REST API (JSON)
                                    /         \
                              website        Android app (planned)
```

The ESP32 reads its sensors and sends the readings as JSON over WiFi. The server stores them and exposes them through a REST API, which the web dashboard (and, later, a mobile app) consumes.

## Project structure

```
firmware/   ESP32 code (sensor readings and HTTP requests)
server/     Ktor backend and web dashboard
```

## Tech stack

- **Hardware:** ESP32, DHT22 temperature and humidity sensor
- **Backend:** Kotlin, Ktor
- **Frontend:** HTML, JavaScript
- **Planned:** SQLite database, history graphs, more sensors (pressure, light), Android app

## Roadmap

- [ ] ESP32 sends readings to the server
- [ ] Server stores readings in a database
- [ ] Dashboard with current temperature and humidity
- [ ] Weekly and monthly graphs
- [ ] Additional sensors
- [ ] Android app

## Running the server

1. Open the `server/` folder in IntelliJ IDEA
2. Run the application (it starts on `http://localhost:8080`)

## License

MIT, see the [LICENSE](LICENSE) file.
