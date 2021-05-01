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


import com.gabrielbalbuena.saludutn.data.SaludUtnContract.AlergiasEntry;

/**
 * Displays list of alergias that were entered and stored in the app.
 */
public class Alergias extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ALERGIAS_LOADER = 0;

    AlergiasCursorAdapter mCursorAdapter;

    /** Database helper that will provide us access to the database */
    //private SaludUtnDbHelper mDbHelper; Finish

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alergias);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Alergias.this, EditorAlergias.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the alergia data
        ListView alergiasListView = (ListView) findViewById(R.id.list_alergias);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        alergiasListView.setEmptyView(emptyView);

        //Setup an Adapter to create a list item for each row of alergia data in the Cursor.
        //There is no alergia data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter= new AlergiasCursorAdapter(this,null);
        alergiasListView.setAdapter(mCursorAdapter);

        //Kick off the loader
        //getLoaderManager().initLoader(ALERGIAS_LOADER, null, this);
        getSupportLoaderManager().initLoader(ALERGIAS_LOADER, null, this);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //mDbHelper = new SaludUtnDbHelper(this);

        //displayDatabaseInfo();

        //Setup item click listener
        alergiasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(Alergias.this, EditorAlergias.class);

                //Form the content URI that represents the specific alergia that was clicked on,
                //by appending the "id" (passed as input to this method) onto the
                //{@link AlergiasEntry#CONTENT_URI}.
                //For example, the URI would be "content://com.example.android.saludutn/alergias/2"
                //if the alergia with ID 2 was clicked on.
                Uri currentAlergiaUri = ContentUris.withAppendedId(AlergiasEntry.CONTENT_URI, id);

                //Set the URI on the data field of the intent
                intent.setData(currentAlergiaUri);

                //Launch the{@link EditorActivity} to display the data for the current alergia.
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
     * Helper method to insert hardcoded alergia data into the database. For debugging purposes only.
     */
    private void insertAlergias() {
        // Gets the database in write mode
        //SQLiteDatabase db = mDbHelper.getWritableDatabase(); //Code Comment BAD PRACTICE

        // Create a ContentValues object where column names are the keys,
        // and Toto's alergia attributes are the values.
        ContentValues values = new ContentValues();
        values.put(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA, "25/20/21");
        values.put(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE, AlergiasEntry.ALERGIA_UNKNOWN);
        values.put(AlergiasEntry.COLUMN_TIPO_ALERGIA, "Esto debe ocultarse");
        values.put(AlergiasEntry.COLUMN_COMENTARIO_ALERGIA,
                "One minute I held the key\n" +
                        "Next the walls were closed on me\n" +
                        "And I discovered that my castles stand\n" +
                        "Upon pillars of salt and pillars of sand");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the alergias table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto
        //long newRowId = db.insert(AlergiasEntry.TABLE_NAME, null, values); //Code Comment BAD PRACTICE
        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link AlergiasEntry#CONTENT_URI} to indicate that we want to insert
        // into the alergias database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(AlergiasEntry.CONTENT_URI, values);

        //Log.v("CatalogActivity", "New Row ID: " + newRowId);
    }

    /**
     * Helper method to delete all alergias in the database.
     */
    private void deleteAllAlergias() {
        int rowsDeleted = getContentResolver().delete(AlergiasEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from alergias database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog_alergias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            //case R.id.action_insert_dummy_data:
                //insertAlergias();
                //displayDatabaseInfo();
                //return true;
            // Respond to a click on the "Delete all entries" menu option
            //case R.id.action_delete_all_entries:
                // Do nothing for now
                //deleteAllAlergias();
                //return true;
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //Define a projection that specifies the columns from the table we care about
        String[] projection = {
                AlergiasEntry._ID,
                AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA,
                AlergiasEntry.COLUMN_ALERGIAS_NOMBRE,
                AlergiasEntry.COLUMN_TIPO_ALERGIA,
                AlergiasEntry.COLUMN_COMENTARIO_ALERGIA };

        //This loader will execute the ContentProvider´s query method on a background thread
        return new CursorLoader(this,   //Parent activity context
                AlergiasEntry.CONTENT_URI,           //Provider content URI to query
                projection,                     //Columns to include in the resulting Cursor
                null,                   //No selection clause
                null,               //No selection arguments
                null);                 //Default sort order
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        //Update {@link AlergiasCursorAdapter} with this new cursor containing updated alergia data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //Callback called when the data need to be deleted
        mCursorAdapter.swapCursor(null);
    }
}