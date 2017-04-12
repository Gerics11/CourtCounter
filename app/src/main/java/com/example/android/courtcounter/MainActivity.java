package com.example.android.courtcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String onAttack = "A";
    int matchScoreA = 0;
    int matchScoreB = 0;
    private static final String ATTACKER = "SAVEDATTACKER";
    private static final String MATCHSCOREA = "SAVEDMATCHSCOREA";
    private static final String MATCHSCOREB = "SAVEDMATCHSCOREB";
    CurrentGame game = new CurrentGame("PH", "PH", 0, "PH", "PH");
//    CurrentGame game;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b == null) {
            setContentView(R.layout.activity_intro);                //launch intro screen
        } else {
            setContentView(R.layout.activity_main);
            TextView teamA = (TextView) findViewById(R.id.teamAName);
            TextView teamB = (TextView) findViewById(R.id.teamBName);
            if (savedInstanceState == null) {                       //read object from intent
                game = b.getParcelable("gameObject");

            } else {                                                //read object from save state
                game = savedInstanceState.getParcelable("gameObject");
                onAttack = savedInstanceState.getString(ATTACKER);
                matchScoreA = savedInstanceState.getInt(MATCHSCOREA);
                matchScoreB = savedInstanceState.getInt(MATCHSCOREB);

            }
            //display names and scores
            teamA.setText(game.getName("A"));
            teamB.setText(game.getName("B"));
            display("A", game.getScore("A"));
            display("B", game.getScore("B"));
            mScoreDisplay("A");
            mScoreDisplay("B");
            //additional checks
            winCheck("A", true);
            winCheck("B", true);
            scoreButtonDisplay();
            if (onAttack.equals("B")) {
                onAttack = "A";
                switchAttack();
            } else {
                scoreButtonState("attackA");
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ATTACKER, onAttack);
        outState.putInt(MATCHSCOREA, matchScoreA);
        outState.putInt(MATCHSCOREB, matchScoreB);
        outState.putParcelable("gameObject", game);
    }

    /**
     * Display the current game score of given team
     * @param team input "A" or "B"
     * @param score integer value to display
     */
    public void display(String team, int score) {
        switch (team) {
            case "A":
                TextView scoreViewA = (TextView) findViewById(R.id.team_a_score);
                scoreViewA.setText(String.valueOf(score));
                break;
            case "B":
                TextView scoreViewB = (TextView) findViewById(R.id.team_b_score);
                scoreViewB.setText(String.valueOf(score));
                break;
        }
    }

    /**
     * Display the number of games won by given team
     * @param team input "A" or "B"
     */
    private void mScoreDisplay(String team) {
        switch (team) {
            case "A":
                TextView mScoreViewA = (TextView) findViewById(R.id.matchScoreA);
                mScoreViewA.setText(String.valueOf(matchScoreA));
                break;
            case "B":
                TextView mScoreViewB = (TextView) findViewById(R.id.matchScoreB);
                mScoreViewB.setText(String.valueOf(matchScoreB));
                break;
        }
    }

    /**
     * Set score button text according to given score system
     */
    private void scoreButtonDisplay() {
        if (game.getPointSystem().equals("2,3")) {
            Button teamA1pt = (Button) findViewById(R.id.team_a_1pt);
            Button teamA2pt = (Button) findViewById(R.id.team_a_2pt);
            Button teamB1pt = (Button) findViewById(R.id.team_b_1pt);
            Button teamB2pt = (Button) findViewById(R.id.team_b_2pt);
            teamA1pt.setText(getString(R.string.twopointer));
            teamA2pt.setText(getString(R.string.threepointer));
            teamB1pt.setText(getString(R.string.twopointer));
            teamB2pt.setText(getString(R.string.threepointer));
        }
    }

    /**
     * Add points to team score according to caller button and point system
     * @param view score buttons
     */
    public void score(View view) {
        switch (view.getId()) {
            case R.id.team_a_1pt:
                if (game.getPointSystem().equals("1,2")) {
                    game.addScore("A", 1);
                } else {
                    game.addScore("A", 2);
                }
                display("A", game.getScore("A"));
                winCheck("A", false);
                break;
            case R.id.team_a_2pt:
                if (game.getPointSystem().equals("1,2")) {
                    game.addScore("A", 2);
                } else {
                    game.addScore("A", 3);
                }
                display("A", game.getScore("A"));
                winCheck("A", false);
                break;
            case R.id.team_b_1pt:
                if (game.getPointSystem().equals("1,2")) {
                    game.addScore("B", 1);
                } else {
                    game.addScore("B", 2);
                }
                display("B", game.getScore("B"));
                winCheck("B", false);
                break;
            case R.id.team_b_2pt:
                if (game.getPointSystem().equals("1,2")) {
                    game.addScore("B", 2);
                } else {
                    game.addScore("B", 3);
                }
                display("B", game.getScore("B"));
                winCheck("B", false);
                break;
        }
        if (game.getMatchType().equals("switch") && game.getScore("A") < game.getWinScore()) {
            switchAttack();
        }
    }

    public void rebound(View view) {
        switchAttack();
    }

    /**
     * Compare given teams current score to the custom win score, if met, winner announced and match score adjusted
     * @param team "A" or "B" team to check
     * @param saveState if the method is called while activity is being recreated, no points added
     */
    public void winCheck(String team, boolean saveState) {
        if (game.getScore("A") >= game.getWinScore() || game.getScore("B") >= game.getWinScore()) {
            Button reset = (Button) findViewById(R.id.resetButton);
            reset.setText(getString(R.string.nextGame));
            scoreButtonState("locked");
            switch (team) {
                case "A":
                    if (game.getScore("A") >= game.getWinScore()) {
                        TextView TV = (TextView) findViewById(R.id.team_a_score);
                        TV.setText(getString(R.string.winner));
                        if (!saveState) {
                            matchScoreA++;
                        }
                        mScoreDisplay("A");
                        break;
                    }
                case "B":
                    if (game.getScore("B") >= game.getWinScore()) {
                        TextView TV = (TextView) findViewById(R.id.team_b_score);
                        TV.setText(getString(R.string.winner));
                        if (!saveState) {
                            matchScoreB++;
                        }
                        mScoreDisplay("B");
                        break;
                    }
            }
        }
    }

    /**
     * reset scores of current match
     * @param view called by reset button
     */
    public void resetScore(View view) {
        game.resetScore();
        display("A", game.getScore("A"));
        display("B", game.getScore("B"));
        //reset attacker and reset button text
        onAttack = "B";
        switchAttack();
        scoreButtonState("attackA");
        Button reset = (Button) findViewById(R.id.resetButton);
        reset.setText(getString(R.string.reset));
    }

    /**
     * Current attackers score buttons are enabled and background changes accordingly
     */
    public void switchAttack() {
        RelativeLayout layoutA = (RelativeLayout) findViewById(R.id.team_a_layout);
        RelativeLayout layoutB = (RelativeLayout) findViewById(R.id.team_b_layout);
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

    /**
     * Handle enabling buttons for the current attacker team
     * @param buttonState "attackA", "attackB" or "locked"
     */
    private void scoreButtonState(String buttonState) {
        Button a_3pt = (Button) findViewById(R.id.team_a_2pt);
        Button a_2pt = (Button) findViewById(R.id.team_a_1pt);
        Button a_rb = (Button) findViewById(R.id.team_a_rb);
        Button b_3pt = (Button) findViewById(R.id.team_b_2pt);
        Button b_2pt = (Button) findViewById(R.id.team_b_1pt);
        Button b_rb = (Button) findViewById(R.id.team_b_rb);
        switch (buttonState) {
            case "attackA":
                a_3pt.setEnabled(true);
                a_2pt.setEnabled(true);
                a_rb.setEnabled(true);
                b_3pt.setEnabled(false);
                b_2pt.setEnabled(false);
                b_rb.setEnabled(false);
                break;
            case "attackB":
                a_3pt.setEnabled(false);
                a_2pt.setEnabled(false);
                a_rb.setEnabled(false);
                b_3pt.setEnabled(true);
                b_2pt.setEnabled(true);
                b_rb.setEnabled(true);
                break;
            case "locked":
                a_3pt.setEnabled(false);
                a_2pt.setEnabled(false);
                a_rb.setEnabled(false);
                b_3pt.setEnabled(false);
                b_2pt.setEnabled(false);
                b_rb.setEnabled(false);
        }
    }

    /**
     * Read all input from intro screen and start main activity
     * @param view "All set" button
     */
    public void readInfo(View view) {
        EditText nameA = (EditText) findViewById(R.id.editText_teamA);
        EditText nameB = (EditText) findViewById(R.id.editText_teamB);
        EditText score = (EditText) findViewById(R.id.editText_score);
        RadioGroup r1 = (RadioGroup) findViewById(R.id.radiogroup1);
        RadioGroup r2 = (RadioGroup) findViewById(R.id.radiogroup2);
        game.setName("A", nameA.getText().toString());
        game.setName("B", nameB.getText().toString());
        //read team names, break and toast if no input
        if (game.getName("A").equals("") || game.getName("B").equals("")) {
            Toast.makeText(this, "Enter team names", Toast.LENGTH_LONG).show();
            return;
        }
        if (score.getText().toString().equals("")) {
            Toast.makeText(this, "Set score to win", Toast.LENGTH_LONG).show();
            return;
        } else {
            game.setWinScore(Integer.parseInt(score.getText().toString()));
        }
        //get scoring points from radiogroup
        int radioButtonID = r1.getCheckedRadioButtonId();
        View radioButton = r1.findViewById(radioButtonID);
        int selected = r1.indexOfChild(radioButton);
        if (selected == 0) {
            game.setPointSystem("1,2");
        } else if (selected == 1) {
            game.setPointSystem("2,3");
        } else {                                    //break and toast if no input
            Toast.makeText(this, "Pick scoring type", Toast.LENGTH_SHORT).show();
            return;
        }
        //get matchtype from radiogroup
        radioButtonID = r2.getCheckedRadioButtonId();
        radioButton = r2.findViewById(radioButtonID);
        selected = r2.indexOfChild(radioButton);
        if (selected == 0) {
            game.setMatchType("set");
        } else if (selected == 1) {
            game.setMatchType("switch");
        } else {                                    //toast message if no input
            Toast.makeText(this, "Pick match type", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("gameObject", game);
        startActivity(i);
    }

    /**
     * Display toast explaining matchtype
     */
    public void setRules(View view) {
        Toast.makeText(this, "Scorer retains attack after scoring", Toast.LENGTH_LONG).show();
    }

    /**
     * Display toast explaining matchtype
     */
    public void switchRules(View view) {
        Toast.makeText(this, "Attacking side is switched after scoring", Toast.LENGTH_LONG).show();
    }
}


































