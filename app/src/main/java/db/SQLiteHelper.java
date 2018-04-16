package db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private String create_city = "CREATE TABLE city" +
            "(city VARCHAR(20)," +
            "primary_pollutant VARCHAR(33)," +
            "quality VARCHAR(10)," +
            "date CHAR(8)," +
            "hour CHAR(2)," +
            "aqi FLOAT," +
            "co FLOAT," +
            "no2 FLOAT," +
            "o3 FLOAT," +
            "pm10 FLOAT," +
            "pm2_5 FLOAT," +
            "so2 FLOAT," +
            "PRIMARY KEY(city, date, hour));";

    private String create_site = "CREATE TABLE site " +
            "(position_name VARCHAR(21)," +
            "station_code CHAR(5)," +
            "primary_pollutant VARCHAR(33)," +
            "quality VARCHAR(10)," +
            "date CHAR(8)," +
            "hour CHAR(2)," +
            "aqi FLOAT," +
            "co FLOAT," +
            "no2 FLOAT," +
            "o3 FLOAT," +
            "pm10 FLOAT," +
            "pm2_5 FLOAT," +
            "so2 FLOAT," +
            "PRIMARY KEY(position_name, date, hour));";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_city);
        db.execSQL(create_site);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
