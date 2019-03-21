package com.example.ansh.scoretracker;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;




public class setwin extends AppCompatActivity
{
    private static final String TAG = "setwin";
    public static String winner,loser,data, mainstring,winstatem,statement;
    public static int set1,set2,set,points;

    @Override
    public void onBackPressed() {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), "Back not Allowed", duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setwin);

        Intent intent = getIntent();
        winner = intent.getStringExtra("winner");
        loser = intent.getStringExtra("loser");
        set = intent.getIntExtra("sets", 0);
        set1 = intent.getIntExtra("setw", 0);
//            StringBuilder okay = new StringBuilder();
        set2 = intent.getIntExtra("setl", 0);
        data = intent.getStringExtra("data");
        points=intent.getIntExtra("points", 0);

        winstatem=winner+" beats "+loser+" - "+String.valueOf(set1)+" : "+String.valueOf(set2);
        mainstring = winner + "," + loser + "," + String.valueOf(set) + ","+ String.valueOf(points) + "," + String.valueOf(set1) + ","+String.valueOf(set2) + data;
        Log.d(TAG, "onCreate: mainstring " + mainstring);
        String[] parts = mainstring.split(",");
        statement="";
        for(int i=0;i<set1+set2;i++)
        {	statement = new StringBuilder().append(statement).append("set ").append(String.valueOf(i + 1)).append(" - ").append(parts[6 + 2 * i]).append(" : ").append(parts[6 + 2 * i + 1]).append("\n").toString();}
            //statement=okay.toString();
            displaytext(statement);
            displaystat(winstatem);
    }

    private void displaytext(String name1) {
        TextView dataView = findViewById(R.id.datamax);
        dataView.setText(name1);
    }

    private void displaystat(String okay1)
    {	TextView dataView = findViewById(R.id.winstat);
        dataView.setText(okay1);
    }

    public void saveMatch(View view) {
        mainstring = winner + "," + loser + "," + String.valueOf(set) + ","+ String.valueOf(points) + "," + String.valueOf(set1) + ","+String.valueOf(set2) + data;
        Context mContext = getApplicationContext();
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();

        int size = prefs.getInt(ViewActivity.ArrayName + "_size", 0);

        /////////////////////////////////////////////////////////////

        if (isNetworkAvailable()) {
            PostToServer();
        }
        else {

            String toSync = prefs.getString(ViewActivity.ArrayName + "_to_sync", "");
            editor.putString(ViewActivity.ArrayName +  "_to_sync", toSync + size + ",");

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(mContext, "No Internet Detected. Sync to server when online ", duration);
            toast.show();
        }

        /////////////////////////////////////////////////////////////

        editor.putString(ViewActivity.ArrayName + "_" + size, mainstring).apply();
        editor.putInt(ViewActivity.ArrayName + "_size", ++size).apply();



        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void discardMatch(View v) {
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void PostToServer(){

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        winner = winner.trim();
        loser = loser.trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyTErSsWIqbMRCxuFWeEWRhGD_voiUfEAGMetBULjJiUwegJ8A/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(setwin.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("winner",winner);
                parmas.put("loser",loser);
                parmas.put("points", String.valueOf(points));
                parmas.put("numOfSets", String.valueOf(set));
                parmas.put("setsWinnerWon", String.valueOf(set1));
                parmas.put("setsLoserWon", String.valueOf(set2));

                return parmas;
            }
        };

        int socketTimeOut = 10000;// u can change this .. here it is 10 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }
}
