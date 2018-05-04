package db;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.Utils;

import java.util.ArrayList;
import java.util.List;

import bean.aqi.City;
import bean.aqi.Site;

public class DB {
    public static final String DB_Name = "aqi.db";
    public static final int DB_VERSION = 1;

    private static SQLiteDatabase db;

    public DB() {
        db = new SQLiteHelper(Utils.getApp(), DB_Name, null, DB_VERSION).getWritableDatabase();
    }

    public static void insertCity(City c) {
        String sql = "INSERT INTO city(city, primary_pollutant, quality, date, hour, aqi, co, no2, o3, pm10, pm2_5, so2)" +
                "VALUES(" +
                c.getCity() +
                c.getPrimary_pollutant() +
                c.getQuality() +
                c.getDate() +
                c.getHour() +
                c.getAqi() +
                c.getCo() +
                c.getNo2() +
                c.getO3() +
                c.getPm10() +
                c.getPm2_5() +
                c.getSo2() + ")";
        db.execSQL(sql);
    }

    public static City queryCity(String city, String date, String hour) {
        String sql = "SELECT * FROM city WHERE city=? AND date=? AND hour=?";
        Cursor cursor = db.rawQuery(sql, new String[]{city, date, hour});
        try {
            if (cursor.moveToFirst()) {
                City c = new City();
                c.setCity(cursor.getString(cursor.getColumnIndex("city")));
                c.setPrimary_pollutant(cursor.getString(cursor.getColumnIndex("primary_pollutant")));
                c.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                c.setDate(cursor.getString(cursor.getColumnIndex("date")));
                c.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                c.setTime(cursor.getString(cursor.getColumnIndex("time")));

                c.setAqi(cursor.getFloat(cursor.getColumnIndex("aqi")));
                c.setCo(cursor.getFloat(cursor.getColumnIndex("co")));
                c.setNo2(cursor.getFloat(cursor.getColumnIndex("no2")));
                c.setO3(cursor.getFloat(cursor.getColumnIndex("o3")));
                c.setPm10(cursor.getFloat(cursor.getColumnIndex("pm10")));
                c.setPm2_5(cursor.getFloat(cursor.getColumnIndex("pm2_5")));
                c.setSo2(cursor.getFloat(cursor.getColumnIndex("so2")));
                return c;
            }
            return null;
        } finally {
            CloseUtils.closeIO(cursor);
        }
    }

