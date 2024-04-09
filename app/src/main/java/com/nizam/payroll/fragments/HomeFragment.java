package com.nizam.payroll.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nizam.payroll.R;
import com.nizam.payroll.fragments.nested.DashboardNOWorkFragment;
import com.nizam.payroll.fragments.nested.DashboardOnWorkFragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.concurrent.ExecutorService;

public class HomeFragment extends Fragment {
    View view;
    CircularProgressBar circularProgressBar;
    LinearLayout noWorkDashboard, timeTableDashboard, timeStampDashboard;
    TextView leaveStatDashboard, timeClockDashboard, modeText, modeDate, leaveDaysLeft;
    ImageView modeIcon, leaveIcon;
    PieChart mPieChart;
    StackedBarChart mStackedBarChart;
    FragmentContainerView fragment_work_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getView()==null){
            view = inflater.inflate(R.layout.fragment_home, container, false);
            new Thread(() ->{
                initComp();
                initData();
                initListeners();
                initWorkStatus();
            }).start();
        } else {
            view = getView();
            initWorkStatus();
        }
        return view;
    }
    int dashModeFlag = 1;

    private void initComp(){
        fragment_work_status = view.findViewById(R.id.fragment_work_status);
        fragment_work_status.setVisibility(View.INVISIBLE);
        mPieChart = view.findViewById(R.id.pieChart);
        mStackedBarChart = (StackedBarChart) view.findViewById(R.id.stackedbarchart);

    }
    private void initData(){
        requireActivity().runOnUiThread(()->{
            popDonutChar();
            popBarChar();
        });
    }
    private void initListeners(){

    }
    private void initWorkStatus(){
        view.findViewById(R.id.leaveStatCard).setOnClickListener(view -> {
            dashModeFlag = (dashModeFlag==3)? 1: dashModeFlag + 1;
            if(dashModeFlag == 1){
                replaceFragment(new DashboardOnWorkFragment());
            } else if ((dashModeFlag==2) || (dashModeFlag == 3)) {
                replaceFragment(DashboardNOWorkFragment.newInstance(dashModeFlag));
            }
            if(dashModeFlag == 3){
                onLeave(true);
            } else {
                onLeave(false);
            }
        });
//        new Handler(Looper.getMainLooper()).postDelayed(()->{
//            replaceFragment(new DashboardOnWorkFragment());
//        },1000);
        replaceFragment(new DashboardOnWorkFragment());

    }
    private void onLeave(Boolean onLeave){
        leaveIcon = view.findViewById(R.id.leaveIcon);
        leaveDaysLeft = view.findViewById(R.id.leaveDaysLeft);
        leaveStatDashboard = view.findViewById(R.id.leaveStatDashboard);

        // 1 = Clock mode
        // 2 = No work mode
        if (onLeave){
            leaveIcon.setVisibility(View.GONE);
            leaveDaysLeft.setVisibility(View.VISIBLE);
            leaveStatDashboard.setText("days left");
        } else {
            leaveIcon.setVisibility(View.VISIBLE);
            leaveDaysLeft.setVisibility(View.GONE);
            leaveStatDashboard.setText("No leaves");
        }
    }
    private void popDonutChar(){
        mPieChart.addPieSlice(new PieModel("Freetime", 15, getResources().getColor(R.color.Undertime)));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, getResources().getColor(R.color.Ontime)));
        mPieChart.addPieSlice(new PieModel("Eating", 9, getResources().getColor(R.color.Late)));
        mPieChart.addPieSlice(new PieModel("Eating", 2, getResources().getColor(R.color.Absent)));
        mPieChart.setAnimationTime(750);
        mPieChart.startAnimation();

        popBarChar();
    }
    private void popBarChar(){
        StackedBarModel s1 = new StackedBarModel("J");
        s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s1.addBar(new BarModel(5.3f, getResources().getColor(R.color.TranparenAccent)));

        StackedBarModel s2 = new StackedBarModel("F");
        s2.addBar(new BarModel(9.3f, 0xFF63CBB0));
        s2.addBar(new BarModel(4.3f, getResources().getColor(R.color.TranparenAccent)));

        StackedBarModel s3 = new StackedBarModel("M");
        s3.addBar(new BarModel(3.3f, 0xFF63CBB0));
        s3.addBar(new BarModel(8.3f, getResources().getColor(R.color.TranparenAccent)));

        StackedBarModel s4 = new StackedBarModel("A");
        s4.addBar(new BarModel(1.3f, 0xFF63CBB0));
        s4.addBar(new BarModel(7.3f, getResources().getColor(R.color.TranparenAccent)));

        StackedBarModel s5 = new StackedBarModel("M");
        s5.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s5.addBar(new BarModel(4.3f, getResources().getColor(R.color.TranparenAccent)));

        StackedBarModel s6 = new StackedBarModel("J");
        s6.addBar(new BarModel(6.3f, 0xFF63CBB0));
        s6.addBar(new BarModel(8.3f, getResources().getColor(R.color.TranparenAccent)));

        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);
        mStackedBarChart.addBar(s4);
        mStackedBarChart.addBar(s5);
        mStackedBarChart.addBar(s6);

        mStackedBarChart.setAnimationTime(500);
        mStackedBarChart.startAnimation();
        requireActivity().runOnUiThread(()->mStackedBarChart.startAnimation());

    }


//    private ArrayList<PieEntry> dataValues(){
//        ArrayList<PieEntry> dataVals = new ArrayList<PieEntry>();
//        dataVals.add(new PieEntry(15,"Fifteen"));
//        dataVals.add(new PieEntry(10,"Ten"));
//        return dataVals;
//    }
private void replaceFragment(Fragment fragment) {
    // Hide the fragmentContainer
    if (fragment_work_status != null) {
        fragment_work_status.setVisibility(View.INVISIBLE);
    }

    // Replace the fragment with the new one using the child FragmentManager
    getChildFragmentManager().beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragment_work_status, fragment)
            .commit();

    // Show the replaced fragment after a delay
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
        @Override
        public void run() {
            if (fragment_work_status != null) {
                fragment_work_status.setVisibility(View.VISIBLE);
            }
        }
    }, 250);
}

}