package dehaan.kdehaan_countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    public void editCounter(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        Counter newCounter = new Counter("count0", 0);
        Gson gson = new Gson();
        String gsonCounter = gson.toJson(newCounter);
        intent.putExtra(EXTRA_MESSAGE, gsonCounter);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.createCounter);
        Button clearButton = (Button) findViewById(R.id.clearCounters);
        CounterList = (ListView) findViewById(R.id.CounterList);

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
