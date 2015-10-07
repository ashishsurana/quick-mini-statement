package com.example.ashish.mini;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by ashish on 2/9/15.
 */
public class dbHelper extends SQLiteOpenHelper {
    public static final String dbname = "message.db";
    public static final int dbversion = 1;
    public static String TABLE_NAME = new String();

    public void create_table(String tbname,SQLiteDatabase db){
        String create_query =
                "CREATE TABLE " + tbname + " (" +
                        FeedEntry._ID + " INTEGER ," +
//                        FeedEntry.COLUMN_NAME_RAW_MESSAGE + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_CORD + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_BALLANCE + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_AMOUNT + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_ACNO + TEXT_TYPE + COMMA_SEP +
                        " PRIMARY KEY (" + FeedEntry.COLUMN_NAME_DATE + COMMA_SEP + FeedEntry.COLUMN_NAME_TIME +")" +
                        " )";
        db.execSQL(create_query);
    }


    private static final String TEXT_TYPE = " TEXT";
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + TABLE_NAME + " (" +
//                    FeedEntry._ID + " INTEGER ," +
//                    FeedEntry.COLUMN_NAME_RAW_MESSAGE + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_CORD + TEXT_TYPE + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_BALLANCE + TEXT_TYPE + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_AMOUNT + TEXT_TYPE + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_ACNO + TEXT_TYPE +
//                    " )";
////                    FeedEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE +

    private static final String COMMA_SEP = ",";

    public static final String SQL_DELETE_ENTRIES ="DROP TABLE IF EXISTS " + TABLE_NAME;


    public dbHelper(Context context) {
        super(context, dbname, null, dbversion);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);
    }



    public static abstract class FeedEntry implements BaseColumns {
//        public static final String TABLE_NAME = "message";
//        public static final String COLUMN_NAME_ID = "_id";
//        public static final String COLUMN_NAME_RAW_MESSAGE = "rawmessage";
        public static final String COLUMN_NAME_CORD = "c_d";
        public static final String COLUMN_NAME_BALLANCE = "bal";
        public static final String COLUMN_NAME_AMOUNT = "amt";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ACNO = "acno";
        public static final String COLUMN_NAME_TIME = "time";
    }
    public void setTABLE_NAME(String tbname){
        TABLE_NAME=tbname;
    }
}

