package com.example.akra.testapp;

import android.media.MediaPlayer;
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

    int amuValue = 20;
    int lifePoints = 999999;
    String uebergabeString;
    String uebergabeLifepoints;
    String uebergabeScore;
    String uebergabeStufe;
    CountDownTimer target1ScalingInitiative;
    CountDownTimer target2ScalingInitiative;
    CountDownTimer target3ScalingInitiative;
    CountDownTimer target4ScalingInitiative;
    CountDownTimer target5ScalingInitiative;
    CountDownTimer difficulty;
    CountDownTimer ausblenden1;
    CountDownTimer ausblenden2;
    CountDownTimer ausblenden3;
    CountDownTimer ausblenden4;
    CountDownTimer ausblenden5;
    CountDownTimer ausblendenStart;
    long score = 0;
    double scoreGain = 1;
    long scalingSpeed = 5250;                                                                       //für alle targets verwendbar
    long scalingSteps = 25;
    float scalingAmount = 0.008F;
    boolean scalingTarget1Started = false;
    boolean scalingTarget2Started = false;
    boolean scalingTarget3Started = false;
    boolean scalingTarget4Started = false;
    boolean scalingTarget5Started = false;
    boolean target1FirstSpawn = true;
    boolean target2FirstSpawn = true;
    boolean target3FirstSpawn = true;
    boolean target4FirstSpawn = true;
    boolean target5FirstSpawn = true;
    float iScalerTarget1 = 1F;
    float iScalerTarget2 = 1F;
    float iScalerTarget3 = 1F;
    float iScalerTarget4 = 1F;
    float iScalerTarget5 = 1F;
    int schwierigStufe = 0;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);                         //buttons initialisieren .
        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);
        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
        ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);
        ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);
        ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reloadButton);
        ImageButton backgroundButton = (ImageButton) findViewById(R.id.buttonBackground);
        ImageButton blockShots = (ImageButton) findViewById(R.id.buttonBlockShots);
       // ImageButton buttonStartRound = (ImageButton) findViewById(R.id.imageButtonStartRound);

        ImageView backgroundImage = (ImageView) findViewById(R.id.imageViewBackgroundPicture);
        ImageView levelBackground = (ImageView) findViewById(R.id.imageViewLevelBackground);
        ImageView backgroundHeart = (ImageView) findViewById(R.id.imageViewHeart);

        final TextView currentLifepoints = (TextView) findViewById(R.id.lifePointsTV);
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        final TextView currentScore = (TextView) findViewById(R.id.scoreTV);
        final TextView currentLevel = (TextView) findViewById(R.id.textViewStufe);

        iBR1.setBackground(null);
        iBR2.setBackground(null);
        iBR3.setBackground(null);
        iBR4.setBackground(null);
        iBR5.setBackground(null);
        reloadButton.setBackground(null);
        backgroundButton.setBackground(null);
        blockShots.setBackground(null);
       // buttonStartRound.setBackground(null);

        btms2.setOnClickListener(this);
        iBR1.setOnClickListener(this);
        iBR2.setOnClickListener(this);
        iBR3.setOnClickListener(this);
        iBR4.setOnClickListener(this);
        iBR5.setOnClickListener(this);
        backgroundButton.setOnClickListener(this);
        blockShots.setOnClickListener(this);
        reloadButton.setOnClickListener(this);
       // buttonStartRound.setOnClickListener(this);

        uebergabeString = String.valueOf(amuValue);
        actualAmmunition.setText(uebergabeString);

        backgroundImage.setScaleX(1.15F);
        backgroundImage.setScaleY(1.15F);

        iBR1.setX(5000);
        iBR1.setY(5000);
        iBR2.setX(5000);
        iBR2.setY(5000);
        iBR3.setX(5000);
        iBR3.setY(5000);
        iBR4.setX(5000);
        iBR4.setY(5000);
        iBR5.setX(5000);
        iBR5.setY(5000);

        blockShots.setX(5000F);
        blockShots.setY(5000F);

        //###################################### Handler section ###################################
        final Handler handler1 = new Handler();
        final Handler handler2 = new Handler();
        final Handler handler3 = new Handler();
        final Handler handler4 = new Handler();
        final Handler handler5 = new Handler();
        //##########################################################################################



        difficulty = new CountDownTimer(86400000, 10000) {                    //Schwerigkeitslevel der sich über die Zeit erhöht

            ButtonPlacement werteRandomen = new ButtonPlacement();

            @Override
            public void onTick(long millisUntilFinished) {
                scoreGain = scoreGain * 1.25;
                schwierigStufe++;
                uebergabeStufe = String.valueOf(schwierigStufe);
                currentLevel.setText("Stufe: " + uebergabeStufe);

                if (scalingSpeed > 1500) {                                                           //erhöhung der Geschwindigkeit
                    scalingSpeed = scalingSpeed - 200;
                    scalingSteps = scalingSpeed / 200;
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Punktzahl: " + currentScore, Toast.LENGTH_LONG).show();
            }
        };
//################## Target 1 ######################################################################
        handler1.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);

                difficulty.start();

                if (scalingTarget1Started == true) {
                    target1ScalingInitiative.cancel();
                    target1ScalingInitiative.start();
                }

                ButtonPlacement werteRandomen = new ButtonPlacement();
                iBR1.setX(werteRandomen.getRandomZahlX());
                iBR1.setY(werteRandomen.getRandomZahlY());
                                                                                                    //wenn scheibe nicht im Bild ist
                target1ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                    ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);
                    ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
                    ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);
                    ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);
                    ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);

                    @Override
                    public void onTick(long millisUntilFinished) {                              // alle x milisekunden Scheibe vergrößern.
                        iScalerTarget1 = iScalerTarget1 + scalingAmount;
                        iBR1.setScaleX(iScalerTarget1);
                        iBR1.setScaleY(iScalerTarget1);
                        scalingTarget1Started = true;
                    }

                    @Override
                    public void onFinish() {                                                    //Wenn Scheibe maximal skaliert hat.
                        lifePoints = lifePoints - 1;                                            //Lebenspunkt abziehen
                        uebergabeLifepoints = String.valueOf(lifePoints);
                        currentLifepoints.setText(uebergabeLifepoints);
                        iScalerTarget1 = 1;

                        iBR1.setX(5000F);                                                       //Scheibe aus Spielfeld bewegen
                        iBR1.setY(5000F);

                        ausblenden1 = new CountDownTimer((long) (500 + (Math.random() * 1500)), 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
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
                                    iBR3.setX(5000);
                                    iBR3.setY(5000);
                                    iBR4.setX(5000);
                                    iBR4.setY(5000);
                                    iBR5.setX(5000);
                                    iBR5.setY(5000);
                                    difficulty.cancel();
                                    difficulty.onFinish();
                                }
                            }
                        };
                        ausblenden1.start();
                    }
                };
                if (target1FirstSpawn == true) {
                    target1ScalingInitiative.start();
                    target1FirstSpawn = false;
                }
            }
        }, (long) (4000));
