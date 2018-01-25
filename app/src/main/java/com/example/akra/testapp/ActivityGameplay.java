package com.example.akra.testapp;

import android.content.Intent;
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
 * Created by akra on 22.09.2017.
 */

public class ActivityGameplay extends AppCompatActivity implements View.OnClickListener
{

    int amuValue = 15;                                                                              //Munitionsmenge die am Start des Spiels ohne Nachladen zur Verfügung steht
    int lifePoints = 5;                                                                             //Lebenspunkte die dem Spieler zur Verfügung stehen.
    String uebergabeMunition;                                                                       //Wird verwendet um den Int Wert der Munition die aktuell zur Verfügung steht der Anzeige zu übergeben
    String uebergabeLifepoints;                                                                     //Wird verwendet um den Int Wert der restelichen Lebenspunkte der Anzeige zu übergeben.
    String uebergabeScore;                                                                          //Wird verwendet um den long-Wert der aktuellen Punktzahl der Anzeige zu übergeben.
    String uebergabeStufe;                                                                          //Wird verwendet um den Int-Wert der aktuellen Stufe der Anzeige zu übergeben.
    CountDownTimer target1ScalingInitiative;                                                        //Countdowntimer für die Steuerung für Zielscheiben
    CountDownTimer target2ScalingInitiative;
    CountDownTimer target3ScalingInitiative;
    CountDownTimer target4ScalingInitiative;
    CountDownTimer target5ScalingInitiative;
    CountDownTimer difficulty;                                                                      //Steuert den Anstieg der Stufe und die Erhöhung der Punktzahl die pro getroffener Scheibe angerechnet wird.
    CountDownTimer ausblenden1;                                                                     //Diese Countdowntimer werden verwendet um nachdem eine Scheibe gedrückt wurde oder die Lebenszeit abgelaufen ist die Wiederkehrdauer zufällig zu gestalten.
    CountDownTimer ausblenden2;
    CountDownTimer ausblenden3;
    CountDownTimer ausblenden4;
    CountDownTimer ausblenden5;
    long score = 0;                                                                                 //Punktzahl
    double scoreGain = 1;                                                                           //Punktzahl die man für das treffen einer Scheibe erhält, erhöht sich durch difficulty CountDownTimer
    long scalingSpeed = 5250;                                                                       //Definiert die Dauer, die man für das Drücken einer Scheibe hat.
    long scalingSteps = 25;                                                                         //Definiert die Dauer die zwischen den CountDownTimer-ticks der Scalierungsmethoden liegt.
    float scalingAmount = 0.008F;                                                                   //Definiert um wieviel sich die Zielscheibe pro Tick vergrößert.
    boolean scalingTarget1Started = false;                                                          //Überbleibsel aus älterer Version. Sollte keinen Effekt mehr haben.
    boolean scalingTarget2Started = false;
    boolean scalingTarget3Started = false;
    boolean scalingTarget4Started = false;
    boolean scalingTarget5Started = false;
    boolean target1FirstSpawn = true;                                                               //Wird benötigt um das erste Spawnen der Scheiben zu initialisieren. Ohne die If-funktion in der diese Vorkommt, hätte man springede Zielscheiben.
    boolean target2FirstSpawn = true;
    boolean target3FirstSpawn = true;
    boolean target4FirstSpawn = true;
    boolean target5FirstSpawn = true;
    boolean gameStillRunning = true;
    float iScalerTarget1 = 1F;                                                                      //Standart skalierungsgrad der Zielscheiben
    float iScalerTarget2 = 1F;
    float iScalerTarget3 = 1F;
    float iScalerTarget4 = 1F;
    float iScalerTarget5 = 1F;
    int schwierigStufe = 0;                                                                         //Schwierigkeitsstufe
    int onlyOneHighscore = 0;
    final String user = SharedPrefManager.getInstance(this).getUsername();
    Intent restartGame;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygameplaytest);

        restartGame = new Intent(ActivityGameplay.this, ActivityGameplay.class);
                                                                                                    //Buttons initialisieren
        Button btms2 = (Button) findViewById(R.id.buttonBackToMainScreen2);                         //Zurück button
        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);                     //Zielscheiben
        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);
        ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);
        ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);
        ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reloadButton);                   //Nachladebutton
        ImageButton backgroundButton = (ImageButton) findViewById(R.id.buttonBackground);           //Button der hinter den Zielscheiben liegt und der dafür sorgt das Munition abgezogen wird, wenn daneben gedrückt wird.
        ImageButton blockShots = (ImageButton) findViewById(R.id.buttonBlockShots);                 //wird wärend des Nachladens über die Zielscheiben gelegt und verhindert so, dass während des Nachladevorgangs geschossen werden kann.
        final ImageButton scoreSenden = (ImageButton) findViewById(R.id.imageButtonScoreUebergeben);
        final ImageButton neustart = (ImageButton)findViewById(R.id.imageButtonNeustart);
        // ImageButton buttonStartRound = (ImageButton) findViewById(R.id.imageButtonStartRound);    //wird nicht mehr verwendet. (Da das Spawnen neuer Scheiben von handlern geregelt wird und damit so oder so das Spiel beginnt.

        ImageView backgroundImage = (ImageView) findViewById(R.id.imageViewBackgroundPicture);      //Hintergrundbild
        ImageView levelBackground = (ImageView) findViewById(R.id.imageViewLevelBackground);        //Bild das Rahmen für die aktuelle Schwierigkeitsstufe darstellt.
        ImageView backgroundHeart = (ImageView) findViewById(R.id.imageViewHeart);                  //Bild das hinter dem Lebenspunktezähler liegt.
        final ImageView bannerScore = (ImageView) findViewById(R.id.imageViewScoreBackground);
        final ImageView bannerEndScore = (ImageView) findViewById(R.id.imageViewScoreBackgroundEndscreen);

        final TextView currentLifepoints = (TextView) findViewById(R.id.lifePointsTV);              //Textanzeige für die Lebenspunkte
        TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);                   //Textanzeige für Munition
        final TextView currentScore = (TextView) findViewById(R.id.scoreTV);                        //Textanzeige für aktuelle Punktzahl
        final TextView currentLevel = (TextView) findViewById(R.id.textViewStufe);                  //Textanzeige für aktuelle Schwierigkeitsstufe
        final TextView sendScoreTextView = (TextView) findViewById((R.id.textViewSendScore));
        final TextView neustartTextView = (TextView) findViewById(R.id.textViewNeustart);
        final TextView endscoreTextView = (TextView) findViewById(R.id.scoreTVEndscreen);

        iBR1.setBackground(null);                                                                   //entfernen des grauen Hintergrunds der Zielscheiben-Imagebuttons und aller anderen buttons
        iBR2.setBackground(null);
        iBR3.setBackground(null);
        iBR4.setBackground(null);
        iBR5.setBackground(null);
        reloadButton.setBackground(null);
        backgroundButton.setBackground(null);
        blockShots.setBackground(null);
        scoreSenden.setBackground(null);
        neustart.setBackground(null);
       // buttonStartRound.setBackground(null);

        btms2.setOnClickListener(this);                                                             //Clicklistener für alle Buttons
        iBR1.setOnClickListener(this);
        iBR2.setOnClickListener(this);
        iBR3.setOnClickListener(this);
        iBR4.setOnClickListener(this);
        iBR5.setOnClickListener(this);
        backgroundButton.setOnClickListener(this);
        blockShots.setOnClickListener(this);
        reloadButton.setOnClickListener(this);
        scoreSenden.setOnClickListener(this);
        neustart.setOnClickListener(this);
       // buttonStartRound.setOnClickListener(this);

        uebergabeMunition = String.valueOf(amuValue);                                               //uebergeben der eingestellten Munitionsmenge an den String
        actualAmmunition.setText(uebergabeMunition);

        backgroundImage.setScaleX(1.15F);                                                           //vergrößern des Hintergrundbilds um die komplette Fläche abzudecken.
        backgroundImage.setScaleY(1.15F);

        iBR1.setX(5000);                                                                            //Erstmaliges positionieren der Zielscheiben (ausserhalb des Spielbaren bereichs)
        iBR1.setY(5000);
        iBR2.setX(5000);
        iBR2.setY(5000);
        iBR3.setX(5000);
        iBR3.setY(5000);
        iBR4.setX(5000);
        iBR4.setY(5000);
        iBR5.setX(5000);
        iBR5.setY(5000);

        blockShots.setX(5000F);                                                                     //Schussblockierer vom Spielfeld weg setzen. Wird später für die Nachladedauer ins Spielfeldgesetzt und danach wieder auf diese Position gesetzt.
        blockShots.setY(5000F);

        bannerEndScore.setVisibility(View.GONE);
        endscoreTextView.setVisibility(View.GONE);
        neustart.setVisibility(View.GONE);
        neustart.setEnabled(false);
        neustartTextView.setVisibility(View.GONE);
        neustartTextView.setEnabled(false);
        scoreSenden.setVisibility(View.GONE);
        scoreSenden.setEnabled(false);
        sendScoreTextView.setVisibility(View.GONE);
        sendScoreTextView.setEnabled(false);

        //###################################### Handler section ###################################
        final Handler handler1 = new Handler();
        final Handler handler2 = new Handler();
        final Handler handler3 = new Handler();
        final Handler handler4 = new Handler();
        final Handler handler5 = new Handler();
        //##########################################################################################



        difficulty = new CountDownTimer(86400000, 10000) {                //Schwerigkeitslevel der sich über die Zeit erhöht (Dazu ist zu sagen, das der erste Tick des Countdowntimers nicht erst nach x milisekunden auftritt, sondern schon beim starten des Countdowntimers mit name.start()

            ButtonPlacement werteRandomen = new ButtonPlacement();

            @Override
            public void onTick(long millisUntilFinished) {
                scoreGain = scoreGain * 1.25;                                                       //Erhöhung der Punktzahl die pro getroffener Scheibe erhalten wird.
                schwierigStufe++;                                                                   //Erhöhund der Schwierigkeitsstufe
                uebergabeStufe = String.valueOf(schwierigStufe);
                currentLevel.setText("Stufe: " + uebergabeStufe);

                if (scalingSpeed > 1500) {                                                          //erhöhung der Geschwindigkeit Aktuell nicht verwendbar, da zum bugfixen die Struktur der Programms geändert wurde und die CDT für die Zielscheiben nur einmal erzeugt werden. Somit wird der verringerte Wert, der normalerweise als neuer millisInFuture limit übergeben wird nie real verwendet.
                    scalingSpeed = scalingSpeed - 200;
                    scalingSteps = scalingSpeed / 200;
                }
            }

            @Override
            public void onFinish() {                                                                //wird angezeigt wenn 24 Stunnden um oder alle Lebenspunkte verloren sind.
                onlyOneHighscore++;
                if(onlyOneHighscore < 2)
                {
                    bannerScore.setX(5000);
                    bannerScore.setY(5000);
                    currentScore.setX(5000);
                    currentScore.setY(5000);

                    if(score >0)
                    {
                        endscoreTextView.setText("Score: " + uebergabeScore);
                    }
                    else
                    {
                        endscoreTextView.setText("Score: 0");
                    }
                    bannerEndScore.setVisibility(View.VISIBLE);
                    endscoreTextView.setVisibility(View.VISIBLE);

                    neustart.setVisibility(View.VISIBLE);
                    neustart.setEnabled(true);
                    neustartTextView.setVisibility(View.VISIBLE);
                    neustartTextView.setEnabled(true);
                    scoreSenden.setVisibility(View.VISIBLE);
                    scoreSenden.setEnabled(true);
                    sendScoreTextView.setVisibility(View.VISIBLE);
                    sendScoreTextView.setEnabled(true);
                    gameStillRunning = false;
                }
            }
        };
