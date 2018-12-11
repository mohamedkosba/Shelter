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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
// one thing to remember I can import specific variable inside inner
// class which itself inside other class and use it directly as it declared #here


// this statement for importing the data we create in our custom package
import com.example.android.pets.data.*;
// this line do as the above but I use it to understand IMPORTING
// more clearly
import com.example.android.pets.data.petsContract.petEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    petsDBHelper mDbHelper;
//first we get reference to the Edit Fields
    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();

    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        // we importing the value we set to the gender values here
                        // inside our spinner
                        mGender = petsContract.petEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = petsContract.petEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = petEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = petEntry.GENDER_UNKNOWN; // Unknown
            }
        });
    }
/*
* ==============================================================================================
* */

// get the user input from the editor activity and insert it as a new pet into the Database
    private void insertPet(){
        //second get the data from the user and pass it to the ContentValue

        //getText() : is to get the text from that EditText

        //toString() : convert the input to string data type

        //trim() : for clearing all the white space the user might
        //type by mistake when he entering the value
        String insertedPetName = mNameEditText.getText().toString().trim();
        String insertedPetBreed = mBreedEditText.getText().toString().trim();
        String insertedPetWeight = mWeightEditText.getText().toString().trim();
        //now we convert the string to integer value
        int InsertedWeight = Integer.parseInt(insertedPetWeight);

        // now we initializing the petDbHelper object by using dbHelper Constructor
        mDbHelper = new petsDBHelper(this);

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
        values.put(petEntry.COLUMN_PET_NAME, insertedPetName);
        values.put(petEntry.COLUMN_PET_BREED, insertedPetBreed);
        values.put(petEntry.COLUMN_PET_GENDER, mGender);
        values.put(petEntry.COLUMN_PET_WEIGHT, InsertedWeight);

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

//        Log.v("EditorActivity", "Pet Saved With raw ID " + newRowID);
        if (newRowID == -1){
            Toast.makeText(getBaseContext(),"Error Inserting ur Pet", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getBaseContext(),"Pet Saved With raw ID" + newRowID, Toast.LENGTH_SHORT).show();
        }
    }
/*
* ==============================================================================================
* */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save the current pet to th Database
                insertPet();
                //exit the activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}