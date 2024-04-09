package com.nizam.payroll.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.nizam.payroll.R;
import com.nizam.payroll.adapters.PayheadAdapter;
import com.nizam.payroll.models.PayheadModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalaryFragment extends Fragment {
    Handler handler;
    Context context;
    String SALARY_SHARED_PREF = "salary_state";
    String YEAR_ARRAY = "year_array";
    String MONTH_ARRAY = "month_array";
    String DAYS_ARRAY = "days_array";

    View view;
    String[] year = {"2000","2001","2002"};
    String[] month = {"Jan","Feb","Mar"};
    String[] days = {"01-15","16-30"};
    AutoCompleteTextView dropdown_year_list;
    AutoCompleteTextView dropdown_month_list;
    AutoCompleteTextView dropdown_days_list;
    ArrayAdapter<String> adapterYear;
    ArrayAdapter<String> adapterMonth;
    ArrayAdapter<String> adapterDays;
    ArrayList<PayheadModel> dataArrayList = new ArrayList<>();
    RecyclerView earnings;
    RecyclerView deducts;

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(YEAR_ARRAY, TextUtils.join(",",year));
//        outState.putString(MONTH_ARRAY, TextUtils.join(",",month));
//        outState.putString(DAYS_ARRAY, TextUtils.join(",",days));
////        saveState();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(Looper.getMainLooper());
        context = this.getContext();
//        restoreState();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if (savedInstanceState != null){
//            year = savedInstanceState.getString(YEAR_ARRAY).split(",");
//            month = savedInstanceState.getString(MONTH_ARRAY).split(",");
//            days = savedInstanceState.getString(DAYS_ARRAY).split(",");
//        }
        if (getView()==null){
            view = inflater.inflate(R.layout.fragment_salary, container, false);
            new Thread(() ->{
                initDropdown();
                initPayheads();
            }).start();
        } else {
            view = getView();
            popDropdown();
            popPayheads();
        }
        return view;
    }
    private void initDropdown(){
        dropdown_year_list = view.findViewById(R.id.list_year);
        dropdown_month_list = view.findViewById(R.id.list_month);
        dropdown_days_list = view.findViewById(R.id.list_days);

        dropdown_year_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext().getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
        dropdown_month_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext().getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
        dropdown_days_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext().getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
        popDropdown();
    }
    private void initPayheads(){
        try{
            earnings = view.findViewById(R.id.earning_listview);
            deducts = view.findViewById(R.id.deduct_listview);
            earnings.setLayoutManager(new LinearLayoutManager(getContext()){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            deducts.setLayoutManager(new LinearLayoutManager(getContext()){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            popPayheads();
        } catch (Exception e){
            Toast.makeText(this.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void popDropdown(){
        adapterYear = new ArrayAdapter<String>(this.getContext(),R.layout.dropdown_date,year);
        adapterMonth = new ArrayAdapter<String>(this.getContext(),R.layout.dropdown_date,month);
        adapterDays = new ArrayAdapter<String>(this.getContext(),R.layout.dropdown_date,days);

        handler.post(() -> {
            dropdown_year_list.setAdapter(adapterYear);
            dropdown_month_list.setAdapter(adapterMonth);
            dropdown_days_list.setAdapter(adapterDays);
        });
    }
    private void popPayheads(){
        new Thread(() -> {
            PayheadModel basicSalary = new PayheadModel("Basic Salary",100.5f);
            PayheadModel sss = new PayheadModel("SSS",200.5f);
            dataArrayList.clear();
            for (int i = 0; i < 2; i++) {
                dataArrayList.add(basicSalary);
                dataArrayList.add(sss);
            }
            PayheadAdapter payheadAdapter = new PayheadAdapter(context, dataArrayList);

            handler.post(()->{
                earnings.setAdapter(payheadAdapter);
                deducts.setAdapter(payheadAdapter);
            });
        }).start();
    }
}