//################## Target 1 ######################################################################
        handler1.postDelayed(new Runnable() {                                                       //Der handler bestimmt wann die Scheibe erstmalig in das Spielfeld verschoben und der Countdowntimer der Zielscheibe ebenfalls erstmalig gestartet wird.
            public void run() {
                ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);

                difficulty.start();                                                                 //starten des CountDownTimer difficulty.

                if (scalingTarget1Started == true) {                                                //Altes Sicherheitselement das vor Abstürzen gesichert hat. Sollte aktuell keine Auswirkung haben.
                    target1ScalingInitiative.cancel();
                    target1ScalingInitiative.start();
                }

                ButtonPlacement werteRandomen = new ButtonPlacement();                              //Zielscheibe wird an zufällige Position im Spielbereich verschoben.
                iBR1.setX(werteRandomen.getRandomZahlX());
                iBR1.setY(werteRandomen.getRandomZahlY());

                target1ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {         //CountDownTimer für das Scaling wird initialisiert.
                    ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);
                    ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);
                    ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);
                    ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);
                    ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);

                    @Override
                    public void onTick(long millisUntilFinished) {                                  // alle x milisekunden Scheibe vergrößern.
                        iScalerTarget1 = iScalerTarget1 + scalingAmount;
                        iBR1.setScaleX(iScalerTarget1);
                        iBR1.setScaleY(iScalerTarget1);
                        scalingTarget1Started = true;
                    }

                    @Override
                    public void onFinish() {                                                        //Wenn Scheibe maximal skaliert hat.
                        lifePoints = lifePoints - 1;                                                //Lebenspunkt abziehen
                        uebergabeLifepoints = String.valueOf(lifePoints);
                        currentLifepoints.setText(uebergabeLifepoints);
                        iScalerTarget1 = 1;                                                         //Übergabeparameter für die größe der Scheibe wieder auf Ausgangsposition zurück setzen.

                        iBR1.setX(5000F);                                                           //Scheibe aus Spielfeld bewegen
                        iBR1.setY(5000F);

                        ausblenden1 = new CountDownTimer((long) (500 + (Math.random() * 1500)), 1500) {             //wird nach mindestens 500 und maximal 2000 milisekunden gestartet
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                ButtonPlacement werteRandomen = new ButtonPlacement();              //nach den maximal 2000 milisekunden wird die Zielscheibe erneut in das Spielfeld verschoben
                                iBR1.setX(werteRandomen.getRandomZahlX());
                                iBR1.setY(werteRandomen.getRandomZahlY());
                                iBR1.setScaleX((float) 1);                                          //Größe der Scheibe auf 1 zurück setzen.
                                iBR1.setScaleY((float) 1);
                                if (lifePoints > 0) {
                                    target1ScalingInitiative.start();                               //wenn noch Lebenspunkte vorhanden sind, wird der CountDownTimer zum skalieren der Scheibe neu gestartet.
                                } else {
                                    iBR1.setX(5000);                                                //wenn nicht, werden alle Zielscheiben aus dem Spielfeld entfernt und die difficulty beendet.
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
                        ausblenden1.start();                                                        //starten der CountDownTimers ausblenden
                    }
                };
                if (target1FirstSpawn == true) {                                                    //beim ersten Start der Zielscheibe wird sie darüber gestartet, später über den Aufruf onFinish() beim drücken der Zielscheibe oder beim auslaufen der Scheibe. Dadurch wird ausblenden gestartet, was die Scheibe neu startet.
                    target1ScalingInitiative.start();
                    target1FirstSpawn = false;
                }
            }
        }, (long) (4000));                                                                          //Wartezeit für den Handler
