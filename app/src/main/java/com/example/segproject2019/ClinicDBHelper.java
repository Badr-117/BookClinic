package com.example.segproject2019;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ClinicDBHelper extends SQLiteOpenHelper {
    //CLASS VARIABLE REGARDING THE CREATION OF A DATABASE (TABLE)
    private static final String DB_NAME="Clinics.db";
    private static final String TABLE_NAME= "Clinics_table";
    private static final String COL_1="ID";
    private static final String COL_2="SERVICES";
    private static final String COL_3="ADDRESSE";
    private static final String COL_4= "TARIFS";
    private static final String COL_5="HEURES";
    private static final String COL_6="EVALUATION";
    private static final String COL_7="WAIT TIME";

    public ClinicDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 1);
        //constructor called, creates database in getWritableDatabase
        SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override   //method creates a database using the class variables (table with a number that get autoincremented)
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE"+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, SERVICES TEXT, ADRESSE TEXT, TARIFS TEXT, HEURES TEXT, EVALUATION TEXT, WAIT TIME TEXT)");

    }

    @Override//method updates the database with i being the old version and i1 the new version
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        onCreate(db);

    }
}
