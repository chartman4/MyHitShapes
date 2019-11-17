package com.example.hitshapes;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class CustomAdapter extends ArrayAdapter<HighScoreModel> {

    private static final String TAG = "CustomAdapter";
    private Context mContext;
    private List<HighScoreModel> highScoresList = new ArrayList<>();

//    int mResource;

//    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<HighScoreModel> objects) {
//        super(context, resource, objects);
//        this.mContext = mContext;
//        this.mResource = mResource;
//    }

    public CustomAdapter(@NonNull Context context, @LayoutRes ArrayList<HighScoreModel> list) {
        super(context, 0 , list);
        mContext = context;
        highScoresList = list;
    }
//
//    @Override
//        public int getCount() {
//            return arrayList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return arrayList.get(position);
//        }

//        @Override
//        public long getItemId(int position) {
//            return position;
//        }

    @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);

        HighScoreModel currentHS = highScoresList.get(position);

            String name = currentHS.getName();
            int score = currentHS.getScore();
            Date date = currentHS.getDate();

            // create the high score object
//            HighScoreModel hs = new HighScoreModel(name, score, date);

//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            convertView = inflater.inflate(mResource, parent, false);
//
            TextView tvName = (TextView) listItem.findViewById(R.id.nameTextView);
            TextView tvScore =  (TextView) listItem.findViewById(R.id.scoreTextView);
            TextView tvDate = (TextView) listItem.findViewById(R.id.dateTextView);

            DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
            String dateStr = dateFormat.format(date);
            String scoreStr = score + " ";

            Log.i(TAG, name + " " + scoreStr + " " + dateStr);

            tvName.setText(name);
            tvScore.setText(scoreStr);
            tvDate.setText(dateStr);
            return listItem;
        }
    }

