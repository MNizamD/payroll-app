package com.nizam.payroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class SplashScreen extends AppCompatActivity {
    LinearProgressIndicator progressBar;
    final String IS_THEME_CHANGED = "IS_THEME_CHANGED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences logData = getSharedPreferences(Login.LOGIN_CREDENTIAL, MODE_PRIVATE);
        boolean isLogged = logData.getBoolean(Login.IS_LOGGED,false);
//        MainActivity.showToast(isLogged+"",this);
        if (isLogged){
            if (savedInstanceState == null){
                new Handler().postDelayed(()->{
                    SharedPreferences setPrefs = getSharedPreferences(Settings.SETTING_PREF_KEY, MODE_PRIVATE);
                    AppCompatDelegate.setDefaultNightMode(setPrefs.getInt(Settings.THEME_PREF_KEY,AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM));
                    recreate();
                },1000);
            } else {
                new Handler().postDelayed(()->{
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                },1000);
            }
        } else { // Not Logged in
            if (savedInstanceState == null){
                new Handler().postDelayed(()->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                },1000);
            } else {
                new Handler().postDelayed(()->{
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                    finish();
                },1000);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_THEME_CHANGED, true);
    }
}