# Weather Forecast Service

## Description
The Weather Forecast Service is a Spring Boot application that provides weather data for a given city using two external APIs: WeatherStack and OpenWeatherMap. The application fetches temperature and wind speed information from these APIs and returns them in a simplified format.

## Features
- Fetch weather data from WeatherStack API.
- Fetch weather data from OpenWeatherMap API.
- Configurable API keys and URLs via environment variables or default values.

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- IntelliJ IDEA (optional, for development)

## Dependencies
The application uses the following dependencies:
- Spring Boot 3.x
- Spring Web
- Spring Boot Test
- Maven for dependency management

## Environment Variables
The application requires the following environment variables to be set for API keys and URLs. Default values are provided in the `application.properties` file.

| Environment Variable         | Default Value                                   | Description                          |
|------------------------------|------------------------------------------------|--------------------------------------|
| `WEATHERSTACK_API_KEY`       | `b63b31c4a9fab4f4937eb17cb37de442`             | WeatherStack API key                 |
| `WEATHERSTACK_API_URL`       | `http://api.weatherstack.com/current`          | WeatherStack API URL                 |
| `OPENWEATHERMAP_API_KEY`     | `87a976898f751acaf5f5a710d8005768`             | OpenWeatherMap API key               |
| `OPENWEATHERMAP_API_URL`     | `http://api.openweathermap.org/data/2.5/weather` | OpenWeatherMap API URL               |

## How to Compile and Run

### Step 1: Clone the Repository
git clone https://github.com/your-username/weather-forecast-service.git
cd weather-forecast-service

### Step 2: Set Environment Variables
Set the required environment variables in your system. For example:
```bash
export WEATHERSTACK_API_KEY=your_weatherstack_api_key
export OPENWEATHERMAP_API_KEY=your_openweathermap_api_key
```

Alternatively, you can use the default values provided in the `application.properties` file.

### Step 3: Build the Application
Use Maven to build the application:
```bash
mvn clean install
```

### Step 4: Run the Application
Run the application using Maven or the compiled JAR file:
```bash
mvn spring-boot:run
```
Or:
```bash
java -jar target/weather-forecast-service-0.0.1-SNAPSHOT.jar
```

### Step 5: Test the Application
Access the application endpoints (if implemented) or test the functionality using unit tests:
```bash
mvn test
```

## API Instructions

### Endpoint: `/v1/weather/{city}`
**Method:** `GET`  
**Description:** Fetches the weather forecast for the specified city.

#### Request Payload
No request body is required. The city name is passed as a path parameter.

#### Example Request
```bash
curl -X GET http://localhost:8080/v1/weather/melbourne
```

#### Response
The API returns a JSON object containing the weather data for the specified city.

#### Example Response
```json
{
  "wind_speed": "10.0",
  "temperature_degrees": "22.0"
}
```

#### Response Fields
| Field                | Type    | Description                          |
|----------------------|---------|--------------------------------------|
| `wind_speed`         | String  | The wind speed in the city (km/h).   |
| `temperature_degrees`| String  | The temperature in the city (°C).    |

### Notes
- If the primary API fails, the application will fallback to the secondary API.
- If both APIs fail, default values (`wind_speed: "0.0"`, `temperature_degrees: "0.0"`) will be returned.

## License
This project is licensed under the MIT License.
```