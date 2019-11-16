package com.example.hitshapes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int GAME_REQUEST_CODE = 1;  // The request code
    final static String TAG = MainActivity.class.getName();
    private TextView msgTextView;
    private TextView msg2TextView;


    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgTextView = (TextView)findViewById(R.id.msgTextView);
        msg2TextView = (TextView)findViewById(R.id.msg2TextView);

    }

    /** Called when the user taps the Send button */
    public void startGame(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent, GAME_REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == GAME_REQUEST_CODE  && resultCode  == RESULT_OK) {
//                Add score to high scores if user requested
                int requiredScore = data.getIntExtra("score",0);
                int missedBalloons = data.getIntExtra("missedBalloons",0);

                String scoreTxt =  "Your score: " + Integer.toString(requiredScore);
                msgTextView.setText(scoreTxt);
                String missedTxt =   "Balloons missed "+ missedBalloons;
                msg2TextView.setText(missedTxt);
//                Log.i(TAG, "MainActivity onActivityResult()" + scoreTxt);
//
//                Toast.makeText(this, requiredScore,
//                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
