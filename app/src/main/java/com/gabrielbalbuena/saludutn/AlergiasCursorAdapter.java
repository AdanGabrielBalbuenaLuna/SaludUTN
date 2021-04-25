package com.gabrielbalbuena.saludutn;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.AlergiasEntry;

/**
 * {@link AlergiasCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of alergia data as its data source. This adapter knows
 * how to create list items for each row of alergia data in the {@link Cursor}.
 */
public class AlergiasCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link AlergiasCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public AlergiasCursorAdapter(Context context, Cursor c) {
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
        return LayoutInflater.from(context).inflate(R.layout.list_item_alergias, parent, false);
    }

    /**
     * This method binds the alergia data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current alergia can be set on the name TextView
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
        TextView dateTextView = (TextView) view.findViewById(R.id.fecha_alergia);
        TextView alergyNameTextView = (TextView) view.findViewById(R.id.alergia_nombre);
        TextView typeAlergyTextView = (TextView) view.findViewById(R.id.tipo_alergia);
        TextView commentAlergyTextView = (TextView) view.findViewById(R.id.comentario_alergia);

        // Find the columns of alergia attributes that we're interested in
        int dateColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_ALERGIAS_FECHAHORA);
        int alergyNameColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_ALERGIAS_NOMBRE);
        int typeAlergyColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_TIPO_ALERGIA);
        int commentAlergyColumnIndex = cursor.getColumnIndex(AlergiasEntry.COLUMN_COMENTARIO_ALERGIA);

        // Read the alergia attributes from the Cursor for the current alergia
        String alergiasDate = cursor.getString(dateColumnIndex);
        String alergiasNameAlergy = cursor.getString(alergyNameColumnIndex);
        String alergiasTypeAlergy = cursor.getString(typeAlergyColumnIndex);
        String alergiasComentary = cursor.getString(commentAlergyColumnIndex);

        // If the alergia breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(alergiasTypeAlergy)) {
            alergiasTypeAlergy = context.getString(R.string.alergia_unknown);
        }

        // If the alergia breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(alergiasComentary)) {
            alergiasComentary = context.getString(R.string.alergia_comentary_unknown);
        }

        // Update the TextViews with the attributes for the current alergia
        dateTextView.setText(alergiasDate);

        if (alergiasNameAlergy.equals("0")) {
            alergyNameTextView.setText("No hubo eleccion");
        }  else if (alergyNameTextView.equals("1")){
            alergyNameTextView.setText("Huevo");
        }   else if (alergyNameTextView.equals("2")){
            alergyNameTextView.setText("Pescado");
        }   /*else if (diarioEmocionesEmotion.equals("3")){
            emotionTextView.setText("Alegría");
        }   else if (diarioEmocionesEmotion.equals("4")){
            emotionTextView.setText("Amor");
        }   else if (diarioEmocionesEmotion.equals("5")){
            emotionTextView.setText("Asco");
        }else if (diarioEmocionesEmotion.equals("6")){
            emotionTextView.setText("Culpabilidad");
        }   else if (diarioEmocionesEmotion.equals("7")){
            emotionTextView.setText("Desesperación");
        }   else if (diarioEmocionesEmotion.equals("8")){
            emotionTextView.setText("Diversión");
        }   else if (diarioEmocionesEmotion.equals("9")){
            emotionTextView.setText("Esperanza");
        }   else if (diarioEmocionesEmotion.equals("10")){
            emotionTextView.setText("Gratitud");
        }else if (diarioEmocionesEmotion.equals("11")){
            emotionTextView.setText("Admiración");
        }   else if (diarioEmocionesEmotion.equals("12")){
            emotionTextView.setText("Inspiración");
        }   else if (diarioEmocionesEmotion.equals("13")){
            emotionTextView.setText("Interés");
        }   else if (diarioEmocionesEmotion.equals("14")){
            emotionTextView.setText("Ira");
        }   else if (diarioEmocionesEmotion.equals("15")){
            emotionTextView.setText("Miedo");
        }else if (diarioEmocionesEmotion.equals("16")){
            emotionTextView.setText("Orgullo");
        }   else if (diarioEmocionesEmotion.equals("17")){
            emotionTextView.setText("Serenidad");
        }   else if (diarioEmocionesEmotion.equals("18")){
            emotionTextView.setText("Soledad");
        }   else if (diarioEmocionesEmotion.equals("19")){
            emotionTextView.setText("Tristeza");
        }*/

        typeAlergyTextView.setText(alergiasTypeAlergy);
        commentAlergyTextView.setText(alergiasComentary);
    }
}