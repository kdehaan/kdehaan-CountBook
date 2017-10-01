package dehaan.kdehaan_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;


public class editCounterActivity extends AppCompatActivity {

    private Intent intent;
    private Counter counter;

    private EditText nameText;
    private EditText initValText;
    private EditText currentValText;
    private EditText commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        intent = getIntent();
        String gsonCounter = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Gson gson = new Gson();
        counter = gson.fromJson(gsonCounter, Counter.class);

        nameText = (EditText) findViewById(R.id.editName);
        nameText.setText(counter.getName());

        initValText = (EditText) findViewById(R.id.editInitVal);
        initValText.setText(counter.getInitValue().toString());

        currentValText = (EditText) findViewById(R.id.editCurrentVal);
        currentValText.setText(counter.getCurrentValue().toString());

        commentText = (EditText) findViewById(R.id.editComment);
        commentText.setText(counter.getComment());


    }


    public void confirmChangeCounter(View view) {
        String nameString = nameText.getText().toString();

        Integer initValInt = Integer.parseInt(initValText.getText().toString());

        Integer currentValInt = Integer.parseInt(initValText.getText().toString());


        String commentString = commentText.getText().toString();

        counter.setName(nameString);
        counter.setInitValue(initValInt);
        counter.setCurrentValue(currentValInt);
        counter.setComment(commentString);

        Gson gson = new Gson();
        String gsonCounter = gson.toJson(counter);

        intent.putExtra("gsonCounter", gsonCounter);
        setResult(RESULT_OK, intent);

        finish();
    }


}