package com.example.hitshapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    private static final String LOG_TAG = GameView.class.getSimpleName();

    private static final int PIECE_MIN_DEF = 32;
    private static final int PIECE_MAX_DEF = 64;

    private static final int SPEED_MIN_DEF = 10;
    private static final int SPEED_MAX_DEF = 30;

    private static final int MIN_BALLOONS = 6;
    private static final int MAX_BALLOONS = 12;

    private Boolean running = false;
    private int targetShape = 0;
    private int score = 0;
    private int canvasWidth = 0;
    private int canvasHeight = 0;

    final static String TAG = GameView.class.getName();


    //        <!--   game piece colors: Red, Orange, Yellow, Green, Blue, Purple, and White-->
    private static final int[] colors = {Color.RED, Color.rgb(255,165,0), Color.YELLOW, Color.GREEN, Color.BLUE, Color.rgb(128,0,128), Color.rgb(255,255,255)};
    private static final String[] colorStrings = {"RED", "Orange", "Yellow", "Green", "Blue", "Purple", "White"};
    private String targetColorStr = "RED";
    private int targetColor = colors[0];
    private int missedBalloons = 0;

    private int numObjs = 0;
    private int canvasMargin = 70;

    private static ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
    private ArrayList<Integer> shapeslst = new ArrayList<Integer>();
    private ArrayList<Integer> colorslst = new ArrayList<Integer>();

    private Boolean piecesPositioned = false;


    public GameView(Context context) {
        super(context);
        init(null);
//        setWillNotDraw(false);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    private void init(@Nullable AttributeSet set) {

        if (set == null)
            return;
    }

    public void initGame() {

        Log.d(TAG, "GameView Init");

        numObjs = getRandomInteger(MIN_BALLOONS, MAX_BALLOONS);

        running = true;

        //get shapes and colors of pieces
        for (int s = 0; s < numObjs; s++) {
            int color =  getRandomInteger(0,colors.length-1);
            colorslst.add(color);
            int shape =  getRandomInteger(0,1);
            shapeslst.add(shape);
        }

        // set the target color and shape
        int r = getRandomInteger(0,numObjs-1);
        targetColorStr = colorStrings[colorslst.get(r)];
        targetColor = colors[colorslst.get(r)];
        targetShape = shapeslst.get(r); }

    // init game piece positions
    private void initGamePieces(Canvas canvas){
//        Log.d(TAG, "Pieces Init");

        int size, color, speed, shape;
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        int spacing = (canvasWidth - (2*canvasMargin) )/numObjs;

        int initPos =  canvasMargin;

        for (int i = 0; i < numObjs; i++) {
            size =  getRandomInteger(PIECE_MIN_DEF,PIECE_MAX_DEF);
            color =  colors[colorslst.get(i)];
            speed = getRandomInteger(SPEED_MIN_DEF,SPEED_MAX_DEF);
            shape = shapeslst.get(i);
            GamePiece gamePiece = new GamePiece(shape, size, initPos, canvasHeight, speed, color);
            initPos = initPos + spacing;
            pieces.add(gamePiece);
        }
        piecesPositioned = true;
    }

    // piece has gone off the screen or been hit, reposition it
    private void reposition( GamePiece piece){
        Boolean posOK = true;
        int newPos;
        // repeat until good position found
        do {
            // get random xPos
            newPos = getRandomInteger(0, canvasWidth-2*PIECE_MAX_DEF) +PIECE_MAX_DEF;
            posOK = true;
            GamePiece checkPiece;
            // check if this position collides with any other piece
            for (int p=0; p<this.pieces.size(); p++){
                // if collides find another position
                checkPiece = pieces.get(p);
                if (checkPiece.willCollide(newPos, canvasHeight, piece.getSize())) {
                    posOK= false;
                    break;
                }
            }
        } while (!posOK);

        // until piece does not collide with existing piece
        piece.changePos(newPos, canvasHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "GameView onDraw");

        if (running) {
            if (!piecesPositioned) initGamePieces(canvas);
            for (int i = 0; i < pieces.size(); i++) {
                GamePiece piece = pieces.get(i);
                if (piece.isOffBoard()) {
                    reposition(piece);
                    // if piece was a target piece increment  missedBalloons
                    if ((piece.getShape() == targetShape) && (piece.getColor() == targetColor)) {
                        missedBalloons++;
                    }
                }
                piece.draw(canvas);
            }
        }
    }
    public int getMissedBalloons(){
        return missedBalloons;
    }
    public int getScore(){
        return score;
    }
    public String getTargetColor() {
        return targetColorStr;
    }
    public String getTargetShape(){
        if (targetShape == 0) return "square"; else return "circle";
    }

    /*
     * returns random integer between minimum and maximum range
     */
    public static int getRandomInteger(int min, int  max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public Boolean hitGamePiece(float x, float y) {
        Boolean hit = false;
        for (int p=0; p< pieces.size(); p++){
            // if current game piece is a target, check if hit
            GamePiece currentP = pieces.get(p);
            if ((currentP.getShape() == targetShape) && (currentP.getColor() == targetColor)){
                hit = currentP.hitMe((int)x,(int)y);
               if (hit) {
                   reposition(currentP);
                   break;
               }
            }
        }
        return hit;
    }


    public void stopGame() {
        running = false;
        pieces.clear();
        score = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();
                Boolean hit = hitGamePiece((int)x,(int)y);
                Log.i(TAG, "GameViw onTouchEvent()" + hit.toString());
                if (hit) score++; else score--;
                return hit;
            }
        }
        return true;
    }

}
