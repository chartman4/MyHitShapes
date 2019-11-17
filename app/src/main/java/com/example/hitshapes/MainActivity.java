package com.example.hitshapes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int GAME_REQUEST_CODE = 1;  // The request code
    final static String TAG = MainActivity.class.getName();
    private TextView msgTextView;
    private TextView msg2TextView;
    private Button addHSBtn;

    private ListView listView;
    String fileName = "highscores.txt";

    private ArrayList<HighScoreModel> arrayList = new ArrayList<HighScoreModel>();

    private final static int REQUEST_CODE_1 = 1;
    private int score = 0;

//    CustomAdapter adapter = new CustomAdapter(this, R.layout.item_layout, arrayList);
CustomAdapter adapter;


    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgTextView = (TextView)findViewById(R.id.msgTextView);
        msg2TextView = (TextView)findViewById(R.id.msg2TextView);
        addHSBtn = (Button)findViewById(R.id.addHSBtn);

        listView = (ListView)findViewById(R.id.listView);

        FileHelper txtFile = new FileHelper();
        arrayList = txtFile.ReadFile(MainActivity.this);

        // sort file
        sortArray();

        // pass results to listViewAdapter
//        listView.setAdapter(customAdapter);

      adapter = new CustomAdapter(this, arrayList);

        listView.setAdapter(adapter);

    }
    //    private ArrayList<HighScoreModel> sortArray (ArrayList<HighScoreModel>  arr){
    private void sortArray (){

        Collections.sort(arrayList, new Comparator<HighScoreModel>(){
            public int compare(HighScoreModel s1, HighScoreModel s2) {
                int s1Score = s1.getScore();
                int s2Score = s2.getScore();
                int sizeCmp = s2Score - s1Score;

                if (sizeCmp !=0) return sizeCmp;
                Date s1Date = s1.getDate();
                Date s2Date = s2.getDate();
                if (s2Date.after(s1Date)) return 0; else return -1;

            }
        });

        return;
    }

    static final int HS_REQUEST_CODE = 2;  // The request code

    private void sendIntent(){
        Intent intent = new Intent(this, AddHighScore.class);
        intent.putExtra("score", score);
        startActivityForResult(intent, HS_REQUEST_CODE);
    }
    public void launchHSActivity(View view) {
//        Intent intent = new Intent(this, AddHighScore.class);
//        startActivityForResult(intent, REQUEST_CODE);
        sendIntent();
    }


    /** Called when the user taps the Send button */
    public void startGame(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent, GAME_REQUEST_CODE);
    }

    public void addToHighScore(View view){
        Log.i(TAG, "MainActivity addToHighScore()" );
        launchHSActivity(view);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == GAME_REQUEST_CODE  && resultCode  == RESULT_OK) {
//                Add score to high scores if user requested
                score = data.getIntExtra("score",0);
                int missedBalloons = data.getIntExtra("missedBalloons",0);

                String scoreTxt =  "Your score: " + Integer.toString(score);
                msgTextView.setText(scoreTxt);
                String missedTxt =   "Balloons missed "+ missedBalloons;
                msg2TextView.setText(missedTxt);
                addHSBtn.setEnabled(true);

//                Log.i(TAG, "MainActivity onActivityResult()" + scoreTxt);
//
//                Toast.makeText(this, requiredScore,
//                        Toast.LENGTH_SHORT).show();
            } else if  (requestCode == HS_REQUEST_CODE  && resultCode  == RESULT_OK) {
                Log.i(LOG_TAG, "return from HS");
                String requiredName = data.getStringExtra("name");
                int requiredScore = data.getIntExtra("score",0);
//                Date returnedDate = data.getLongExtra("date",0);
                Date d = (Date)data.getSerializableExtra("date");

//                Date d = new Date();
//                d.setTime(data.getLongExtra("date", -1));

                addingToList(requiredName,requiredScore,d);
                addHSBtn.setEnabled(false);

            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addingToList(String name, int score, Date date){
        // add info to array list
        HighScoreModel newScore = new HighScoreModel(name, score, date);
        arrayList.add(newScore);
        Log.i(TAG, Integer.toString(arrayList.size()));
        sortArray();

        // save it to file saveToFile
        FileHelper txtFile = new FileHelper();

        boolean result = txtFile.saveToFile(newScore, MainActivity.this);

        //change values array with your new data then update the adapter
        adapter.notifyDataSetChanged();
        return;
    }

}
