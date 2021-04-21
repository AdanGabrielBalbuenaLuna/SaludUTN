package com.gabrielbalbuena.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gabrielbalbuena.data.SaludUtnContract.DatosPersonalesEntry;


public class SaludUtnHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "saludutn.db";
    public static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link SaludUtnHelper}.
     *
     * @param context of the app
     */
    public SaludUtnHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String SQL_CREATE_DATOS_PERSONALES_TABLE = " CREATE TABLE " + DatosPersonalesEntry.TABLE_NAME + " (" +
                DatosPersonalesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatosPersonalesEntry.COLUMN_MATRICULA + " TEXT NOT NULL, " +
                DatosPersonalesEntry.COLUMN_NOMBRES + " TEXT NOT NULL, " +
                DatosPersonalesEntry.COLUMN_APELLIDOS + " TEXT NOT NULL, " +
                DatosPersonalesEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL, " +
                DatosPersonalesEntry.COLUMN_CONTACT_PHONE + " TEXT NOT NULL, " +
                DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT + " INTEGER NOT NULL, " +
                DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT + " INTEGER NOT NULL, " +
                DatosPersonalesEntry.COLUMN_NSS + " TEXT NOT NULL );";
        sqLiteDatabase.execSQL(SQL_CREATE_DATOS_PERSONALES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
