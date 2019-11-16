package com.example.hitshapes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import android.os.Handler;



public class GameActivity extends AppCompatActivity {

    final static String TAG = GameActivity.class.getName();

    private GameView gameView;


    private TextView timerTextView;
    private TextView scoreTextView;
    private TextView msgTextView;
    private Button gameBtn;

    private int score = 0;
    private Boolean gameRunning = false;
    private int numTens = 0;
    private int incTime = 1000;

    private long timeLeftInMilliseconds = 60000; // 1 minute
    private long gameLength = 60000; // 1 minute
    private long startTime;
    private long endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.i(TAG, "GameActivity onCreate()");

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        gameBtn = (Button)findViewById(R.id.startBtn);
        gameView = (GameView)findViewById(R.id.gameView);
        msgTextView = (TextView)findViewById(R.id.msgTextView);

        //        get rid of action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }



// called when button is pushed
    public void startGameView(View view) {
        Log.d(TAG, "button pressed");
        startTime = System.currentTimeMillis();

        gameRunning = true;

//        startTimer();
        startRegTimer();
    }


    // !!!!!!!!!!
    // Version which uses regular timer
    // !!!!!!!!!
    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            // update score display
            int score = gameView.getScore();
            String scoreStr = Integer.toString(score);
            scoreTextView.setText(scoreStr);

            // if score bumped up by 10 add 10 seconds to game time
            if( score  >= (numTens+1) * 10) {
                numTens++;
                gameLength += 10000;
            }

            // update time display
            long curMillis = System.currentTimeMillis();
            long millis = curMillis - startTime;
            long timeLeft = gameLength - millis;
            int seconds = (int) (timeLeft / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            Log.i(TAG,endTime+" ");
            Log.i(TAG,System.currentTimeMillis()+" ");

            gameView.invalidate();


            if (timeLeft > 0 ){
                timerHandler.postDelayed(this, 250);

            }
            else stopRegTimer();
        }
    };


    private void setupGame (){
        gameView.initGame();
        String colorStr = gameView.getTargetColor();
        String shapeStr = gameView.getTargetShape();
        String msg = " Hit: " + colorStr + " " + shapeStr + "s";
        msgTextView.setText(msg);
    }
    private void startRegTimer() {
        startTime = System.currentTimeMillis();
        endTime = startTime + 60000;
        Log.i(TAG,endTime+" ");
        setupGame();
        timerHandler.postDelayed(timerRunnable, 0);
    }
    private void stopRegTimer() {
        Log.i(TAG,"Timer ended");
        int score = gameView.getScore();
        int missedBalloons = gameView.getMissedBalloons();
        gameView.stopGame();

        Intent intent = getIntent();
        intent.putExtra("score", score);
        intent.putExtra("missedBalloons", missedBalloons);


        setResult(RESULT_OK, intent);

        finish();
        timerHandler.removeCallbacks(timerRunnable);

    }



}
