package com.gabrielbalbuena.saludutn;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.HistorialMedicoEntry;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorHistorialMedico extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    //LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int EXISTING_HISTORIALMEDICO_LOADER = 0;

    /** Content URI for the existing pet (null if it's a new pet) */
    private Uri mCurrentHistorialMedicoUri;

    /** EditText field to enter the pet's name */
    private TextView mDateEditText;
    //private EditText mDateEditText;

    /** EditText field to enter the pet's name */
    private EditText mDiagnosticEditText;

    /** EditText field to enter the pet's name */
    private EditText mUrlPhotoOneEditText;

    /** EditText field to enter the pet's name */
    private EditText mUrlPhotoTwoEditText;

    /** EditText field to enter the pet's name */
    private EditText mPriceConsultEditText;

    /** EditText field to enter the pet's name */
    private EditText mDoctorNameEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mSpecialitySpinner;

    /**
     * Gender of the pet. The possible valid values are in the PetContract.java file:
     * {@link HistorialMedicoEntry#ESPECIALIDAD_UNKNOWN}, {@link HistorialMedicoEntry#ESPECIALIDAD_ANESTESIOLOGIA}, or
     * {@link HistorialMedicoEntry#ESPECIALIDAD_ANGIOLOGIA}.
     */
    private int mSpeciality = HistorialMedicoEntry.ESPECIALIDAD_UNKNOWN;

    /** Boolean flag that keeps track of whether the pet has been edited (true) or not (false) */
    private boolean mHistorialMedicoHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHistorialMedicoHasChanged = true;
            return false;
        }
    };


    //Refrencias TextView //Calendario
    TextView tv; //Calendario

    private ImageView img; //Foto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_historial_medico);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getIntent();
        mCurrentHistorialMedicoUri = intent.getData();

        // If the intent DOES NOT contain a pet content URI, then we know that we are
        // creating a new pet.
        if (mCurrentHistorialMedicoUri == null) {
            // This is a new pet, so change the app bar to say "Add a Pet"
            setTitle(getString(R.string.editor_activity_title_new_historial_medico));
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing pet, so change app bar to say "Edit Pet"
            setTitle(getString(R.string.editor_activity_title_edit_historial_medico));

            // Initialize a loader to read the pet data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_HISTORIALMEDICO_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mDateEditText = (TextView) findViewById(R.id.et_historial_medico_date);
        //mDateEditText = (EditText) findViewById(R.id.et_historial_medico_date);

        mDiagnosticEditText = (EditText) findViewById(R.id.et_historial_medico_diagnostic);
        mUrlPhotoOneEditText = (EditText) findViewById(R.id.et_historial_medico_photo_one);
        mUrlPhotoTwoEditText = (EditText) findViewById(R.id.et_historial_medico_photo_two);
        mPriceConsultEditText = (EditText) findViewById(R.id.et_historial_medico_price);
        mDoctorNameEditText = (EditText) findViewById(R.id.et_historial_medico_doctor_name);
        mSpecialitySpinner = (Spinner) findViewById(R.id.spinner_speciality);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mDateEditText.setOnTouchListener(mTouchListener);
        mDiagnosticEditText.setOnTouchListener(mTouchListener);
        mUrlPhotoOneEditText.setOnTouchListener(mTouchListener);
        mUrlPhotoTwoEditText.setOnTouchListener(mTouchListener);
        mPriceConsultEditText.setOnTouchListener(mTouchListener);
        mDoctorNameEditText.setOnTouchListener(mTouchListener);
        mSpecialitySpinner.setOnTouchListener(mTouchListener);


        setupSpinner();

        tv = findViewById(R.id.et_historial_medico_date);//Calendario


        //Foto
        img = (ImageView)findViewById(R.id.imageView);
        if (ContextCompat.checkSelfPermission(EditorHistorialMedico.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditorHistorialMedico.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditorHistorialMedico.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }

    public void abrirCalendario(View view) {//Calendario
        Calendar cal = Calendar.getInstance();//Obtener un calendario
        int anio = cal.get(Calendar.YEAR);//Calendario
        int mes = cal.get(Calendar.MONTH);//Calendario
        int dia = cal.get(Calendar.DAY_OF_MONTH);//Calendario

        //Inicializar el day picker dialog//Calendario
        DatePickerDialog dpd = new DatePickerDialog(EditorHistorialMedico.this, new DatePickerDialog.OnDateSetListener() {//Calendario
            @Override//Calendario
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {//Calendario
                String fecha = dayOfMonth + "/" + month + "/" + year;//Calendario
                tv.setText(fecha);//Calendario
            }//Calendario
        },anio, mes, dia);//Calendario
        dpd.show();//Calendario
    }//Calendario


    //___________________________________________________________________________________PHOTOGRAPHY
    //Method to create a name unique per photograph
    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Method to take pictures and create the file
    static final int REQUEST_TAKE_PHOTO = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.gabrielbalbuena.saludutn",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Method to show tumbnail in an ImageView
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }

    public void tomarFoto(View view) {
        dispatchTakePictureIntent();
    }


    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_speciality_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSpecialitySpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSpecialitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.speciality_anestesiologia))) {
                        mSpeciality = HistorialMedicoEntry.ESPECIALIDAD_ANESTESIOLOGIA;
                    } else if (selection.equals(getString(R.string.speciality_angiologia))) {
                        mSpeciality = HistorialMedicoEntry.ESPECIALIDAD_ANGIOLOGIA;
                    } else {
                        mSpeciality = HistorialMedicoEntry.ESPECIALIDAD_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpeciality = HistorialMedicoEntry.ESPECIALIDAD_UNKNOWN;
            }
        });
    }

    /**
     * ------Get user input from editor and save new pet into database.
     * Get user input from editor and save pet into database.
     */
    //private void insertPet()
    private void saveHistorialMedico() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = mDateEditText.getText().toString().trim();
        String diagnosticString = mDiagnosticEditText.getText().toString().trim();
        String urlPhotoOneString = mUrlPhotoOneEditText.getText().toString().trim();
        String urlPhotoTwoString = mUrlPhotoTwoEditText.getText().toString().trim();
        String priceConsultString = mPriceConsultEditText.getText().toString().trim();
        String doctorNameString = mDoctorNameEditText.getText().toString().trim();
        //int weight = Integer.parseInt(weightString);

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentHistorialMedicoUri == null &&
                TextUtils.isEmpty(dateString) &&
                TextUtils.isEmpty(diagnosticString) &&
                TextUtils.isEmpty(urlPhotoOneString) &&
                TextUtils.isEmpty(urlPhotoTwoString) &&
                TextUtils.isEmpty(priceConsultString) &&
                TextUtils.isEmpty(doctorNameString) &&
                mSpeciality == HistorialMedicoEntry.ESPECIALIDAD_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HistorialMedicoEntry.COLUMN_FECHA_HM, dateString);
        values.put(HistorialMedicoEntry.COLUMN_DIAGNOSTICO, diagnosticString);
        values.put(HistorialMedicoEntry.COLUMN_FOTO_UNO_URL, urlPhotoOneString);
        values.put(HistorialMedicoEntry.COLUMN_FOTO_DOS_URL, urlPhotoTwoString);
        values.put(HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR, doctorNameString);

        values.put(HistorialMedicoEntry.COLUMN_ESPECIALIDAD, mSpeciality);

        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int price = 0;
        if (!TextUtils.isEmpty(priceConsultString)) {
            price = Integer.parseInt(priceConsultString);
        }
        values.put(HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA, price);



        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentHistorialMedicoUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(HistorialMedicoEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_historialmedico_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_historiamedico_successful),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentHistorialMedicoUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_historialmedico_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_historialmedico_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor_historial_medico, menu);
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
        if (mCurrentHistorialMedicoUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                //insertPet();
                saveHistorialMedico();
                // Exit activity
                finish();
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
                if (!mHistorialMedicoHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorHistorialMedico.this);
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
                                NavUtils.navigateUpFromSameTask(EditorHistorialMedico.this);
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
        if (!mHistorialMedicoHasChanged) {
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
                HistorialMedicoEntry._ID,
                HistorialMedicoEntry.COLUMN_FECHA_HM,
                HistorialMedicoEntry.COLUMN_DIAGNOSTICO,
                HistorialMedicoEntry.COLUMN_FOTO_UNO_URL,
                HistorialMedicoEntry.COLUMN_FOTO_DOS_URL,
                HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA,
                HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR,
                HistorialMedicoEntry.COLUMN_ESPECIALIDAD};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentHistorialMedicoUri,         // Query the content URI for the current pet
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
            int dateColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_FECHA_HM);
            int diagnosticColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_DIAGNOSTICO);
            int urlPhotoOneColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_FOTO_UNO_URL);
            int urlPhotoTwoColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_FOTO_DOS_URL);
            int priceConsultColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA);
            int doctorNameColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR);
            int specialityColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_ESPECIALIDAD);

            // Extract out the value from the Cursor for the given column index
            String date = cursor.getString(dateColumnIndex);
            String diagnostic = cursor.getString(diagnosticColumnIndex);
            String urlPhotoOne = cursor.getString(urlPhotoOneColumnIndex);
            String urlPhotoTwo = cursor.getString(urlPhotoTwoColumnIndex);
            String doctorName = cursor.getString(doctorNameColumnIndex);
            int priceConsult = cursor.getInt(priceConsultColumnIndex);
            int speciality = cursor.getInt(specialityColumnIndex);

            // Update the views on the screen with the values from the database
            mDateEditText.setText(date);
            mDiagnosticEditText.setText(diagnostic);
            mUrlPhotoOneEditText.setText(urlPhotoOne);
            mUrlPhotoTwoEditText.setText(urlPhotoTwo);
            mDoctorNameEditText.setText(doctorName);

            mPriceConsultEditText.setText(Integer.toString(priceConsult));

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (speciality) {
                case HistorialMedicoEntry.ESPECIALIDAD_ANESTESIOLOGIA:
                    mSpecialitySpinner.setSelection(1);
                    break;
                case HistorialMedicoEntry.ESPECIALIDAD_ANGIOLOGIA:
                    mSpecialitySpinner.setSelection(2);
                    break;
                default:
                    mSpecialitySpinner.setSelection(0);
                    break;
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mDateEditText.setText("");
        mDiagnosticEditText.setText("");
        mUrlPhotoOneEditText.setText("");
        mUrlPhotoTwoEditText.setText("");
        mPriceConsultEditText.setText("");
        mDoctorNameEditText.setText("");
        mSpecialitySpinner.setSelection(0); // Select "Unknown" gender
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
                deleteHistorialMedico();
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
    private void deleteHistorialMedico() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentHistorialMedicoUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentHistorialMedicoUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_historialmedico_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_historialmedico_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }
}