//################## Target 2 ######################################################################
        handler2.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);

                if (scalingTarget2Started == true) {
                    target2ScalingInitiative.cancel();
                    target2ScalingInitiative.start();
                }


                ButtonPlacement werteRandomen = new ButtonPlacement();
                iBR2.setX(werteRandomen.getRandomZahlX());
                iBR2.setY(werteRandomen.getRandomZahlY());


                target2ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                    ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);
                    ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
                    ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);
                    ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);
                    ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);

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

                        ausblenden2 = new CountDownTimer((long) (500 + (Math.random() * 1500)), 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
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
                                    iBR3.setX(5000);
                                    iBR3.setY(5000);
                                    iBR4.setX(5000);
                                    iBR4.setY(5000);
                                    iBR5.setX(5000);
                                    iBR5.setY(5000);
                                    difficulty.cancel();
                                    difficulty.onFinish();
                                }
                            }
                        };
                        ausblenden2.start();
                    }
                };
                if (target2FirstSpawn == true) {
                    target2ScalingInitiative.start();
                    target2FirstSpawn = false;
                }
            }
        }, (long) (24500));
//################## Target 3 ######################################################################
        handler3.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);

                if (scalingTarget3Started == true) {
                    target3ScalingInitiative.cancel();
                    target3ScalingInitiative.start();
                }

                ButtonPlacement werteRandomen = new ButtonPlacement();
                iBR3.setX(werteRandomen.getRandomZahlX());
                iBR3.setY(werteRandomen.getRandomZahlY());

                target3ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                    ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);
                    ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
                    ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);
                    ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);
                    ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);

                    @Override
                    public void onTick(long millisUntilFinished) {                          // alle x milisekunden Scheibe vergrößern.
                        iScalerTarget3 = iScalerTarget3 + scalingAmount;
                        iBR3.setScaleX(iScalerTarget3);
                        iBR3.setScaleY(iScalerTarget3);
                        scalingTarget3Started = true;
                    }

                    @Override
                    public void onFinish() {                                                //Wenn Scheibe maximal skaliert hat.
                        lifePoints = lifePoints - 1;                                        //Lebenspunkt abziehen
                        uebergabeLifepoints = String.valueOf(lifePoints);
                        currentLifepoints.setText(uebergabeLifepoints);
                        iScalerTarget3 = 1;

                        iBR3.setX(5000F);                                                    //Scheibe aus Spielfeld bewegen
                        iBR3.setY(5000F);

                        ausblenden3 = new CountDownTimer((long) (500 + (Math.random() * 1500)), 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                ButtonPlacement werteRandomen = new ButtonPlacement();
                                iBR3.setX(werteRandomen.getRandomZahlX());
                                iBR3.setY(werteRandomen.getRandomZahlY());
                                iBR3.setScaleX((float) 1);
                                iBR3.setScaleY((float) 1);
                                if (lifePoints > 0) {
                                    target3ScalingInitiative.start();
                                } else {
                                    iBR1.setX(5000);
                                    iBR1.setY(5000);
                                    iBR2.setX(5000);
                                    iBR2.setY(5000);
                                    iBR3.setX(5000);
                                    iBR3.setY(5000);
                                    iBR4.setX(5000);
                                    iBR4.setY(5000);
                                    iBR5.setX(5000);
                                    iBR5.setY(5000);
                                    difficulty.cancel();
                                    difficulty.onFinish();
                                }
                            }
                        };
                        ausblenden3.start();
                    }
                };
                if (target3FirstSpawn == true) {
                    target3ScalingInitiative.start();
                    target3FirstSpawn = false;
                }
            }
        }, (long) (44500));
