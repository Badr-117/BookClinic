package com.example.segproject2019;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;



public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="Users.db";
    private static final String TABLE_NAME= "users_table";
    private static final String COL_1="ID";
    private static final String COL_2="TYPE";
    private static final String COL_3="EMAIL";
    private static final String COL_4= "PASSWORD";
    private static final String COL_5="FIRST NAME";
    private static final String COL_6="LAST NAME";



    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE"+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, TYPE TEXT, EMAIL TEXT, PASSWORD TEXT, FIRST NAME TEXT, LAST NAME TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }
}
