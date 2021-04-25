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
        public final static String COLUMN_CONTACT_NAME ="nombre_contacto";

        /**
         * Phone of the contact of the student.
         *
         * Type: TEXT
         */
        public final static String COLUMN_CONTACT_PHONE ="telefono_contacto";

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
     * For instance, content://com.gabrielbalbuena.saludutn/datospersonales/ is a valid path for
     * looking at datospersonal data. content://com.gabrielbalbuena.saludutns/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_DIARIO_EMOCIONES = "diarioemociones";

    public static final class DiarioEmocionesEntry implements BaseColumns{

        /** The content URI to access the diarioemocion data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DIARIO_EMOCIONES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of diarioemociones.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARIO_EMOCIONES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single diarioemocion.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARIO_EMOCIONES;

        /** Name of database table for diarioemociones */
        public final static String TABLE_NAME = "diarioemociones";

        /**
         * Unique ID number for the diarioemocion (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the diarioemocion.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIARIOEMOCIONES_FECHAHORA ="name";

        /**
         * Gender of the diarioemocion.
         *
         * The only possible values are {@link #EMOCION_UNKNOWN+}, {@link #EMOCION_APATIA},
         * or {@link #EMOCION_ADMIRACION}...
         *
         * Type: INTEGER
         */
        public final static String COLUMN_DIARIOEMOCIONES_EMOCION= "nombre_emocion";

        /**
         * Breed of the diarioemocion.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIARIOEMOCIONES_SIENTE = "siente";

        /**
         * Breed of the diarioemocion.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIARIOEMOCIONES_PENSAMIENTO = "pensamiento";

        /**
         * Possible values for the gender of the diarioemocion.
         */
        public static final int EMOCION_UNKNOWN = 0;
        public static final int EMOCION_APATIA = 1;
        public static final int EMOCION_ADMIRACION = 2;
        public static final int EMOCION_ALEGRIA = 3;
        public static final int EMOCION_AMOR = 4;
        public static final int EMOCION_ASCO = 5;

        public static final int EMOCION_CULPABILIDAD = 6;
        public static final int EMOCION_DESESPERACION = 7;
        public static final int EMOCION_DIVERSION = 8;
        public static final int EMOCION_ESPERANZA = 9;
        public static final int EMOCION_GRATITUD = 10;

        public static final int EMOCION_INDIFERENCIA = 11;
        public static final int EMOCION_INSPIRACION = 12;
        public static final int EMOCION_INTERES = 13;
        public static final int EMOCION_IRA = 14;
        public static final int EMOCION_MIEDO = 15;

        public static final int EMOCION_ORGULLO = 16;
        public static final int EMOCION_SERENDIDAD = 17;
        public static final int EMOCION_SOLEDAD = 18;
        public static final int EMOCION_TRISTEZA = 19;

        /**
         * Returns whether or not the given gender is
         * {@link #EMOCION_UNKNOWN},
         * or {@link #EMOCION_APATIA},
         * or {@link #EMOCION_ADMIRACION}
         * or {@link #EMOCION_ALEGRIA}
         * or {@link #EMOCION_AMOR}
         * or {@link #EMOCION_ASCO}
         *
         * or {@link #EMOCION_CULPABILIDAD}
         * or {@link #EMOCION_DESESPERACION}
         * or {@link #EMOCION_DIVERSION}
         * or {@link #EMOCION_ESPERANZA}
         * or {@link #EMOCION_GRATITUD}
         *
         * or {@link #EMOCION_INDIFERENCIA}
         * or {@link #EMOCION_INSPIRACION}
         * or {@link #EMOCION_INTERES}
         * or {@link #EMOCION_IRA}
         * or {@link #EMOCION_MIEDO}
         *
         * or {@link #EMOCION_ORGULLO}
         * or {@link #EMOCION_SERENDIDAD}
         * or {@link #EMOCION_SOLEDAD}
         * or {@link #EMOCION_TRISTEZA}
         */
        public static boolean isValidEmocion(int emocion) {
            if (emocion == EMOCION_UNKNOWN ||
                    emocion == EMOCION_APATIA ||
                    emocion == EMOCION_ADMIRACION ||
                    emocion == EMOCION_ALEGRIA ||
                    emocion == EMOCION_AMOR ||
                    emocion == EMOCION_ASCO ||

                    emocion == EMOCION_CULPABILIDAD ||
                    emocion == EMOCION_DESESPERACION ||
                    emocion == EMOCION_DIVERSION ||
                    emocion == EMOCION_ESPERANZA ||
                    emocion == EMOCION_GRATITUD ||

                    emocion == EMOCION_INDIFERENCIA ||
                    emocion == EMOCION_INSPIRACION ||
                    emocion == EMOCION_INTERES ||
                    emocion == EMOCION_IRA ||
                    emocion == EMOCION_MIEDO ||

                    emocion == EMOCION_ORGULLO ||
                    emocion == EMOCION_SERENDIDAD ||
                    emocion == EMOCION_SOLEDAD ||
                    emocion == EMOCION_TRISTEZA) {
                return true;
            }
            return false;
        }
    }

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.gabrielbalbuena.saludutn/datospersonales/ is a valid path for
     * looking at datospersonal data. content://com.gabrielbalbuena.saludutns/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_ALERGIAS = "alergias";

    public static final class AlergiasEntry implements BaseColumns{

        /** The content URI to access the alergia data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ALERGIAS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of alergias.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ALERGIAS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single alergia.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ALERGIAS;

        /** Name of database table for alergias */
        public final static String TABLE_NAME = "alergias";

        /**
         * Unique ID number for the alergia (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Date of the alergia.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ALERGIAS_FECHAHORA ="fecha";

        /**
         * Name of the alergia.
         *
         * The only possible values are {@link #ALERGIA_UNKNOWN+}, {@link #ALERGIA_HUEVOS},
         * or {@link #ALERGIA_PESCADO}...
         *
         * Type: INTEGER
         */
        public final static String COLUMN_ALERGIAS_NOMBRE= "nombre_alergia";

        /**
         * Tipo de alergia, alimentaria o farmacologica of the alergia.
         *
         * Type: TEXT
         */
        public final static String COLUMN_TIPO_ALERGIA = "tipo_alergia";

        /**
         * Comment of the alergia.
         *
         * Type: TEXT
         */
        public final static String COLUMN_COMENTARIO_ALERGIA = "comentario";

        /**
         * Possible values for the gender of the alergia.
         */
        public static final int ALERGIA_UNKNOWN = 0;
        public static final int ALERGIA_HUEVOS = 1;
        public static final int ALERGIA_PESCADO = 2;
        /*public static final int EMOCION_ALEGRIA = 3;
        public static final int EMOCION_AMOR = 4;
        public static final int EMOCION_ASCO = 5;

        public static final int EMOCION_CULPABILIDAD = 6;
        public static final int EMOCION_DESESPERACION = 7;
        public static final int EMOCION_DIVERSION = 8;
        public static final int EMOCION_ESPERANZA = 9;
        public static final int EMOCION_GRATITUD = 10;

        public static final int EMOCION_INDIFERENCIA = 11;
        public static final int EMOCION_INSPIRACION = 12;
        public static final int EMOCION_INTERES = 13;
        public static final int EMOCION_IRA = 14;
        public static final int EMOCION_MIEDO = 15;

        public static final int EMOCION_ORGULLO = 16;
        public static final int EMOCION_SERENDIDAD = 17;
        public static final int EMOCION_SOLEDAD = 18;
        public static final int EMOCION_TRISTEZA = 19;*/

        /**
         * Returns whether or not the given gender is
         * {@link #ALERGIA_UNKNOWN},
         * or {@link #ALERGIA_HUEVOS},
         * or {@link #ALERGIA_PESCADO}
         * or {@link #EMOCION_ALEGRIA}
         * or {@link #EMOCION_AMOR}
         * or {@link #EMOCION_ASCO}
         *
         * or {@link #EMOCION_CULPABILIDAD}
         * or {@link #EMOCION_DESESPERACION}
         * or {@link #EMOCION_DIVERSION}
         * or {@link #EMOCION_ESPERANZA}
         * or {@link #EMOCION_GRATITUD}
         *
         * or {@link #EMOCION_INDIFERENCIA}
         * or {@link #EMOCION_INSPIRACION}
         * or {@link #EMOCION_INTERES}
         * or {@link #EMOCION_IRA}
         * or {@link #EMOCION_MIEDO}
         *
         * or {@link #EMOCION_ORGULLO}
         * or {@link #EMOCION_SERENDIDAD}
         * or {@link #EMOCION_SOLEDAD}
         * or {@link #EMOCION_TRISTEZA}
         */
        public static boolean isValidAlergia(int alergia) {
            if (alergia == ALERGIA_UNKNOWN ||
                    alergia == ALERGIA_HUEVOS ||
                    alergia == ALERGIA_PESCADO /*||
                    emocion == EMOCION_ALEGRIA ||
                    emocion == EMOCION_AMOR ||
                    emocion == EMOCION_ASCO ||

                    emocion == EMOCION_CULPABILIDAD ||
                    emocion == EMOCION_DESESPERACION ||
                    emocion == EMOCION_DIVERSION ||
                    emocion == EMOCION_ESPERANZA ||
                    emocion == EMOCION_GRATITUD ||

                    emocion == EMOCION_INDIFERENCIA ||
                    emocion == EMOCION_INSPIRACION ||
                    emocion == EMOCION_INTERES ||
                    emocion == EMOCION_IRA ||
                    emocion == EMOCION_MIEDO ||

                    emocion == EMOCION_ORGULLO ||
                    emocion == EMOCION_SERENDIDAD ||
                    emocion == EMOCION_SOLEDAD ||
                    emocion == EMOCION_TRISTEZA*/) {
                return true;
            }
            return false;
        }
    }

}
