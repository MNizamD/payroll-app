package com.nizam.payroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {
    public static final String LOGIN_CREDENTIAL = "LOGIN_CREDENTIAL";
    public static final String IS_LOGGED = "IS_LOGGED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        SharedPreferences.Editor editor = getSharedPreferences(LOGIN_CREDENTIAL, MODE_PRIVATE).edit();
        editor.putBoolean(IS_LOGGED, true);
        editor.apply();

        // Direct to Main Activity
        passToSplash();

    }
    private void passToSplash(){
        new Thread(()->{
            SharedPreferences.Editor editor = getSharedPreferences(Settings.SETTING_PREF_KEY, MODE_PRIVATE).edit();
            editor.putBoolean(Settings.TAB_SWIPE_PREF_KEY, false);
            editor.putBoolean(Settings.PUSH_NOTIF_PREF_KEY, false);
            editor.putInt(Settings.THEME_PREF_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            editor.apply();

            Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
            finish();

        }).start();
    }
}