//################## Target 2 ######################################################################
        handler2.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);

                if (scalingTarget2Started == true) {
                    target2ScalingInitiative.cancel();
                    target2ScalingInitiative.start();
                }

                if (gameStillRunning) {
                    ButtonPlacement werteRandomen = new ButtonPlacement();
                    iBR2.setX(werteRandomen.getRandomZahlX());
                    iBR2.setY(werteRandomen.getRandomZahlY());


                    target2ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);
                        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);
                        ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);
                        ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);
                        ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);

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
            }
        }, (long) (24500));

//################## Target 3 ######################################################################
        handler3.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);

                if (scalingTarget3Started == true) {
                    target3ScalingInitiative.cancel();
                    target3ScalingInitiative.start();
                }

                if (gameStillRunning) {
                    ButtonPlacement werteRandomen = new ButtonPlacement();
                    iBR3.setX(werteRandomen.getRandomZahlX());
                    iBR3.setY(werteRandomen.getRandomZahlY());

                    target3ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);
                        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);
                        ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);
                        ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);
                        ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);

                        @Override
                        public void onTick(long millisUntilFinished) {
                            iScalerTarget3 = iScalerTarget3 + scalingAmount;
                            iBR3.setScaleX(iScalerTarget3);
                            iBR3.setScaleY(iScalerTarget3);
                            scalingTarget3Started = true;
                        }

                        @Override
                        public void onFinish() {
                            lifePoints = lifePoints - 1;
                            uebergabeLifepoints = String.valueOf(lifePoints);
                            currentLifepoints.setText(uebergabeLifepoints);
                            iScalerTarget3 = 1;

                            iBR3.setX(5000F);
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
            }
        }, (long) (44500));
