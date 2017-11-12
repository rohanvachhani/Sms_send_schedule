package com.rohan.sms_send_schedule.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rohan on 11/4/2017.
 */

public class databaseHelper_students extends SQLiteOpenHelper {

    public static final String Db_name = "mysqlitedatabse";
    public static final String Table_name = "students";
    public static final String col_id = "id";
    public static final String col_c_id = "c_id";
    public static final String col_mobile_no = "mobile_no";
    public static final String col_mail = "mail_ID";
    public static final String col_name = "name";
    public static final String col_result = "result";
    public static final String col_attendance = "attendance";
    public static final String col_fees_pending = "fees_pending";


    public static final int DB_Version = 1;

    public databaseHelper_students(Context context) {
        super(context, Db_name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       /* String sql = "create table " + Table_name + "(" + col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_name + " VARCHAR," + col_mobile_no + " number(10)," + col_mail + " varchar2(15)," + " number(10),"
                + col_result + "VARCHAR," + col_attendance + "VARCHAR," + col_fees_pending
                + "VARCHAR)";
                */

        db.execSQL("create table " + Table_name + " (id INTEGER PRIMARY KEY AUTOINCREMENT, c_id TEXT, name TEXT, mobile_no TEXT,mail_ID TEXT, result TEXT, attendance TEXT, fees_pending TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXITS " + Table_name;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean add_student(String c_id, String name, String mob, String mail, String result, String atten, String fees) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_c_id, c_id);
        contentValues.put(col_name, name);
        contentValues.put(col_result, result);
        contentValues.put(col_attendance, atten);
        contentValues.put(col_fees_pending, fees);
        contentValues.put(col_mobile_no, mob);
        contentValues.put(col_mail, mail);

        long rslt = sqLiteDatabase.insert(Table_name, null, contentValues);
        if (rslt == -1)
            return false;
        else
            return true;

        //sqLiteDatabase.close();
        //return true;
    }

    public Cursor getStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + Table_name, null);
        return res;
    }
}
