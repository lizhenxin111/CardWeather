package aqi;

public class HeWeather {
    private static final String HEADER = "https://free-api.heweather.com/s6/weather/";
    private static final String KEY = "?key=00b26dd0d0af466ba4d237e806445868";
    private static final String WEATHER_NOW = "now";
    private static final String LIFESTYLE = "lifestyle";
    private static final String FORECAST = "forecast";
    private static final String LOC = "&location=";


    public static String getWeatherNow(String location) {
        return type(WEATHER_NOW, location);
    }

    public static String getLifeStyle(String location) {
        return type(LIFESTYLE, location);
    }

    public static String getForecast(String location) {
        return type(FORECAST, location);
    }

    private static String type(String type, String location) {
        return HEADER + type + KEY + LOC + location;
    }
}
