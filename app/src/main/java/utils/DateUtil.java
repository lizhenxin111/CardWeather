package utils;

import java.util.Calendar;

public class DateUtil {
    private static Calendar c = Calendar.getInstance();

    public static int getYear() {
        return c.get(Calendar.YEAR);
    }
    public static int getMonth() {
        return c.get(Calendar.MONTH) + 1;
    }
    public static int getDay() {
        return c.get(Calendar.DAY_OF_MONTH);
    }
    public static int getHour() {
        return c.get(Calendar.HOUR_OF_DAY);
    }
    public static int getMinute() {
        return c.get(Calendar.MINUTE);
    }

    public static String getDate() {
        String year = getYear() + "";
        String month = add0(getMonth());
        String day = add0(getDay());
        return year + month + day;
    }

    public static String getHourString() {
        return add0(getHour());
    }

    public static String getApiHour() {
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return add0(minute > 47 ? hour : hour - 1);
    }

    private static String add0(int c) {
        String result = c + "";
        if (c < 10) {
            result = "0" + result;
        }
        return result;
    }
}
