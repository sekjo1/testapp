package com.example.akra.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by akra on 21.09.2017.
 */





public class ActivityStartGame extends AppCompatActivity implements View.OnClickListener
{

    Intent backBttnActive;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        //Toast.makeText(this, "8===>", Toast.LENGTH_LONG).show();

        Button btms = (Button)findViewById(R.id.buttonBackToMainScreen);

        btms.setOnClickListener(this);

        backBttnActive = new Intent(ActivityStartGame.this, MainActivity.class);

    }



    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonBackToMainScreen:
                startActivity(backBttnActive);
                finish();
                break;

        }
    }

}