    public static List<City> queryCity(String city) {
        String sql = "SELECT * FROM city WHERE city=?;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        try {
            List<City> cities = new ArrayList<>();
            while (cursor.moveToNext()) {
                City c = new City();
                c.setCity(cursor.getString(cursor.getColumnIndex("city")));
                c.setPrimary_pollutant(cursor.getString(cursor.getColumnIndex("primary_pollutant")));
                c.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                c.setDate(cursor.getString(cursor.getColumnIndex("date")));
                c.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                c.setTime(cursor.getString(cursor.getColumnIndex("time")));

                c.setAqi(cursor.getFloat(cursor.getColumnIndex("aqi")));
                c.setCo(cursor.getFloat(cursor.getColumnIndex("co")));
                c.setNo2(cursor.getFloat(cursor.getColumnIndex("no2")));
                c.setO3(cursor.getFloat(cursor.getColumnIndex("o3")));
                c.setPm10(cursor.getFloat(cursor.getColumnIndex("pm10")));
                c.setPm2_5(cursor.getFloat(cursor.getColumnIndex("pm2_5")));
                c.setSo2(cursor.getFloat(cursor.getColumnIndex("so2")));
                cities.add(c);
            }
            return cities;
        } finally {
            CloseUtils.closeIO(cursor);
        }
    }

    public static List<City> queryAllCity() {
        String sql = "SELECT * FROM city;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        try {
            List<City> cities = new ArrayList<>();
            while (cursor.moveToNext()) {
                City c = new City();
                c.setCity(cursor.getString(cursor.getColumnIndex("city")));
                c.setPrimary_pollutant(cursor.getString(cursor.getColumnIndex("primary_pollutant")));
                c.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                c.setDate(cursor.getString(cursor.getColumnIndex("date")));
                c.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                c.setTime(cursor.getString(cursor.getColumnIndex("time")));

                c.setAqi(cursor.getFloat(cursor.getColumnIndex("aqi")));
                c.setCo(cursor.getFloat(cursor.getColumnIndex("co")));
                c.setNo2(cursor.getFloat(cursor.getColumnIndex("no2")));
                c.setO3(cursor.getFloat(cursor.getColumnIndex("o3")));
                c.setPm10(cursor.getFloat(cursor.getColumnIndex("pm10")));
                c.setPm2_5(cursor.getFloat(cursor.getColumnIndex("pm2_5")));
                c.setSo2(cursor.getFloat(cursor.getColumnIndex("so2")));
                cities.add(c);
            }
            return cities;
        } finally {
            CloseUtils.closeIO(cursor);
        }
    }

    public static void insertSite(Site s) {
        String sql = "INSERT INTO site(position_name, station_code , primary_pollutant, quality, date, hour, aqi, co, no2, o3, pm10, pm2_5, so2)" +
                "VALUES(" +
                s.getSite() +
                s.getSite_code() +
                s.getPrimary_pollutant() +
                s.getQuality() +
                s.getDate() +
                s.getHour() +
                s.getAqi() +
                s.getCo() +
                s.getNo2() +
                s.getO3() +
                s.getPm10() +
                s.getPm2_5() +
                s.getSo2() + ")";
        db.execSQL(sql);
    }

    public static Site querySite(String site, String date, String hour) {
        String sql = "SELECT * FROM site WHERE site=? AND date=? AND hour=?";
        Cursor cursor = db.rawQuery(sql, new String[]{site, date, hour});
        try {
            if (cursor.moveToFirst()) {
                Site s = new Site();
                s.setSite(cursor.getString(cursor.getColumnIndex("position_name")));
                s.setSite_code(cursor.getString(cursor.getColumnIndex("station_code")));
                s.setPrimary_pollutant(cursor.getString(cursor.getColumnIndex("primary_pollutant")));
                s.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                s.setDate(cursor.getString(cursor.getColumnIndex("date")));
                s.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                s.setTime(cursor.getString(cursor.getColumnIndex("time")));

                s.setAqi(cursor.getFloat(cursor.getColumnIndex("aqi")));
                s.setCo(cursor.getFloat(cursor.getColumnIndex("co")));
                s.setNo2(cursor.getFloat(cursor.getColumnIndex("no2")));
                s.setO3(cursor.getFloat(cursor.getColumnIndex("o3")));
                s.setPm10(cursor.getFloat(cursor.getColumnIndex("pm10")));
                s.setPm2_5(cursor.getFloat(cursor.getColumnIndex("pm2_5")));
                s.setSo2(cursor.getFloat(cursor.getColumnIndex("so2")));
                return s;
            }
            return null;
        } finally {
            CloseUtils.closeIO(cursor);
        }
    }

    public static List<Site> querySite(String site) {
        String sql = "SELECT * FROM site WHERE site=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{site});
        cursor.moveToFirst();

        try {
            List<Site> sites = new ArrayList<>();
            while (cursor.moveToNext()) {
                Site s = new Site();
                s.setSite(cursor.getString(cursor.getColumnIndex("position_name")));
                s.setSite_code(cursor.getString(cursor.getColumnIndex("station_code")));
                s.setPrimary_pollutant(cursor.getString(cursor.getColumnIndex("primary_pollutant")));
                s.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                s.setDate(cursor.getString(cursor.getColumnIndex("date")));
                s.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                s.setTime(cursor.getString(cursor.getColumnIndex("time")));

                s.setAqi(cursor.getFloat(cursor.getColumnIndex("aqi")));
                s.setCo(cursor.getFloat(cursor.getColumnIndex("co")));
                s.setNo2(cursor.getFloat(cursor.getColumnIndex("no2")));
                s.setO3(cursor.getFloat(cursor.getColumnIndex("o3")));
                s.setPm10(cursor.getFloat(cursor.getColumnIndex("pm10")));
                s.setPm2_5(cursor.getFloat(cursor.getColumnIndex("pm2_5")));
                s.setSo2(cursor.getFloat(cursor.getColumnIndex("so2")));
                sites.add(s);
            }
            return sites;
        } finally {
            CloseUtils.closeIO(cursor);
        }
    }

    public static List<Site> queryAllSite() {
        String sql = "SELECT * FROM city;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        try {
            List<Site> sites = new ArrayList<>();
            while (cursor.moveToNext()) {
                Site s = new Site();
                s.setSite(cursor.getString(cursor.getColumnIndex("position_name")));
                s.setSite_code(cursor.getString(cursor.getColumnIndex("station_code")));
                s.setPrimary_pollutant(cursor.getString(cursor.getColumnIndex("primary_pollutant")));
                s.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                s.setDate(cursor.getString(cursor.getColumnIndex("date")));
                s.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                s.setTime(cursor.getString(cursor.getColumnIndex("time")));

                s.setAqi(cursor.getFloat(cursor.getColumnIndex("aqi")));
                s.setCo(cursor.getFloat(cursor.getColumnIndex("co")));
                s.setNo2(cursor.getFloat(cursor.getColumnIndex("no2")));
                s.setO3(cursor.getFloat(cursor.getColumnIndex("o3")));
                s.setPm10(cursor.getFloat(cursor.getColumnIndex("pm10")));
                s.setPm2_5(cursor.getFloat(cursor.getColumnIndex("pm2_5")));
                s.setSo2(cursor.getFloat(cursor.getColumnIndex("so2")));
                sites.add(s);
            }
            return sites;
        } finally {
            CloseUtils.closeIO(cursor);
        }
    }
}
