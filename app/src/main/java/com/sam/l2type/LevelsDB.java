package com.sam.l2type;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sam on 11/1/2015.
 */
public class LevelsDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "levels.db";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE LevelPacks (id INT PRIMARY KEY, " +
                    "name TEXT," +
                    "levels int," +
                    "level1 TEXT," +
                    "level2 TEXT," +
                    "level3 TEXT," +
                    "level4 TEXT," +
                    "level5 TEXT," +
                    "level6 TEXT," +
                    "level7 TEXT," +
                    "level8 TEXT," +
                    "level9 TEXT," +
                    "level10 TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS LevelPacks";
    public LevelsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
