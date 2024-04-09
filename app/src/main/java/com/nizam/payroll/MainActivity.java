package com.nizam.payroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nizam.payroll.adapters.ViewPagerAdapter;
import com.nizam.payroll.backend.RequestAPI;
import com.nizam.payroll.fragments.AttendanceFragment;
import com.nizam.payroll.fragments.HomeFragment;
import com.nizam.payroll.fragments.LeaveFragment;
import com.nizam.payroll.fragments.SalaryFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public ViewPager2 tabViewPager;
    ArrayList<Fragment> fragmentArrayList;
    TextView mainHeader;
    ImageButton setting;
    private final String CURRENT_FRAGMENT_KEY = "CURRENT_FRAGMENT";
    SharedPreferences prefs;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_FRAGMENT_KEY, tabViewPager.getCurrentItem());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Settings.mainActivity = this;
        initComp();
        if (savedInstanceState != null) {
            tabViewPager.setCurrentItem(savedInstanceState.getInt(CURRENT_FRAGMENT_KEY));
        }
        initListeners();
        new RequestAPI(this).testRF();
        initSettingPref();
    }
    private void initSettingPref(){
        SharedPreferences prefs = getSharedPreferences(Settings.SETTING_PREF_KEY, MODE_PRIVATE);
        tabViewPager.setUserInputEnabled(prefs.getBoolean(Settings.TAB_SWIPE_PREF_KEY,false));
    }

    private void initComp(){

        bottomNavigationView = findViewById(R.id.bottom_nav);
        mainHeader = findViewById(R.id.main_header);
        setting = findViewById(R.id.open_setting);

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new AttendanceFragment());
        fragmentArrayList.add(new SalaryFragment());
        fragmentArrayList.add(new LeaveFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,fragmentArrayList);
        tabViewPager = findViewById(R.id.tabViewPager);
        tabViewPager.setAdapter(viewPagerAdapter);
        tabViewPager.setOffscreenPageLimit(2); // Optimization

    }
    private void initListeners(){

        // Open setting
        setting.setOnClickListener(view -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });

        // Switch tabs through NavigationBar
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_dashboard){
//                replaceFragment(dashboard_fragment, "Dashboard", 0);
                replaceFragment("Dashboard", 0);
            } else if (item.getItemId() == R.id.bottom_attendance) {
//                replaceFragment(salary_fragment,"Salary",1);
                replaceFragment("Attendance",1);
            } else if (item.getItemId() == R.id.bottom_salary) {
//                replaceFragment(attendance_fragment,"Attendance",2);
                replaceFragment("Salary",2);
            } else if (item.getItemId() == R.id.bottom_leave) {
//                replaceFragment(leave_fragment,"Leave",2);
                replaceFragment("Leave",3);
            }
            return true;
        });

        // Switch tab through ViewPager
//        Settings.tabViewPager = tabViewPager; // Setting's reference to Main's View Pager
        tabViewPager.setUserInputEnabled(false);
        tabViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    bottomNavigationView.setSelectedItemId(R.id.bottom_dashboard);
                } else if (position == 1) {
                    bottomNavigationView.setSelectedItemId(R.id.bottom_attendance);
                } else if (position == 2) {
                    bottomNavigationView.setSelectedItemId(R.id.bottom_salary);
                } else if (position == 3) {
                    bottomNavigationView.setSelectedItemId(R.id.bottom_leave);
                }
                super.onPageSelected(position);
            }
        });
    }

    private void replaceFragment(String header, int index){
        tabViewPager.setCurrentItem(index);
        mainHeader.setText(header);
    }

    public static void showToast(String message, Context context){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(()->{
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        });
    }
    public void setAppTheme(@NonNull int nightMode, Settings settings) {
        SharedPreferences.Editor editor = getSharedPreferences(Settings.SETTING_PREF_KEY, MODE_PRIVATE).edit();
        editor.putInt(Settings.THEME_PREF_KEY,nightMode).apply();
        new Handler(Looper.getMainLooper()).postDelayed(()-> {
//            this.recreate();
            AppCompatDelegate.setDefaultNightMode(nightMode);
            settings.recreate();
        },1000);
    }
}