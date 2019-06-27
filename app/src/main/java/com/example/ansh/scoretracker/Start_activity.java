package com.example.ansh.scoretracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Start_activity extends AppCompatActivity {

    private static String name1, name2;
    private static String points, sets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);

        Button back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void sendMessage(View view)
    {


        EditText names1 = findViewById(R.id.name1);
        EditText names2 = findViewById(R.id.name2);
        EditText set = findViewById(R.id.sets);
        EditText point = findViewById(R.id.points);

        name1 = names1.getText().toString();
        name2 = names2.getText().toString();
        sets = set.getText().toString();
        points = point.getText().toString();

        if(names1.length() == 0 || names2.length() == 0 || set.length() == 0 || point.length() == 0){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, "Please fill all values", duration);
            toast.show();
        }
        else {
            if(name1.indexOf(',') != -1 || name2.indexOf(',') != -1){
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, "No comma in name of players !", duration);
                toast.show();
            }
            else{
                if(Integer.valueOf(sets) % 2 == 0){
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(this, "Number of sets must be odd", duration);
                    toast.show();
                }
                else{
                    startIntent();
                }

            }
        }



    }

    private void startIntent() {
        Intent intent = new Intent(getApplicationContext(), Welcome.class );
        intent.putExtra("Name1", name1);
        intent.putExtra("Name2", name2);
        intent.putExtra("points", points);
        intent.putExtra("sets", sets);
        startActivity(intent);
    }
}