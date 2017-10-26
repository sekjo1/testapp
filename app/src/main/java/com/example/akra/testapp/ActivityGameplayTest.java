package com.example.akra.testapp;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    String uebergabeStufe;
    CountDownTimer target1ScalingInitiative;
    CountDownTimer target2ScalingInitiative;
    CountDownTimer difficulty;
    long score = 0;
    double scoreGain = 1;
    long scalingSpeed = 5250;                                                                       //für alle targets verwendbar
    long scalingSteps = 25;
    float scalingAmount = 0.008F;
    boolean scalingTarget1Started = false;
    boolean scalingTarget2Started = false;
    float iScalerTarget1 = 1F;
    float iScalerTarget2 = 1F;
    int schwierigStufe = 0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);                         //buttons initialisieren
        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket);
        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reloadButton);
        ImageButton backgroundButton = (ImageButton)findViewById(R.id.buttonBackground);
        ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);
        ImageButton buttonStartRound = (ImageButton)findViewById(R.id.imageButtonStartRound);

        ImageView backgroundImage = (ImageView)findViewById(R.id.imageViewBackgroundPicture);
        ImageView levelBackground = (ImageView)findViewById(R.id.imageViewLevelBackground);
        ImageView backgroundHeart = (ImageView)findViewById(R.id.imageViewHeart);

        final TextView currentLifepoints = (TextView) findViewById(R.id.lifePointsTV);
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        TextView currentScore = (TextView) findViewById(R.id.scoreTV);
        final TextView currentLevel = (TextView) findViewById(R.id.textViewStufe);

        iBR1.setBackground(null);
        iBR2.setBackground(null);
        reloadButton.setBackground(null);
        backgroundButton.setBackground(null);
        blockShots.setBackground(null);
        buttonStartRound.setBackground(null);

        btms2.setOnClickListener(this);
        iBR1.setOnClickListener(this);
        iBR2.setOnClickListener(this);
        backgroundButton.setOnClickListener(this);
        blockShots.setOnClickListener(this);
        reloadButton.setOnClickListener(this);
        buttonStartRound.setOnClickListener(this);

        uebergabeString = String.valueOf(amuValue);
        actualAmmunition.setText(uebergabeString);

        backgroundImage.setScaleX(1.15F);
        backgroundImage.setScaleY(1.15F);

        iBR1.setX(5000);
        iBR1.setY(5000);
        iBR2.setX(5000);
        iBR2.setY(5000);

        blockShots.setX(5000F);
        blockShots.setY(5000F);


        difficulty = new CountDownTimer(86400000,10000){
            ImageButton iBR1 = (ImageButton)findViewById(R.id.imageButtonRocket);
            ImageButton iBR2 = (ImageButton)findViewById(R.id.imageButtonRocket2);

            @Override
            public void onTick(long millisUntilFinished) {
                scoreGain = scoreGain*1.25;
                schwierigStufe++;
                uebergabeStufe = String.valueOf(schwierigStufe);
                currentLevel.setText("Stufe: " + uebergabeStufe);

                if(scalingSpeed > 500) {
                    scalingSpeed = scalingSpeed - 250;
                    scalingSteps = scalingSpeed / 200;
//################## Target 1 ######################################################################
                    if(scalingTarget1Started == true){
                        target1ScalingInitiative.cancel();
                        target1ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                iScalerTarget1 = iScalerTarget1 + scalingAmount;
                                iBR1.setScaleX(iScalerTarget1);
                                iBR1.setScaleY(iScalerTarget1);
                                scalingTarget1Started = true;
                            }

                            @Override
                            public void onFinish() {
                                lifePoints = lifePoints - 1;
                                uebergabeLifepoints = String.valueOf(lifePoints);
                                currentLifepoints.setText(uebergabeLifepoints);
                                iScalerTarget1 = 1;

                                iBR1.setX(5000F);
                                iBR1.setY(5000F);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        //ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

                                        ButtonPlacement werteRandomen = new ButtonPlacement();
                                        iBR1.setX(werteRandomen.getRandomZahlX());
                                        iBR1.setY(werteRandomen.getRandomZahlY());
                                        iBR1.setScaleX((float) 1);
                                        iBR1.setScaleY((float) 1);
                                        if (lifePoints > 0) {
                                            target1ScalingInitiative.start();
                                        } else {
                                            iBR1.setX(5000);
                                            iBR1.setY(5000);
                                            iBR2.setX(5000);
                                            iBR2.setY(5000);
                                            difficulty.cancel();
                                        }
                                    }
                                },(long) (500 + (Math.random()*1500)));

                            }
                        };
                        target1ScalingInitiative.start();
                    }
                    else{
                        target1ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {

                            @Override
                            public void onTick(long millisUntilFinished) {                          // alle x milisekunden Scheibe vergrößern.
                                iScalerTarget1 = iScalerTarget1 + scalingAmount;
                                iBR1.setScaleX(iScalerTarget1);
                                iBR1.setScaleY(iScalerTarget1);
                                scalingTarget1Started = true;
                            }

                            @Override
                            public void onFinish() {                                                //Wenn Scheibe maximal skaliert hat.
                                lifePoints = lifePoints - 1;                                        //Lebenspunkt abziehen
                                uebergabeLifepoints = String.valueOf(lifePoints);
                                currentLifepoints.setText(uebergabeLifepoints);
                                iScalerTarget1 = 1;

                                iBR1.setX(5000F);                                                    //Scheibe aus Spielfeld bewegen
                                iBR1.setY(5000F);

                                final Handler handler = new Handler();                              //nach 1 - 1500 ms wird selber Countdowntimer erneut gestartet
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        //ImageButton iBR = (ImageButton) findViewById(R.id.imageButtonRocket);

                                        ButtonPlacement werteRandomen = new ButtonPlacement();
                                        iBR1.setX(werteRandomen.getRandomZahlX());
                                        iBR1.setY(werteRandomen.getRandomZahlY());
                                        iBR1.setScaleX((float) 1);
                                        iBR1.setScaleY((float) 1);
                                        if (lifePoints > 0) {
                                            target1ScalingInitiative.start();                         //
                                        } else {
                                            iBR1.setX(5000);
                                            iBR1.setY(5000);
                                            iBR2.setX(5000);
                                            iBR2.setY(5000);
                                            difficulty.cancel();
                                        }
                                    }
                                },(long) (500 + (Math.random()*1500)));

                            }
                        };
                    }
