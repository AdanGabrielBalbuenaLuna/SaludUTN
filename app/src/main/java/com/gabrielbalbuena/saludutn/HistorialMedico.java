package com.gabrielbalbuena.saludutn;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;//4
import android.database.sqlite.SQLiteDatabase;//2
import android.net.Uri;
import android.os.Bundle;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract;//
import com.gabrielbalbuena.saludutn.data.SaludUtnHelper;//
import com.gabrielbalbuena.saludutn.data.SaludUtnProvider;
import com.gabrielbalbuena.saludutn.data.SaludUtnContract.HistorialMedicoEntry;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;//5
import android.widget.Toast;



/**
 * Displays list of pets that were entered and stored in the app.
 */
public class HistorialMedico extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int HISTORIAL_MEDICO_LOADER = 0;

    HistorialMedicoCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_medico);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistorialMedico.this, EditorHistorialMedico.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView historialMedicoListView = (ListView) findViewById(R.id.list_historial_medico);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        historialMedicoListView.setEmptyView(emptyView);

        //Setup an Adapter to create a list item for each row of pet data in the Cursor.
        //There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter= new HistorialMedicoCursorAdapter(this,null);
        historialMedicoListView.setAdapter(mCursorAdapter);

        //Kick off the loader
        //getLoaderManager().initLoader(PET_LOADER, null, this);
        getSupportLoaderManager().initLoader(HISTORIAL_MEDICO_LOADER, null, this);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //mDbHelper = new PetDbHelper(this);

        //displayDatabaseInfo();

        //Setup item click listener
        historialMedicoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(HistorialMedico.this, EditorHistorialMedico.class);

                //Form the content URI that represents the specific pet that was clicked on,
                //by appending the "id" (passed as input to this method) onto the
                //{@link PetEntry#CONTENT_URI}.
                //For example, the URI would be "content://com.example.android.pets/pets/2"
                //if the pet with ID 2 was clicked on.
                Uri currentHistorialMedicoUri = ContentUris.withAppendedId(HistorialMedicoEntry.CONTENT_URI, id);

                //Set the URI on the data field of the intent
                intent.setData(currentHistorialMedicoUri);

                //Launch the{@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //displayDatabaseInfo();
    }




    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertHistorialMedico() {
        // Gets the database in write mode
        //SQLiteDatabase db = mDbHelper.getWritableDatabase(); //Code Comment BAD PRACTICE

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HistorialMedicoEntry.COLUMN_FECHA_HM, "1/05/2021");
        values.put(HistorialMedicoEntry.COLUMN_DIAGNOSTICO, "Afortunadamente nada");
        values.put(HistorialMedicoEntry.COLUMN_FOTO_UNO_URL, "URL 1");
        values.put(HistorialMedicoEntry.COLUMN_FOTO_DOS_URL, "url 2");
        values.put(HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA, 200);
        values.put(HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR, "Gabriel Balbuena");
        values.put(HistorialMedicoEntry.COLUMN_ESPECIALIDAD, HistorialMedicoEntry.ESPECIALIDAD_UNKNOWN);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto
        //long newRowId = db.insert(PetEntry.TABLE_NAME, null, values); //Code Comment BAD PRACTICE
        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(HistorialMedicoEntry.CONTENT_URI, values);

        //Log.v("CatalogActivity", "New Row ID: " + newRowId);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllHistorialMedico() {
        int rowsDeleted = getContentResolver().delete(HistorialMedicoEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog_historial_medico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            //case R.id.action_insert_dummy_data_historial_medico:
                //insertHistorialMedico();
                //displayDatabaseInfo();
                //return true;
            // Respond to a click on the "Delete all entries" menu option
            //case R.id.action_delete_all_entries:
                // Do nothing for now
                //deleteAllHistorialMedico();
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //Define a projection that specifies the columns from the table we care about
        String[] projection = {
                HistorialMedicoEntry._ID,
                HistorialMedicoEntry.COLUMN_FECHA_HM,
                HistorialMedicoEntry.COLUMN_DIAGNOSTICO,
                HistorialMedicoEntry.COLUMN_FOTO_UNO_URL,
                HistorialMedicoEntry.COLUMN_FOTO_DOS_URL,
                HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA,
                HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR,
                HistorialMedicoEntry.COLUMN_ESPECIALIDAD};

        //This loader will execute the ContentProvider´s query method on a background thread
        return new CursorLoader(this,   //Parent activity context
                HistorialMedicoEntry.CONTENT_URI,           //Provider content URI to query
                projection,                     //Columns to include in the resulting Cursor
                null,                   //No selection clause
                null,               //No selection arguments
                null);                 //Default sort order
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        //Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //Callback called when the data need to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
