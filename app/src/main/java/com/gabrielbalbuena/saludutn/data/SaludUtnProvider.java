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


import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DatosPersonalesEntry;//


public class SaludUtnProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = SaludUtnProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the pets table */
    private static final int DATOS_PERSONALES = 100;
    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int DATOS_PERSONALES_ID = 101;


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
        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_DATOS_PERSONALES, DATOS_PERSONALES);
        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PET_ID}. This URI is used to provide access to ONE single row
        // of the pets table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(SaludUtnContract.CONTENT_AUTHORITY, SaludUtnContract.PATH_DATOS_PERSONALES + "/#", DATOS_PERSONALES_ID);
    }


    //DataBase helper object
    private SaludUtnHelper mDbHelper;

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        // TODO: Create and initialize a PetDbHelper object to gain access to the pets database. Line 17
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
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table
                cursor = database.query(DatosPersonalesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DATOS_PERSONALES_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DatosPersonalesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DatosPersonalesEntry.TABLE_NAME, projection, selection, selectionArgs,
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
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
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

        // Insert the new pet with the given values
        long id = database.insert(DatosPersonalesEntry.TABLE_NAME, null, values);
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
                //return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(DatosPersonalesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DATOS_PERSONALES_ID:
                // Delete a single row given by the ID in the URI
                selection = DatosPersonalesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                //return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
                rowsDeleted = database.delete(DatosPersonalesEntry.TABLE_NAME, selection, selectionArgs);
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
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = DatosPersonalesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateDatosPersonales(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
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
        //return database.update(PetEntry.TABLE_NAME, values, selection, selectionArgs);

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
}