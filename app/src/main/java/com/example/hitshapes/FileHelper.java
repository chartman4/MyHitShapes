package com.example.hitshapes;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileHelper {
    private  Context context;

    public FileHelper(){
        this.context=context;
    }
    static String fileName = "highscores.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()  ;
    final static String TAG = FileHelper.class.getName();
//    final static ArrayList<HighScoreModel> arrayList = new ArrayList<HighScoreModel>();

//    public static List ReadFile(Context context){
        public static ArrayList<HighScoreModel> ReadFile(Context context){

            String line = null;
            ArrayList<HighScoreModel> arrayList = new ArrayList<HighScoreModel>();          String text="";
            String name;
            int score;
            Date newDate;
            String strDate;

        try {
//            InputStream is = context.getAssets().open(fileName);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));

            while ( (line = bufferedReader.readLine()) != null )
            {
                String[] row = line.split("\t");
                name = row[0];
                score = Integer.parseInt(row[1]);
                strDate = row[2];
                SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    newDate = format1.parse(strDate);
                } catch (ParseException pe){
                    Log.d(TAG, pe.getMessage());
                    newDate = new Date();
                }
                HighScoreModel model = new HighScoreModel(name, score, newDate);
                arrayList.add(model);
            }

//            is.close();

        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return arrayList;

    }

    public static boolean saveToFile( HighScoreModel highScore, Context context){


        String data = highScore.getName() + "\t" + highScore.getScore()+ "\t" + highScore.getDateString()+"\n";

        try {

            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);

            fileOutputStream.write((data).getBytes());

            fileOutputStream.close();
            fileOutputStream.flush();

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;


    }
}
