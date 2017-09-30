package dehaan.kdehaan_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;


public class createCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Gson gson = new Gson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_counter);

        Intent intent = getIntent();
        String gsonMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        Counter newCounter = gson.fromJson(gsonMessage, Counter.class);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(newCounter.toString());

        intent.putExtra("gsonCounter", gsonMessage);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void confirmNewCounter( ) {
        finish();
    }


}