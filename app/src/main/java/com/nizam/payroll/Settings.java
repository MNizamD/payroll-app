package com.nizam.payroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class Settings extends AppCompatActivity {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComp();
        initListeners();
    }
    private void initComp(){
        backButton = findViewById(R.id.close_setting);
    }
    private void initListeners(){
        backButton.setOnClickListener(view -> {
            finish();
        });
    }
}