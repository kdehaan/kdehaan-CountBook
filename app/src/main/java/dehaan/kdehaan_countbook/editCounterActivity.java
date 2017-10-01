package dehaan.kdehaan_countbook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class editCounterActivity extends AppCompatActivity {

    private Intent intent;
    private Counter counter;
    //
    private EditText nameText;
    private EditText initValText;
    private EditText currentValText;
    private EditText commentText;

    private String arrayIndex;
;
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

    private void updateDisplay() {
        nameText = (EditText) findViewById(R.id.editName);
        nameText.setText(counter.getName());

        initValText = (EditText) findViewById(R.id.editInitVal);
        initValText.setText(counter.getInitValue().toString());

        currentValText = (EditText) findViewById(R.id.editCurrentVal);
        currentValText.setText(counter.getCurrentValue().toString());

        commentText = (EditText) findViewById(R.id.editComment);
        commentText.setText(counter.getComment());
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private void displayError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void incrementCounter(View view) {
        counter.increment();
        updateDisplay();
    }

    public void decrementCounter(View view) {
        counter.decrement();
        updateDisplay();
    }

    public void resetCounter(View view) {
        counter.reset();
        updateDisplay();
    }

    public void deleteCounter(View view){
        intent.putExtra("delete", "true");
        intent.putExtra("arrayIndex", arrayIndex);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void confirmChangeCounter(View view) {
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
