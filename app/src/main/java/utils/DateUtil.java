package utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateUtil {

    private static final Integer[] MONTH_SHORT_ARRAY = new Integer[]{4, 6, 9, 11};

    private static final List MONTH_SHORT = Arrays.asList(MONTH_SHORT_ARRAY);


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

    public static String add0(int c) {
        String result = c + "";
        if (c < 10) {
            result = "0" + result;
        }
        return result;
    }

    public static String add0(String c) {
        if (c.length() == 1) {
            c = "0" + c;
        }
        return c;
    }

    public static int getDaysInMonth(int year, int month) {
        if (month == 2) {
            return year % 100 != 0 && year% 4 == 0 ? 29 : 28;
        }
        if (MONTH_SHORT.contains(month)) {
            return 30;
        }
        return 31;
    }
}
