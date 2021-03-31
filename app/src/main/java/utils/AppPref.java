package utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.Utils;

public class AppPref {
    public static final String APP_PREF = "_APP_PREF_";
    public static final String CARD_PREF = "_CARD_PREF_";

    public static void setCity(String city) {
        SharedPreferences sp = Utils.getApp().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("city", city);
        editor.commit();
    }

    public static String getCity() {
        SharedPreferences sp = Utils.getApp().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        return sp.getString("city", "绵阳");
    }

    public static void setCardList(String list) {
        SharedPreferences sp = Utils.getApp().getSharedPreferences(CARD_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("card_list", list);
        editor.commit();
    }

    public static String getCardList() {
        SharedPreferences sp = Utils.getApp().getSharedPreferences(CARD_PREF, Context.MODE_PRIVATE);
        return sp.getString("card_list", null);
    }

    public static void setFirstOpen() {
        SharedPreferences sp = Utils.getApp().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first_open", false);
        editor.commit();
    }

    public static boolean getFirstOpen() {
        SharedPreferences sp = Utils.getApp().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        return sp.getBoolean("first_open", true);
    }
}
