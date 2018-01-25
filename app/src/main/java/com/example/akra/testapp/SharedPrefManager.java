package com.example.akra.testapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akra on 16.01.2018.
 */

public class SharedPrefManager
{

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_ID = "accountID";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_GIVENNAME = "givenname";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SCORE_ID = "scoreID";
    private static final String KEY_ACCOUNTNAMEHIGHSCORE = "accountNameHighscore";
    private static final String KEY_SCORE = "score";
    private static final String KEY_OVERALL_HIGHSCORE = "score2";
    private static final String KEY_ACCNAMEOVERALLHC = "accountNameHighscore2";


    private SharedPrefManager(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int accountID, String surname, String givenname, String username)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, accountID);
        editor.putString(KEY_SURNAME, surname);
        editor.putString(KEY_GIVENNAME, givenname);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null)
        {
            return true;
        }
        return false;
    }

    public boolean logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public boolean highscoreToSharedPrefMan(int scoreID, String accountNameHighscore, String score)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_SCORE_ID, scoreID);
        editor.putString(KEY_ACCOUNTNAMEHIGHSCORE, accountNameHighscore);
        editor.putString(KEY_SCORE, score);
        editor.apply();
        return true;
    }

    public String getHighscore()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SCORE, null);
    }


    public boolean overallHighscoreToSharedPrefMan(String accountNameHighscore2, String score2)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ACCNAMEOVERALLHC, accountNameHighscore2);
        editor.putString(KEY_OVERALL_HIGHSCORE, score2);
        editor.apply();
        return true;
    }

    public String getOverallHighscore()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_OVERALL_HIGHSCORE, null);
    }

    public String getOverallHCName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCNAMEOVERALLHC, null);
    }
}