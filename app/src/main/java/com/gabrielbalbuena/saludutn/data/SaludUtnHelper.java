package com.gabrielbalbuena.saludutn.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.gabrielbalbuena.saludutn.data.SaludUtnContract.HistorialMedicoEntry;

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
        String SQL_CREATE_DATOS_PERSONALES_TABLE = " CREATE TABLE " + SaludUtnContract.DatosPersonalesEntry.TABLE_NAME + " (" +
                SaludUtnContract.DatosPersonalesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_MATRICULA + " TEXT NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_NOMBRES + " TEXT NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_APELLIDOS + " TEXT NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_CONTACT_PHONE + " TEXT NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT + " INTEGER NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT + " INTEGER NOT NULL, " +
                SaludUtnContract.DatosPersonalesEntry.COLUMN_NSS + " TEXT NOT NULL );";
        sqLiteDatabase.execSQL(SQL_CREATE_DATOS_PERSONALES_TABLE);


        String SQL_CREATE_DIARIOEMOCIONES_TABLE = " CREATE TABLE " + SaludUtnContract.DiarioEmocionesEntry.TABLE_NAME + " (" +
                SaludUtnContract.DiarioEmocionesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SaludUtnContract.DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA + " TEXT NOT NULL, " +
                SaludUtnContract.DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION + " TEXT NOT NULL," +
                SaludUtnContract.DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE + "  TEXT, " +
                SaludUtnContract.DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO + "  TEXT );";
        sqLiteDatabase.execSQL(SQL_CREATE_DIARIOEMOCIONES_TABLE);

        String SQL_CREATE_ALERGIAS_TABLE = " CREATE TABLE " + SaludUtnContract.AlergiasEntry.TABLE_NAME + " (" +
                SaludUtnContract.AlergiasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SaludUtnContract.AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA + " TEXT NOT NULL, " +
                SaludUtnContract.AlergiasEntry.COLUMN_ALERGIAS_NOMBRE + " TEXT NOT NULL," +
                SaludUtnContract.AlergiasEntry.COLUMN_TIPO_ALERGIA + "  TEXT, " +
                SaludUtnContract.AlergiasEntry.COLUMN_COMENTARIO_ALERGIA + "  TEXT );";
        sqLiteDatabase.execSQL(SQL_CREATE_ALERGIAS_TABLE);

        String SQL_CREATE_HISTORIALMEDICO_TABLE = " CREATE TABLE " + SaludUtnContract.HistorialMedicoEntry.TABLE_NAME + " (" +
                HistorialMedicoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HistorialMedicoEntry.COLUMN_FECHA_HM + " TEXT NOT NULL, " +
                HistorialMedicoEntry.COLUMN_DIAGNOSTICO + "  TEXT, " +
                HistorialMedicoEntry.COLUMN_FOTO_UNO_URL + "  TEXT, " +
                HistorialMedicoEntry.COLUMN_FOTO_DOS_URL + "  TEXT, " +
                HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA + " INTEGER, " +
                HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR + "  TEXT, " +
                HistorialMedicoEntry.COLUMN_ESPECIALIDAD + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_HISTORIALMEDICO_TABLE);
        }

    /**
     * This is called when the database needs to be upgraded.
     */
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
