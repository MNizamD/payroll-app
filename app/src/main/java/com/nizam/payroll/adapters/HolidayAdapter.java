package com.nizam.payroll.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.nizam.payroll.R;
import com.nizam.payroll.models.HolidayModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HolidayAdapter extends ArrayAdapter<HolidayModel> {

    ArrayList<HolidayModel> dataList;

    public HolidayAdapter(@NonNull Context context, ArrayList<HolidayModel> dataList) {
        super(context,R.layout.holiday_item);
        this.dataList = dataList;
    }
//    MainActivity main;


    @Override
    public int getPosition(@Nullable HolidayModel item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HolidayModel holidayModel = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.holiday_item,parent,false);
        }
        ImageView indicator = convertView.findViewById(R.id.indicator);
        TextView hName = convertView.findViewById(R.id.holidayName);
        TextView hDate = convertView.findViewById(R.id.holidayDate);

        hName.setText(holidayModel.getName());
        SimpleDateFormat df = new SimpleDateFormat("MMM dd");
        hDate.setText(df.format(holidayModel.getDate()));
        return convertView;
    }

}
