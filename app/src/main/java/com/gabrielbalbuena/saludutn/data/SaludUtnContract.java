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
        public static final int ALERGIA_LACTEOS = 3;
        public static final int ALERGIA_MANIES = 4;
        public static final int ALERGIA_MARSICOS = 5;

        public static final int ALERGIA_SOYA = 6;
        public static final int ALERGIA_NUECES = 7;
        public static final int ALERGIA_TRIGO = 8;
        public static final int ALERGIA_ANTICONVULSIVOS = 9;
        public static final int ALERGIA_INSULINA = 10;

        public static final int ALERGIA_YODO = 11;
        public static final int ALERGIA_PENICILINA = 12;
        public static final int ALERGIA_SULFAMIDAS = 13;

        /**
         * Returns whether or not the given gender is
         * {@link #ALERGIA_UNKNOWN},
         * or {@link #ALERGIA_HUEVOS},
         * or {@link #ALERGIA_PESCADO}
         * or {@link #ALERGIA_LACTEOS}
         * or {@link #ALERGIA_MANIES}
         * or {@link #ALERGIA_MARSICOS}
         *
         * or {@link #ALERGIA_SOYA}
         * or {@link #ALERGIA_NUECES}
         * or {@link #ALERGIA_TRIGO}
         * or {@link #ALERGIA_ANTICONVULSIVOS}
         * or {@link #ALERGIA_INSULINA}
         *
         * or {@link #ALERGIA_YODO}
         * or {@link #ALERGIA_PENICILINA}
         * or {@link #ALERGIA_SULFAMIDAS}
         */
        public static boolean isValidAlergia(int alergia) {
            if (alergia == ALERGIA_UNKNOWN ||
                    alergia == ALERGIA_HUEVOS ||
                    alergia == ALERGIA_PESCADO ||
                    alergia == ALERGIA_LACTEOS ||
                    alergia == ALERGIA_MANIES ||
                    alergia == ALERGIA_MARSICOS ||

                    alergia == ALERGIA_SOYA ||
                    alergia == ALERGIA_NUECES ||
                    alergia == ALERGIA_TRIGO ||
                    alergia == ALERGIA_ANTICONVULSIVOS ||
                    alergia == ALERGIA_INSULINA ||

                    alergia == ALERGIA_YODO ||
                    alergia == ALERGIA_PENICILINA ||
                    alergia == ALERGIA_SULFAMIDAS) {
                return true;
            }
            return false;
        }
    }

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.gabrielbalbuena/saludutn/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_HISTORIAL_MEDICO = "historialmedico";

    public static final class HistorialMedicoEntry implements BaseColumns{

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HISTORIAL_MEDICO);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORIAL_MEDICO;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORIAL_MEDICO;

        /** Name of database table for pets */
        public final static String TABLE_NAME = "historialmedico";

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
        public final static String COLUMN_FECHA_HM ="fecha";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DIAGNOSTICO = "diagnostico";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_FOTO_UNO_URL = "url_receta_uno";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_FOTO_DOS_URL = "url_receta_dos";

        /**
         * Weight of the pet.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRECIO_CONSULTA = "consulta";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_NOMBRE_DOCTOR = "nombre_medico";

        /**
         * Gender of the pet.
         *
         * The only possible values are {@link #ESPECIALIDAD_UNKNOWN
         * }, {@link #ESPECIALIDAD_ANESTESIOLOGIA},
         * or {@link #ESPECIALIDAD_ANGIOLOGIA}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_ESPECIALIDAD = "especialidad";

        /**
         * Possible values for the gender of the pet.
         */
        public static final int ESPECIALIDAD_UNKNOWN = 0;

        public static final int ESPECIALIDAD_ANESTESIOLOGIA = 1;
        public static final int ESPECIALIDAD_ANGIOLOGIA = 2;
        public static final int ESPECIALIDAD_CARDIOLOGIA= 3;
        public static final int ESPECIALIDAD_CARDIOLOGIA_INTERVENCIONISTA = 4;
        public static final int ESPECIALIDAD_CIRUGIA = 5;

        public static final int ESPECIALIDAD_CIRUGIA_ONCOLOGICA= 6;
        public static final int ESPECIALIDAD_CIRUGIA_PLASTICA = 7;
        public static final int ESPECIALIDAD_DERMATOLOGIA = 8;
        public static final int ESPECIALIDAD_ENDOSCOPIA = 9;
        public static final int ESPECIALIDAD_GASTROENTEROLOGIA = 10;

        public static final int ESPECIALIDAD_GINECOLOGIA_OBSTRETICIA= 11;
        public static final int ESPECIALIDAD_HEMATOLOGIA = 12;
        public static final int ESPECIALIDAD_INFECTOLOGIA = 13;
        public static final int ESPECIALIDAD_MEDICINA_DE_REHABILITACION = 14;
        public static final int ESPECIALIDAD_MEDICINA_INTERNA = 15;

        public static final int ESPECIALIDAD_NEFROLOGIA= 16;
        public static final int ESPECIALIDAD_NEUMOLOGIA = 17;
        public static final int ESPECIALIDAD_NEUROLOGIA = 18;
        public static final int ESPECIALIDAD_OFTALMOLOGIA = 19;
        public static final int ESPECIALIDAD_ONOCOLOGIA_MEDICA = 20;

        public static final int ESPECIALIDAD_ONCOLOGIA_PEDIATRICA= 21;
        public static final int ESPECIALIDAD_ORTOPEDIA = 22;
        public static final int ESPECIALIDAD_OTORRINOLARINGOLOGIA = 23;
        public static final int ESPECIALIDAD_PATOLOGIA_CLINICA = 24;
        public static final int ESPECIALIDAD_PEDIATRIA = 25;

        public static final int ESPECIALIDAD_PSIQUIATRIA= 26;
        public static final int ESPECIALIDAD_RADIOLOGIA_IMAGEN = 27;
        public static final int ESPECIALIDAD_RADIO_ONCOLOGICA = 28;
        public static final int ESPECIALIDAD_UROLOGIA = 29;

        /**
         * Returns whether or not the given gender is {@link #ESPECIALIDAD_UNKNOWN}, {@link #ESPECIALIDAD_ANESTESIOLOGIA},
         * or {@link #ESPECIALIDAD_ANGIOLOGIA}.
         */
        public static boolean isValidSpeciality(int speciality) {
            if (speciality == ESPECIALIDAD_UNKNOWN ||
                    speciality == ESPECIALIDAD_ANESTESIOLOGIA ||
                    speciality == ESPECIALIDAD_ANGIOLOGIA ||
                    speciality == ESPECIALIDAD_CARDIOLOGIA ||
                    speciality == ESPECIALIDAD_CARDIOLOGIA_INTERVENCIONISTA ||
                    speciality == ESPECIALIDAD_CIRUGIA ||

                    speciality == ESPECIALIDAD_CIRUGIA_ONCOLOGICA ||
                    speciality == ESPECIALIDAD_CIRUGIA_PLASTICA ||
                    speciality == ESPECIALIDAD_DERMATOLOGIA ||
                    speciality == ESPECIALIDAD_ENDOSCOPIA ||
                    speciality == ESPECIALIDAD_GASTROENTEROLOGIA ||

                    speciality == ESPECIALIDAD_GINECOLOGIA_OBSTRETICIA ||
                    speciality == ESPECIALIDAD_HEMATOLOGIA ||
                    speciality == ESPECIALIDAD_INFECTOLOGIA ||
                    speciality == ESPECIALIDAD_MEDICINA_DE_REHABILITACION ||
                    speciality == ESPECIALIDAD_MEDICINA_INTERNA ||

                    speciality == ESPECIALIDAD_NEFROLOGIA ||
                    speciality == ESPECIALIDAD_NEUMOLOGIA ||
                    speciality == ESPECIALIDAD_NEUROLOGIA ||
                    speciality == ESPECIALIDAD_OFTALMOLOGIA ||
                    speciality == ESPECIALIDAD_ONOCOLOGIA_MEDICA ||

                    speciality == ESPECIALIDAD_ONCOLOGIA_PEDIATRICA ||
                    speciality == ESPECIALIDAD_ORTOPEDIA ||
                    speciality == ESPECIALIDAD_OTORRINOLARINGOLOGIA ||
                    speciality == ESPECIALIDAD_PATOLOGIA_CLINICA ||
                    speciality == ESPECIALIDAD_PEDIATRIA ||

                    speciality == ESPECIALIDAD_PSIQUIATRIA ||
                    speciality == ESPECIALIDAD_RADIOLOGIA_IMAGEN ||
                    speciality == ESPECIALIDAD_RADIO_ONCOLOGICA ||
                    speciality == ESPECIALIDAD_UROLOGIA ) {
                return true;
            }
            return false;
        }
    }
}

