package com.example.android.courtcounter;

import android.os.Parcel;
import android.os.Parcelable;


public class CurrentGame implements Parcelable{
    private String teamAName = "East";
    private String teamBName = "West";
    private int teamAScore;
    private int teamBScore;
    private int winScore;
    private String pointSystem;
    private String matchType;

    //standard constructor
    public CurrentGame (String teamAName, String teamBName, int winScore, String pointSystem, String matchType) {
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.teamAScore = 0;
        this.teamBScore = 0;
        this.winScore = winScore;
        this.pointSystem = pointSystem;
        this.matchType = matchType;
    }
    //parcel constructor
    public CurrentGame (Parcel in){
        readFromParcel(in);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public CurrentGame createFromParcel(Parcel in) {
                    return new CurrentGame(in);
                }

                public CurrentGame[] newArray(int size) {
                    return new CurrentGame[size];
                }
            };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamAName);
        dest.writeString(teamBName);
        dest.writeInt(teamAScore);
        dest.writeInt(teamBScore);
        dest.writeInt(winScore);
        dest.writeString(pointSystem);
        dest.writeString(matchType);
    }

    private void readFromParcel(Parcel in) {
        teamAName = in.readString();
        teamBName = in.readString();
        teamAScore = in.readInt();
        teamBScore = in.readInt();
        winScore = in.readInt();
        pointSystem = in.readString();
        matchType = in.readString();
    }

    public void addScore (String team, int points) {
        switch (team) {
            case "A": teamAScore = teamAScore + points;
                break;
            case "B": teamBScore = teamBScore + points;
                break;
        }
    }
    public int getScore (String team) {
        int score = 0;
        switch (team) {
            case "A": score = teamAScore;
                break;
            case "B": score = teamBScore;
                break;
        }
        return score;
    }
    public void setName (String team, String name) {
        switch (team) {
            case "A": teamAName = name;
                break;
            case "B": teamBName = name;
                break;
        }
    }
    public String getName(String team){
        String name = "";
        switch (team) {
            case "A": name = teamAName;
                break;
            case "B": name = teamBName;
                break;
        }
        return name;
    }
    public void resetScore() {
        teamAScore = 0;
        teamBScore = 0;
    }

    public void setWinScore(int score) {
        winScore = score;
    }

    public int getWinScore() {
        return winScore;
    }

    public void setPointSystem(String pointSystem) {
        this.pointSystem = pointSystem;
    }

    public String getPointSystem(){
        return pointSystem;
    }

    public void setMatchType(String matchType){
        this.matchType = matchType;
    }

    public String getMatchType() {
        return matchType;
    }
}























