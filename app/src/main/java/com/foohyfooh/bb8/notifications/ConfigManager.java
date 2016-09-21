package com.foohyfooh.bb8.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final Map<String, Config> configs = new HashMap<>();
    private static final String TABLE_NAME = "Config";
    private static final String COLUMN_PACKAGE = "packageName";
    private static final String COLUMN_HEX     = "hex";
    private static final String COLUMN_PATTERN = "pattern";
    public  static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_PACKAGE + " text not null primary key," +
            COLUMN_HEX     + " text not null," +
            COLUMN_PATTERN + " text not null)";

    private ConfigManager(){}

    public static void putConfig(String packageName, Config config){
        configs.put(packageName, config);
    }

    public static void removeConfig(String packageName){
        configs.remove(packageName);
    }

    public static Config getConfig(String packageName){
        return configs.get(packageName);
    }

    public static long insertIntoDatabase(Context context, String packageName, Config config){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PACKAGE, packageName);
        contentValues.put(COLUMN_HEX,     config.getHexColour());
        contentValues.put(COLUMN_PATTERN, config.getPattern());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    public static void loadConfigsFromDatabase(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String packageName = cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE));
            @Config.Pattern String pattern = cursor.getString(cursor.getColumnIndex(COLUMN_PATTERN));
            Config config = new Config(cursor.getString(cursor.getColumnIndex(COLUMN_HEX)), pattern);
            configs.put(packageName, config);
        }
        cursor.close();
    }

    public static int updateInDatabase(Context context, String packageName, Config config) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HEX,     config.getHexColour());
        contentValues.put(COLUMN_PATTERN, config.getPattern());

        return database.update(TABLE_NAME, contentValues, COLUMN_PACKAGE + "=?", new String[]{packageName});
    }

    public static int deleteFromDatabase(Context context, String packageName){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        return database.delete(TABLE_NAME, COLUMN_PACKAGE + "=?", new String[]{packageName});
    }
}
