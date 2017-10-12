package com.example.akra.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import java.util.Random;

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
        AbsoluteLayout.LayoutParams absParams =
                (AbsoluteLayout.LayoutParams) btms2.getLayoutParams();


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;


        Random r = new Random();

        absParams.x =  r.nextInt(width ) ;
        absParams.y =  r.nextInt(height );
        buttonTarget.setLayoutParams(absParams);
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonBackToMainScreen2:
                startActivity(new Intent(ActivityGameplayTest.this, MainActivity.class));
                finish();
        }
    }
}
