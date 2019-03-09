package com.example.ansh.scoretracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestSaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_save);

        Context mContext = getApplicationContext();

        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);

        String size = String.valueOf(prefs.getInt(ViewActivity.ArrayName + "_size", 7));

        TextView t = findViewById(R.id.testsave);
        t.setText(size);

    }
}