//################## Target 4 ######################################################################
        handler4.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);

                if (scalingTarget4Started == true) {
                    target4ScalingInitiative.cancel();
                    target4ScalingInitiative.start();
                }

                ButtonPlacement werteRandomen = new ButtonPlacement();
                iBR4.setX(werteRandomen.getRandomZahlX());
                iBR4.setY(werteRandomen.getRandomZahlY());

                target4ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                    ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);
                    ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
                    ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);
                    ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);
                    ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);

                    @Override
                    public void onTick(long millisUntilFinished) {                          // alle x milisekunden Scheibe vergrößern.
                        iScalerTarget4 = iScalerTarget4 + scalingAmount;
                        iBR4.setScaleX(iScalerTarget4);
                        iBR4.setScaleY(iScalerTarget4);
                        scalingTarget4Started = true;
                    }

                    @Override
                    public void onFinish() {                                                //Wenn Scheibe maximal skaliert hat.
                        lifePoints = lifePoints - 1;                                        //Lebenspunkt abziehen
                        uebergabeLifepoints = String.valueOf(lifePoints);
                        currentLifepoints.setText(uebergabeLifepoints);
                        iScalerTarget4 = 1;

                        iBR4.setX(5000F);                                                    //Scheibe aus Spielfeld bewegen
                        iBR4.setY(5000F);

                        ausblenden4 = new CountDownTimer((long) (500 + (Math.random() * 1500)), 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                ButtonPlacement werteRandomen = new ButtonPlacement();
                                iBR4.setX(werteRandomen.getRandomZahlX());
                                iBR4.setY(werteRandomen.getRandomZahlY());
                                iBR4.setScaleX((float) 1);
                                iBR4.setScaleY((float) 1);
                                if (lifePoints > 0) {
                                    target4ScalingInitiative.start();
                                } else {
                                    iBR1.setX(5000);
                                    iBR1.setY(5000);
                                    iBR2.setX(5000);
                                    iBR2.setY(5000);
                                    iBR3.setX(5000);
                                    iBR3.setY(5000);
                                    iBR4.setX(5000);
                                    iBR4.setY(5000);
                                    iBR5.setX(5000);
                                    iBR5.setY(5000);
                                    difficulty.cancel();
                                    difficulty.onFinish();
                                }
                            }
                        };
                        ausblenden4.start();
                    }
                };
                if (target4FirstSpawn == true) {
                    target4ScalingInitiative.start();
                    target4FirstSpawn = false;
                }
            }
        }, (long) (64500));
