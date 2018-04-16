package aqi;

public class AQI {
    private static final String HEADER = "http://35.201.207.118:8080/query/";
    private static final String DATE = "&date=";
    private static final String NOW_CITY = "now?city=";
    private static final String NOW_SITE = "now?site=";
    private static final String CITY = "city?city=";


    public static String getCityNow(String city) {
        return HEADER + NOW_CITY + city;
    }
    public static String getSiteNow(String site) {
        return HEADER + NOW_SITE + site;
    }

    public static String getCityDay(String city, String day) {
        return HEADER + CITY + city + DATE + day;
    }

}
