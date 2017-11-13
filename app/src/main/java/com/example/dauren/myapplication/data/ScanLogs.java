package com.example.dauren.myapplication.data;

import android.provider.BaseColumns;

/**
 * Created by dauren on 1/25/17.
 */
public final class ScanLogs {

    public static final class ScanLog implements BaseColumns {
        public static final String TABLE_NAME = "scan_logs";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TEXT    = "text"   ;
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_DATE    = "date"   ;
    }
}
