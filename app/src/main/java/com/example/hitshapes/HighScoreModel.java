package com.example.hitshapes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HighScoreModel{
    private String name;
    private int score;
    private Date scoreDate;

    public HighScoreModel (String name, int score, Date date)
    {
        this.name = name;
        this.score = score;
        this.scoreDate = date;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return scoreDate;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }
    public void setScore(int score) {
        this.score = score;
    }


    public String getDateString () {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(scoreDate);
    }

}
