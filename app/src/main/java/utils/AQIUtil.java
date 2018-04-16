package utils;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.blankj.utilcode.util.Utils;
import com.lzx.cardweather.R;

public class AQIUtil {

    public static int getColor(int aqi) {
        if (aqi == 0) {
            return getContextColor(R.color.colorWhite);
        } else if (aqi>0 && aqi<=50) {
            return getContextColor(R.color.colorAqiPerfect);
        } else if (aqi>=50 && aqi<=100) {
            return getContextColor(R.color.colorAqiGood);
        } else if (aqi>=100 && aqi<=150) {
            return getContextColor(R.color.colorAqiMidPollu);
        } else if (aqi>=150 && aqi<=200) {
            return getContextColor(R.color.colorAqiModeratePollu);
        } else if (aqi>=200 && aqi<=250) {
            return getContextColor(R.color.colorAqiSeverePollu);
        } else if (aqi>=250 && aqi<=300) {
            return getContextColor(R.color.colorAqiSerioudPollu);
        } else if (aqi>300) {
            return getContextColor(R.color.colorAqiOutRange);
        }
        return -1;
    }

    public static String getLevel(int aqi) {
        if (aqi>=0 && aqi<=50) {
            return getString(R.string.str_aqi_perfect);
        } else if (aqi>=50 && aqi<=100) {
            return getString(R.string.str_aqi_good);
        } else if (aqi>=100 && aqi<=150) {
            return getString(R.string.str_aqi_mild_pollutant);
        } else if (aqi>=150 && aqi<=200) {
            return getString(R.string.str_aqi_moderate_pollutant);
        } else if (aqi>=200 && aqi<=300) {
            return getString(R.string.str_aqi_severe_pollutant);
        } else if (aqi>300) {
            return getString(R.string.str_aqi_serious_pollutant);
        }
        return getString(R.string.str_aqi_0);
    }

    private static int getContextColor(@ColorRes int resId) {
        return Utils.getApp().getResources().getColor(resId);
    }

    private static String getString(@StringRes int resId) {
        return Utils.getApp().getResources().getString(resId);
    }
}
