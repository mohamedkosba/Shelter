package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
// this line do as the above but I use it to understand IMPORTING
// more clearly
import com.example.android.pets.data.petsContract.petEntry;
/*
* this blankDBHelper provide a connection to (SQLiteDatabase) which has the ability
* to manipulate the DB cylinder itself (in other word SQLiteDatabase has the commands that deal with
*                                       the DB)
*    by executing this command
*       petsDBHelper mDbHelper = new petsDBHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Hint:
        u will use this line of code to (Create - Update - Delete)
        from DB
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        u will use this line of code to (Select)
        from DB
        SQLiteDatabase db = mDbHelper.getReedableDatabase();
*
* */
public class petsDBHelper extends SQLiteOpenHelper {

    // this is the DB file name (the cylinder itself)
    private static final String DATABASE_NAME = "petsShelter.db";

    // this is the DB Version and must be changed when you upgrade the DB schema
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link petsDBHelper}.
     *
     * @param context of the app
     *
     * only take the context from outside and there is no need for the factory
     */
    public petsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /**
     * This is called when the database is created for the first time.
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // this variable holds the string with the SQL statement to create a table
        String DATABASE_CREATE_TABLE = "CREATE TABLE " + petEntry.TABLE_NAME + " ("
                + petEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + petEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + petEntry.COLUMN_PET_BREED + " TEXT, "
                + petEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + petEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        // this execSQL method only accept SQL statements for creating, updating and deleting commands
        // never use any reading commands with it e.i SELECT

        // we pass to it here a constants string by writing a schema for
        // creating a table
        sqLiteDatabase.execSQL(DATABASE_CREATE_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // The database is still at version 1, so there's nothing to do be done here.

    }

/*        // this variable holds the string with the SQL statement to drop a table
        String DATABASE_DELETE_TABLE = "DROP TABLE IF EXISTS";

        // this execSQL method only accept SQL statements for creating, updating and deleting commands
        // never use any reading commands with it e.i SELECT

        // we pass to it here a constants string for deleting the current table

        sqLiteDatabase.execSQL(DATABASE_DELETE_TABLE);

        // after deleting a table we call here onCreate method to create a new updated on
        onCreate(sqLiteDatabase);
*/
}
