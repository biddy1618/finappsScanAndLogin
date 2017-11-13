package com.example.dauren.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.animation.ScaleAnimation;

import com.example.dauren.myapplication.LogsFragment.LogsListItem;

import java.util.ArrayList;

/**
 * Created by dauren on 1/25/17.
 */
public class LogsDbHelper extends SQLiteOpenHelper {

    public  static final String LOG_TAG    = LogsDbHelper.class.getSimpleName();
    private static final String DB_NAME    = "scan_logs.db";
    private static final int    DB_VERSION = 1;

    public static LogsDbHelper mDbInstance;

    private LogsDbHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOGS_TABLE = "CREATE TABLE " + ScanLogs.ScanLog.TABLE_NAME + " ("
                + ScanLogs.ScanLog._ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ScanLogs.ScanLog.COLUMN_TEXT    + " TEXT NOT NULL DEFAULT \"no text\", "
                + ScanLogs.ScanLog.COLUMN_MESSAGE + " TEXT NOT NULL DEFAULT \" no scan \", "
                + ScanLogs.ScanLog.COLUMN_DATE    + " TEXT);";

        db.execSQL(SQL_CREATE_LOGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Updating from " + oldVersion + " version to " + newVersion + " version.");

        db.execSQL("DROP TABLE IF EXISTS " + ScanLogs.ScanLog.TABLE_NAME + ";");

        onCreate(db);
    }

    public static LogsDbHelper getInstance(Context context) {
        if (mDbInstance == null) mDbInstance = new LogsDbHelper(context);
        return mDbInstance;
    }

    public ArrayList<LogsListItem> getData() {
        ArrayList<LogsListItem> retData = new ArrayList<>();

        if (mDbInstance == null) return retData;

        SQLiteDatabase db = mDbInstance.getReadableDatabase();

        String[] projection = {
                ScanLogs.ScanLog._ID        ,
                ScanLogs.ScanLog.COLUMN_TEXT,
                ScanLogs.ScanLog.COLUMN_MESSAGE
        };

        Cursor cursor = db.query(
                ScanLogs.ScanLog.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex   = cursor.getColumnIndex(ScanLogs.ScanLog._ID           );
            int idColumnText    = cursor.getColumnIndex(ScanLogs.ScanLog.COLUMN_TEXT   );
            int idColumnMessage = cursor.getColumnIndex(ScanLogs.ScanLog.COLUMN_MESSAGE);

            while(cursor.moveToNext()) {
                Long   id      = cursor.getLong  (idColumnIndex  );
                String text    = cursor.getString(idColumnText   );
                String message = cursor.getString(idColumnMessage);

                retData.add(new LogsListItem(id, text +": " + message));
            }
        } finally {
            cursor.close();
        }

        return retData;
    }

    public void insert(String text, String message) {
        if (mDbInstance == null) return;

        SQLiteDatabase db = mDbInstance.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScanLogs.ScanLog.COLUMN_TEXT   , text   );
        values.put(ScanLogs.ScanLog.COLUMN_MESSAGE, message);
        values.put(ScanLogs.ScanLog.COLUMN_DATE   , "lol"  );

        db.insert(ScanLogs.ScanLog.TABLE_NAME, null, values);
    }
}
