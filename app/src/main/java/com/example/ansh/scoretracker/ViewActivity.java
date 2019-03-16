package com.example.ansh.scoretracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    public static String ArrayName = "savearray";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Context mContext = getApplicationContext();

        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);

        final int size = prefs.getInt(ArrayName + "_size", 0);

        final ArrayList<Match> matchlist = new ArrayList<>();

        for(int i=size-1;i>=0;i--)
            matchlist.add(decode(prefs.getString(ArrayName + "_" + i, null)));

        ListView mListView = findViewById(R.id.listView);
        MatchListAdapter adapter = new MatchListAdapter(this, R.layout.adapter_view, matchlist);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // https://www.viralandroid.com/2016/04/start-new-activity-from-android-listview-onitem-clicked.html
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewItemActivity.class);
                intent.putExtra("position", size - i - 1);
                startActivity(intent);
            }
        });

    }


    private Match decode(String mainstring){

        String winner, loser, setString;
        String[] parts = mainstring.split(",");

        winner = parts[0];
        loser = parts[1];
        setString = parts[4] +  " : " + parts[5];

        return new Match(winner, loser, setString);
    }

}

