package com.nizam.payroll;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.nizam.payroll.tools.MyDialog;

public class Settings extends AppCompatActivity {

    ImageButton backButton;
    MaterialSwitch switchPushNotif, switchTabSwipe;
    LinearLayout selectThemeOption;
    TextView currentThemeText;
    static MainActivity mainActivity;
//    static ViewPager2 tabViewPager;
    static final String SETTING_PREF_KEY = "SETTING_PREF_KEY";
    static final String TAB_SWIPE_PREF_KEY = "TAB_SWIPE_PREF_KEY";
    static final String PUSH_NOTIF_PREF_KEY = "PUSH_NOTIF_PREF_KEY";
    static final String THEME_PREF_KEY = "THEME_PREF_KEY";
    int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComp();
        initListeners();
        initSettingPref();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(SETTING_PREF_KEY, MODE_PRIVATE).edit();
        editor.putBoolean(TAB_SWIPE_PREF_KEY, switchTabSwipe.isChecked());
        editor.putBoolean(PUSH_NOTIF_PREF_KEY, switchPushNotif.isChecked());
        editor.apply();
    }

    private void initSettingPref(){
        SharedPreferences prefs = getSharedPreferences(SETTING_PREF_KEY, MODE_PRIVATE);
        switchPushNotif.setChecked(prefs.getBoolean(PUSH_NOTIF_PREF_KEY,false));
        switchTabSwipe.setChecked(prefs.getBoolean(TAB_SWIPE_PREF_KEY,false));

        theme = prefs.getInt(THEME_PREF_KEY,AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (theme == AppCompatDelegate.MODE_NIGHT_NO){
            currentThemeText.setText("Light");
        } else if (theme == AppCompatDelegate.MODE_NIGHT_YES){
            currentThemeText.setText("Dark");
        } else if (theme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM){
            currentThemeText.setText("System");
        }
    }
    private void initComp(){
        backButton = findViewById(R.id.close_setting);
        switchPushNotif = findViewById(R.id.switchPushNotif);
        switchTabSwipe = findViewById(R.id.switchTabSwipe);
        selectThemeOption = findViewById(R.id.selectThemeOption);
        currentThemeText = findViewById(R.id.currentThemeText);
    }
    private void initListeners(){
        selectThemeOption.setOnClickListener((view)->{selectTheme(view);});
        backButton.setOnClickListener(view -> {
            finish();
        });
        switchTabSwipe.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                mainActivity.tabViewPager.setUserInputEnabled(true);
            } else {
                mainActivity.tabViewPager.setUserInputEnabled(false);
            }
        });
    }

    public void togglePushNotif(View view){
        switchPushNotif.setChecked(!switchPushNotif.isChecked());
    }

    public void toggleTabSwipe(View view){
        switchTabSwipe.setChecked(!switchTabSwipe.isChecked());
    }

    public void logOut(View view){
        MyDialog confirmLogout = new MyDialog(this,"Logout?");
        confirmLogout.setMessage("Are you sure you want to logout?");
        confirmLogout.setConfirmOnclick(true,()->{
            SharedPreferences.Editor editLog = getSharedPreferences(Login.LOGIN_CREDENTIAL, MODE_PRIVATE).edit();
            editLog.putBoolean(Login.IS_LOGGED, false);
            editLog.apply();

            SharedPreferences.Editor editSet = getSharedPreferences(Login.LOGIN_CREDENTIAL, MODE_PRIVATE).edit();
            editSet.clear(); // Clears all data in the SharedPreferences
            editSet.commit(); // Applies the changes synchronously

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            mainActivity.finish();
            finish();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Restrore Light Mode
        });
        confirmLogout.show();
    }
    boolean isThemeSetting = false;
    public void selectTheme(View view) {
        MyDialog selectTheme = new MyDialog(this,"Select Theme");
        selectTheme.setBody(R.layout.select_theme);
        RadioGroup themeSelectRadioGroup = selectTheme.findViewById(R.id.themeSelectRadioGroup);
        RadioButton chosenBtn = null;
        if (theme == AppCompatDelegate.MODE_NIGHT_NO){
            chosenBtn = themeSelectRadioGroup.findViewById(R.id.lightMode);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_YES){
            chosenBtn = themeSelectRadioGroup.findViewById(R.id.darkMode);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM){
            chosenBtn = themeSelectRadioGroup.findViewById(R.id.systemMode);
        }
        chosenBtn.setChecked(true);

        selectTheme.setConfirmOnclick(true, ()->{
            isThemeSetting = true;
            int SELECTED_RB = themeSelectRadioGroup.getCheckedRadioButtonId();
            new Thread(()->{
                if (SELECTED_RB == R.id.lightMode){
                    mainActivity.setAppTheme(AppCompatDelegate.MODE_NIGHT_NO,this);
                } else if (SELECTED_RB == R.id.darkMode){
                    mainActivity.setAppTheme(AppCompatDelegate.MODE_NIGHT_YES,this);
                } else if (SELECTED_RB == R.id.systemMode){
                    mainActivity.setAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,this);
                }
            }).start();
        });
        selectTheme.show();
    }

    @Override
    public void onBackPressed() {
        if(!isThemeSetting){
            super.onBackPressed();
        } else {
//            MainActivity.showToast("Surpressed",this);
        }
    }
}