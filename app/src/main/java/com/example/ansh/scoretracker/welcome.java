package com.example.ansh.scoretracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class welcome extends AppCompatActivity {

    public static int winguy,set1=0,set2=0,point,set,current=0,total;
    public static  String name1,name2;
    public static int[] scoreTemA;
    public static int[] scoreTemB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        set1=0;
        set2=0;
        current=0;
        Intent intent = getIntent();
        name1 = intent.getStringExtra("Name1");
        name2 = intent.getStringExtra("Name2");
        point = Integer.valueOf(intent.getStringExtra("points"));
        set = Integer.valueOf(intent.getStringExtra("sets"));
        total=set;
        // check here
        set = (set+1)/2;
        scoreTemA= new int[total];
        scoreTemB= new int[total];
        displayForTeam(name1, name2);
        TextView scoreView3 = findViewById(R.id.set1);
        scoreView3.setText("sets won : "+set1);
        TextView scoreView4 = findViewById(R.id.set2);
        scoreView4.setText("sets won : "+set2);
    }

    public void add1(View view) {
        scoreTemA[current] += 1;
        displayForTeamA(scoreTemA[current]);
        if(((scoreTemA[current] >= point)&&(scoreTemA[current]-scoreTemB[current]>=2))||(scoreTemA[current]>=30))
        {   set1++;
            if(set1>=set)
            {
                winguy=1;
                String okay;
                okay = String.valueOf(scoreTemA[current])+" : "+String.valueOf(scoreTemB[current]);
                okay = name1 + " wins the set by " + okay;
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, okay, duration);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), setwin.class );
                String sets1,setw1,setl1,point1;
                sets1=String.valueOf(total);
                setw1=String.valueOf(set1);
                setl1=String.valueOf(set2);
                point1=String.valueOf(point);
                intent.putExtra("winner",name1);
                intent.putExtra("loser",name2);
                intent.putExtra("sets",sets1);
                intent.putExtra("setw",setw1);
                intent.putExtra("setl",setl1);
                intent.putExtra("points",point1);
                //StringBuilder extras= new StringBuilder();
                String asn=",";
               // extras.append(String.valueOf(scoreTemA[0])).append(",").append(String.valueOf(scoreTemB[0]));
                //extras.append(String.valueOf(scoreTemB[0])).append(",").append(String.valueOf(scoreTemA[0]));
                for(int i=0;i<=current;i++)
                {  // extras.append(",").append(String.valueOf(scoreTemA[i])).append(",").append(String.valueOf(scoreTemB[i]));
                   // extras.append(String.valueOf(scoreTemB[i])).append(",").append(String.valueOf(scoreTemA[i])).append(",");
                    asn = asn + scoreTemA[i] + "," + scoreTemB[i] + ",";
//
                }
                current++;
                intent.putExtra("data",asn);
                startActivity(intent);
            }
            else
            {   String okay;
                okay = String.valueOf(scoreTemA[current])+" : "+String.valueOf(scoreTemB[current]);
                okay = name1 + " wins the set by " +okay;
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, okay, duration);
                toast.show();
                current++;
                resetButton(view);
            }
        }
    }

    private void displayForTeam(String name11, String name22) {
        TextView scoreView = findViewById(R.id.PlayerA);
        scoreView.setText(name11);
        TextView scoreView1 = findViewById(R.id.PlayerB);
        scoreView1.setText(name22);
    }

    public void sub1(View view) {
        scoreTemA[current] = scoreTemA[current] - 1;
        displayForTeamA(scoreTemA[current]);
    }

    private void displayForTeamA(int score) {
        TextView scoreView = findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void add2(View view) {
        scoreTemB[current] = scoreTemB[current] + 1;
        displayForTeamB(scoreTemB[current]);
        if(((scoreTemB[current]>=point)&&(scoreTemB[current]-scoreTemA[current]>=2))||(scoreTemB[current]>=30))
        {   set2++;
            if(set2>=set)
            {
                winguy=2;
                String okay;
                okay = String.valueOf(scoreTemB[current])+" : "+String.valueOf(scoreTemA[current]);
                okay = name2 + " wins the set by " +okay;
                
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, okay, duration);
                toast.show();

                String sets1,setw1,setl1,point1;
                sets1=String.valueOf(total);
                setw1=String.valueOf(set2);
                setl1=String.valueOf(set1);
                point1=String.valueOf(point);
                String asn = ",";
                //extras.append(String.valueOf(scoreTemB[0])).append(",").append(String.valueOf(scoreTemA[0]));
//                extras.append(String.valueOf(scoreTemA[0])).append(",").append(String.valueOf(scoreTemB[0]));
                for(int i=0;i<=current;i++)
                {  // extras.append(",").append(String.valueOf(scoreTemB[i])).append(",").append(String.valueOf(scoreTemA[i]));
                    asn = asn + String.valueOf(scoreTemA[i]) + "," + String.valueOf(scoreTemB[i])+",";
//                    extras.append(String.valueOf(scoreTemA[i])).append(",").append(String.valueOf(scoreTemB[i])).append(",");
                }
                giveMessage(name2, name1, point1, sets1, setw1, setl1, asn);
//

            }
            else
            {   String okay;
                okay = String.valueOf(scoreTemB[current])+" : "+String.valueOf(scoreTemA[current]);
                okay = name2 + " wins the set by " +okay;
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, okay, duration);
                toast.show();
                current++;
                resetButton(view);
            }
        }
    }

    public void sub2(View view) {
        scoreTemB[current] = scoreTemB[current] - 1;
        displayForTeamB(scoreTemB[current]);
    }

    private void displayForTeamB(int score) {
        TextView scoreView = findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void resetButton(View view) {
        scoreTemA[current] = 0;
        scoreTemB[current] = 0;
        displayForTeamA(0);
        displayForTeamB(0);
        TextView scoreView = findViewById(R.id.set1);
        scoreView.setText("sets won : "+set1);
        TextView scoreView1 = findViewById(R.id.set2);
        scoreView1.setText("sets won : "+set2);
    }

    private void giveMessage(String winner, String loser, String points, String setDecided, String setAwon, String setBwon, String data){
        Intent intent = new Intent(getApplicationContext(), setwin.class);
        intent.putExtra("winner",winner);
        intent.putExtra("loser",loser);
        intent.putExtra("sets",setDecided);
        intent.putExtra("setw",setAwon);
        intent.putExtra("setl",setBwon);
        intent.putExtra("points",points);
        intent.putExtra("data", data);
        startActivity(intent);
     }
}
