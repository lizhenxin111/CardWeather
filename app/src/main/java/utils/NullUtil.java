package utils;

public class NullUtil {
    public static <T> T checkNull(T o) {
        if (o == null) {
            return null;
        }
        return o;
    }

    public static <T> T checkNull(T o, String message) {
        if (checkNull(o) == null) {
            throw new NullPointerException(message);
        }
        return o;
    }

    public static boolean isNull(String s) {
        return s == null || s.length() == 0;
    }
}
