package com.example.akra.testapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity
{
    private EditText edTeReSurname, edTeReGivenname, edTeReUsername, edTeRePassword;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        edTeReSurname = (EditText) findViewById(R.id.editTextRegisterSurname);
        edTeReGivenname = (EditText) findViewById(R.id.editTextRegisterGivenname);
        edTeReUsername = (EditText) findViewById(R.id.editTextRegisterUsername);
        edTeRePassword = (EditText) findViewById(R.id.editTextRegisterPassword);

        progressDialog = new ProgressDialog(this);
    }

    private void registerUser()
    {
        final String surname = edTeReSurname.getText().toString().trim();
        final String givenname = edTeReGivenname.getText().toString().trim();
        final String username = edTeReUsername.getText().toString().trim();
        final String password = edTeRePassword.getText().toString().trim();

        if (TextUtils.isEmpty(surname))
        {
            edTeReSurname.setError("Nachnamen eingeben!");
        }
        else if(TextUtils.isEmpty(givenname))
        {
            edTeReGivenname.setError("Vornamen eingeben!");
        }
        else if (TextUtils.isEmpty(username))
        {
            edTeReUsername.setError("Nutzernamen eingeben!");
        }
        else if (TextUtils.isEmpty(password))
        {
            edTeRePassword.setError("Passwort eingeben!");
        }
        else
        {
            progressDialog.setMessage("Nutzer registrieren...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("surname", surname);
                    params.put("givenname", givenname);
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    public void rRegister(View view)
    {
        registerUser();
    }
}
