package com.example.akra.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.akra.testapp.R.id.buttonBackToMainScreen2;
import static com.example.akra.testapp.R.id.reloadButton;

/**
 * Created by akra on 22.09.2017.
 */

public class ActivityGameplayTest extends AppCompatActivity implements View.OnClickListener
{

    int amuValue = 10;
    String uebergabeString;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);                         //buttons initialisieren
        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reloadButton);
        ImageButton backgroundButton = (ImageButton)findViewById(R.id.buttonBackground);

        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);

        iBR.setBackground(null);
        reloadButton.setBackground(null);
        backgroundButton.setBackground(null);

        btms2.setOnClickListener(this);
        iBR.setOnClickListener(this);
        backgroundButton.setOnClickListener(this);

        reloadButton.setOnClickListener(this);

        uebergabeString = String.valueOf(amuValue);
        actualAmmunition.setText(uebergabeString);

        iBR.setX(500);
        iBR.setY(900);

    }
    @Override
    public void onClick(View v)
    {
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        //Toast.makeText(this, v.getId() + "p", Toast.LENGTH_LONG).show();

            switch (v.getId()) {
                case R.id.buttonBackToMainScreen2:
                    finish();
                    break;

                case R.id.imageButtonRocket:
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);

                        ButtonPlacement idk = new ButtonPlacement();
                        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

                        iBR.setX(5000);
                        iBR.setY(5000);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable()
                        {
                            public void run() {
                                ButtonPlacement idk = new ButtonPlacement();
                                ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

                                ButtonPlacement werteRandomen = new ButtonPlacement();
                                iBR.setX(werteRandomen.getRandomZahlX());
                                iBR.setY(werteRandomen.getRandomZahlY());
                                iBR.setScaleX((float) 1);
                                iBR.setScaleY((float) 1);
                        }

                        }, 1000);


                        break;
                    } else

                    {
                        break;
                    }


                case R.id.reloadButton:
                    amuValue = 10;
                    uebergabeString = String.valueOf(amuValue);
                    actualAmmunition.setText(uebergabeString);
                    break;

                case R.id.buttonBackground:
                    if (amuValue > 0) {
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);
                        break;
                    }
                    else{
                        break;
                    }

            }

    }
}

        /*if(v.getId() != R.id.buttonBackToMainScreen2 && v.getId() != R.id.imageButtonRocket && v.getId() != R.id.reloadButton){
            if(amuValue > 0) {
                amuValue = amuValue - 1;
                actualAmmunition.setText(Integer.toString(amuValue));
            }
        }
        else {*/