//################## Target 4 ######################################################################
        handler4.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);

                if (scalingTarget4Started == true) {
                    target4ScalingInitiative.cancel();
                    target4ScalingInitiative.start();
                }

                if (gameStillRunning) {
                    ButtonPlacement werteRandomen = new ButtonPlacement();
                    iBR4.setX(werteRandomen.getRandomZahlX());
                    iBR4.setY(werteRandomen.getRandomZahlY());

                    target4ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);
                        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);
                        ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);
                        ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);
                        ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);

                        @Override
                        public void onTick(long millisUntilFinished) {
                            iScalerTarget4 = iScalerTarget4 + scalingAmount;
                            iBR4.setScaleX(iScalerTarget4);
                            iBR4.setScaleY(iScalerTarget4);
                            scalingTarget4Started = true;
                        }

                        @Override
                        public void onFinish() {
                            lifePoints = lifePoints - 1;
                            uebergabeLifepoints = String.valueOf(lifePoints);
                            currentLifepoints.setText(uebergabeLifepoints);
                            iScalerTarget4 = 1;

                            iBR4.setX(5000F);
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
            }
        }, (long) (64500));
//################## Target 5 ######################################################################
        handler5.postDelayed(new Runnable() {
            public void run() {
                ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);

                if (scalingTarget5Started == true) {
                    target5ScalingInitiative.cancel();
                    target5ScalingInitiative.start();
                }

                if (gameStillRunning) {
                    ButtonPlacement werteRandomen = new ButtonPlacement();
                    iBR5.setX(werteRandomen.getRandomZahlX());
                    iBR5.setY(werteRandomen.getRandomZahlY());

                    target5ScalingInitiative = new CountDownTimer(scalingSpeed, scalingSteps) {
                        ImageButton iBR1 = (ImageButton) findViewById(R.id.imageButtonTarget1);
                        ImageButton iBR2 = (ImageButton) findViewById(R.id.imageButtonTarget2);
                        ImageButton iBR3 = (ImageButton) findViewById(R.id.imageButtonTarget3);
                        ImageButton iBR4 = (ImageButton) findViewById(R.id.imageButtonTarget4);
                        ImageButton iBR5 = (ImageButton) findViewById(R.id.imageButtonTarget5);

                        @Override
                        public void onTick(long millisUntilFinished) {
                            iScalerTarget5 = iScalerTarget5 + scalingAmount;
                            iBR5.setScaleX(iScalerTarget5);
                            iBR5.setScaleY(iScalerTarget5);
                            scalingTarget5Started = true;
                        }

                        @Override
                        public void onFinish() {
                            lifePoints = lifePoints - 1;
                            uebergabeLifepoints = String.valueOf(lifePoints);
                            currentLifepoints.setText(uebergabeLifepoints);
                            iScalerTarget5 = 1;

                            iBR5.setX(5000F);
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

        switch (v.getId()) {
                case R.id.buttonBackToMainScreen2:                                                  //zurück Button
                    finish();
                    break;

                case R.id.imageButtonTarget1:                                                       //Zielscheiben Button
                    if(amuValue >0)                                                                 //Wenn man genug Munition hat
                    {
                        amuValue = amuValue - 1;                                                    //Munition wird abgezogen
                        uebergabeMunition = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeMunition);

                        lifePoints++;                                                               //hinzufügen eines Lebenspunkts, da durch den folgenden onFinish() aufruf immer ein Leben abgezogen wird.
                        target1ScalingInitiative.cancel();
                        target1ScalingInitiative.onFinish();
                        scalingTarget1Started = false;

                        score = Math.round((long) (score + scoreGain));                             //errechnen des neuen Punktestands
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Score: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.imageButtonTarget2:
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeMunition = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeMunition);

                        lifePoints++;
                        target2ScalingInitiative.cancel();
                        target2ScalingInitiative.onFinish();
                        scalingTarget2Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Score: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.imageButtonTarget3:
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeMunition = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeMunition);

                        lifePoints++;
                        target3ScalingInitiative.cancel();
                        target3ScalingInitiative.onFinish();
                        scalingTarget3Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Score: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.imageButtonTarget4:
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeMunition = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeMunition);

                        lifePoints++;
                        target4ScalingInitiative.cancel();
                        target4ScalingInitiative.onFinish();
                        scalingTarget4Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Score: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.imageButtonTarget5:
                    if(amuValue >0)
                    {
                        amuValue = amuValue - 1;
                        uebergabeMunition = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeMunition);

                        lifePoints++;
                        target5ScalingInitiative.cancel();
                        target5ScalingInitiative.onFinish();
                        scalingTarget5Started = false;

                        score = Math.round((long) (score + scoreGain));
                        uebergabeScore = String.valueOf(score);
                        currentScore.setText("Score: " + uebergabeScore);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.reloadButton:                                                             //Nachlade Button
                    ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);      //Der Blockierbutton wird über die Zielscheiben gelegt damit nicht während des Nachladens geschossen werden kann.
                    blockShots.setX(0);
                    blockShots.setY(80);

                    CountDownTimer reloadCountDown = new CountDownTimer(1500,5){ //Der CountDownTimer für das Nachladen wird gestartet
                        int i = 0;
                        @Override
                        public void onTick(long milliSekunden){                                     //Progress Bar wird beim Nachladen gefüllt. Kann etwas off wirken.
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);
                            i++;
                            reloadBar.setProgress((int)i*100/(1500/15));

                        }

                        @Override
                        public void onFinish() {                                                    //Nachladevorgang zu Ende
                            TextView actualAmmunition = (TextView) findViewById(R.id.textActualAmmu);
                            ProgressBar reloadBar = (ProgressBar)findViewById(R.id.progressBarReload);

                            amuValue = 15;                                                          //Munitionswert neu setzen.
                            uebergabeMunition = String.valueOf(amuValue);
                            actualAmmunition.setText(uebergabeMunition);
                            reloadBar.setProgress(0);                                               //Nachlade-Progressbar wieder zurücksetzen
                            i = 0;

                            ImageButton blockShots = (ImageButton)findViewById(R.id.buttonBlockShots);
                            blockShots.setX(5000);                                                  //Blockier-button wird wieder aus dem Spielfeld geschoben
                            blockShots.setY(5000);
                        }

                    };
                    reloadCountDown.start();                                                        //starten des CountDownTimers.

                    break;

                case R.id.buttonBackground:                                                         //Hintergrund Button der Schüsse die daneben gehen registriert.
                    if (amuValue > 0) {                                                             //Wenn Munition vorhanden ist
                        amuValue = amuValue - 1;                                                    //Munition wird abgezogen.
                        uebergabeMunition = String.valueOf(amuValue);
                        actualAmmunition.setText(uebergabeMunition);
                        break;
                    }
                    else{
                        break;
                    }

                case R.id.buttonBlockShots:                                                         //Schüsse während nachladen blockieren
                    break;

                case R.id.imageButtonScoreUebergeben:                                               //Die erreichte Punktzahl wird übergeben. Wenn der Eintrag geklappt hat wird der Button entfernt und ein Neustartbutton angezeigt
                    uebergabeScore = String.valueOf(score);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            Constants.URL_SENDSCORE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        final ImageButton scoreSenden = (ImageButton) findViewById(R.id.imageButtonScoreUebergeben);
                                        final TextView sendScoreTextView = (TextView) findViewById((R.id.textViewSendScore));
                                        scoreSenden.setEnabled(false);
                                        scoreSenden.setX(5000);
                                        scoreSenden.setY(5000);
                                        sendScoreTextView.setEnabled(false);
                                        sendScoreTextView.setX(5000);
                                        sendScoreTextView.setX(5000);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Nicht so hastig", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("score", uebergabeScore);
                            params.put("accountNameHighscore", user);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
                    break;

            case R.id.imageButtonNeustart:                                                          //wird nach dem erfolgreichen hochladen des erzielten Scores angezeigt. Startet die Activity neu
                finish();
                startActivity(restartGame);
                break;

            }
    }

}