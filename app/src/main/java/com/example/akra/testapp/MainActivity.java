package com.example.akra.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView label;
    Intent startGame;
    Intent startLoginScreen;
    Intent startHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startButton = (ImageButton)findViewById(R.id.imageStartButton);
        ImageButton highscoreButton = (ImageButton)findViewById(R.id.imageHighscoreButton);
        ImageButton endButton = (ImageButton)findViewById(R.id.imageLeaveButton);


        TextView startButtonText = (TextView) findViewById(R.id.textviewStarten);
        TextView highscoreButtonText = (TextView)findViewById(R.id.textviewHighscore);
        TextView endButtonText = (TextView)findViewById(R.id.textviewEnde);
        TextView loggedInUser = (TextView) findViewById(R.id.textViewUsername);

        startButton.setBackground(null);
        highscoreButton.setBackground(null);
        endButton.setBackground(null);

        startButton.setOnClickListener(this);
        highscoreButton.setOnClickListener(this);
        endButton.setOnClickListener(this);

        startGame = new Intent(MainActivity.this, ActivityGameplay.class);
        startHighscore = new Intent (MainActivity.this, HighscoreActivity.class);

        loggedInUser.setText(SharedPrefManager.getInstance(this).getUsername());

        getHighscoreCurrentPlayer();
        getHighscoreOverall();

    }
//######################################################Highscore des aktuellen Spielers holen######################################################


    private void getHighscoreCurrentPlayer()
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
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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

//#########################################################Besten Highscore holen####################################################################

    private void getHighscoreOverall()
    {
        final String zeroString = "0";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETOVERALLHIGHSCORE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error"))
                    {
                        SharedPrefManager.getInstance(getApplicationContext()).overallHighscoreToSharedPrefMan(obj.getString("accountNameHighscore2"), obj.getString("score2"));
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("score", zeroString);
                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.imageStartButton:
                startActivity(startGame);
                break;

            case R.id.imageHighscoreButton:
                getHighscoreCurrentPlayer();
                getHighscoreOverall();
                startActivity(startHighscore);
                break;

            case R.id.imageLeaveButton:
                finishAffinity();
                break;
        }
    }

    public void userLogout(View view){

        SharedPrefManager.getInstance(this).logout();
        startLoginScreen = new Intent(MainActivity.this, LoginActivity.class);
        finishAffinity();
        startActivity(startLoginScreen);
    }
}
