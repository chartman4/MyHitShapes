package com.example.hitshapes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHighScore extends AppCompatActivity {

    private int score;
    private String name;
    private Date scoreDate;

    private EditText nameEditText;
    private EditText scoreEditText;
    private EditText dateEditText;
    private TextView errorMsgText;
    private Button addScoreBtn;

    private boolean nameIsValid = false;
    private boolean scoreIsValid = true;
    private boolean dateIsValid = true;

    private static final String LOG_TAG =
            AddHighScore.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_high_score);

        Intent intent = getIntent();

        score = intent.getIntExtra("score", 0);


        scoreEditText = (EditText) findViewById(R.id.addScoreEditText);
        dateEditText = (EditText) findViewById(R.id.addDateEditText);
        nameEditText = (EditText) findViewById(R.id.addNameEditText);
        errorMsgText = (TextView) findViewById(R.id.addErrMsgTextView);
        addScoreBtn = (Button) findViewById(R.id.addHighScoreBtn);


        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        scoreDate = new Date();
        String todayStr = dateFormat.format(scoreDate);
        dateEditText.setText(todayStr);
        String scoreStr = score + " ";

        scoreEditText.setText(scoreStr);
//        scoreEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    int numberEntered = Integer.parseInt(scoreEditText.getText().toString());
//                    // if no error number is OK
//                    if (numberEntered > 0)  scoreIsValid = true; else scoreIsValid = false;
//                    if (nameIsValid && scoreIsValid && dateIsValid) addScoreBtn.setEnabled(true); else addScoreBtn.setEnabled(false);
//                } catch(NumberFormatException e)
//                {
//                        errorMsgText.setText("Score must be a number");
//                }
//
//            }
//        });
        nameEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.d(LOG_TAG, "After text changed!");
                // you can call or do what you want with your EditText here
                // yourEditText...
                String nameEntered  = nameEditText.getText().toString();
                if (nameEntered.length()>0 && nameEntered.length() < 31) nameIsValid = true; else nameIsValid = false;
                if (nameIsValid && scoreIsValid && dateIsValid) addScoreBtn.setEnabled(true); else addScoreBtn.setEnabled(false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d(LOG_TAG, "Before Text Changed!");

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(LOG_TAG, "On Text Changed!");

            }
        });
    }


        public void launchMainWithData(View view) {

        String nameEntered  = nameEditText.getText().toString();
//        int numberEntered = Integer.parseInt(scoreEditText.getText().toString());
        String dateStringEntered=(dateEditText.getText().toString());
//
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); // Make sure user insert date into edittext in this format.
        Date dateObject;
        try{
            dateObject = formatter.parse(dateStringEntered);

//            date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
//            time = new SimpleDateFormat("h:mmaa").format(dateObject);

//            Toast.makeText(getBaseContext(), date + time, Toast.LENGTH_LONG).show();

            Intent intent = getIntent();
            intent.putExtra("name", nameEntered);
            intent.putExtra("score", score);
//            intent.putExtra("dateString", dateStringEntered);
            intent.putExtra("date", scoreDate);

            setResult(RESULT_OK, intent);
            finish();
        }

        catch (java.text.ParseException e)
        {
//            e.printStackTrace();
            Log.i("E11111111111", e.toString());
            errorMsgText.setText("Date format must be in MM/dd/yyyy format");
        }



    }
}
