package com.example.akra.testapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by akra on 22.09.2017.
 */

public class ActivityGameplayTest extends AppCompatActivity implements View.OnClickListener
{

    int amuValue = 10;
    int lifePoints = 5;
    String uebergabeString;
    String uebergabeLifepoints;
    String uebergabeScore;
    CountDownTimer targetScaling;
    CountDownTimer difficulty;
    long score = 0;
    double scoreGain = 1;
    long scalingSpeed = 5000;
    long scalingSteps = 25;
    float scalingAmount = 0.0025F;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);                         //buttons initialisieren
        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reloadButton);
        ImageButton backgroundButton = (ImageButton)findViewById(R.id.buttonBackground);
        ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);

        final TextView currentLifepoints = (TextView) findViewById(R.id.lifePointsTV);
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        TextView currentScore = (TextView) findViewById(R.id.scoreTV);

        iBR.setBackground(null);
        reloadButton.setBackground(null);
        backgroundButton.setBackground(null);
        blockShots.setBackground(null);

        btms2.setOnClickListener(this);
        iBR.setOnClickListener(this);
        backgroundButton.setOnClickListener(this);
        blockShots.setOnClickListener(this);
        reloadButton.setOnClickListener(this);

        uebergabeString = String.valueOf(amuValue);
        actualAmmunition.setText(uebergabeString);



        iBR.setX(500);
        iBR.setY(900);

        blockShots.setX(5000F);
        blockShots.setY(5000F);

        difficulty = new CountDownTimer(86400000,30000){

            @Override
            public void onTick(long millisUntilFinished) {
                scoreGain = scoreGain*1.5;
                if(scalingSpeed > 500) {
                    scalingSpeed = scalingSpeed - 250;
                    scalingSteps = scalingSpeed / 200;
                    scalingAmount = scalingSpeed / 2000000;
                }
                else{

                }
            }

            @Override
            public void onFinish() {

            }
        };
        difficulty.start();



        targetScaling = new CountDownTimer(scalingSpeed, scalingSteps) {
            float i = 1F;
            ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

            @Override
            public void onTick(long millisUntilFinished) {
                i = i + scalingAmount;
                iBR.setScaleX(i);
                iBR.setScaleY(i);
            }

            @Override
            public void onFinish() {
                lifePoints = lifePoints - 1;
                uebergabeLifepoints = String.valueOf(lifePoints);
                currentLifepoints.setText("Lebenspunkte: " + uebergabeLifepoints);
                i = 1;

                iBR.setX(5000F);
                iBR.setY(5000F);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

                        ButtonPlacement werteRandomen = new ButtonPlacement();
                        iBR.setX(werteRandomen.getRandomZahlX());
                        iBR.setY(werteRandomen.getRandomZahlY());
                        iBR.setScaleX((float) 1);
                        iBR.setScaleY((float) 1);
                        if(lifePoints > 0) {
                            targetScaling.start();
                        }
                        else{
                            iBR.setX(5000);
                            iBR.setY(5000);
                        }
                    }
                }, (long) (Math.random()*1000));
            }
        };
        targetScaling.start();

    }





    @Override
    public void onClick(View v)
    {
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        TextView currentScore = (TextView) findViewById(R.id.scoreTV);
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

                        lifePoints++;
                        targetScaling.cancel();
                        targetScaling.onFinish();

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Punktzahl: " + uebergabeScore);

                        break;
                    } else

                    {
                        break;
                    }


                case R.id.reloadButton:
                    ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);
                    blockShots.setX(0);
                    blockShots.setY(80);

                    CountDownTimer reloadCountDown = new CountDownTimer(3000,5){
                        int i = 0;
                        @Override
                        public void onTick(long milliSekunden){
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);
                            i++;
                            reloadBar.setProgress((int)i*100/(3000/15));

                        }

                        @Override
                        public void onFinish() {
                            TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);

                            amuValue = 10;
                            uebergabeString = String.valueOf(amuValue);
                            actualAmmunition.setText(uebergabeString);
                            reloadBar.setProgress(0);
                            i = 0;

                            ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);
                            blockShots.setX(5000);
                            blockShots.setY(5000);
                        }

                    };
                    reloadCountDown.start();

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

                case R.id.buttonBlockShots:

                    break;

            }

    }
}