package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.AlergiasEntry;
import com.gabrielbalbuena.saludutn.data.SaludUtnHelper;

import java.util.Calendar;

/**
 * Allows user to create a new alergia or edit an existing one.
 */
public class EditorAlergias extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the alergia data loader */
    private static final int EXISTING_ALERGIAS_LOADER = 0;

    /** Content URI for the existing alergia (null if it's a new alergia) */
    private Uri mCurrentAlergiasUri;

    /** EditText field to enter the alergia's date */
    private TextView mDateEditText;
    //private EditText mDateEditText;

    /** EditText field to enter the alergias's alergia */
    private Spinner mAlergiasNombreSpinner;

    /** EditText field to enter the alergias's feel */
    private EditText mTipoAlergiaEditText;

    /** EditText field to enter the alergias's thought */
    private EditText mComentarioAlergiaEditText;




    /**
     * Gender of the alergia. The possible valid values are in the SaludUtnContract.java file:
     * {@link AlergiasEntry#ALERGIA_UNKNOWN}, {@link AlergiasEntry#ALERGIA_HUEVOS}, or
     * {@link AlergiasEntry#ALERGIA_PESCADO}...
     */
    private int mAlergiaName = AlergiasEntry.ALERGIA_UNKNOWN;

    /** Boolean flag that keeps track of whether the alergias has been edited (true) or not (false) */
    private boolean mAlergiasHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mDiarioEmocionesHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mAlergiasHasChanged = true;
            return false;
        }
    };



    //Refrencias TextView //Calendario
    TextView tv; //Calendario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_alergias);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new alergias or editing an existing one.
        Intent intent = getIntent();
        mCurrentAlergiasUri = intent.getData();

        // If the intent DOES NOT contain a alergia content URI, then we know that we are
        // creating a new alergia.
        if (mCurrentAlergiasUri == null) {
            // This is a new alergia, so change the app bar to say "Add a Alergia"
            setTitle(getString(R.string.editor_activity_title_new_diarioemociones));
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a alergia that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing alergia, so change app bar to say "Edit alergia"
            setTitle(getString(R.string.editor_activity_title_edit_diarioemociones));

            // Initialize a loader to read the alergia data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_ALERGIAS_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mDateEditText = (TextView) findViewById(R.id.edit_date);
        //mDateEditText = (EditText) findViewById(R.id.edit_date);
        mAlergiasNombreSpinner = (Spinner) findViewById(R.id.spinner_emotion);
        mTipoAlergiaEditText = (EditText) findViewById(R.id.edit_feel);
        mComentarioAlergiaEditText = (EditText) findViewById(R.id.edit_tought);


        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mDateEditText.setOnTouchListener(mTouchListener);
        mAlergiasNombreSpinner.setOnTouchListener(mTouchListener);
        mTipoAlergiaEditText.setOnTouchListener(mTouchListener);
        mComentarioAlergiaEditText.setOnTouchListener(mTouchListener);

        setupSpinner();

        tv = findViewById(R.id.edit_date);//Calendario
    }

    public void abrirCalendario(View view) {//Calendario
        Calendar cal = Calendar.getInstance();//Obtener un calendario
        int anio = cal.get(Calendar.YEAR);//Calendario
        int mes = cal.get(Calendar.MONTH);//Calendario
        int dia = cal.get(Calendar.DAY_OF_MONTH);//Calendario

        //Inicializar el day picker dialog//Calendario
        DatePickerDialog dpd = new DatePickerDialog(EditorAlergias.this, new DatePickerDialog.OnDateSetListener() {//Calendario
            @Override//Calendario
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {//Calendario
                String fecha = dayOfMonth + "/" + month + "/" + year;//Calendario
                tv.setText(fecha);//Calendario
            }//Calendario
        },anio, mes, dia);//Calendario
        dpd.show();//Calendario
    }//Calendario


    /**
     * Setup the dropdown spinner that allows the user to select the gender of the alergia.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter alergiaSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_alergias_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        alergiaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mAlergiasNombreSpinner.setAdapter(alergiaSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mAlergiasNombreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.alergia_pescado))) {
                        mAlergiaName = AlergiasEntry.ALERGIA_PESCADO;
                    } else if (selection.equals(getString(R.string.alergia_huevo))) {
                        mAlergiaName = AlergiasEntry.ALERGIA_HUEVOS;
                    } /*else if (selection.equals(getString(R.string.emotion_alegria))) {
                        mAlergiaName = AlergiasEntry.ALERGIA_UNKNOWN;
                    }/* else if (selection.equals(getString(R.string.emotion_amor))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_AMOR;
                    } else if (selection.equals(getString(R.string.emotion_asco))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_ASCO;
                        //------------------------------------------------>
                    } else if (selection.equals(getString(R.string.emotion_culpabilidad))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_CULPABILIDAD;
                    } else if (selection.equals(getString(R.string.emotion_desesperacion))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_DESESPERACION;
                    } else if (selection.equals(getString(R.string.emotion_diversion))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_DIVERSION;
                    } else if (selection.equals(getString(R.string.emotion_esperanza))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_ESPERANZA;
                    } else if (selection.equals(getString(R.string.emotion_gratitud))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_GRATITUD;
                        //------------------------------------------------->
                    } else if (selection.equals(getString(R.string.emotion_indiferencia))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_INDIFERENCIA;
                    } else if (selection.equals(getString(R.string.emotion_inspiracion))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_INSPIRACION;
                    } else if (selection.equals(getString(R.string.emotion_interes))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_INTERES;
                    } else if (selection.equals(getString(R.string.emotion_ira))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_IRA;
                    } else if (selection.equals(getString(R.string.emotion_miedo))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_MIEDO;
                        //------------------------------------------------>
                    } else if (selection.equals(getString(R.string.emotion_orgullo))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_ORGULLO;
                    } else if (selection.equals(getString(R.string.emotion_serenidad))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_SERENDIDAD;
                    } else if (selection.equals(getString(R.string.emotion_soledad))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_SOLEDAD;
                    } else if (selection.equals(getString(R.string.emotion_tristeza))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_TRISTEZA;
                    }*/else {
                        mAlergiaName = AlergiasEntry.ALERGIA_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAlergiaName = AlergiasEntry.ALERGIA_UNKNOWN;
            }
        });
    }

    /**
     * ------Get user input from editor and save new alergia into database.
     * Get user input from editor and save alergia into database.
     */
    //private void insertDiarioEmociones()
    private void saveAlergias() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = mDateEditText.getText().toString().trim();
        String alergyTypeString = mTipoAlergiaEditText.getText().toString().trim();
        String commentAlergyString = mComentarioAlergiaEditText.getText().toString().trim();
        //int weight = Integer.parseInt(weightString);

        // Check if this is supposed to be a new alergias
        // and check if all the fields in the editor are blank
        if (mCurrentAlergiasUri == null &&
                TextUtils.isEmpty(dateString) && TextUtils.isEmpty(alergyTypeString) &&
                TextUtils.isEmpty(commentAlergyString) && mAlergiaName == AlergiasEntry.ALERGIA_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new alergia.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and alergia attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA, dateString);
        values.put(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE, mAlergiaName);
        values.put(AlergiasEntry.COLUMN_TIPO_ALERGIA, alergyTypeString);
        values.put(AlergiasEntry.COLUMN_COMENTARIO_ALERGIA, commentAlergyString);


        // Determine if this is a new or existing alergia by checking if mCurrentAlergiassUri is null or not
        if (mCurrentAlergiasUri == null) {
            // This is a NEW alergia, so insert a new alergia into the provider,
            // returning the content URI for the new alergia.
            Uri newUri = getContentResolver().insert(AlergiasEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_diarioemocion_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_diarioemocion_successful),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            // Otherwise this is an EXISTING alergia, so update the alergia with content URI: mCurrentAlergiasUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentAlergiasUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentAlergiasUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_diarioemocion_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_diarioemocion_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor_alergias, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new alergias, hide the "Delete" menu item.
        if (mCurrentAlergiasUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView fechaEditText = (TextView)findViewById(R.id.edit_date);
        //TextView fechaEditText = (TextView)findViewById(R.id.edit_date);
        //EditText fechaEditText = (EditText)findViewById(R.id.edit_date);
        String fecha  =  fechaEditText.getText().toString();
        Log.d("Numero", "La fecha es: " + fecha);

        Spinner alergiaNombreEditText = (Spinner)findViewById(R.id.spinner_emotion);
        String alergiaNombreString = alergiaNombreEditText.getSelectedItem().toString();
        //Object alergiaNombreString = alergiaNombreEditText.getSelectedItem();
        Log.d("Numero", "El spiner trae: " + alergiaNombreString);


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if (fecha.equals("Dia/Mes/Año")){
                    Toast.makeText(this, "Necesitas añadir la fecha", Toast.LENGTH_LONG).show();
                } else if (alergiaNombreString.equals("Selecciona una alergia")){
                    Toast.makeText(this, "Debes elegir una alergia", Toast.LENGTH_LONG).show();
                } else {
                    // Save diarioemocion to database
                    saveAlergias();
                    // Exit activity
                    finish();
                    return true;
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                //NavUtils.navigateUpFromSameTask(this);

                // If the alergias hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mAlergiasHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorAlergias.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorAlergias.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the alergia hasn't changed, continue with handling back button press
        if (!mAlergiasHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all alergia attributes, define a projection that contains
        // all columns from the alergia table
        String[] projection = {
                AlergiasEntry._ID,
                AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA,
                AlergiasEntry.COLUMN_ALERGIAS_NOMBRE,
                AlergiasEntry.COLUMN_TIPO_ALERGIA,
                AlergiasEntry.COLUMN_COMENTARIO_ALERGIA };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentAlergiasUri,         // Query the content URI for the current diarioemocion
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of alergia attributes that we're interested in
            int dateColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA);
            int alergyNameColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
            int typeAlergyColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_TIPO_ALERGIA);
            int commentAlergyColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_COMENTARIO_ALERGIA);

            // Extract out the value from the Cursor for the given column index
            String date = cursor.getString(dateColumnIndex);
            int alergyName = cursor.getInt(alergyNameColumnIndex);
            String typeAlergy = cursor.getString(typeAlergyColumnIndex);
            String commentAlergy = cursor.getString(commentAlergyColumnIndex);

            // Update the views on the screen with the values from the database
            mDateEditText.setText(date);
            mTipoAlergiaEditText.setText(typeAlergy);
            mComentarioAlergiaEditText.setText(commentAlergy);

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (alergyName) {
                case AlergiasEntry.ALERGIA_PESCADO:
                    mAlergiasNombreSpinner.setSelection(1);
                    break;
                case AlergiasEntry.ALERGIA_HUEVOS:
                    mAlergiasNombreSpinner.setSelection(2);
                    break;
                /*case DiarioEmocionesEntry.EMOCION_ALEGRIA:
                    mEmotionSpinner.setSelection(3);
                    break;
                case DiarioEmocionesEntry.EMOCION_AMOR:
                    mEmotionSpinner.setSelection(4);
                    break;
                case DiarioEmocionesEntry.EMOCION_ASCO:
                    mEmotionSpinner.setSelection(5);
                    break;
                //------------------------------->
                case DiarioEmocionesEntry.EMOCION_CULPABILIDAD:
                    mEmotionSpinner.setSelection(6);
                    break;
                case DiarioEmocionesEntry.EMOCION_DESESPERACION:
                    mEmotionSpinner.setSelection(7);
                    break;
                case DiarioEmocionesEntry.EMOCION_DIVERSION:
                    mEmotionSpinner.setSelection(8);
                    break;
                case DiarioEmocionesEntry.EMOCION_ESPERANZA:
                    mEmotionSpinner.setSelection(9);
                    break;
                case DiarioEmocionesEntry.EMOCION_GRATITUD:
                    mEmotionSpinner.setSelection(10);
                    break;
                //------------------------------->
                case DiarioEmocionesEntry.EMOCION_INDIFERENCIA:
                    mEmotionSpinner.setSelection(11);
                    break;
                case DiarioEmocionesEntry.EMOCION_INSPIRACION:
                    mEmotionSpinner.setSelection(12);
                    break;
                case DiarioEmocionesEntry.EMOCION_INTERES:
                    mEmotionSpinner.setSelection(13);
                    break;
                case DiarioEmocionesEntry.EMOCION_IRA:
                    mEmotionSpinner.setSelection(14);
                    break;
                case DiarioEmocionesEntry.EMOCION_MIEDO:
                    mEmotionSpinner.setSelection(15);
                    break;
                //------------------------------->
                case DiarioEmocionesEntry.EMOCION_ORGULLO:
                    mEmotionSpinner.setSelection(16);
                    break;
                case DiarioEmocionesEntry.EMOCION_SERENDIDAD:
                    mEmotionSpinner.setSelection(17);
                    break;
                case DiarioEmocionesEntry.EMOCION_SOLEDAD:
                    mEmotionSpinner.setSelection(18);
                    break;
                case DiarioEmocionesEntry.EMOCION_TRISTEZA:
                    mEmotionSpinner.setSelection(19);
                    break;*/
                default:
                    mAlergiasNombreSpinner.setSelection(0);
                    break;
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mDateEditText.setText("");
        mAlergiasNombreSpinner.setSelection(0); // Select "Unknown" gender
        mTipoAlergiaEditText.setText("");
        mComentarioAlergiaEditText.setText("");
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the alergia.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the alergia.
                deleteAlergias();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the alergias.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the alergias in the database.
     */
    private void deleteAlergias() {
        // Only perform the delete if this is an existing alergias.
        if (mCurrentAlergiasUri != null) {
            // Call the ContentResolver to delete the alergias at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentAlergiasUri
            // content URI already identifies the alergia that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentAlergiasUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_diarioemocion_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_diarioemocion_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }
}