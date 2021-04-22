package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import androidx.core.app.NavUtils;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DatosPersonalesEntry;
/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorDatosPersonales extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int EXISTING_PET_LOADER = 0;

    /** Content URI for the existing pet (null if it's a new pet) */
    private Uri mCurrentDatosPersonalesUri;

    /** EditText field to enter the student's matricula */
    private EditText mMatriculaEditText;

    /** EditText field to enter the student's name */
    private EditText mNameEditText;

    /** EditText field to enter the student's last name */
    private EditText mLastNameEditText;

    /** EditText field to enter the student's contact name */
    private EditText mContactNameEditText;

    /** EditText field to enter the student's contact name */
    private EditText mContactPhoneEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's height */
    private EditText mHeightEditText;

    /** EditText field to enter the student's NSS */
    private EditText mNssEditText;



    /** Boolean flag that keeps track of whether the pet has been edited (true) or not (false) */
    private boolean mPetHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_datos_personales);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getIntent();
        mCurrentDatosPersonalesUri = intent.getData();

        // If the intent DOES NOT contain a pet content URI, then we know that we are
        // creating a new pet.
        if (mCurrentDatosPersonalesUri == null) {
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
            getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mMatriculaEditText = (EditText) findViewById(R.id.edit_matricula_dp);
        mNameEditText = (EditText) findViewById(R.id.edit_name_dp);
        mLastNameEditText = (EditText) findViewById(R.id.edit_last_name_db);
        mContactNameEditText = (EditText) findViewById(R.id.edit_contact_name_dp);
        mContactPhoneEditText = (EditText) findViewById(R.id.edit_contact_phone_dp);
        mWeightEditText = (EditText) findViewById(R.id.edit_weight_db);
        mHeightEditText = (EditText) findViewById(R.id.edit_height_db);
        mNssEditText = (EditText) findViewById(R.id.edit_nss_dp);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mMatriculaEditText.setOnTouchListener(mTouchListener);
        mNameEditText.setOnTouchListener(mTouchListener);
        mLastNameEditText.setOnTouchListener(mTouchListener);
        mContactNameEditText.setOnTouchListener(mTouchListener);
        mContactPhoneEditText.setOnTouchListener(mTouchListener);
        mNameEditText.setOnTouchListener(mTouchListener);
        mWeightEditText.setOnTouchListener(mTouchListener);
        mHeightEditText.setOnTouchListener(mTouchListener);
        mNssEditText.setOnTouchListener(mTouchListener);
    }



    /**
     * ------Get user input from editor and save new pet into database.
     * Get user input from editor and save pet into database.
     */
    //private void insertPet()
    private void saveDatosPersonales() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String matriculaString = mMatriculaEditText.getText().toString().trim();
        String nameString = mNameEditText.getText().toString().trim();
        String lastNameString = mLastNameEditText.getText().toString().trim();
        String contactNameString = mContactNameEditText.getText().toString().trim();
        String contactPhoneString = mContactPhoneEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        String heightString = mHeightEditText.getText().toString().trim();
        String nssString = mNssEditText.getText().toString().trim();
        //int weight = Integer.parseInt(weightString);

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentDatosPersonalesUri == null &&
                TextUtils.isEmpty(matriculaString) &&
                TextUtils.isEmpty(nameString) &&
                TextUtils.isEmpty(lastNameString) &&
                TextUtils.isEmpty(contactNameString) &&
                TextUtils.isEmpty(contactPhoneString) &&
                TextUtils.isEmpty(weightString) &&
                TextUtils.isEmpty(heightString) &&
                TextUtils.isEmpty(nssString)
        ) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(DatosPersonalesEntry.COLUMN_MATRICULA, matriculaString);
        values.put(DatosPersonalesEntry.COLUMN_NOMBRES, nameString);
        values.put(DatosPersonalesEntry.COLUMN_APELLIDOS, lastNameString);
        values.put(DatosPersonalesEntry.COLUMN_CONTACT_NAME, contactNameString);
        values.put(DatosPersonalesEntry.COLUMN_CONTACT_PHONE, contactPhoneString);
        values.put(DatosPersonalesEntry.COLUMN_NSS, nssString);
        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int weight = 0;
        if (!TextUtils.isEmpty(weightString)) {
            weight = Integer.parseInt(weightString);
        }
        values.put(DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT, weight);

        // If the height is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int height = 0;
        if (!TextUtils.isEmpty(heightString)) {
            height = Integer.parseInt(heightString);
        }
        values.put(DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT, height);



        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentDatosPersonalesUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(DatosPersonalesEntry.CONTENT_URI, values);

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
            int rowsAffected = getContentResolver().update(mCurrentDatosPersonalesUri, values, null, null);

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
        getMenuInflater().inflate(R.menu.menu_editor_datos_personales, menu);
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
        if (mCurrentDatosPersonalesUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    /*

     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText matriculaEditText = (EditText)findViewById(R.id.edit_matricula_dp);
        String matricula  =  matriculaEditText.getText().toString();

        EditText nameEditText = (EditText)findViewById(R.id.edit_name_dp);
        String name  =  nameEditText.getText().toString();

        EditText lastNameEditText = (EditText)findViewById(R.id.edit_last_name_db);
        String lastName  =  lastNameEditText.getText().toString();

        EditText contactNameEditText = (EditText)findViewById(R.id.edit_contact_name_dp);
        String contactName  =  contactNameEditText.getText().toString();

        EditText contactPhoneEditText = (EditText)findViewById(R.id.edit_contact_phone_dp);
        String contactPhone  =  contactPhoneEditText.getText().toString();

        EditText weightEditText = (EditText)findViewById(R.id.edit_weight_db);
        String weight  =  weightEditText.getText().toString();

        EditText heightEditText = (EditText)findViewById(R.id.edit_height_db);
        String height  =  heightEditText.getText().toString();

        EditText nssEditText = (EditText)findViewById(R.id.edit_nss_dp);
        String nss  =  nssEditText.getText().toString();

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if (TextUtils.isEmpty(matricula)){
                    Toast.makeText(this, "Necesitas añadir tu matricula", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)){
                    Toast.makeText(this, "Necesitas añadir tu nombre", Toast.LENGTH_SHORT).show();
                }  else if (TextUtils.isEmpty(lastName)){
                    Toast.makeText(this, "Necesitas añadir tus apellidos", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(contactName)){
                    Toast.makeText(this, "Necesitas añadir tu nombre de contacto", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(contactPhone)){
                    Toast.makeText(this, "Necesitas añadir el telefono de tu contacto", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(weight)){
                    Toast.makeText(this, "Necesitas añadir tu peso", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(height)){
                    Toast.makeText(this, "Necesitas añadir tu altura", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(nss)){
                    Toast.makeText(this, "Necesitas añadir tu numero de seguridad social", Toast.LENGTH_LONG).show();
                }else {
                    // Save student to database
                    saveDatosPersonales();
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
                if (!mPetHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorDatosPersonales.this);
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
                                NavUtils.navigateUpFromSameTask(EditorDatosPersonales.this);
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
        if (!mPetHasChanged) {
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
                DatosPersonalesEntry._ID,
                DatosPersonalesEntry.COLUMN_MATRICULA,
                DatosPersonalesEntry.COLUMN_NOMBRES,
                DatosPersonalesEntry.COLUMN_APELLIDOS,
                DatosPersonalesEntry.COLUMN_CONTACT_NAME,
                DatosPersonalesEntry.COLUMN_CONTACT_PHONE,
                DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT,
                DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT,
                DatosPersonalesEntry.COLUMN_NSS};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentDatosPersonalesUri,     // Query the content URI for the current pet
                projection,                     // Columns to include in the resulting Cursor
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
            int matriculaColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_MATRICULA);
            int nameColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_NOMBRES);
            int lastNameColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_APELLIDOS);
            int contactNameColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_CONTACT_NAME);
            int contactPhoneColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_CONTACT_PHONE);
            int weightColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT);
            int heightColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT);
            int nssColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_NSS);

            // Extract out the value from the Cursor for the given column index
            String matricula = cursor.getString(matriculaColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            String lastName = cursor.getString(lastNameColumnIndex);
            String contactName = cursor.getString(contactNameColumnIndex);
            String contactPhoneName = cursor.getString(contactPhoneColumnIndex);
            int weight = cursor.getInt(weightColumnIndex);
            int height = cursor.getInt(heightColumnIndex);
            String nss = cursor.getString(nssColumnIndex);

            // Update the views on the screen with the values from the database
            mMatriculaEditText.setText(matricula);
            mNameEditText.setText(name);
            mLastNameEditText.setText(lastName);
            mContactNameEditText.setText(contactName);
            mContactPhoneEditText.setText(contactPhoneName);
            mWeightEditText.setText(Integer.toString(weight));
            mHeightEditText.setText(Integer.toString(height));
            mNssEditText.setText(nss);
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mMatriculaEditText.setText("");
        mNameEditText.setText("");
        mLastNameEditText.setText("");
        mContactNameEditText.setText("");
        mContactPhoneEditText.setText("");
        mWeightEditText.setText("");
        mHeightEditText.setText("");
        mNssEditText.setText("");
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
                deletePet();
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
    private void deletePet() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentDatosPersonalesUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentDatosPersonalesUri, null, null);

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