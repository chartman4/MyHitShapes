package com.example.hitshapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class GamePiece extends Drawable {

    final static String TAG = GamePiece.class.getName();

    //public class GamePiece {
    private int shape;
    private int size;
    private int Xpos;
    private int Ypos;
    private int speed;
    private int color;
    private int totalSizes;
    private int numObjs;
    private int pos;


    private Rect mRectSquare;
    private Paint mPaintSquare;
    private Paint mPaintCircle;
    private Paint mPaint;

    private float mCircleX,mCircleY;
    private float mCircleRadius = 100f;


    public GamePiece (int shape, int size, int xPos, int yPos, int speed, int color)
    {
        this.shape = shape;
        this.size = size;

        this.Xpos = xPos;
        this.Ypos = yPos;
        this.speed = speed;
        this.color = color;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectSquare = new Rect();
        mPaintCircle = new Paint();

    }
    public int getShape() {
        return shape;
    }
    public int getSize() {
        return size;
    }
    public int getColor() {return color;};

    public void changePos(int x, int y) {
        this.Xpos = x;
        this.Ypos = y;
    }

    public Boolean hitMe(int x, int y){
        if ( (this.Xpos < x) && (x < this.Xpos + this.size)
            && (this.Ypos-this.size < y) && (y < this.Ypos)) return true;
        else return false;
    }
    public Boolean willCollide( int x, int y, int size){
        Log.i(TAG, " " + x + " " + y + " "+ size);
        if (
                // right edge of object overlaps
                ((x < this.Xpos) && (x + size > this.Xpos))
            ||
                        // left size of object overlaps
                (( x >this.Xpos)  && (x < this.Xpos + this.size))
        )
        {
            Log.i(TAG, " collides with " +  this.Xpos + " " + this.size);

            return true;
        }
        else {
            Log.i(TAG, " does not collides with " + this.Xpos + " " + this.size);
            return false;

        }

    }

    public Boolean isOffBoard(){
        return this.Ypos <= 0;
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(this.color);
        this.Ypos -= this.speed;
        if (this.shape ==0 ) {
            mRectSquare.left =  this.Xpos;
            mRectSquare.top = this.Ypos - this.size ;
            mRectSquare.right = this.Xpos + this.size;
            mRectSquare.bottom = this.Ypos;
            canvas.drawRect(mRectSquare,mPaint);
        } else {
            mCircleX = this.Xpos + this.size/2;
            mCircleY = this.Ypos - this.size/2;
            mCircleRadius = this.size/2;
            canvas.drawCircle(mCircleX,mCircleY, mCircleRadius, mPaint);
        }

    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }
    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
