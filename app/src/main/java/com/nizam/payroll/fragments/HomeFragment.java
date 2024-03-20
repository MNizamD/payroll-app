package com.nizam.payroll.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nizam.payroll.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        PieChart mPieChart = (PieChart) view.findViewById(R.id.pieChart);

        mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));
        mPieChart.setAnimationTime(500);
        mPieChart.setInnerPaddingColor(getResources().getColor(R.color.TranparenAccent));

        mPieChart.startAnimation();

        ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(Color.parseColor("#545696"));

        series.addPoint(new ValueLinePoint("Jan 15", 2.4f));
        series.addPoint(new ValueLinePoint("Jan 31", 2.4f));
        series.addPoint(new ValueLinePoint("Feb 15", 3.4f));
        series.addPoint(new ValueLinePoint("Feb 29", 3.4f));
        series.addPoint(new ValueLinePoint("Mar 15", 5.4f));
        series.addPoint(new ValueLinePoint("Mar 31", 5.4f));
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
        mCubicValueLineChart.setUseDynamicScaling(true);

        return view;
    }
//    private ArrayList<PieEntry> dataValues(){
//        ArrayList<PieEntry> dataVals = new ArrayList<PieEntry>();
//        dataVals.add(new PieEntry(15,"Fifteen"));
//        dataVals.add(new PieEntry(10,"Ten"));
//        return dataVals;
//    }
}