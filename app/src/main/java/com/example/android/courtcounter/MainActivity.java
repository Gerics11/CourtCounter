package com.example.android.courtcounter;

import android.graphics.Color;
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
        onAttack= "B";
        switchAttack();
        findViewById(R.id.winner_a).setVisibility(View.GONE);
        findViewById(R.id.winner_b).setVisibility(View.GONE);
        if (savedInstanceState != null){
            scoreTeamA = savedInstanceState.getInt(TEAMASCORE);
            scoreTeamB = savedInstanceState.getInt(TEAMBSCORE);
            onAttack = savedInstanceState.getString(ATTACKER);
            display (scoreTeamA);
            display2(scoreTeamB);
            winCheckTeamA();
            winCheckTeamB();
            if ( onAttack.equals("A")) {
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
    /**
     * Displays the given score for Team A.
     * @param number takes scores from methods.
     */
    public void display (int number){
        TextView countViewer = (TextView)findViewById(R.id.team_a_score);
        countViewer.setText(""+ number);
    }
    /**
     * Displays the given score for Team B.
     * @param number takes scores from methods.
     */
    public void display2 (int number){
        TextView countViewer = (TextView)findViewById(R.id.team_b_score);
        countViewer.setText(""+ number);
    }
    /**
     * Adds one to Team A score.
     * @param view updates team score
     */
    public void scoreOneTeamA(View view){
        scoreTeamA=scoreTeamA+1;
        display(scoreTeamA);
        winCheckTeamA();
    }
    /**
     * Adds two to Team A score
     * @param view updates team score
     */
    public void scoreTwoTeamA(View view){
        scoreTeamA=scoreTeamA+2;
        display(scoreTeamA);
        winCheckTeamA();
    }
    /**
     * Adds one to Team B score
     * @param view updates team score
     */
    public void scoreOneTeamB(View view){
        scoreTeamB=scoreTeamB+1;
        winCheckTeamB();
        display2(scoreTeamB);
    }
    /**
     * Adds two to Team B score
     * @param view updates team score
     */
    public void scoreTwoTeamB(View view){
        scoreTeamB=scoreTeamB+2;
        winCheckTeamB();
        display2(scoreTeamB);
    }
    /**
     * Switches attacking side via switchattack method.
     * @param view Activated by Rebound buttons.
     */
    public void rebound(View view){
        switchAttack();
    }
    /**
     * Method run by Team A score buttons.
     * If there is a winner, buttons except reset are disabled, and WINNER textview
     * replaces the teams score.
     */
    public void winCheckTeamA() {
        if (scoreTeamA >= 21) {
            findViewById(R.id.winner_a).setVisibility(View.VISIBLE);
            findViewById(R.id.team_a_score).setVisibility(View.GONE);
            disable_A();
            disable_B();
        }
        else {
        }
     }
    /**
     * Method run by Team A score buttons.
     * If there is a winner, buttons except reset are disabled, and WINNER textview
     * replaces the teams score.
     */
    public void winCheckTeamB() {
        if (scoreTeamB >= 21) {
            findViewById(R.id.winner_b).setVisibility(View.VISIBLE);
            findViewById(R.id.team_b_score).setVisibility(View.GONE);
            disable_A();
            disable_B();
        }
        else {
        }
    }
    /**
     * *Reset Button: resets both scores to 0, reset attacking side to Team A,
     * resets winner textview to team score.
     */
    public void resetScore(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        display(scoreTeamA);
        display2(scoreTeamB);
        onAttack= "B";
        switchAttack();
        findViewById(R.id.winner_a).setVisibility(View.GONE);
        findViewById(R.id.winner_b).setVisibility(View.GONE);
        findViewById(R.id.team_a_score).setVisibility(View.VISIBLE);
        findViewById(R.id.team_b_score).setVisibility(View.VISIBLE);
        enable_A();
    }
    /**
     * Swtiches the attacking team, gives gray backgroud to attacking team, and
     * disables defensive teams buttons.
     */
    public void switchAttack() {
    LinearLayout layoutA = (LinearLayout) findViewById(R.id.team_a_layout);
    LinearLayout layoutB = (LinearLayout) findViewById(R.id.team_b_layout);
   if (onAttack.equals("A")) {
            onAttack = "B";
            layoutB.setBackgroundColor(Color.parseColor("#A4A4A4"));
            layoutA.setBackgroundColor(0x00000000);
            disable_A();
            enable_B();
    }
    else{
            onAttack = "A";
            layoutA.setBackgroundColor(Color.parseColor("#A4A4A4"));
            layoutB.setBackgroundColor(0x00000000);
            enable_A();
            disable_B();
        }
    }
    /**
     * Disable Team A related buttons. Used by methods for switching attacking side and reset.
     */
    public void disable_A() {
        Button a_3pt = (Button) findViewById(R.id.team_a_2pt);
        Button a_2pt = (Button) findViewById(R.id.team_a_1pt);
        Button a_rb = (Button) findViewById(R.id.team_a_rb);
        a_3pt.setEnabled(false);
        a_2pt.setEnabled(false);
        a_rb.setEnabled(false);
    }
    /**
     * Disable Team B related buttons. Used by methods for switching attacking side and reset.
     */
    public void disable_B() {
        Button b_3pt = (Button) findViewById(R.id.team_b_2pt);
        Button b_2pt = (Button) findViewById(R.id.team_b_1pt);
        Button b_rb = (Button) findViewById(R.id.team_b_rb);
        b_3pt.setEnabled(false);
        b_2pt.setEnabled(false);
        b_rb.setEnabled(false);
    }
    /**
     * Enable Team A related buttons. Used by methods for switching attacking side and reset.
     */
    public void enable_A() {
        Button a_3pt = (Button) findViewById(R.id.team_a_2pt);
        Button a_2pt = (Button) findViewById(R.id.team_a_1pt);
        Button a_rb = (Button) findViewById(R.id.team_a_rb);
        a_3pt.setEnabled(true);
        a_2pt.setEnabled(true);
        a_rb.setEnabled(true);
    }
    /**
     * Enable Team B related buttons. Used by methods for switching attacking side and reset.
     */
    public void enable_B() {
        Button b_3pt = (Button) findViewById(R.id.team_b_2pt);
        Button b_2pt = (Button) findViewById(R.id.team_b_1pt);
        Button b_rb = (Button) findViewById(R.id.team_b_rb);
        b_3pt.setEnabled(true);
        b_2pt.setEnabled(true);
        b_rb.setEnabled(true);
    }
}