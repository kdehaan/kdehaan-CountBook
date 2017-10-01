package dehaan.kdehaan_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;


public class addCounterActivity extends AppCompatActivity {

    private Intent intent;
//    private String gsonCounter;
//    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_counter);

        intent = getIntent();
//        gsonCounter = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//        counter = gson.fromJson(gsonCounter, Counter.class);
//
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(counter.toString());
//        intent.putExtra("gsonCounter", gsonCounter);
//        setResult(RESULT_OK, intent);

//        finish();


//        finish();
    }


    public void confirmNewCounter(View view) {
        EditText nameText = (EditText) findViewById(R.id.editName);
        String nameString = nameText.getText().toString();

        EditText initValText = (EditText) findViewById(R.id.editInitVal);
        Integer initValInt = Integer.parseInt(initValText.getText().toString());

        EditText commentText = (EditText) findViewById(R.id.editComment);
        String commentString = commentText.getText().toString();

        Counter counter = new Counter(nameString, initValInt, commentString); // int not behaving

        Gson gson = new Gson();
        String gsonCounter = gson.toJson(counter);

        intent.putExtra("gsonCounter", gsonCounter);
        setResult(RESULT_OK, intent);

        finish();
    }


}