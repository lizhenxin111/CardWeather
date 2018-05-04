package aqi;

/**
 * AQI的数据接口
 * 返回值均为字符串
 */
public class AQI {
    private static final String HEADER = "http://35.201.207.118:8080/query/";

    private static final String NOW_CITY = "now?city=";
    private static final String NOW_SITE = "now?site=";
    private static final String CITY = "city?city=";
    private static final String SITE = "site?site=";
    private static final String MONTH_CITY = "month?city=";
    private static final String MONTH_SITE = "month?site=";

    private static final String DATE = "&date=";
    private static final String HOUR = "&hour=";
    private static final String DETAIL = "&detail=true";


    /**
     * 指定城市当前AQI
     * @param city
     * @return
     */
    public static String getCityNow(String city) {
        return HEADER + NOW_CITY + city;
    }


    /**
     * 当前城市及站点
     * @param city
     * @return
     */
    public static String getCityAndSites(String city) {
        return HEADER + NOW_CITY + city + DETAIL;
    }
    /**
     * 某城市、某天
     * @param city
     * @param date
     * @return
     */
    public static String getCityDay(String city, String date) {
        return HEADER + CITY + city + DATE + date;
    }

    /**
     * 城市、某小时
     * @param city
     * @param date
     * @param hour
     * @return
     */
    public static String getCityHour(String city, String date, String hour) {
        return getCityDay(city, date) + HOUR + hour;
    }

    public static String getCityMonth(String city, String year, String month) {
        return HEADER + MONTH_CITY + city + "&year=" + year + "&month=" + month;
    }


    /**
     * 指定站点当前AQI
     * @param site
     * @return
     */
    public static String getSiteNow(String site) {
        return HEADER + NOW_SITE + site;
    }
    public static String getSiteHour(String site, String date, String hour) {
        return HEADER + SITE + site + DATE + date + HOUR + hour;
    }

    public static String getSiteDay(String site, String date) {
        return HEADER + SITE + site + DATE + date;
    }

    public static String getSiteMonth(String city, String year, String month) {
        return HEADER + MONTH_SITE + city + "&year=" + year + "&month=" + month;
    }
}
