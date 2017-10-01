package dehaan.kdehaan_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


public class addCounterActivity extends AppCompatActivity {

    private Intent intent;
    private Toast toast;
//    private String gsonCounter;
//    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_counter);

        intent = getIntent();
        toast = new Toast(this);

    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public void confirmNewCounter(View view) {



        EditText nameText = (EditText) findViewById(R.id.editName);
        if (isEmpty(nameText)) {
            toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return;
        }
        String nameString = nameText.getText().toString();

        EditText initValText = (EditText) findViewById(R.id.editInitVal);
        Integer initValInt = Integer.parseInt(initValText.getText().toString());

        EditText commentText = (EditText) findViewById(R.id.editComment);
        String commentString = commentText.getText().toString();




//        Counter counter = new Counter("sdf", 1, "");

        Counter counter = new Counter(nameString, initValInt, commentString); // int not behaving

        Gson gson = new Gson();
        String gsonCounter = gson.toJson(counter);

        intent.putExtra("gsonCounter", gsonCounter);
        setResult(RESULT_OK, intent);

        finish();
    }


}