package com.example.ansh.scoretracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String winner, loser, points, set, set1, set2, failedAttempts;

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newmatch =  findViewById(R.id.newmatch);
        Button viewresults = findViewById(R.id.viewresults);
        Button syncServer = findViewById(R.id.syncserver);

        newmatch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Start_activity.class);
                startActivity(intent);
            }
        });


        viewresults.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                startActivity(intent);
            }
        });

        syncServer.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(isNetworkAvailable()){
                    Log.d(TAG, "onClick: started");
                    failedAttempts = "";

                    Context mContext = getApplicationContext();
                    SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    String toSync = prefs.getString(ViewActivity.ArrayName + "_to_sync", null);
                    Log.d(TAG, "onClick: toSync " + toSync );

                    if(toSync != null){
                        String[] parts = toSync.split(",");
                        for(String i: parts){

                            Log.d(TAG, "onClick: in for loop " + i);
                            String mainstring = prefs.getString(ViewActivity.ArrayName + "_" + i, null);
                            if(mainstring != null){
                                String[] subParts = mainstring.split(",");
                                winner = subParts[0];
                                loser = subParts[1];
                                set = subParts[2];
                                points = subParts[3];
                                set1 = subParts[4];
                                set2 = subParts[5];

                                PostToServer(i);
                            }
                            else{
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(getApplicationContext(), "You are Synced", duration);
                                toast.show();
                            }



                        }
                    }


                    editor.putString(ViewActivity.ArrayName + "_to_sync", failedAttempts).apply();

                }
                else {
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(getApplicationContext(), "No Internet Detected", duration);
                    toast.show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void PostToServer(final String k){

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyTErSsWIqbMRCxuFWeEWRhGD_voiUfEAGMetBULjJiUwegJ8A/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        failedAttempts = failedAttempts + k + ",";
                        Toast.makeText(MainActivity.this, "Cannot sync now. Try Later", Toast.LENGTH_SHORT).show();
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

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);

        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), "Match Saved Successfully !", duration);
        toast.show();


    }
}
