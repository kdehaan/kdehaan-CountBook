/*
 * Counter
 *
 * Version 1.0
 *
 * September 27, 2017
 *
 * Copyright © 2017 Kevin de Haan, CMPUT301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the COde of Student Behavior at the University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact kdehaan@ualberta.ca
 */
package dehaan.kdehaan_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


/**
 * Manages the add counter page
 *
 * @author kdehaan
 * @version 1.0
 * @since 1.0
 */
public class addCounterActivity extends AppCompatActivity {

    private Intent intent;

    /**
     * onCreate function sets up intent and XML layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_counter);

        intent = getIntent();

    }

    /**
     * Returns true if EditText has no text
     *
     * @param editText
     * @return
     */
    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * Displays a toast with an error message
     *
     * @param message
     */
    public void displayError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * onClick event tied to the "Add Counter" button
     * Confirms the creation of a new counter and sends
     * a gson description of it to the main activity
     *
     * @param view
     */
    public void confirmCounter(View view) {

        String nameString;
        Integer initValInt;
        String commentString;

        try {
            EditText nameText = (EditText) findViewById(R.id.editName);
            nameString = nameText.getText().toString();
            if (isEmpty(nameText)) {
                displayError("Please enter a name");
                return;
            }
        } catch (Exception e) {
            displayError("Invalid name");
            return;
        }

        try {
            EditText initValText = (EditText) findViewById(R.id.editInitVal);
            initValInt = Integer.parseInt(initValText.getText().toString());
            if (initValInt < 0) {
                displayError("Please enter a positive value");
                return;
            }
        } catch (Exception e) {
            displayError("Invalid initial value");
            return;
        }

        try {
            EditText commentText = (EditText) findViewById(R.id.editComment);
            commentString = commentText.getText().toString();
        } catch (Exception e) {
            displayError("Invalid comment");   // probably can't happen but you never know
            return;
        }


        Counter counter = new Counter(nameString, initValInt, commentString); // int not behaving

        Gson gson = new Gson();
        String gsonCounter = gson.toJson(counter);

        intent.putExtra("gsonCounter", gsonCounter);
        setResult(RESULT_OK, intent);

        finish();
    }


}