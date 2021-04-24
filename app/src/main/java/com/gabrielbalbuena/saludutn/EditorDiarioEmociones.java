package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DiarioEmocionesEntry;
import com.gabrielbalbuena.saludutn.data.SaludUtnHelper;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorDiarioEmociones extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int EXISTING_DIARIOEMOCIONES_LOADER = 0;

    /** Content URI for the existing pet (null if it's a new pet) */
    private Uri mCurrentDiarioEmocionesUri;

    /** EditText field to enter the pet's name */
    private EditText mDateEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mEmotionSpinner;

    /** EditText field to enter the pet's weight */
    private EditText mFeelEditText;

    /** EditText field to enter the pet's breed */
    private EditText mToughtEditText;




    /**
     * Gender of the pet. The possible valid values are in the PetContract.java file:
     * {@link DiarioEmocionesEntry#EMOCION_UNKNOWN}, {@link DiarioEmocionesEntry#EMOCION_MALO}, or
     * {@link DiarioEmocionesEntry#EMOCION_BUENO}.
     */
    private int mEmotion = DiarioEmocionesEntry.EMOCION_UNKNOWN;

    /** Boolean flag that keeps track of whether the pet has been edited (true) or not (false) */
    private boolean mDiarioEmociomesHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mDiarioEmociomesHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_diario_emociones);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getIntent();
        mCurrentDiarioEmocionesUri = intent.getData();

        // If the intent DOES NOT contain a pet content URI, then we know that we are
        // creating a new pet.
        if (mCurrentDiarioEmocionesUri == null) {
            // This is a new pet, so change the app bar to say "Add a Pet"
            setTitle(getString(R.string.editor_activity_title_new_pet));
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing pet, so change app bar to say "Edit Pet"
            setTitle(getString(R.string.editor_activity_title_edit_pet));

            // Initialize a loader to read the pet data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_DIARIOEMOCIONES_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mDateEditText = (EditText) findViewById(R.id.edit_date);
        mEmotionSpinner = (Spinner) findViewById(R.id.spinner_emotion);
        mFeelEditText = (EditText) findViewById(R.id.edit_feel);
        mToughtEditText = (EditText) findViewById(R.id.edit_tought);


        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mDateEditText.setOnTouchListener(mTouchListener);
        mEmotionSpinner.setOnTouchListener(mTouchListener);
        mFeelEditText.setOnTouchListener(mTouchListener);
        mToughtEditText.setOnTouchListener(mTouchListener);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter emotionSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_emotion_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        emotionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mEmotionSpinner.setAdapter(emotionSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mEmotionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.emotion_malo))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_MALO;
                    } else if (selection.equals(getString(R.string.emotion_bueno))) {
                        mEmotion = DiarioEmocionesEntry.EMOCION_BUENO;
                    } else {
                        mEmotion = DiarioEmocionesEntry.EMOCION_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mEmotion = DiarioEmocionesEntry.EMOCION_UNKNOWN;
            }
        });
    }

    /**
     * ------Get user input from editor and save new pet into database.
     * Get user input from editor and save pet into database.
     */
    //private void insertPet()
    private void saveDiarioEmociones() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = mDateEditText.getText().toString().trim();
        String feelString = mFeelEditText.getText().toString().trim();
        String toughtString = mToughtEditText.getText().toString().trim();
        //int weight = Integer.parseInt(weightString);

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentDiarioEmocionesUri == null &&
                TextUtils.isEmpty(dateString) && TextUtils.isEmpty(feelString) &&
                TextUtils.isEmpty(toughtString) && mEmotion == DiarioEmocionesEntry.EMOCION_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA, dateString);
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION, mEmotion);
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE, feelString);
        values.put(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO, toughtString);


        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentDiarioEmocionesUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(DiarioEmocionesEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentDiarioEmocionesUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor_diario_emociones, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentDiarioEmocionesUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText fechaEditText = (EditText)findViewById(R.id.edit_date);
        String fecha  =  fechaEditText.getText().toString();

        /*Spinner emocionEditText = (Spinner)findViewById(R.id.spinner_emotion);
        String emocion  =  emocionEditText.getText().toString();*/


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if (TextUtils.isEmpty(fecha)){
                    Toast.makeText(this, "Necesitas añadir la fecha", Toast.LENGTH_LONG).show();
                } else {
                    // Save pet to database
                    saveDiarioEmociones();
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

                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mDiarioEmociomesHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorDiarioEmociones.this);
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
                                NavUtils.navigateUpFromSameTask(EditorDiarioEmociones.this);
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
        // If the pet hasn't changed, continue with handling back button press
        if (!mDiarioEmociomesHasChanged) {
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
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                DiarioEmocionesEntry._ID,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE,
                DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentDiarioEmocionesUri,         // Query the content URI for the current pet
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
            // Find the columns of pet attributes that we're interested in
            int dateColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA);
            int emotionColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION);
            int feelColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE);
            int toughtColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO);

            // Extract out the value from the Cursor for the given column index
            String date = cursor.getString(dateColumnIndex);
            int emotion = cursor.getInt(emotionColumnIndex);
            String feel = cursor.getString(feelColumnIndex);
            String tought = cursor.getString(toughtColumnIndex);

            // Update the views on the screen with the values from the database
            mDateEditText.setText(date);
            mFeelEditText.setText(feel);
            mToughtEditText.setText(tought);

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (emotion) {
                case DiarioEmocionesEntry.EMOCION_MALO:
                    mEmotionSpinner.setSelection(1);
                    break;
                case DiarioEmocionesEntry.EMOCION_BUENO:
                    mEmotionSpinner.setSelection(2);
                    break;
                default:
                    mEmotionSpinner.setSelection(0);
                    break;
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mDateEditText.setText("");
        mEmotionSpinner.setSelection(0); // Select "Unknown" gender
        mFeelEditText.setText("");
        mToughtEditText.setText("");
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
                // and continue editing the pet.
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
                // User clicked the "Delete" button, so delete the pet.
                deleteDiarioEmociones();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
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
     * Perform the deletion of the pet in the database.
     */
    private void deleteDiarioEmociones() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentDiarioEmocionesUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentDiarioEmocionesUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }
}