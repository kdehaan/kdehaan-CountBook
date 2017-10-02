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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Manages main list of counters
 *
 * @author kdehaan
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "countBook.sav";

    private ListView CounterList;

    private ArrayList<Counter> counters = new ArrayList<>();
    private ArrayAdapter<Counter> adapter;

    public static final String EXTRA_MESSAGE = "dehaan.kdehaan_countbook.MESSAGE";
    public static final Integer CREATE_CODE = 1;
    public static final Integer EDIT_CODE = 2;


    /**
     * Runs when an intent is returned from a sub-activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            // New Counter being created
            if (requestCode == CREATE_CODE  && resultCode  == RESULT_OK) {

                Gson gson = new Gson();
                String gsonString = data.getStringExtra("gsonCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.add(newCounter);
                updateScreen();
            }
            // Existing Counter being edited
            else if (requestCode == EDIT_CODE  && resultCode  == RESULT_OK) {

                Gson gson = new Gson();


                String arrayIndex = data.getStringExtra("arrayIndex");
                String deleteCounter = data.getStringExtra("delete");
                if (Boolean.parseBoolean(deleteCounter)) {
                    counters.remove(Integer.parseInt(arrayIndex));
                    updateScreen();
                    return;
                }
                String gsonString = data.getStringExtra("gsonCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.set(Integer.parseInt(arrayIndex), newCounter);
                updateScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Runs on creation, sets up the main window
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.createCounter);
        CounterList = (ListView) findViewById(R.id.CounterList);

        // Edit Counter when Counter is clicked
        CounterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object o = CounterList.getItemAtPosition(position);
                Counter editingCounter = (Counter)o;

                Intent intent = new Intent(MainActivity.this, editCounterActivity.class);

                Gson gson = new Gson();
                String gsonCounter = gson.toJson(editingCounter);
                intent.putExtra(EXTRA_MESSAGE, gsonCounter);
                intent.putExtra("arrayIndex", Integer.toString(position));
                startActivityForResult(intent, EDIT_CODE);


            }
        });

        // Add new counter onClick
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addCounterActivity.class);
                startActivityForResult(intent, CREATE_CODE);
            }
        });

    }

    /**
     * Runs on start, loads data from file and creates adapter
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<>(this, R.layout.list_item, counters);
        CounterList.setAdapter(adapter);
        updateCounterQuantity();

    }

    /**
     * Refreshes the screen
     */
    private void updateScreen() {
        updateCounterQuantity();
        saveInFile();
        adapter.notifyDataSetChanged();
    }

    /**
     * Refreshes the Counter counter
     */
    private void updateCounterQuantity() {
        TextView counterQuant = (TextView) findViewById(R.id.counterQuantity);
        String counterQuantifier = " Counters";
        if (counters.size() == 1){
            counterQuantifier = " Counter";
        }
        counterQuant.setText(Integer.toString(counters.size())+ counterQuantifier);
    }

    /**
     * Loads counters from file
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(in, listType);
        } catch(FileNotFoundException e) {
            counters = new ArrayList<>();
        }
    }

    /**
     * Saves counters to file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();
            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
