package com.sam.l2type;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A local SQLite DB for persistent local storage of level data.
 * Created by Sam on 11/1/2015.
 */
public class LevelsDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    //Some global constants for ease of table access
    public static final String DATABASE_NAME = "levels.db";
    public static final String TABLE_NAME = "LevelPacks";
    public static final String ID_ATTR = "id";
    public static final String NAME_ATTR = "name";
    public static final String NUM_LEVELS_ATTR = "levels";
    public static final String LEVEL_ATTR = "level";

    //Query to create the LevelPacks table
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME +
                    " (" + ID_ATTR + " INT PRIMARY KEY, " +
                    NAME_ATTR + " TEXT," +
                    NUM_LEVELS_ATTR + " int," +
                    LEVEL_ATTR + "1 TEXT," +
                    LEVEL_ATTR + "2 TEXT," +
                    LEVEL_ATTR + "3 TEXT," +
                    LEVEL_ATTR + "4 TEXT," +
                    LEVEL_ATTR + "5 TEXT," +
                    LEVEL_ATTR + "6 TEXT," +
                    LEVEL_ATTR + "7 TEXT," +
                    LEVEL_ATTR + "8 TEXT," +
                    LEVEL_ATTR + "9 TEXT," +
                    LEVEL_ATTR + "10 TEXT)";

    //Query to delete the LevelPacks table
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Creates a new database
     * @param context
     */
    public LevelsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Queries the DB for all of the level packs
     * @return all of the levelpacks returned as a list of LevelPacks
     */
    public List<LevelPack> getAllLevelPacks() {
        List<LevelPack> levelpacks= new ArrayList<LevelPack>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        LevelPack pack = null;
        if(cursor.moveToFirst()) {
            do {
                pack = new LevelPack();
                pack.setId(Integer.parseInt(cursor.getString(0)));
                pack.setName(cursor.getString(1));
                pack.setNumLevels(Integer.parseInt(cursor.getString(2)));
                for(int i = 0; i < 10; i++) {
                    pack.setLevel(i, "" + cursor.getString(i + 3));
                }
                levelpacks.add(pack);
                Log.i("L2Type", pack.toString());
            } while(cursor.moveToNext());
        }
        return levelpacks;
    }
}
