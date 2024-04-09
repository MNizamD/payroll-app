package com.nizam.payroll.fragments;

import android.graphics.Color;
import android.icu.text.DateFormatSymbols;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.kishannareshpal.horizontalbarchartview.HorizontalBarChartView;
import com.nizam.payroll.MainActivity;
import com.nizam.payroll.R;
//import com.nizam.payroll.adapters.HolidayAdapter;
//import com.nizam.payroll.models.HolidayModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceFragment extends Fragment {

    CompactCalendarView calendarView;
    TextView monthText, yearText, attBarLabel;
    HorizontalBarChartView attBarChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        monthText = view.findViewById(R.id.att_month_text);
        yearText = view.findViewById(R.id.att_year_text);
        calendarView = view.findViewById(R.id.att_cal_view);
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        String string_date = "24-03-2024";
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        long milliseconds = 0L;
        try {
            Date d = f.parse(string_date);
            milliseconds = d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Event ev1 = new Event(Color.GREEN,milliseconds);
        calendarView.addEvent(ev1);
        Date thisDate = calendarView.getFirstDayOfCurrentMonth();
        monthText.setText(getMonthName(getMyDate(thisDate,Calendar.MONTH)));
        yearText.setText(String.valueOf(getMyDate(thisDate,Calendar.YEAR)));
        adaptCalendarHeight(thisDate);

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                MainActivity.showToast(dateClicked.toString(),getContext());
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthText.setText(getMonthName(getMyDate(firstDayOfNewMonth,Calendar.MONTH)));
                yearText.setText(String.valueOf(getMyDate(firstDayOfNewMonth,Calendar.YEAR)));
                adaptCalendarHeight(firstDayOfNewMonth);
            }
        });

        attBarLabel = view.findViewById(R.id.att_bar_label);
        String labels = "Ontime:\nLate:\nUndertime:\nAbsent:";
        SpannableString spannableString = new SpannableString(labels);

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Ontime)), labels.indexOf("Ontime"), labels.indexOf("Ontime") + "Ontime".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Late)), labels.indexOf("Late"), labels.indexOf("Late") + "Late".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Undertime)), labels.indexOf("Undertime"), labels.indexOf("Undertime") + "Undertime".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Absent)), labels.indexOf("Absent"), labels.indexOf("Absent") + "Absent".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        attBarLabel.setText(spannableString);

        attBarChart = view.findViewById(R.id.att_bar_chart);
        int total = 15+2+9+5;
        attBarChart.addData(0,toPercentage(15,total),R.color.Ontime);
        attBarChart.addData(1,toPercentage(2,total),R.color.Late);
        attBarChart.addData(2,toPercentage(9,total),R.color.Undertime);
        attBarChart.show();

//        ListView holidayList = (ListView) view.findViewById(R.id.holidayList);
//        /////////////////
//        ArrayList<HolidayModel> holidayModelArrayList = new ArrayList<>();
//        holidayModelArrayList.add(new HolidayModel("Event 1", tryDate()[0]));
//        holidayModelArrayList.add(new HolidayModel("Event 2", tryDate()[1]));
//        holidayModelArrayList.add(new HolidayModel("Event 3", tryDate()[2]));
//        ////////////
//        HolidayAdapter holidayAdapter = new HolidayAdapter(this.getContext(),holidayModelArrayList);
//        holidayList.setAdapter(holidayAdapter);
//
//        MainActivity.showToast(String.valueOf(holidayModelArrayList.size()),getContext());

        return view;
    }

    private Date[] tryDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString1 = "03/25/2024";
        String dateString2 = "03/26/2024";
        String dateString3 = "03/27/2024";

//        long[] millisArray = new long[3];

        Date[] dateArray = new Date[3];
        try {
            // Parse date strings to Date objects
            dateArray[0] = sdf.parse(dateString1);
            dateArray[1] = sdf.parse(dateString2);
            dateArray[2] = sdf.parse(dateString3);

            // Convert Date objects to milliseconds since Unix epoch
//            millisArray[0] = date1.getTime();
//            millisArray[1] = date2.getTime();
//            millisArray[2] = date3.getTime();

        } catch (Exception e) {
            MainActivity.showToast(e.getMessage(),getContext());
        }

        return dateArray;
    }
    private float toPercentage(long yourValue, long maxValue){
//        MainActivity.showToast(String.valueOf(yourValue * 100F / maxValue),getContext());
        return yourValue * 100F / maxValue;
    }

    private int getMyDate(Date date, int dateType){
        SimpleDateFormat df;
        int result = 0;

        switch (dateType){
            case Calendar.YEAR:
                df = new SimpleDateFormat("yyyy");
                result = Integer.parseInt(df.format(date));
                break;
            case Calendar.MONTH:
                df = new SimpleDateFormat("MM");
                result = Integer.parseInt(df.format(date));
                break;
            case Calendar.DAY_OF_MONTH:
                df = new SimpleDateFormat("dd");
                result = Integer.parseInt(df.format(date));
                break;
        }
        return result;
    }
    private String getMonthName(int index){
        String[] months = new DateFormatSymbols().getMonths();
        return months[index-1];
    }
    private void adaptCalendarHeight(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
        int newHeight;
        if (dayOfWeek == Calendar.FRIDAY && dayOfMonth == 31){
            newHeight = 400;
        } else if (dayOfWeek == Calendar.SATURDAY && dayOfMonth >= 30){
            newHeight = 400;
        } else if (dayOfWeek == Calendar.SUNDAY && dayOfMonth == 28){
            newHeight = 300;
        } else {
            newHeight = 350;
        }
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newHeight, getResources().getDisplayMetrics());;
        calendarView.setLayoutParams(layoutParams);
    }
}