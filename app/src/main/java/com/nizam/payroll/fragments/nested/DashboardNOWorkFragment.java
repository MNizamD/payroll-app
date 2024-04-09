package com.nizam.payroll.fragments.nested;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nizam.payroll.MainActivity;
import com.nizam.payroll.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardNOWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardNOWorkFragment extends Fragment {
    private static final String MODE = "workMde";
    private int dashModeFlag;
    View view;
    LinearLayout noWorkDashboard,onLeaveDashboard;


    public DashboardNOWorkFragment() {
        // Required empty public constructor
    }

    public static DashboardNOWorkFragment newInstance(int dashModeFlag) {
        DashboardNOWorkFragment fragment = new DashboardNOWorkFragment();
        Bundle args = new Bundle();
        args.putInt(MODE, dashModeFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dashModeFlag = getArguments().getInt(MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard_no_work, container, false);
        onLeaveDashboard = view.findViewById(R.id.onLeaveDashboard);
        noWorkDashboard = view.findViewById(R.id.noWorkDashboard);
        if(dashModeFlag == 2){
            onLeaveDashboard.setVisibility(View.GONE);
            noWorkDashboard.setVisibility(View.VISIBLE);
        } else if (dashModeFlag == 3) {
            onLeaveDashboard.setVisibility(View.VISIBLE);
            noWorkDashboard.setVisibility(View.GONE);
        }
        return view;
    }
}