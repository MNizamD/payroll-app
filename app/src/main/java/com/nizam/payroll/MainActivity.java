package com.nizam.payroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nizam.payroll.adapters.ViewPagerAdapter;
import com.nizam.payroll.fragments.AttendanceFragment;
import com.nizam.payroll.fragments.HomeFragment;
import com.nizam.payroll.fragments.SalaryFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 tabViewPager;
    ArrayList<Fragment> fragmentArrayList;
    TextView mainHeader;
    ImageButton setting;
    private final String CURRENT_FRAGMENT_KEY = "CURRENT_FRAGMENT";

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
        initComp();
        initListeners();
        if (savedInstanceState != null) {
            tabViewPager.setCurrentItem(savedInstanceState.getInt(CURRENT_FRAGMENT_KEY));
        }
    }

    private void initComp(){

        bottomNavigationView = findViewById(R.id.bottom_nav);
        mainHeader = findViewById(R.id.main_header);
        setting = findViewById(R.id.open_setting);

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new AttendanceFragment());
        fragmentArrayList.add(new SalaryFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,fragmentArrayList);
        tabViewPager = findViewById(R.id.tabViewPager);
        tabViewPager.setAdapter(viewPagerAdapter);

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
            }
            return true;
        });

        // Switch tab through ViewPager
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
                }
                super.onPageSelected(position);
            }
        });
    }

//    private void replaceFragment(Fragment fragment, String header, int index){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
//        fragmentTransaction.replace(R.id.home_frame_container,fragment);
//        fragmentTransaction.commit();
//
//        currentFragmentIndex = index;
//        mainHeader.setText(header);
//    }
    private void replaceFragment(String header, int index){
        tabViewPager.setCurrentItem(index);
        mainHeader.setText(header);
    }

    public static void showToast(String message, Context context){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(()->{
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        });
    }

}