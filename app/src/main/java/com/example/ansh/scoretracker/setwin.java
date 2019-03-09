package com.example.ansh.scoretracker;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class setwin extends AppCompatActivity
{
        public static String winner,loser,data,sets, mainstring,winstatem,statement;
        public static int set1,set2,set,points;
        public StringBuilder okay=new StringBuilder();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setwin);

            Intent intent = getIntent();
            winner = intent.getStringExtra("winner");
            loser = intent.getStringExtra("loser");
            set = Integer.valueOf(intent.getStringExtra("sets"));
            set1 = Integer.valueOf(intent.getStringExtra("setw"));
//            StringBuilder okay = new StringBuilder();
            set2 = Integer.valueOf(intent.getStringExtra("setl"));
            data = intent.getStringExtra("data");
            points=Integer.valueOf(intent.getStringExtra("points"));

            winstatem=winner+" beats "+loser+" - "+String.valueOf(set1)+" : "+String.valueOf(set2);
            mainstring = winner + "," + loser + "," + String.valueOf(set) + ","+ String.valueOf(points) + "," + String.valueOf(set1) + ","+String.valueOf(set2) + data;

            String[] parts = mainstring.split(",");
            statement="";
            for(int i=0;i<set1+set2;i++)
            {	statement = statement+"set "+ String.valueOf(i+1)+" - "+parts[6+2*i]+" : "+parts[6+2*i+1]+"\n";}
                //statement=okay.toString();
                displaytext(statement);
                displaystat(winstatem);
        }

        private void displaytext(String name1) {
            TextView dataView = (TextView) findViewById(R.id.datamax);
            dataView.setText(name1);
        }

        private void displaystat(String okay1)
        {	TextView dataView = (TextView) findViewById(R.id.winstat);
            dataView.setText(okay1);
        }

        public void saveMatch(View view) {
            mainstring = winner + "," + loser + "," + String.valueOf(set) + ","+ String.valueOf(points) + "," + String.valueOf(set1) + ","+String.valueOf(set2) + data;
            Context mContext = getApplicationContext();
            SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
            SharedPreferences.Editor editor = prefs.edit();

            int size = prefs.getInt(ViewActivity.ArrayName + "_size", 0);
            editor.putString(ViewActivity.ArrayName + "_" + size, mainstring).commit();
            editor.putInt(ViewActivity.ArrayName + "_size", ++size).commit();

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(mContext, "Match Saved Successfully !", duration);
            toast.show();
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        public void discardMatch(View v) {
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
}
