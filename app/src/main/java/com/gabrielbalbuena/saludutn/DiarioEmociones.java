package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;//4
import android.database.sqlite.SQLiteDatabase;//2
import android.net.Uri;
import android.os.Bundle;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract;
import com.gabrielbalbuena.saludutn.data.SaludUtnHelper;

import com.gabrielbalbuena.saludutn.data.SaludUtnProvider;
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


import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DiarioEmocionesEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class DiarioEmociones extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DIARIOEMOCIONES_LOADER = 0;

    DiarioEmocionesCursorAdapter mCursorAdapter;

    /** Database helper that will provide us access to the database */
    //private PetDbHelper mDbHelper; Finish

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_emociones);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiarioEmociones.this, EditorDiarioEmociones.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView diarioEmocionesListView = (ListView) findViewById(R.id.list_diario_emociones);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        diarioEmocionesListView.setEmptyView(emptyView);

        //Setup an Adapter to create a list item for each row of pet data in the Cursor.
        //There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter= new DiarioEmocionesCursorAdapter(this,null);
        diarioEmocionesListView.setAdapter(mCursorAdapter);

        //Kick off the loader
        //getLoaderManager().initLoader(PET_LOADER, null, this);
        getSupportLoaderManager().initLoader(DIARIOEMOCIONES_LOADER, null, this);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //mDbHelper = new PetDbHelper(this);

        //displayDatabaseInfo();

        //Setup item click listener
        diarioEmocionesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(DiarioEmociones.this, EditorDiarioEmociones.class);

                //Form the content URI that represents the specific pet that was clicked on,
                //by appending the "id" (passed as input to this method) onto the
                //{@link PetEntry#CONTENT_URI}.
                //For example, the URI would be "content://com.example.android.pets/pets/2"
                //if the pet with ID 2 was clicked on.
                Uri currentDiarioEmocionesUri = ContentUris.withAppendedId(DiarioEmocionesEntry.CONTENT_URI, id);

                //Set the URI on the data field of the intent
                intent.setData(currentDiarioEmocionesUri);

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
    private void insertDiarioEociones() {
        // Gets the database in write mode
        //SQLiteDatabase db = mDbHelper.getWritableDatabase(); //Code Comment BAD PRACTICE

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA, "24042021");
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION, DiarioEmocionesEntry.EMOCION_ORGULLO);
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE,
                "I used to rule the world\n" +
                "Seas would rise when I gave the word\n" +
                "Now in the morning I sleep alone\n" +
                "Sweep the streets I used to own");
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO,
                "I used to roll the dice\n" +
                "Feel the fear in my enemy's eyes\n" +
                "Listen as the crowd would sing\n" +
                "Now the old king is dead! Long live the king");

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
        Uri newUri = getContentResolver().insert(DiarioEmocionesEntry.CONTENT_URI, values);

        //Log.v("CatalogActivity", "New Row ID: " + newRowId);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllDiarioEmociones() {
        int rowsDeleted = getContentResolver().delete(DiarioEmocionesEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog_diario_emociones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDiarioEociones();
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllDiarioEmociones();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //Define a projection that specifies the columns from the table we care about
        String[] projection = {
                DiarioEmocionesEntry._ID,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO };

        //This loader will execute the ContentProvider´s query method on a background thread
        return new CursorLoader(this,   //Parent activity context
                DiarioEmocionesEntry.CONTENT_URI,           //Provider content URI to query
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