package com.gabrielbalbuena.saludutn;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.HistorialMedicoEntry;

/**
 * {@link HistorialMedicoCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class HistorialMedicoCursorAdapter extends CursorAdapter {

    private ImageView image; //Foto

    /**
     * Constructs a new {@link HistorialMedicoCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public HistorialMedicoCursorAdapter(Context context, Cursor c) {
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
        return LayoutInflater.from(context).inflate(R.layout.list_item_historial_medico, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
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
        TextView dateTextView = (TextView) view.findViewById(R.id.tv_fecha_historial_medico);
        TextView diagnosticTextView = (TextView) view.findViewById(R.id.tv_diagnostico_historial_medico);
        TextView urlUnoTextView = (TextView) view.findViewById(R.id.tv_urluno_historial_medico);
        TextView urlDosTextView = (TextView) view.findViewById(R.id.tv_urldos_historial_medico);
        TextView priceConsultTextView = (TextView) view.findViewById(R.id.tv_precioconsulta_historial_medico);
        TextView doctorNameTextView = (TextView) view.findViewById(R.id.tv_nombredoctor_historial_medico);
        TextView specialityTextView = (TextView) view.findViewById(R.id.tv_especialidad_historial_medico);

        // Find the columns of pet attributes that we're interested in
        int dateColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_FECHA_HM);
        int diagnosticColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_DIAGNOSTICO);
        int urlUnoColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_FOTO_UNO_URL);
        int urlTwoColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_FOTO_DOS_URL);
        int priceConsultColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_PRECIO_CONSULTA);
        int doctorNameColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_NOMBRE_DOCTOR);
        int specialityColumnIndex = cursor.getColumnIndex(HistorialMedicoEntry.COLUMN_ESPECIALIDAD);

        // Read the pet attributes from the Cursor for the current pet
        String historialMedicoDate = cursor.getString(dateColumnIndex);
        String historialMedicoDiagnostic = cursor.getString(diagnosticColumnIndex);
        String historialMedicoUrlUno = cursor.getString(urlUnoColumnIndex);
        String historialMedicoUrlDos = cursor.getString(urlTwoColumnIndex);
        String historialMedicoPriceConsult = cursor.getString(priceConsultColumnIndex);
        String historialMedicoDoctorName = cursor.getString(doctorNameColumnIndex);
        String historialMedicoSpeciality = cursor.getString(specialityColumnIndex);



        //ImageView miniatura = (ImageView) view.findViewById(R.id.imageViewMini);
        //Bitmap bMap = BitmapFactory.decodeFile(historialMedicoUrlUno);
        //image.setImageBitmap(bMap);
        ImageView miniatura = (ImageView) view.findViewById(R.id.imageViewMini);
        Bitmap bMap = BitmapFactory.decodeFile(historialMedicoUrlUno);
        miniatura.setImageBitmap(bMap);

        ImageView miniatura2 = (ImageView) view.findViewById(R.id.imageViewMini2);
        Bitmap bMap2 = BitmapFactory.decodeFile(historialMedicoUrlDos);
        miniatura2.setImageBitmap(bMap2);


        /*
        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(petBreed)) {
            petBreed = context.getString(R.string.unknown_breed);
        }*/

        // Update the TextViews with the attributes for the current pet
        dateTextView.setText(historialMedicoDate);
        diagnosticTextView.setText(historialMedicoDiagnostic);
        urlUnoTextView.setText(historialMedicoUrlUno);
        urlDosTextView.setText(historialMedicoUrlDos);
        if (historialMedicoPriceConsult.equals("0")){
            priceConsultTextView.setText("No especificado");
        } else {
            priceConsultTextView.setText(historialMedicoPriceConsult);
        }

        if (historialMedicoDoctorName.equals("")){
            doctorNameTextView.setText("No especificado");
        } else {
            doctorNameTextView.setText(historialMedicoDoctorName);
        }

        //specialityTextView.setText(historialMedicoSpeciality);


        if (historialMedicoSpeciality.equals("0")) {
            specialityTextView.setText("No seleccionada ");
        }  else if (historialMedicoSpeciality.equals("1")){
            specialityTextView.setText("Anestesiología");
        }   else if (historialMedicoSpeciality.equals("2")){
            specialityTextView.setText("Angiología cirujano vascular");
        }   else if (historialMedicoSpeciality.equals("3")){
            specialityTextView.setText("Cardiología");
        }   else if (historialMedicoSpeciality.equals("4")){
            specialityTextView.setText("Cardiología intervencionista");
        }   else if (historialMedicoSpeciality.equals("5")){
            specialityTextView.setText("Cirugía");
        }else if (historialMedicoSpeciality.equals("6")){
            specialityTextView.setText("Cirugía Oncológica");
        }   else if (historialMedicoSpeciality.equals("7")){
            specialityTextView.setText("Cirugía plástica");
        }   else if (historialMedicoSpeciality.equals("8")){
            specialityTextView.setText("Dermatología");
        }   else if (historialMedicoSpeciality.equals("9")){
            specialityTextView.setText("Endoscopía");
        }   else if (historialMedicoSpeciality.equals("10")){
            specialityTextView.setText("Gastroenterología");
        }else if (historialMedicoSpeciality.equals("11")){
            specialityTextView.setText("Ginecología y Obstetricia");
        }   else if (historialMedicoSpeciality.equals("12")){
            specialityTextView.setText("Hematología");
        }   else if (historialMedicoSpeciality.equals("13")){
            specialityTextView.setText("Infectología");
        }   else if (historialMedicoSpeciality.equals("14")){
            specialityTextView.setText("Medicina de Rehabilitación");
        }   else if (historialMedicoSpeciality.equals("15")){
            specialityTextView.setText("Medicina Interna");
        }else if (historialMedicoSpeciality.equals("16")){
            specialityTextView.setText("Nefrología");
        }   else if (historialMedicoSpeciality.equals("17")){
            specialityTextView.setText("Neumología");
        }   else if (historialMedicoSpeciality.equals("18")){
            specialityTextView.setText("Neurología");
        }   else if (historialMedicoSpeciality.equals("19")){
            specialityTextView.setText("Oftalmología");
        }   else if (historialMedicoSpeciality.equals("20")){
            specialityTextView.setText("Oncología Médica");
        }else if (historialMedicoSpeciality.equals("21")){
            specialityTextView.setText("Oncología pediátrica");
        }   else if (historialMedicoSpeciality.equals("22")){
            specialityTextView.setText("Ortopedia");
        }   else if (historialMedicoSpeciality.equals("23")){
            specialityTextView.setText("Otorrinolaringología");
        }   else if (historialMedicoSpeciality.equals("24")){
            specialityTextView.setText("Patología Clínica");
        }   else if (historialMedicoSpeciality.equals("25")){
            specialityTextView.setText("Pediatría");
        }else if (historialMedicoSpeciality.equals("26")){
            specialityTextView.setText("Psiquiatría");
        }   else if (historialMedicoSpeciality.equals("27")){
            specialityTextView.setText("Radiología e imagen");
        }   else if (historialMedicoSpeciality.equals("28")){
            specialityTextView.setText("Radio-oncología");
        }   else if (historialMedicoSpeciality.equals("29")){
            specialityTextView.setText("Urología");
        }


    }
}