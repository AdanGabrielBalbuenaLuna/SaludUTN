package com.gabrielbalbuena.saludutn.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class SaludUtnContract {

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.gabrielbalbuena.saludutn";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.gabrielbalbuena.saludutn/datospersonales/ is a valid path for
     * looking at datospersonal data. content://com.gabrielbalbuena.saludutns/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_DATOS_PERSONALES = "datospersonales";

    public static final class DatosPersonalesEntry implements BaseColumns{

        /** The content URI to access the datospersonal data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DATOS_PERSONALES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of datospersonales.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATOS_PERSONALES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single datospersonal.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATOS_PERSONALES;

        /** Name of database table for datospersonales */
        public final static String TABLE_NAME = "datospersonales";

        /**
         * Unique ID number for the datospersonal (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Matricula of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_MATRICULA ="matricula";

        /**
         * Name of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_NOMBRES ="nombres";

        /**
         * Last Name of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_APELLIDOS ="apellidos";

        /**
         * Name of the contact of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_CONTACT_NAME ="nombrecontacto";

        /**
         * Phone of the contact of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_CONTACT_PHONE ="telefonocontacto";

        /**
         * Weight of the student.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_STUDENT_WEIGHT = "peso";

        /**
         * height of the student.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_STUDENT_HEIGHT = "altura";

        /**
         * Phone of the contact of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_NSS ="nss";
    }


    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_DIARIO_EMOCIONES = "diarioemociones";

    public static final class DiarioEmocionesEntry implements BaseColumns{

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DIARIO_EMOCIONES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARIO_EMOCIONES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARIO_EMOCIONES;

        /** Name of database table for pets */
        public final static String TABLE_NAME = "diarioemociones";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIARIOEMOCIONES_FECHAHORA ="name";

        /**
         * Gender of the pet.
         *
         * The only possible values are {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
         * or {@link #GENDER_FEMALE}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_DIARIOEMOCIONES_SENTIMIENTO= "sentimiento";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIARIOEMOCIONES_SIENTE = "siente";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIARIOEMOCIONES_PENSAMIENTO = "pensamiento";

        /**
         * Possible values for the gender of the pet.
         */
        public static final int SENTIMIENTO_UNKNOWN = 0;
        public static final int SENTIMIENTO_MALO = 1;
        public static final int SENTIMIENTO_BUENO = 2;

        /**
         * Returns whether or not the given gender is {@link #SENTIMIENTO_UNKNOWN}, {@link #SENTIMIENTO_MALO},
         * or {@link #SENTIMIENTO_BUENO}.
         */
        public static boolean isValidSentimiento(int sentimiento) {
            if (sentimiento == SENTIMIENTO_UNKNOWN || sentimiento == SENTIMIENTO_MALO || sentimiento == SENTIMIENTO_BUENO) {
                return true;
            }
            return false;
        }
    }
}
