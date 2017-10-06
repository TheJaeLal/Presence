package com.mjjam.attendanceapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mjjam.attendanceapp.data.models.MTimeTable;

import java.util.ArrayList;

/**
 * Created by Archish on 10/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "attendance";
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 3;

    private Resources resources;

    private static DatabaseHelper dbInstance;

    private static Context myContext;

    public static DatabaseHelper getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseHelper(context);
        }
        myContext = context;
        return dbInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
        resources = context.getResources();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TimeTable.CREATE);
        //preFillDatabase(db);
    }
//
//    private void preFillDatabase(SQLiteDatabase db) {
//        // Get All Quiz Data from JSON file in the raw folder
//        db.beginTransaction();
//        try {
//            fillSadvicharTable(db);
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
//
//    }
//
//    private void fillSadvicharTable(SQLiteDatabase db) {
//        ContentValues values = new ContentValues();
//        try {
//            JSONArray jsonArray = new JSONArray(readDataFromResources());
//            JSONObject jObj;
//            jObj = jsonArray.getJSONObject(0);
//            final String s_title = jObj.getString("A_DATE");
//            final String s_message = jObj.getString("A_TIME");
//            final String s_date = jObj.getString("S_DATE");
//            final String s_status = jObj.getString("S_STATUS");
//            final String s_image = jObj.getString("S_IMAGE");
//            fillSadvichar(db, values, s_title, s_message, s_date, s_status, s_image);
//        } catch (Exception ae) {
//        }
//    }
//
//    private void fillSadvichar(SQLiteDatabase db, ContentValues values, String s_title, String s_message, String s_date, String s_status, String s_image) {
//        values.clear();
//
//        values.put(MTimeTable.A_DATE, s_title);
//        values.put(MTimeTable.A_TIME, s_message);
//        values.put(MTimeTable.S_DATE, s_date);
//        values.put(MTimeTable.S_STATUS, s_status);
//        values.put(MTimeTable.S_IMAGE, s_image);
//        db.insert(MTimeTable.NAME, null, values);
//
//    }
//
//    private String readDataFromResources() throws IOException {
//        StringBuilder modulesJSON = new StringBuilder();
//        InputStream rawCategories = resources.openRawResource(R.raw.sadvichar);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(rawCategories));
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            modulesJSON.append(line);
//        }
//        return modulesJSON.toString();
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
//
//        if (2 < newVersion && prefs.getBoolean("versioncode", true)) {
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("versioncode", false);
//        db.execSQL("DROP TABLE IF EXISTS " + MessageTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TimeTable.NAME);
        db.execSQL(TimeTable.CREATE);
//        db.execSQL("DROP TABLE IF EXISTS " + BookmarkTable.NAME);
////        }


    }

    public void insertToAttendance(String a_id, String a_day, String a_time_slot) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(TimeTable.A_ID, a_id);
        contentValues.put(TimeTable.A_DAY, a_day);
        contentValues.put(TimeTable.A_TIME, a_time_slot);
        db.insert(TimeTable.NAME, null, contentValues);

    }


    public int getLastRecord() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + TimeTable.NAME + " ORDER BY " + TimeTable.A_ID + " DESC LIMIT 1", null);
        int s_id = 0;
//        printTableContent(database, MTimeTable.NAME);

        if (c != null && c.moveToFirst()) {
            int id = c.getColumnIndex(TimeTable.A_ID);
            s_id = c.getInt(id);

        }
        if (c != null) {
            c.close();
        }
        return s_id;

    }

    public boolean checkTimeTableisEmpty() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor c = database.rawQuery("SELECT COUNT(*) FROM " + TimeTable.NAME, null);

        if (c != null) {
            c.moveToFirst();                       // Always one row returned.
            // Zero count means empty table.
            return c.getInt(0) == 0;
        }
        return false;
    }


    public void updateToTimeTable(String a_id, String a_day, String a_time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();

        String[] sadvicharArgs = new String[1];
        sadvicharArgs[0] = String.valueOf(a_id);

        contentValues.put(TimeTable.A_DAY, a_day);
        contentValues.put(TimeTable.A_TIME, a_time);

        db.update(TimeTable.NAME, contentValues, "CAST ( " + TimeTable.A_ID + " AS TEXT ) = ? ", sadvicharArgs);

    }


    public Cursor getTimeTable() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + TimeTable.NAME + " ORDER BY " + TimeTable.A_ID + " DESC ", null);
        return c;
    }

    public ArrayList<MTimeTable> getSadvichar(int position) {
        Cursor cursor = getSadvicharCursor(position);
        ArrayList<MTimeTable> questions = new ArrayList<>(cursor.getCount());
        do {
            MTimeTable message = getTimeTable1(cursor);
            questions.add(message);
        } while (cursor.moveToNext());
        return questions;
    }

    private Cursor getSadvicharCursor(int position) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String[] args = new String[1];
        args[0] = position + "";

        Cursor data = readableDatabase
                .query(TimeTable.NAME, TimeTable.PROJECTION, "CAST ( " + TimeTable.A_ID + " as TEXT ) = ?", args, null, null, null);
        data.moveToFirst();
        return data;
    }

    private MTimeTable getTimeTable1(Cursor cursor) {
        final int a_id = cursor.getInt(0);
        final String a_day = cursor.getString(1);
        final String a_time = cursor.getString(2);
        return new MTimeTable(a_id, a_day, a_time);
    }


    /*
    Debugging methods below here
     */
    public void printTableContent(SQLiteDatabase database, String tableName) {
        String query = "SELECT * FROM " + tableName;
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        for (int i = 0; i < c.getColumnCount(); i++) {
            System.out.print(c.getColumnName(i) + "    ");
        }
        System.out.println();
        do {
            for (int i = 0; i < c.getColumnCount(); i++) {
                System.out.print(printColumnContent(c, i) + "    ");
            }
            System.out.println();
        } while (c.moveToNext());
        c.close();
    }

    private String printColumnContent(Cursor c, int colIndex) {
        if (c.getType(colIndex) == Cursor.FIELD_TYPE_STRING)
            return c.getString(colIndex);
        else if (c.getType(colIndex) == Cursor.FIELD_TYPE_INTEGER)
            return c.getInt(colIndex) + "";
        else if (c.getType(colIndex) == Cursor.FIELD_TYPE_FLOAT) {
            return c.getFloat(colIndex) + "";
        } else return null;
    }

    public void deleteFromMessage(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TimeTable.NAME + " where " + TimeTable.A_ID + "=" + id);


    }

    public void deleteFromAllMessage() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TimeTable.NAME);


    }

}
