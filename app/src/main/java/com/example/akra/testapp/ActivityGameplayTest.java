package com.example.akra.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

import static com.example.akra.testapp.R.id.buttonTarget;

/**
 * Created by akra on 22.09.2017.
 */

public class ActivityGameplayTest extends AppCompatActivity implements View.OnClickListener
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        Button buttonTarget = (Button) findViewById(R.id.buttonTarget);
        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);
        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

        iBR.setOnClickListener(this);
        btms2.setOnClickListener(this);
        buttonTarget.setOnClickListener(this);


        double randomValueX = Math.random();
        double randomValueY = Math.random();
        double ValueX = Math.round((randomValueX * 860) + 80);
        double ValueY = Math.round((randomValueY * 1600) + 80);
        iBR.setX((float)ValueX);
        iBR.setY((float)ValueY);

    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonBackToMainScreen2:

                finish();
                break;

            case R.id.imageButtonRocket:
                ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);
                double randomValueX = Math.random();
                double randomValueY = Math.random();
                double ValueX = Math.round((randomValueX * 920) + 80);
                double ValueY = Math.round(randomValueY * 1600 + 80);
                iBR.setX((float)ValueX);
                iBR.setY((float)ValueY);
                break;

            case buttonTarget:

                break;

        }
    }
}
