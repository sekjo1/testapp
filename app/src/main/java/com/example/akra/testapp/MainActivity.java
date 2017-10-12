package com.example.akra.testapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView label;
    Intent startGame;
    Intent startTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "8===>", Toast.LENGTH_LONG).show();

        Button startButton = (Button)findViewById(R.id.buttonStarten);
        Button highscoreButton = (Button)findViewById(R.id.buttonHighscore);
        Button endButton = (Button)findViewById(R.id.buttonEnde);
        Button gameplayTestButton = (Button)findViewById(R.id.buttonGameplayTest);
        label = (TextView)findViewById(R.id.GameTitle);

        startButton.setOnClickListener(this);
        highscoreButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
        gameplayTestButton.setOnClickListener(this);

        startTest = new Intent(MainActivity.this, ActivityGameplayTest.class);
        startGame = new Intent(MainActivity.this, ActivityStartGame.class);
    }



    @Override
    public void onClick(View v)
    {
        Toast.makeText(this, "Sie haben einene Button geklickt", Toast.LENGTH_LONG).show();
        switch (v.getId())
        {
            case R.id.buttonGameplayTest:
                startActivity(startTest);
                break;

            case R.id.buttonStarten:
                startActivity(startGame);
                break;

            case R.id.buttonHighscore:
                label.setBackgroundColor(Color.YELLOW);
                break;

            case R.id.buttonEnde:
                finish();
                break;
        }
    }
}
