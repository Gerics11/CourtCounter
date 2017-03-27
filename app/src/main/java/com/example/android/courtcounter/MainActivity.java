package com.example.android.courtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    String onAttack = "A";
    private static final String TEAMASCORE = "SAVEDSCOREA";
    private static final String TEAMBSCORE = "SAVEDSCOREB";
    private static final String ATTACKER = "SAVEDATTACKER";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAttack = "B";
        switchAttack();
        if (savedInstanceState != null) {
            scoreTeamA = savedInstanceState.getInt(TEAMASCORE);
            scoreTeamB = savedInstanceState.getInt(TEAMBSCORE);
            onAttack = savedInstanceState.getString(ATTACKER);
            display(scoreTeamA);
            display2(scoreTeamB);
            winCheckTeamA();
            winCheckTeamB();
            if (onAttack.equals("A")) {
                onAttack = "B";
                switchAttack();
            } else {
                onAttack = "A";
                switchAttack();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TEAMASCORE, scoreTeamA);
        outState.putInt(TEAMBSCORE, scoreTeamB);
        outState.putString(ATTACKER, onAttack);
    }

    public void display(int number) {
        TextView countViewer = (TextView) findViewById(R.id.team_a_score);
        countViewer.setText(String.valueOf(number));
    }
    public void display2(int number) {
        TextView countViewer = (TextView) findViewById(R.id.team_b_score);
        countViewer.setText(String.valueOf(number));
    }


    public void scoreOneTeamA(View view) {
        scoreTeamA = scoreTeamA + 1;
        display(scoreTeamA);
        winCheckTeamA();
    }
    public void scoreTwoTeamA(View view) {
        scoreTeamA = scoreTeamA + 2;
        display(scoreTeamA);
        winCheckTeamA();
    }
    public void scoreOneTeamB(View view) {
        scoreTeamB = scoreTeamB + 1;
        display2(scoreTeamB);
        winCheckTeamB();
    }
    public void scoreTwoTeamB(View view) {
        scoreTeamB = scoreTeamB + 2;
        display2(scoreTeamB);
        winCheckTeamB();
    }


    public void rebound(View view) {
        switchAttack();
    }

    public void winCheckTeamA() {
        if (scoreTeamA >= 21) {
            TextView TV = (TextView)findViewById(R.id.team_a_score);
            TV.setText(getString(R.string.winner));
            scoreButtonState("locked");
        }
    }

    public void winCheckTeamB() {
        if (scoreTeamB >= 21) {
            TextView TV = (TextView)findViewById(R.id.team_b_score);
            TV.setText(getString(R.string.winner));
            scoreButtonState("locked");
        }
    }

    public void resetScore(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        display(scoreTeamA);
        display2(scoreTeamB);
        onAttack = "B";
        switchAttack();
        scoreButtonState("attackA");
    }

    public void switchAttack() {
        LinearLayout layoutA = (LinearLayout) findViewById(R.id.team_a_layout);
        LinearLayout layoutB = (LinearLayout) findViewById(R.id.team_b_layout);
        if (onAttack.equals("A")) {
            onAttack = "B";
            layoutB.setBackgroundResource(R.color.light_gray);
            layoutA.setBackgroundResource(R.color.transparent);
            scoreButtonState("attackB");
        } else {
            onAttack = "A";
            layoutA.setBackgroundResource(R.color.light_gray);
            layoutB.setBackgroundResource(R.color.transparent);
            scoreButtonState("attackA");
        }
    }

    private void scoreButtonState(String buttonState){
        Button a_3pt = (Button) findViewById(R.id.team_a_2pt);
        Button a_2pt = (Button) findViewById(R.id.team_a_1pt);
        Button a_rb = (Button) findViewById(R.id.team_a_rb);
        Button b_3pt = (Button) findViewById(R.id.team_b_2pt);
        Button b_2pt = (Button) findViewById(R.id.team_b_1pt);
        Button b_rb = (Button) findViewById(R.id.team_b_rb);
        switch (buttonState) {
            case "attackA": a_3pt.setEnabled(true);
                            a_2pt.setEnabled(true);
                            a_rb.setEnabled(true);
                            b_3pt.setEnabled(false);
                            b_2pt.setEnabled(false);
                            b_rb.setEnabled(false);
                            break;
            case "attackB": a_3pt.setEnabled(false);
                            a_2pt.setEnabled(false);
                            a_rb.setEnabled(false);
                            b_3pt.setEnabled(true);
                            b_2pt.setEnabled(true);
                            b_rb.setEnabled(true);
                            break;
            case "locked":  a_3pt.setEnabled(false);
                            a_2pt.setEnabled(false);
                            a_rb.setEnabled(false);
                            b_3pt.setEnabled(false);
                            b_2pt.setEnabled(false);
                            b_rb.setEnabled(false);
        }
    }
}