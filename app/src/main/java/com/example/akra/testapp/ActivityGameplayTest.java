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
import android.widget.TextView;

import java.util.Random;

import static com.example.akra.testapp.R.id.buttonBackToMainScreen2;
import static com.example.akra.testapp.R.id.reloadButton;

/**
 * Created by akra on 22.09.2017.
 */

public class ActivityGameplayTest extends AppCompatActivity implements View.OnClickListener
{

    int amuValue = 10;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);
        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reloadButton);
        //ImageButton backgroundButton = (ImageButton) findViewById(R.id.buttonBackground);
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);

        iBR.setBackground(null);
        iBR.setOnClickListener(this);
        reloadButton.setBackground(null);
        reloadButton.setOnClickListener(this);
        //backgroundButton.setBackground(null);
        //backgroundButton.setOnClickListener(this);
        btms2.setOnClickListener(this);
        actualAmmunition.setText(Integer.toString(amuValue));



        iBR.setX(500);
        iBR.setY(900);

    }
    @Override
    public void onClick(View v)
    {
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);

        /*if(v.getId() != R.id.buttonBackToMainScreen2 && v.getId() != R.id.imageButtonRocket && v.getId() != R.id.reloadButton){
            if(amuValue > 0) {
                amuValue = amuValue - 1;
                actualAmmunition.setText(Integer.toString(amuValue));
            }
        }
        else {*/

            switch (v.getId()) {
                case R.id.buttonBackToMainScreen2:
                    finish();
                    break;

                case R.id.imageButtonRocket:
                    if (amuValue > 0) {
                        ButtonPlacement idk = new ButtonPlacement();
                        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

                        ButtonPlacement werteRandomen = new ButtonPlacement();
                        iBR.setX(werteRandomen.getRandomZahlX());
                        iBR.setY(werteRandomen.getRandomZahlY());
                        iBR.setScaleX((float) 1);
                        iBR.setScaleY((float) 1);
                        amuValue = amuValue - 1;

                        actualAmmunition.setText(Integer.toString(amuValue));
                    } else {
                        break;
                    }

                case R.id.reloadButton:
                    amuValue = 10;

                    actualAmmunition.setText(Integer.toString(amuValue));
                    break;

                default:
                    if(amuValue > 0){
                        amuValue = amuValue - 1;
                        actualAmmunition.setText(Integer.toString(amuValue));


            }
        }
    }
}