//################## Target 5 ######################################################################
        handler5.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);

                if (scalingTarget5Started == true) {
                    target5ScalingInitiative.cancel();
                    target5ScalingInitiative.start();
                }


                ButtonPlacement werteRandomen = new ButtonPlacement();
                iBR5.setX(werteRandomen.getRandomZahlX());
                iBR5.setY(werteRandomen.getRandomZahlY());

                target5ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                    ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonRocket1);
                    ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonRocket2);
                    ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonRocket3);
                    ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonRocket4);
                    ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonRocket5);

                    @Override
                    public void onTick(long millisUntilFinished) {                          // alle x milisekunden Scheibe vergrößern.
                        iScalerTarget5 = iScalerTarget5 + scalingAmount;
                        iBR5.setScaleX(iScalerTarget5);
                        iBR5.setScaleY(iScalerTarget5);
                        scalingTarget5Started = true;
                    }

                    @Override
                    public void onFinish() {                                                //Wenn Scheibe maximal skaliert hat.
                        lifePoints = lifePoints - 1;                                        //Lebenspunkt abziehen
                        uebergabeLifepoints = String.valueOf(lifePoints);
                        currentLifepoints.setText(uebergabeLifepoints);
                        iScalerTarget5 = 1;

                        iBR5.setX(5000F);                                                    //Scheibe aus Spielfeld bewegen
                        iBR5.setY(5000F);

                        ausblenden5 = new CountDownTimer((long) (500 + (Math.random() * 1500)), 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                ButtonPlacement werteRandomen = new ButtonPlacement();
                                iBR5.setX(werteRandomen.getRandomZahlX());
                                iBR5.setY(werteRandomen.getRandomZahlY());
                                iBR5.setScaleX((float) 1);
                                iBR5.setScaleY((float) 1);
                                if (lifePoints > 0) {
                                    target5ScalingInitiative.start();
                                } else {
                                    iBR1.setX(5000);
                                    iBR1.setY(5000);
                                    iBR2.setX(5000);
                                    iBR2.setY(5000);
                                    iBR3.setX(5000);
                                    iBR3.setY(5000);
                                    iBR4.setX(5000);
                                    iBR4.setY(5000);
                                    iBR5.setX(5000);
                                    iBR5.setY(5000);
                                    difficulty.cancel();
                                    difficulty.onFinish();
                                }
                            }
                        };
                        ausblenden5.start();
                    }
                };
                if (target5FirstSpawn == true) {
                    target5ScalingInitiative.start();
                    target5FirstSpawn = false;
                }
            }
        }, (long) (84500));
//################## letzter Button zu Ende ########################################################

    }

//################### Ende OnCreate ###### Start OnClick ###########################################

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v)
    {
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
        TextView currentScore = (TextView) findViewById(R.id.scoreTV);

        //######################################## Sound section ###################################
        MediaPlayer gunClip = MediaPlayer.create(getApplicationContext(), R.raw.gun_clip);
        MediaPlayer gunEmpty = MediaPlayer.create(getApplicationContext(), R.raw.gun_empty);
        MediaPlayer gunShot = MediaPlayer.create(getApplicationContext(), R.raw.gun_shot2);
        //##########################################################################################


        switch (v.getId()) {
                case R.id.buttonBackToMainScreen2:                                                  //zurück Button
                    finish();
                    break;

                case R.id.imageButtonRocket1:                                                        //Zielscheibe
                    if(amuValue >0)
                    {
                        gunShot.start();
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
                        gunEmpty.start();
                        break;
                    }

                case R.id.imageButtonRocket2:
                    if(amuValue >0)
                    {
                        gunShot.start();
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
                        gunEmpty.start();
                        break;
                    }

                case R.id.imageButtonRocket3:
                    if(amuValue >0)
                    {
                        gunShot.start();
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);

                        lifePoints++;
                        target3ScalingInitiative.cancel();
                        target3ScalingInitiative.onFinish();
                        scalingTarget3Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Punktzahl: " + uebergabeScore);
                        break;
                    }
                    else{
                        gunEmpty.start();
                        break;
                    }

                case R.id.imageButtonRocket4:
                    if(amuValue >0)
                    {
                        gunShot.start();
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);

                        lifePoints++;
                        target4ScalingInitiative.cancel();
                        target4ScalingInitiative.onFinish();
                        scalingTarget4Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Punktzahl: " + uebergabeScore);
                        break;
                    }
                    else{
                        gunEmpty.start();
                        break;
                    }

                case R.id.imageButtonRocket5:
                    if(amuValue >0)
                    {
                        gunShot.start();
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);

                        lifePoints++;
                        target5ScalingInitiative.cancel();
                        target5ScalingInitiative.onFinish();
                        scalingTarget5Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Punktzahl: " + uebergabeScore);
                        break;
                    }
                    else{
                        gunEmpty.start();
                        break;
                    }

                case R.id.reloadButton:                                                             //Nachladen
                    ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);
                    blockShots.setX(0);
                    blockShots.setY(80);

                    CountDownTimer reloadCountDown = new CountDownTimer(1500,5){
                        int i = 0;
                        @Override
                        public void onTick(long milliSekunden){                                     //Progress Bar beim Nachladen füllen
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);
                            i++;
                            reloadBar.setProgress((int)i*100/(1500/15));

                        }

                        @Override
                        public void onFinish() {                                                    //Nachladevorgang
                            TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);

                            amuValue = 20;
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
                        gunShot.start();
                        amuValue = amuValue - 1;
                        uebergabeString = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeString);
                        break;
                    }
                    else{
                        gunEmpty.start();
                        break;
                    }

                case R.id.buttonBlockShots:                                                         //Schüsse während nachladen blockieren
                    gunClip.start();
                    break;

            }
    }
}