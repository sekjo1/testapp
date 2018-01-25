package com.example.akra.testapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andreas Kraske on 23.01.2018.
 */

public class HighscoreActivity extends AppCompatActivity
{
    private TextView highscoreTextview;
    private TextView overallHighscoreTextview;
    private TextView besterSpieler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        final Handler handler = new Handler();

        highscoreTextview = (TextView)findViewById(R.id.erhaltenerHighscore);
        overallHighscoreTextview = (TextView)findViewById(R.id.hoechsterHighscore);
        besterSpieler = (TextView)findViewById(R.id.textViewBesterSpieler);

        highscoreTextview.setText(SharedPrefManager.getInstance(this).getHighscore());
        overallHighscoreTextview.setText(SharedPrefManager.getInstance(this).getOverallHighscore());
        besterSpieler.setText(SharedPrefManager.getInstance(this).getOverallHCName());


    }






    /*private void getHighscoreWhatever()
    {
        final String accountNameHighscore = SharedPrefManager.getInstance(this).getUsername();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETSCORE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error"))
                    {
                        SharedPrefManager.getInstance(getApplicationContext()).highscoreToSharedPrefMan(obj.getInt("scoreID"), obj.getString("accountNameHighscore"), obj.getString("score"));
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "error kommt vom response zurück du hund", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("accountNameHighscore", accountNameHighscore);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getHighscore2(View view)
    {
        getHighscoreWhatever();
        highscoreTextview.setText(SharedPrefManager.getInstance(this).getHighscore());
        //Toast.makeText(getApplicationContext(), "funktionier doch du scheisse", Toast.LENGTH_SHORT).show();
    }*/
}