package com.gabrielbalbuena.saludutn.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.gabrielbalbuena.saludutn.DiarioEmociones;
import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DatosPersonalesEntry;//

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DiarioEmocionesEntry;//

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.AlergiasEntry;//

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.HistorialMedicoEntry;//

public class SaludUtnProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = SaludUtnProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the datospersonales table */
    private static final int DATOS_PERSONALES = 100;
    /** URI matcher code for the content URI for a single datospersonal in the datospersonales table */
    private static final int DATOS_PERSONALES_ID = 101;


    /** URI matcher code for the content URI for the diarioemociones table */
    private static final int DIARIO_EMOCIONES = 200;
    /** URI matcher code for the content URI for a single diarioemocion in the diarioemociones table */
    private static final int DIARIO_EMOCIONES_ID = 201;

    /** URI matcher code for the content URI for the alergias table */
    private static final int ALERGIAS = 300;
    /** URI matcher code for the content URI for a single alergia in the alergias table */
    private static final int ALERGIAS_ID = 301;

    /** URI matcher code for the content URI for the pets table */
    private static final int HISTORIAL_MEDICO = 400;
    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int HISTORIAL_MEDICO_ID = 401;

        //s because is static
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        // The content URI of the form "com.gabrielbalbuena.saludutn/datospersonales" will map to the
        // integer code {@link #DATOSPERSONALES}. This URI is used to provide access to MULTIPLE rows
        // of the datospersonales table.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_DATOS_PERSONALES, DATOS_PERSONALES);
        // The content URI of the form "content://com.gabrielbalbuena.saludutn/datospersonales/#" will map to the
        // integer code {@link #DATOSPERSONALES_ID}. This URI is used to provide access to ONE single row
        // of the datospersonales table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.gabrielbalbuena.saludutn/datospersonales/3" matches, but
        // "content://com.gabrielbalbuena.saludutn/datospersonales" (without a number at the end) doesn't match.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_DATOS_PERSONALES + "/#", DATOS_PERSONALES_ID);


        // The calls to addURI() go here, for all of the content URI patterns that the provider________________________________________________
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        // The content URI of the form "com.gabrielbalbuena.saludutn/diarioemociones" will map to the
        // integer code {@link #DIARIOEMOCIONES}. This URI is used to provide access to MULTIPLE rows
        // of the diarioemociones table.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_DIARIO_EMOCIONES, DIARIO_EMOCIONES);

         // The content URI of the form "content://com.example.android.saludutn/diarioemociones/#" will map to the
        // integer code {@link #DIARIOEMOCIONES_ID}. This URI is used to provide access to ONE single row
        // of the diarioemociones table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.saludutn/diarioemociones/3" matches, but
        // "content://com.example.android.saludutn/diarioemociones" (without a number at the end) doesn't match.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_DIARIO_EMOCIONES + "/#", DIARIO_EMOCIONES_ID);


        // The calls to addURI() go here, for all of the content URI patterns that the provider________________________________________________
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        // The content URI of the form "com.gabrielbalbuena.saludutn/alergias" will map to the
        // integer code {@link #ALERGIAS}. This URI is used to provide access to MULTIPLE rows
        // of the alergias table.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_ALERGIAS, ALERGIAS);

        // The content URI of the form "content://com.example.android.saludutn/alergias/#" will map to the
        // integer code {@link #ALERGIAS_ID}. This URI is used to provide access to ONE single row
        // of the alergias table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.saludutn/alergias/3" matches, but
        // "content://com.example.android.saludutn/alergias" (without a number at the end) doesn't match.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_ALERGIAS + "/#", ALERGIAS_ID);

        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_HISTORIAL_MEDICO, HISTORIAL_MEDICO);
        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PET_ID}. This URI is used to provide access to ONE single row
        // of the pets table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_HISTORIAL_MEDICO + "/#", HISTORIAL_MEDICO_ID);
    }


    //DataBase helper object
    private SaludUtnHelper mDbHelper;
        /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        // TODO: Create and initialize a SaludUtnDbHelper object to gain access to the datospersonales database. Line 17
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        mDbHelper = new SaludUtnHelper(getContext());
        return false;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case DATOS_PERSONALES:
                // For the DATOSPERSONALES code, query the datospersonales table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the datospersonales table.
                // TODO: Perform database query on datospersonales table
                cursor = database.query(DatosPersonalesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DATOS_PERSONALES_ID:
                // For the DATOSPERSONALES_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.gabrielbalbuena.saludutn/datospersonales/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DatosPersonalesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the datospersonales table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DatosPersonalesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case DIARIO_EMOCIONES:
                // For the DIARIOEMOCIONES code, query the diarioemociones table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the diarioemociones table.
                // TODO: Perform database query on diarioemociones table
                cursor = database.query(DiarioEmocionesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DIARIO_EMOCIONES_ID:
                // For the DIARIOEMOCIONES_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.saludutn/diarioemociones/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DiarioEmocionesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the diarioemociones table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DiarioEmocionesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ALERGIAS:
                // For the ALERGIAS code, query the alergias table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the alergias table.
                // TODO: Perform database query on alergias table
                cursor = database.query(AlergiasEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ALERGIAS_ID:
                // For the ALERGIAS_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.saludutn/alergias/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = AlergiasEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the alergias table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(AlergiasEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case HISTORIAL_MEDICO:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table
                cursor = database.query(HistorialMedicoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case HISTORIAL_MEDICO_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = HistorialMedicoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(HistorialMedicoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }


    /**
     * Returns the MIME type of data for the content URI.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATOS_PERSONALES:
                return DatosPersonalesEntry.CONTENT_LIST_TYPE;
            case DATOS_PERSONALES_ID:
                return DatosPersonalesEntry.CONTENT_ITEM_TYPE;
            case DIARIO_EMOCIONES:
                return DiarioEmocionesEntry.CONTENT_LIST_TYPE;
            case DIARIO_EMOCIONES_ID:
                return DiarioEmocionesEntry.CONTENT_ITEM_TYPE;
            case ALERGIAS:
                return AlergiasEntry.CONTENT_LIST_TYPE;
            case ALERGIAS_ID:
                return AlergiasEntry.CONTENT_ITEM_TYPE;

            case HISTORIAL_MEDICO:
                return HistorialMedicoEntry.CONTENT_LIST_TYPE;
            case HISTORIAL_MEDICO_ID:
                return HistorialMedicoEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATOS_PERSONALES:
                return insertDatosPersonales(uri, contentValues);
            case DIARIO_EMOCIONES:
                return insertDiarioEmociones(uri, contentValues);
            case ALERGIAS:
                return insertAlergias(uri, contentValues);

            case HISTORIAL_MEDICO:
                return insertHistorialMedico(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a datospersonales into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertDatosPersonales(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Check that the matricula is not null
        String matricula = values.getAsString(DatosPersonalesEntry.COLUMN_MATRICULA);
        //name==null || name.isEmpty()
        if (matricula.equals("")) {
            throw new IllegalArgumentException("Student requires a matricula");
        }

        // Check that the name is not null
        String name = values.getAsString(DatosPersonalesEntry.COLUMN_NOMBRES);
        //name==null || name.isEmpty()
        if (name.equals("")) {
            throw new IllegalArgumentException("Student requires a name");
        }

        // Check that the last name is not null
        String lastName = values.getAsString(DatosPersonalesEntry.COLUMN_APELLIDOS);
        //name==null || name.isEmpty()
        if (lastName.equals("")) {
            throw new IllegalArgumentException("Student requires a last name");
        }

        // Check that the contact name is not null
        String contactName = values.getAsString(DatosPersonalesEntry.COLUMN_CONTACT_NAME);
        //name==null || name.isEmpty()
        if (contactName.equals("")) {
            throw new IllegalArgumentException("Student requires a contact name");
        }

        // Check that the contact phone is not null
        String contactPhone = values.getAsString(DatosPersonalesEntry.COLUMN_CONTACT_PHONE);
        //name==null || name.isEmpty()
        if (contactPhone.equals("")) {
            throw new IllegalArgumentException("Student requires a contact phone");
        }

        // If the weight is provided, check that it's greater than 150 or equal to 0 kg
        Integer weight = values.getAsInteger(DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT);
        if (weight != null && weight < 0 && weight >= 150 ) {
            throw new IllegalArgumentException("Student requires valid weight");
        }

        // If the height is provided, check that it's greater than 230 or equal to 0 cm
        Integer height = values.getAsInteger(DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT);
        if (height != null && weight < 0 && weight >= 230 ) {
            throw new IllegalArgumentException("Student requires valid height");
        }

        // Check that the NSS is not null
        String nss = values.getAsString(DatosPersonalesEntry.COLUMN_NSS);
        //name==null || name.isEmpty()
        if (nss.equals("")) {
            throw new IllegalArgumentException("Student requires a NSS");
        }



        // No need to check the breed, any value is valid (including null).

        // Insert the new datospersonal with the given values
        long id = database.insert(DatosPersonalesEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the datospersonal content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);


        /*// TODO: Insert a new datospersonal into the datospersonales database table with the given ContentValues

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the datospersonales table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto
        // Insert the new datospersonal with the given values
        long id = database.insert(DatosPersonalesEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);*/
    }

    /**
     * Insert a diarioemociones into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertDiarioEmociones(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Check that the name is not null
        String date = values.getAsString(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA);
        //name==null || name.isEmpty()
        if (date.equals("")) {
            throw new IllegalArgumentException("Diarioemocion requires a date");
        }

        // Check that the gender is valid
        Integer emotion = values.getAsInteger(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION);
        if (emotion == null || !DiarioEmocionesEntry.isValidEmocion(emotion)) {
            throw new IllegalArgumentException("Diarioemocion requires valid emotion");
        }

        /* No need to check the breed, any value is valid (including null).
        // Check that the name is not null
        String feel = values.getAsString(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE);
        //name==null || name.isEmpty()
        if (feel.equals("")) {
            throw new IllegalArgumentException("Diarioemocion requires a name");
        }

        // Check that the name is not null
        String tought = values.getAsString(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO);
        //name==null || name.isEmpty()
        if (tought.equals("")) {
            throw new IllegalArgumentException("Diarioemocion requires a name");
        }*/


        // No need to check the breed, any value is valid (including null).

        // Insert the new diarioemocion with the given values
        long id = database.insert(DiarioEmocionesEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the diarioemocion content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);


        /*// TODO: Insert a new diarioemocion into the diarioemociones database table with the given ContentValues

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the diarioemociones table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto
        // Insert the new diarioemocion with the given values
        long id = database.insert(DiariEmocionesEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);*/
    }

    /**
     * Insert a alergia into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertAlergias(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Check that the name is not null
        String date = values.getAsString(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA);
        //name==null || name.isEmpty()
        if (date.equals("")) {
            throw new IllegalArgumentException("alergia requires a date");
        }

        // Check that the gender is valid
        Integer alergia = values.getAsInteger(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
        if (alergia == null || !AlergiasEntry.isValidAlergia(alergia)) {
            throw new IllegalArgumentException("alergia requires valid alergia");
        }

        /* No need to check the breed, any value is valid (including null).
        // Check that the name is not null
        String feel = values.getAsString(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA);
        //name==null || name.isEmpty()
        if (feel.equals("")) {
            throw new IllegalArgumentException("alergias requires a name");
        }

        // Check that the name is not null
        String tought = values.getAsString(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
        //name==null || name.isEmpty()
        if (tought.equals("")) {
            throw new IllegalArgumentException("alergia requires a name");
        }*/


        // No need to check the breed, any value is valid (including null).

        // Insert the new alergias with the given values
        long id = database.insert(AlergiasEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the alergia content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);


        /*// TODO: Insert a new alergia into the alergia database table with the given ContentValues

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the alergia table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto
        // Insert the new alergia with the given values
        long id = database.insert(AlergiasEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);*/
    }


    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertHistorialMedico(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Check that the name is not null
        String date = values.getAsString(HistorialMedicoEntry.COLUMN_FECHA_HM);
        //name==null || name.isEmpty()
        if (date.equals("")) {
            throw new IllegalArgumentException("Registro requires a date");
        }

        // Check that the name is not null
        String diagnostic = values.getAsString(HistorialMedicoEntry.COLUMN_DIAGNOSTICO);
        //name==null || name.isEmpty()
        if (diagnostic.equals("")) {
            throw new IllegalArgumentException("Registro requires a diagnostic");
        }
        // No need to check the foto 1, any value is valid (including null).
        // No need to check the foto 2, any value is valid (including null).
        // No need to check the precio consulta 1, any value is valid (including null).
        // No need to check the nombre doctor 1, any value is valid (including null).



        // No need to check the breed, any value is valid (including null).

        // Insert the new pet with the given values
        long id = database.insert(HistorialMedicoEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);


        /*// TODO: Insert a new pet into the pets database table with the given ContentValues

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto
        // Insert the new pet with the given values
        long id = database.insert(PetEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);*/
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATOS_PERSONALES:
                // Delete all rows that match the selection and selection args
                //return database.delete(DatosPersonalesEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(DatosPersonalesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DATOS_PERSONALES_ID:
                // Delete a single row given by the ID in the URI
                selection = DatosPersonalesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                //return database.delete(DatosPersonalesEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(DatosPersonalesEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case DIARIO_EMOCIONES:
                // Delete all rows that match the selection and selection args
                //return database.delete(DiariEmocionesEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(DiarioEmocionesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DIARIO_EMOCIONES_ID:
                // Delete a single row given by the ID in the URI
                selection = DiarioEmocionesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                //return database.delete(DiariEmocionesEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(DiarioEmocionesEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case ALERGIAS:
                // Delete all rows that match the selection and selection args
                //return database.delete(AlergiasEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(AlergiasEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ALERGIAS_ID:
                // Delete a single row given by the ID in the URI
                selection = AlergiasEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                //return database.delete(AlergiasEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(AlergiasEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case HISTORIAL_MEDICO:
                // Delete all rows that match the selection and selection args
                //return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(HistorialMedicoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case HISTORIAL_MEDICO_ID:
                // Delete a single row given by the ID in the URI
                selection = HistorialMedicoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                //return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(HistorialMedicoEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows deleted
        return rowsDeleted;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATOS_PERSONALES:
                return updateDatosPersonales(uri, contentValues, selection, selectionArgs);
            case DATOS_PERSONALES_ID:
                // For the DATOSPERSONALES_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = DatosPersonalesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateDatosPersonales(uri, contentValues, selection, selectionArgs);

            case DIARIO_EMOCIONES:
                return updateDiarioEmociones(uri, contentValues, selection, selectionArgs);
            case DIARIO_EMOCIONES_ID:
                // For the DIARIO_EMOCIONES_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = DiarioEmocionesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateDiarioEmociones(uri, contentValues, selection, selectionArgs);

            case ALERGIAS:
                return updateAlergias(uri, contentValues, selection, selectionArgs);
            case ALERGIAS_ID:
                // For the ALERGIAS_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = AlergiasEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateAlergias(uri, contentValues, selection, selectionArgs);

            case HISTORIAL_MEDICO:
                return updateHistorialMedico(uri, contentValues, selection, selectionArgs);
            case HISTORIAL_MEDICO_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = HistorialMedicoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateHistorialMedico(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update datospersonales in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more datospersonales).
     * Return the number of rows that were successfully updated.
     */
    private int updateDatosPersonales(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link DatosPersonalesEntry#COLUMN_NOMBRES} key is present,
        // check that the name value is not null.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_MATRICULA)) {
            String matricula = values.getAsString(DatosPersonalesEntry.COLUMN_MATRICULA);
            if (matricula == null) {
                throw new IllegalArgumentException("Student requires a matricula");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_NOMBRES} key is present,
        // check that the name value is not null.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_NOMBRES)) {
            String name = values.getAsString(DatosPersonalesEntry.COLUMN_NOMBRES);
            if (name == null) {
                throw new IllegalArgumentException("Student requires a name");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_APELLIDOS} key is present,
        // check that the name value is not null.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_APELLIDOS)) {
            String lastname = values.getAsString(DatosPersonalesEntry.COLUMN_APELLIDOS);
            if (lastname == null) {
                throw new IllegalArgumentException("Student requires a last name");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_CONTACT_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_CONTACT_NAME)) {
            String contactName = values.getAsString(DatosPersonalesEntry.COLUMN_CONTACT_NAME);
            if (contactName == null) {
                throw new IllegalArgumentException("Student requires a contact name");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_CONTACT_PHONE} key is present,
        // check that the name value is not null.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_CONTACT_PHONE)) {
            String contactPhone = values.getAsString(DatosPersonalesEntry.COLUMN_CONTACT_PHONE);
            if (contactPhone == null) {
                throw new IllegalArgumentException("Student requires a contact phone");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_STUDENT_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Student requires valid weight");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_STUDENT_HEIGHT} key is present,
        // check that the height value is valid.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer height = values.getAsInteger(DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT);
            if (height != null && height < 0) {
                throw new IllegalArgumentException("Student requires valid height");
            }
        }

        // If the {@link DatosPersonalesEntry#COLUMN_NSS} key is present,
        // check that the name value is not null.
        if (values.containsKey(DatosPersonalesEntry.COLUMN_NSS)) {
            String nss = values.getAsString(DatosPersonalesEntry.COLUMN_NSS);
            if (nss == null) {
                throw new IllegalArgumentException("Student requires a NSS");
            }
        }

        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        //return database.update(DatosPersonalesEntry.TABLE_NAME, values, selection, selectionArgs);

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(DatosPersonalesEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

    /**
     * Update diarioemociones in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more diarioemociones).
     * Return the number of rows that were successfully updated.
     */
    private int updateDiarioEmociones(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link DiarioEmocionesEntry#COLUMN_DIARIOEMOCIONES_DATE} key is present,
        // check that the name value is not null.
        if (values.containsKey(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA)) {
            String date = values.getAsString(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA);
            if (date == null) {
                throw new IllegalArgumentException("Diarioemocion requires a date");
            }
        }

        // If the {@link DiarioEmocionesEntry#COLUMN_DIARIOEMOCIONES_EMOCION} key is present,
        // check that the gender value is valid.
        if (values.containsKey(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION)) {
            Integer emotion = values.getAsInteger(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION);
            if (emotion == null || !DiarioEmocionesEntry.isValidEmocion(emotion)) {
                throw new IllegalArgumentException("Diarioemocion requires valid emotion");
            }
        }

        /* No need to check the breed, any value is valid (including null).
        // If the {@link DiarioEmocionesEntry#COLUMN_DIARIOEMOCIONES_DATE} key is present,
        // check that the name value is not null.
        if (values.containsKey(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE)) {
            String feel = values.getAsString(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE);
            if (feel == null) {
                throw new IllegalArgumentException("Diarioemocion requires a date");
            }
        }

        // If the {@link DiarioEmocionesEntry#COLUMN_DIARIOEMOCIONES_DATE} key is present,
        // check that the name value is not null.
        if (values.containsKey(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO)) {
            String tought = values.getAsString(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO);
            if (tought == null) {
                throw new IllegalArgumentException("Diarioemocion requires a date");
            }
        }*/


        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        //return database.update(DiarioEmocionesEntry.TABLE_NAME, values, selection, selectionArgs);

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(DiarioEmocionesEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }


    /**
     * Update alergias in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more alergias).
     * Return the number of rows that were successfully updated.
     */
    private int updateAlergias(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link AlergiasEntry#COLUMN_ALERGIAS_FECHAHORA} key is present,
        // check that the name value is not null.
        if (values.containsKey(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA)) {
            String date = values.getAsString(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA);
            if (date == null) {
                throw new IllegalArgumentException("alergia requires a date");
            }
        }

        // If the {@link AlergiasEntry#COLUMN_ALERGIAS_NOMBRE} key is present,
        // check that the gender value is valid.
        if (values.containsKey(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE)) {
            Integer alergia = values.getAsInteger(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
            if (alergia == null || !AlergiasEntry.isValidAlergia(+alergia)) {
                throw new IllegalArgumentException("alergia requires valid alergia name");
            }
        }

        /* No need to check the breed, any value is valid (including null).
        // If the {@link AlergiasEntry#COLUMN_ALERGIAS_FECHAHORA} key is present,
        // check that the name value is not null.
        if (values.containsKey(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE)) {
            String feel = values.getAsString(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
            if (feel == null) {
                throw new IllegalArgumentException("alergia requires a date");
            }
        }

        // If the {@link AlergiasEntry#COLUMN_ALERGIAS_FECHAHORA} key is present,
        // check that the name value is not null.
        if (values.containsKey(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE)) {
            String tought = values.getAsString(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
            if (tought == null) {
                throw new IllegalArgumentException("alergia requires a date");
            }
        }*/


        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        //return database.update(AlergiasEntry.TABLE_NAME, values, selection, selectionArgs);

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(AlergiasEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateHistorialMedico(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(HistorialMedicoEntry.COLUMN_FECHA_HM)) {
            String date = values.getAsString(HistorialMedicoEntry.COLUMN_FECHA_HM);
            if (date == null) {
                throw new IllegalArgumentException("Register requires a date");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(HistorialMedicoEntry.COLUMN_DIAGNOSTICO)) {
            String diagnostic = values.getAsString(HistorialMedicoEntry.COLUMN_DIAGNOSTICO);
            if (diagnostic == null) {
                throw new IllegalArgumentException("Register requires a diagnostic");
            }
        }


            /*
        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(PetEntry.COLUMN_PET_GENDER)) {
            Integer gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
            if (gender == null || !PetEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(PetEntry.COLUMN_PET_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }*/

        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        //return database.update(PetEntry.TABLE_NAME, values, selection, selectionArgs);

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(HistorialMedicoEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    }

}
