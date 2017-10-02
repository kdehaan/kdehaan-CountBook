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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Manages the edit counter page
 *
 * @see addCounterActivity
 * @see MainActivity
 * @author kdehaan
 * @version 1.0
 * @since 1.0
 */
public class editCounterActivity extends addCounterActivity {

    private Intent intent;
    private Counter counter;
    private EditText nameText;
    private EditText initValText;
    private EditText currentValText;
    private EditText commentText;

    private String arrayIndex;

    /**
     * onCreate function parses gson Counter and sets up the XML layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        intent = getIntent();
        String gsonCounter = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        arrayIndex = intent.getStringExtra("arrayIndex");

        Gson gson = new Gson();
        counter = gson.fromJson(gsonCounter, Counter.class);

        updateDisplay();


    }

    /**
     * Updates the displayed values on the activity when called
     * Receives data from this.counter
     */
    private void updateDisplay() {
        nameText = (EditText) findViewById(R.id.editName);
        nameText.setText(counter.getName());

        initValText = (EditText) findViewById(R.id.editInitVal);
        initValText.setText(counter.getInitValue().toString());

        currentValText = (EditText) findViewById(R.id.editCurrentVal);
        currentValText.setText(counter.getCurrentValue().toString());

        commentText = (EditText) findViewById(R.id.editComment);
        commentText.setText(counter.getComment());

        TextView dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText("Created "+counter.getDate().toString());
    }

    /**
     * onClick, increments the counter
     * @param view
     */
    public void incrementCounter(View view) {
        counter.increment();
        updateDisplay();
    }

    /**
     * onClick, decrements the counter
     * @param view
     */
    public void decrementCounter(View view) {
        counter.decrement();
        updateDisplay();
    }

    /**
     * onClick, resets the counter to init value
     * @param view
     */
    public void resetCounter(View view) {
        counter.reset();
        updateDisplay();
    }

    /**
     * onClick, deletes the counter
     * @param view
     */
    public void deleteCounter(View view){
        intent.putExtra("delete", "true");
        intent.putExtra("arrayIndex", arrayIndex);
        setResult(RESULT_OK, intent);

        finish();
    }

    /**
     * Confirm changes made to counter and send gson Counter to main activity
     * @param view
     */
    @Override
    public void confirmCounter(View view) {
        String nameString;
        Integer initValInt;
        Integer currentValInt;
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
            EditText currentValText = (EditText) findViewById(R.id.editCurrentVal);
            currentValInt = Integer.parseInt(currentValText.getText().toString());
            if (currentValInt < 0) {
                displayError("Please enter a positive value");
                return;
            }
        } catch (Exception e) {
            displayError("Invalid current value");
            return;
        }

        try {
            EditText commentText = (EditText) findViewById(R.id.editComment);
            commentString = commentText.getText().toString();
        } catch (Exception e) {
            displayError("Invalid comment");   // probably can't happen but you never know
            return;
        }

        counter.setName(nameString);
        counter.setCurrentValue(currentValInt);
        counter.setInitValue(initValInt);
        counter.setComment(commentString);


        Gson gson = new Gson();
        String gsonCounter = gson.toJson(counter);
        intent.putExtra("delete", "false");
        intent.putExtra("gsonCounter", gsonCounter);
        intent.putExtra("arrayIndex", arrayIndex);
        setResult(RESULT_OK, intent);

        finish();
    }
}
