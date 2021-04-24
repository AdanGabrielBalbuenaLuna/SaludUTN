package com.gabrielbalbuena.saludutn;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gabrielbalbuena.saludutn.data.SaludUtnContract.DiarioEmocionesEntry;

/**
 * {@link DiarioEmocionesCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class DiarioEmocionesCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link DiarioEmocionesCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public DiarioEmocionesCursorAdapter(Context context, Cursor c) {
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
        return LayoutInflater.from(context).inflate(R.layout.list_item_diario_emociones, parent, false);
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
        TextView dateTextView = (TextView) view.findViewById(R.id.fecha);
        TextView emotionTextView = (TextView) view.findViewById(R.id.emocion);
        TextView feelTextView = (TextView) view.findViewById(R.id.sentimiento);
        TextView thoughtTextView = (TextView) view.findViewById(R.id.pensamiento);

        // Find the columns of pet attributes that we're interested in
        int dateColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_FECHAHORA);
        int emotionColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_EMOCION);
        int feelColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_SIENTE);
        int thoughtColumnIndex = cursor.getColumnIndex(DiarioEmocionesEntry.COLUMN_DIARIOEMOCIONES_PENSAMIENTO);

        // Read the pet attributes from the Cursor for the current pet
        String diarioEmocionesDate = cursor.getString(dateColumnIndex);
        String diarioEmocionesEmotion = cursor.getString(emotionColumnIndex);
        String diarioEmocionesFeel = cursor.getString(feelColumnIndex);
        String diarioEmocionesThought = cursor.getString(thoughtColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(diarioEmocionesFeel)) {
            diarioEmocionesFeel = context.getString(R.string.unknown_feel);
        }

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(diarioEmocionesThought)) {
            diarioEmocionesThought = context.getString(R.string.unknown_feel);
        }

        // Update the TextViews with the attributes for the current pet
        dateTextView.setText(diarioEmocionesDate);
        emotionTextView.setText(diarioEmocionesEmotion);
        feelTextView.setText(diarioEmocionesFeel);
        thoughtTextView.setText(diarioEmocionesThought);
    }
}