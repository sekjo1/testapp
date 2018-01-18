package com.example.akra.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by akra on 14.12.2017.
 */

public class UpgradeInterfaceActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper myDb;
    EditText editTyp, editStufe, editInfotext;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityupgradeinterface);
        myDb = new DatabaseHelper(this);

        editTyp = (EditText) findViewById(R.id.editTextTyp);
        editStufe = (EditText) findViewById(R.id.editTextStufe);
        editInfotext = (EditText) findViewById(R.id.editTextInfotext);
        btnAddData = (Button) findViewById(R.id.buttonAddData);

    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editTyp.getText().toString(),
                                        editStufe.getText().toString(),
                                        editInfotext.getText().toString());
                        if(isInserted == true){
                            Toast.makeText(UpgradeInterfaceActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(UpgradeInterfaceActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {

    }
}
