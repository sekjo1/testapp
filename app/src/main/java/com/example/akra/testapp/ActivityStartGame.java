package com.example.akra.testapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by akra on 21.09.2017.
 */





public class ActivityStartGame extends AppCompatActivity implements View.OnClickListener
{

    final int anzahlButtons = 20;
    int i = 0;
    CountDownTimer buttonSpawning;
    ImageButton[] targetButtons = new ImageButton[anzahlButtons];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        final ConstraintLayout testLayout = (ConstraintLayout) findViewById(R.id.TestConstraintLayout);

        for(i = 0; i < anzahlButtons; i++){

            targetButtons[i] = new ImageButton(this);
            targetButtons[i].setImageResource(R.mipmap.zielscheibe);
            testLayout.addView(targetButtons[i]);
            targetButtons[i].setX(900);
            targetButtons[i].setY(900);


            buttonSpawning = new CountDownTimer(1000, 1000) {
                ButtonPlacement wertRandomen = new ButtonPlacement();
                int a = 0;


                @Override
                public void onTick(long millisUntilFinished) {
                    targetButtons[a].setX(wertRandomen.getRandomZahlX());
                    targetButtons[a].setY(wertRandomen.getRandomZahlY());
                }

                @Override
                public void onFinish() {
                    a++;
                }
            }; buttonSpawning.start();

        }
        Button bbtms = (Button) findViewById(R.id.buttonBackToMainScreen);
        bbtms.setOnClickListener(this);



    }



    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonBackToMainScreen:
                finish();
                break;

        }
    }

}