package com.example.ansh.scoretracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Welcome extends AppCompatActivity {

    private static final String TAG = "Welcome";

    private int pointScoredA = 0, pointScoredB = 0, setsDecided,
            setsAwon = 0, setsBwon = 0, pointsDecided, limit,
            duce, threshold, setsWinnerWon, setsLoserWon;

    private boolean AEnable = true, BEnable = true;

    private String score1 = "", score2 = "", data = "", name1, name2, winner, loser;
    private Context mContext;

    private TextView displaySetA;
    private TextView displaySetB;

    private TextView displayPointA;
    private TextView displayPointB;

    @Override
    public void onBackPressed() {
        showToast("Back not Allowed");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        displaySetA = findViewById(R.id.set1);
        displaySetB = findViewById(R.id.set2);
        displayPointA = findViewById(R.id.team_a_score);
        displayPointB = findViewById(R.id.team_b_score);

        Intent intent = getIntent();
        name1 = intent.getStringExtra("Name1");
        name2 = intent.getStringExtra("Name2");
        pointsDecided = Integer.valueOf(intent.getStringExtra("points"));
        setsDecided = Integer.valueOf(intent.getStringExtra("sets"));

        TextView playerA =  findViewById(R.id.PlayerA);
        TextView playerB =  findViewById(R.id.PlayerB);

        playerA.setText(name1);
        playerB.setText(name2);

        limit = (pointsDecided + 8)*2;
        duce = pointsDecided - 1;
        threshold = (setsDecided + 1)/2;

        setListeners();

        mContext = getApplicationContext();
    }

    private void setListeners(){
        Button adda = findViewById(R.id.add1);
        Button addb = findViewById(R.id.add2);

        adda.setOnClickListener(new DebouncedOnClickListener(300) {
            @Override
            public void onDebouncedClick(View v) {
                addA(v);
            }
        });

        addb.setOnClickListener(new DebouncedOnClickListener(300) {
            @Override
            public void onDebouncedClick(View v) {
                addB(v);
            }
        });
    }

    public void addA(View v){
        if(AEnable){
            pointScoredA++;
            AEnable = false;
            checkAllConditions();
        }

    }

    public void addB(View v){
        if(BEnable){
            pointScoredB++;
            BEnable = false;
            checkAllConditions();
        }
    }

    public void subA(View v){
        if(pointScoredA > 0) pointScoredA--;
        checkAllConditions();
    }

    public void subB(View v){
        if(pointScoredB > 0) pointScoredB--;
        checkAllConditions();
    }

    public void reset(View v){
        getConfirmation("Reset", "Confirm to RESET this match", "reset", "Reset", "Dismiss");
    }

    private void endSet(){
        getConfirmation("End Set", "Confirm to END this set", "endset", "End Now", "Wait");
    }

    private void checkAllConditions(){
        Log.d(TAG, "checkAllConditions");

        changePointDisplay();

        if(pointScoredA + pointScoredB > limit) endSet();
        else if(pointScoredA >= duce && pointScoredB >= duce && abs(pointScoredA - pointScoredB) >= 2) endSet();
        else if(max(pointScoredA, pointScoredB) == pointsDecided && min(pointScoredA, pointScoredB) < duce) endSet();
        else{
            AEnable = true;
            BEnable = true;
        }

    }

    private String getData(String w, String l){
        Log.d(TAG, "getData: w, l " + w + " " + l);
        String[] win = w.split(",");
        String[] los = l.split(",");
        StringBuilder res = new StringBuilder();
        res.append(",");
        for(int i = 0; i < win.length; ++i){
            res.append(win[i]).append(",").append(los[i]).append(",");
        }

        return res.toString();
    }

    private void changeSetDisplay(){
        displaySetA.setText(String.valueOf(setsAwon));
        displaySetB.setText(String.valueOf(setsBwon));
    }

    private void changePointDisplay(){
        displayPointA.setText(String.valueOf(pointScoredA));
        displayPointB.setText(String.valueOf(pointScoredB));

    }

    private void endSetReally(){
        score1 = score1 + pointScoredA + ",";
        score2 = score2 + pointScoredB + ",";
        if(pointScoredA > pointScoredB){
            setsAwon++;
            showToast(name1 + " wins the set by " + pointScoredA + " : " + pointScoredB);
        }
        else{
            setsBwon++;
            showToast(name2 + " wins the set by " + pointScoredB + " : " + pointScoredA);
        }
        pointScoredA = 0;
        pointScoredB = 0;
        changeSetDisplay();
        changePointDisplay();

        AEnable = true;
        BEnable = true;

        // check end of match and pass data to summary of match
        if(setsAwon >= threshold){
            winner = name1;
            loser = name2;
            setsWinnerWon = setsAwon;
            setsLoserWon = setsBwon;
            data = getData(score1, score2);
            endMatchReally();
        }
        else if(setsBwon >= threshold){
            winner = name2;
            loser = name1;
            setsWinnerWon = setsBwon;
            setsLoserWon = setsAwon;
            data = getData(score2, score1);
            endMatchReally();
        }
    }

    private void resetReally(){
        pointScoredA = 0;
        pointScoredB = 0;
        setsBwon = 0;
        setsAwon = 0;
        score1 = "";
        score2 = "";
        changeSetDisplay();
        changePointDisplay();
    }

    private void endMatchReally(){
        Intent intent = new Intent(mContext, setwin.class);
        Log.d(TAG, "endMatchReally: setsdecided " + setsDecided);
        intent.putExtra("winner",winner);
        intent.putExtra("loser",loser);
        intent.putExtra("sets",setsDecided);
        intent.putExtra("setw", setsWinnerWon);
        intent.putExtra("setl",setsLoserWon);
        intent.putExtra("points",pointsDecided);
        intent.putExtra("data", data);

        resetReally();
        startActivity(intent);
    }


    private void getConfirmation(String title, String message, final String type, String rightOption, String leftOption){

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(rightOption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        switch (type) {
                            case "reset":
                                resetReally();
                                break;
                            case "endset":
                                endSetReally();
                                break;
                        }

                    }
                })
                .setNegativeButton(leftOption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void showToast( String message){
        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