//################## Target 2 ######################################################################
                    if(schwierigStufe > 1) {
                        if (scalingTarget2Started == true) {
                            target2ScalingInitiative.cancel();
                            target2ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    iScalerTarget2 = iScalerTarget2 + scalingAmount;
                                    iBR2.setScaleX(iScalerTarget2);
                                    iBR2.setScaleY(iScalerTarget2);
                                    scalingTarget2Started = true;
                                }

                                @Override
                                public void onFinish() {
                                    lifePoints = lifePoints - 1;
                                    uebergabeLifepoints = String.valueOf(lifePoints);
                                    currentLifepoints.setText(uebergabeLifepoints);
                                    iScalerTarget2 = 1;

                                    iBR2.setX(5000F);
                                    iBR2.setY(5000F);

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);

                                            ButtonPlacement werteRandomen = new ButtonPlacement();
                                            iBR2.setX(werteRandomen.getRandomZahlX());
                                            iBR2.setY(werteRandomen.getRandomZahlY());
                                            iBR2.setScaleX((float) 1);
                                            iBR2.setScaleY((float) 1);
                                            if (lifePoints > 0) {
                                                target2ScalingInitiative.start();
                                            } else {
                                                iBR1.setX(5000);
                                                iBR1.setY(5000);
                                                iBR2.setX(5000);
                                                iBR2.setY(5000);
                                                difficulty.cancel();
                                            }
                                        }
                                    }, (long) (1000 + (Math.random() * 2000)));

                                }
                            };
                            target2ScalingInitiative.start();
                        } else {
                            target2ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {

                                @Override
                                public void onTick(long millisUntilFinished) {                          // alle x milisekunden Scheibe vergrößern.
                                    iScalerTarget2 = iScalerTarget2 + scalingAmount;
                                    iBR2.setScaleX(iScalerTarget2);
                                    iBR2.setScaleY(iScalerTarget2);
                                    scalingTarget2Started = true;
                                }

                                @Override
                                public void onFinish() {                                                //Wenn Scheibe maximal skaliert hat.
                                    lifePoints = lifePoints - 1;                                        //Lebenspunkt abziehen
                                    uebergabeLifepoints = String.valueOf(lifePoints);
                                    currentLifepoints.setText(uebergabeLifepoints);
                                    iScalerTarget2 = 1;

                                    iBR2.setX(5000F);                                                    //Scheibe aus Spielfeld bewegen
                                    iBR2.setY(5000F);

                                    final Handler handler = new Handler();                              //nach 1 - 1500 ms wird selber Countdowntimer erneut gestartet
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);

                                            ButtonPlacement werteRandomen = new ButtonPlacement();
                                            iBR2.setX(werteRandomen.getRandomZahlX());
                                            iBR2.setY(werteRandomen.getRandomZahlY());
                                            iBR2.setScaleX((float) 1);
                                            iBR2.setScaleY((float) 1);
                                            if (lifePoints > 0) {
                                                target2ScalingInitiative.start();                         //
                                            } else {
                                                iBR1.setX(5000);
                                                iBR1.setY(5000);
                                                iBR2.setX(5000);
                                                iBR2.setY(5000);
                                                difficulty.cancel();
                                            }
                                        }
                                    }, (long) (1000 + (Math.random() * 2000)));

                                }
                            };target2ScalingInitiative.start();
                        }
                    }
