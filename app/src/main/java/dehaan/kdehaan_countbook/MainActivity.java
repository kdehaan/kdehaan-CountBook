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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "countBook.sav";

    private ListView CounterList;

    private ArrayList<Counter> counters = new ArrayList<>();
    private ArrayAdapter<Counter> adapter;

    public static final String EXTRA_MESSAGE = "dehaan.kdehaan_countbook.MESSAGE";
    public static final Integer CREATE_CODE = 1;
    public static final Integer EDIT_CODE = 2;

    public void addCounter(View view) {
        Intent intent = new Intent(this, addCounterActivity.class);
//        Counter newCounter = new Counter("count0", 0);
//        Gson gson = new Gson();
//        String gsonCounter = gson.toJson(newCounter);
//        intent.putExtra(EXTRA_MESSAGE, gsonCounter);
        startActivityForResult(intent, CREATE_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CREATE_CODE  && resultCode  == RESULT_OK) {

                Gson gson = new Gson();
                String gsonString = data.getStringExtra("gsonCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.add(newCounter);
                adapter.notifyDataSetChanged();
                saveInFile();
            }
            else if (requestCode == EDIT_CODE  && resultCode  == RESULT_OK) {

                Gson gson = new Gson();
                String gsonString = data.getStringExtra("gsonCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.add(newCounter);
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.createCounter);
        Button clearButton = (Button) findViewById(R.id.clearCounters);
        CounterList = (ListView) findViewById(R.id.CounterList);

        CounterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object o = CounterList.getItemAtPosition(position);
                Counter editingCounter = (Counter)o;

                Intent intent = new Intent(MainActivity.this, editCounterActivity.class);
//
                Gson gson = new Gson();
                String gsonCounter = gson.toJson(editingCounter);
                intent.putExtra(EXTRA_MESSAGE, gsonCounter);
                startActivityForResult(intent, EDIT_CODE);


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                counters.add(new Counter("new counter", 0));
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                counters.clear();
                saveInFile();
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<>(this, R.layout.list_item, counters);
        CounterList.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(in, listType);
        } catch(FileNotFoundException e) {
            counters = new ArrayList<>();
        } /*catch(IOException e) {
            throw new RuntimeException(e);
        }*/
    }

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
