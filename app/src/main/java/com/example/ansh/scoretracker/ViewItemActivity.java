package com.example.ansh.scoretracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        Intent listIntent = getIntent();
        int position = listIntent.getIntExtra("position", 0);

        Context mContext = getApplicationContext();
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        String matchInfoString = prefs.getString(ViewActivity.ArrayName + "_" + position, null);

        TextView winnerName = (TextView) findViewById(R.id.item_winner);
        TextView loserName = (TextView) findViewById(R.id.item_loser);
        TextView points = (TextView) findViewById(R.id.item_points);
        TextView sets = (TextView) findViewById(R.id.item_set);
        ListView itemList = (ListView) findViewById(R.id.item_list);

        String[] parts = matchInfoString.split(",");

        int setsInReal = Integer.valueOf(parts[4]) + Integer.valueOf(parts[5]);
        winnerName.setText("Winner : "+parts[0]);
        loserName.setText("Loser : "+parts[1]);
        sets.setText("Sets Played "+setsInReal + " of " + parts[2]);
        points.setText("Number of Points : "+parts[3]);

//        for(int i = 0; i < number_of_sets; ++i){
//            each_set_points_winner[i] = parts[2*i+4];
//            each_set_points_loser[i] = parts[2*i+5];
//        }

        final ArrayList<MyItem> itemlist = new ArrayList<>();

        itemlist.add(new MyItem("Set No.","Points A","Points B"));
        for(int i = 0; i < setsInReal; ++i){
            itemlist.add(new MyItem(String.valueOf(i + 1), parts[2*i + 6], parts[2*i + 7]));
        }

        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.adapter_item, itemlist);
        itemList.setAdapter(adapter);
    }


}