//################## letzter Button zu Ende ########################################################
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Nice bot, dude", Toast.LENGTH_LONG).show();
            }
        };
    }


//##################################################################################################


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v)
    {
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        TextView currentScore = (TextView) findViewById(R.id.scoreTV);
        ImageButton buttonStartRound = (ImageButton)findViewById(R.id.imageButtonStartRound);

            switch (v.getId()) {
                case R.id.buttonBackToMainScreen2:                                                  //zurück Button
                    finish();
                    break;

                case R.id.imageButtonRocket:                                                        //Zielscheibe
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);

                        lifePoints++;
                        target1ScalingInitiative.cancel();
                        target1ScalingInitiative.onFinish();
                        scalingTarget1Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Punktzahl: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.imageButtonRocket2:
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);

                        lifePoints++;
                        target2ScalingInitiative.cancel();
                        target2ScalingInitiative.onFinish();
                        scalingTarget2Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Punktzahl: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }


                case R.id.reloadButton:                                                             //Nachladen
                    ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);
                    blockShots.setX(0);
                    blockShots.setY(80);

                    CountDownTimer reloadCountDown = new CountDownTimer(2500,5){
                        int i = 0;
                        @Override
                        public void onTick(long milliSekunden){                                     //Progress Bar beim Nachladen füllen
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);
                            i++;
                            reloadBar.setProgress((int)i*100/(2500/15));

                        }

                        @Override
                        public void onFinish() {                                                    //Nachladevorgang
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

                case R.id.buttonBackground:                                                         //Daneben schießen
                    if (amuValue > 0) {
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.imageButtonStartRound:                                                    //Runde starten
                    difficulty.start();
                    buttonStartRound.setX(6000);
                    buttonStartRound.setY(6000);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket);

                            ButtonPlacement werteRandomen = new ButtonPlacement();
                            iBR1.setX(werteRandomen.getRandomZahlX());
                            iBR1.setY(werteRandomen.getRandomZahlY());
                            iBR1.setScaleX((float) 1);
                            iBR1.setScaleY((float) 1);
                            target1ScalingInitiative.start();
                        }
                    },(long) (1000));
                    break;

                case R.id.buttonBlockShots:                                                         //Schüsse während nachladen blockieren
                    break;

            }
    }
}