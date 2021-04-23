package com.gabrielbalbuena.saludutn;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import  com.gabrielbalbuena.saludutn.data.SaludUtnContract.DatosPersonalesEntry;

/**
 * {@link DatosPersonalesCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of datospersonal data as its data source. This adapter knows
 * how to create list items for each row of datospersonal data in the {@link Cursor}.
 */
public class DatosPersonalesCursorAdapter extends CursorAdapter {
    /**
     * Constructs a new {@link DatosPersonalesCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public DatosPersonalesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // TODO: Fill out this method and return the list item view (instead of null)
        return LayoutInflater.from(context).inflate(R.layout.list_item_datos_personales, parent, false);
    }

    /**
     * This method binds the datospersonal data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current datospersonal can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView matriculaTextView = (TextView) view.findViewById(R.id.matricula);
        TextView nombreTextView = (TextView) view.findViewById(R.id.nombres);
        TextView apellidosTextView = (TextView) view.findViewById(R.id.apellidos);
        TextView contactoNombreTextView = (TextView) view.findViewById(R.id.contactonombre);
        TextView contactoTelefonoTextView = (TextView) view.findViewById(R.id.contactotelefono);
        TextView pesoTextView = (TextView) view.findViewById(R.id.peso);
        TextView alturaTextView = (TextView) view.findViewById(R.id.altura);
        TextView nssTextView = (TextView) view.findViewById(R.id.nss);


        // Find the columns of datospersonal attributes that we're interested in
        int matriculaColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_MATRICULA);
        int nameColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_NOMBRES);
        int lastNameColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_APELLIDOS);
        int contactNameColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_CONTACT_NAME);
        int conctactPhoneColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_CONTACT_PHONE);
        int weightColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_STUDENT_WEIGHT);
        int heightColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_STUDENT_HEIGHT);
        int nssColumnIndex = cursor.getColumnIndex(DatosPersonalesEntry.COLUMN_NSS);

        // Read the datospersonal attributes from the Cursor for the current datospersonal
        String matricula = cursor.getString(matriculaColumnIndex);
        String name = cursor.getString(nameColumnIndex);
        String lastName = cursor.getString(lastNameColumnIndex);
        String nameContact = cursor.getString(contactNameColumnIndex);
        String phoneContact = cursor.getString(conctactPhoneColumnIndex);
        String weight = cursor.getString(weightColumnIndex);
        String height = cursor.getString(heightColumnIndex);
        String nss = cursor.getString(nssColumnIndex);

        // Update the TextViews with the attributes for the current datospersonal
        matriculaTextView.setText(matricula);
        nombreTextView.setText(name);
        apellidosTextView.setText(lastName);
        contactoNombreTextView.setText(nameContact);
        contactoTelefonoTextView.setText(phoneContact);
        pesoTextView.setText(weight);
        alturaTextView.setText(height);
        nssTextView.setText(nss);
    }
}