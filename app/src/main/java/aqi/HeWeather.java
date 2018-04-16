package aqi;

public class HeWeather {
    private static final String HEADER = "https://free-api.heweather.com/s6/weather/";
    private static final String WEATHER_NOW = "now?key=00b26dd0d0af466ba4d237e806445868";
    private static final String LOC = "&location=";

    public static String getWeatherNow(String location) {
        return HEADER + WEATHER_NOW + LOC + location;
    }
}
