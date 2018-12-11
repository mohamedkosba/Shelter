package com.example.android.pets.data;
// this another package we created ourselves to prepare the data for use
// to use SQLite database in your android project we have to main things
//1 - schema for the table structure && contract
//2- SQL helper method
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/*
 *========this contract for document the schema and create constance for it =========
 *  ===> we make it final because it's only provides data
 *      and also make it not to be extended
 *
 * ===>  the name should be in this form BlankContract
 *       remove Bank and rename it
 * */

public final class petsContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private petsContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PETS = "pets";




    /*
     * ===> every TABLE should represented as an inner class
     *
     * ===>  the name should be in this form BlankEntry
     *       remove Blank and rename it
     *
     * ===>  and also should implements BaseColumns
     * */
    public static abstract class petEntry implements BaseColumns {

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);


        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;


        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;




        //this string data type here is just for the constant
        //not for the attribute value
        /**
         *            ==this is the table name==
         */
        public static final String TABLE_NAME = "pets";

        /**
         *            ==this is the columns names==
         */
        public static final String _ID= BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED= "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        /**
         * Possible values for the types of the Gender.
         *
         * we add these constant here because we must
         * DEFINE every constants related to the table HERE
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


    }
}
