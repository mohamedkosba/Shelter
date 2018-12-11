package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class petsProvider extends ContentProvider {

    private static final int PETS = 100;

    private static final int PETS_ID = 101;

    /*
    * we can use this UriMatcher when we're using query, update, insert and delete methods
    * */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(petsContract.CONTENT_AUTHORITY, petsContract.PATH_PETS, PETS);
        sUriMatcher.addURI(petsContract.CONTENT_AUTHORITY, petsContract.PATH_PETS + "/#", PETS_ID);
    }


    private SQLiteDatabase db;

    /* initializing the object that gives us access to the database
    * from here we get the SQLite object
    * */
    private petsDBHelper mDbHelper;



    /*
    * we override this to initialize the petDBHelper that gives us the access to our database
    * */
    @Override
    public boolean onCreate() {

        // now we initializing the petDbHelper object by using dbHelper Constructor
        mDbHelper = new petsDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // lets access the database using mDbHelper object

        // we chose getReadable specific because the query method won't make any changes or writes to the database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor = null;

        // sUriMatcher helps us to find out what kind of input URI was passed to us up inside the [ query(@NonNull Uri uri ]

        // from this sUriMatcher.match(uri) we will receive an integer code to use it to check whish path to go down
        int match = sUriMatcher.match(uri);
        switch (match){
            case PETS:
                cursor = database.query(petsContract.petEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PETS_ID:
                    selection = petsContract.petEntry._ID + "=?";
                    //convert it to string using (String.valueOf) because it's an integer
                    selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                    cursor = database.query(petsContract.petEntry.TABLE_NAME, projection, selection, selectionArgs,
                            null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can not query unknown uri" + uri);
        }

            return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return petsContract.petEntry.CONTENT_LIST_TYPE;
            case PETS_ID:
                return petsContract.petEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }                    //  selectionArgs is an array of strings to be substituted where was a "?" up in the selection string
    // hint : if there is 3 question mark in the selection string then there should be three element of the selection args array

    // {ontentUris.parseId(uri)} this to extract the end of the URI (this method is to convert the last segment of the uri Path) for example
    // com.example.android.pets/5 ----> it will extract the 5 and then we convert

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
