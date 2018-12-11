/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.petsContract;
import com.example.android.pets.data.petsContract.petEntry;
import com.example.android.pets.data.petsDBHelper;
import com.example.android.pets.data.petsProvider;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {
    protected SQLiteDatabase db;
    petsDBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        mDbHelper = new petsDBHelper(this);
        displayDatabaseInfo();
    }
    /*
    * We use it because when u back from the editor activity
    * we ned to refresh the catalog activity
    * to display the data info
    * */
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
//
//
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//
        petsDBHelper mDbHelper = new petsDBHelper(this);
//                        /*making DB Connection If Not Exit*/
//        // Create and/or open a database to read from it
//
//        // mDBHelper will check for existing database and if there is no one
//        //the class create anew one by calling on create method and this method already has the
//        // code we assign tho create the database
//
//        // ---- and if there is a DB already it will create a SQLiteDatabase instance coupled with
//        //the existing database and return that object to the activity they requested it
//
//        //==so at the end of all cases we will have SQLiteDatabase object Connected to the shelter DB
//        // and we use this object to communicate SQLite instructions to our shelter DB===
    //    SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//        // Perform this raw SQL query "SELECT * FROM pets"
//        // to get a Cursor that contains all rows from the pets table.
//
//        // the cursor is an object which represent the rows of the db
////        Cursor cursor = db.rawQuery("SELECT * FROM " + petEntry.TABLE_NAME, null);
//
//        // allows as to specify what columns we are interested in
//        // hint; NOT ONly the rows has indices the column also has index
//        // which will bbe in the next order
        String[] projection = {
                petEntry._ID,
                petEntry.COLUMN_PET_NAME,
                petEntry.COLUMN_PET_BREED,
                petEntry.COLUMN_PET_GENDER,
                petEntry.COLUMN_PET_WEIGHT
        };


        //Query method
        //      1-return back to us This (Cursor)
                    //this Cursor can pass back to the UI to be use or displayed
                    // the cursor is an object which represent the rows of the db
        //      2- we use it to read out data
//        Cursor cursor = db.query(
//                petEntry.TABLE_NAME,
//                projection,      //--->    // allows as to specify what columns we are interested in
//                null,   //---> //help us to specify what rows we are interested in
//                null,//---> //help us to specify what rows we are interested in
//                null,
//                null,
//                null
//        );


        Cursor cursor = getContentResolver().query(petsContract.petEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);



        TextView displayView = (TextView) findViewById(R.id.text_view_pet);


        // the perpus of the try block is to surrounds the code that uses the cursor
        //and we always must implement the finally clause to close the cursor
        //=== one of the advantages of this try block is if the code cruses for any reason the cursor
        //    will be closed (the finally clause always happens whether or not the app crashes)
        try {

            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // we doing that by using getCount method
            // pets table in the database).
            displayView.setText("This Shelter has " + cursor.getCount() + " Pets");
            // we use the append commant to keep adding text to the TextView
            displayView.append("\n\nThe table name is "+ petEntry.TABLE_NAME + " \n and it has five column which is: \n\n ( "
                    + petEntry._ID + " - "
                    + petEntry.COLUMN_PET_NAME + " - "
                    + petEntry.COLUMN_PET_BREED + " - "
                    + petEntry.COLUMN_PET_GENDER + " - "
                    + petEntry.COLUMN_PET_WEIGHT + " ) \n\n"
            );
//===================== here is the actual start for reading the attributes from the cursor returned ============
            // figure out the index of each column
            // as we knew Query method has the parameter Projection and this pro is responsible for
            // the column and the order of the columns index inside the cursor came from it

            //getColumnIndex :
            //  this method takes in the name of the column and return back to us integer index value
            int idColumnIndex = cursor.getColumnIndex(petEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(petEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(petEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(petEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(petEntry.COLUMN_PET_WEIGHT);

            /*
             *   How to get Information From that CURSOR .... ???
             *   -- by default the cursor starts at position -1
             *   -- and when we inter the while loop we call moveToNext
             *       -- moveToNext : is cursor pre defined method the move to the next ROW in the cursor
             *       -- if there is no next row it will return false (and this mean the loop will stop)
             *
             * */
            //
            //we use while because it's a common pattern to looking through cursors
            //
            while (cursor.moveToNext()){

                //once we have the column index we can get the value inside specific cell
                // from the table wither it's integer of string
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                // this to display the correct gender not only the index of it
                String GenderInString = "";
                if(currentGender == 0){
                    GenderInString = "Unknown";
                }else if (currentGender == 1){
                    GenderInString = "Male";
                }else {
                    GenderInString = "Female";
                }

                int currentWeight = cursor.getInt(weightColumnIndex);
                // we use append method to concatenate  the information to the text in the TextView
                displayView.append(
                        currentID + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        GenderInString + " - " +
                        currentWeight +"\n");
            }


        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            //===========we do that to avoid any memory leaks============
            cursor.close();
        }
    }

//==================================================================================================
    private void insertPet(){
        //get the data repository in the write mode
        db = mDbHelper.getWritableDatabase();
        /*
         * ContentValues :
         *   is a class that (STORES) a bunch of key value pairs
         *
         *   Key : is the column value in the database
         *   Value: is whatever value u want to insert to the database
         *
         * */
        ContentValues values = new ContentValues();
        values.put(petEntry.COLUMN_PET_NAME, "Maxiena");
        values.put(petEntry.COLUMN_PET_BREED, "German");
        values.put(petEntry.COLUMN_PET_GENDER, petEntry.GENDER_FEMALE);
        values.put(petEntry.COLUMN_PET_WEIGHT, 9);

        /*
         * insert:
         *   is part of SQLiteDatabase and if u look at the documentation u will see that we use it
         *   to create new row into our Database and it has three parameters
         *
         *   1-the mane of the table we want to insert the data in
         *   2-optional parameters will always be null which will only be use if u inserting an entirely
         *     empty row into the database
         *   3- the content value u want to create
         *
//####   *   and final thing to know about it it's returns the ID of the newly inserted row
         *   and if there is an error it will return back -1 ==> so that I will save it in var type (Long)
         * */
        long newRowID =  db.insert(petEntry.TABLE_NAME, null, values);

//####  //I put the above line (insert Method) in the variable called newRowID because I want to capture
        //the value that returns from the (insert) method by adding it to a (log message) the next line

        Log.v("CatalogActivity", "Pet Saved With raw ID " + newRowID);
    }
//==================================================================================================

    //this will create the menu item with the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    // this will be the behavior when menuItem selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
