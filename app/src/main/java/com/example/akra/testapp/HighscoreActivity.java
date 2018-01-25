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
    private TextView highscoreTextview;                                                             //Textview für Highscore des eingeloggten Spielers
    private TextView overallHighscoreTextview;                                                      //Textview für Highscore des besten Spielers
    private TextView besterSpieler;                                                                 //Textview für Namen des besten Spielers

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highscoreTextview = (TextView)findViewById(R.id.erhaltenerHighscore);
        overallHighscoreTextview = (TextView)findViewById(R.id.hoechsterHighscore);
        besterSpieler = (TextView)findViewById(R.id.textViewBesterSpieler);

        highscoreTextview.setText(SharedPrefManager.getInstance(this).getHighscore());              //Hier werden die Strings die im SharedPrefManager zwischengespeichert werden abgerufen.
        overallHighscoreTextview.setText(SharedPrefManager.getInstance(this).getOverallHighscore());
        besterSpieler.setText(SharedPrefManager.getInstance(this).getOverallHCName());


    }
}