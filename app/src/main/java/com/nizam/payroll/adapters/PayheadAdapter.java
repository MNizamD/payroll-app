package com.nizam.payroll.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nizam.payroll.R;
import com.nizam.payroll.models.PayheadModel;

import java.util.ArrayList;

public class PayheadAdapter extends RecyclerView.Adapter<PayheadAdapter.ViewHolder> {
    ArrayList<PayheadModel> dataArrayList;
    private Context context;
    public PayheadAdapter(Context context, ArrayList<PayheadModel> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.breakdown_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataArrayList.get(position) != null){
            PayheadModel payheadModel = dataArrayList.get(position);

            holder.itemName.setText(payheadModel.getPayheadName());
            holder.amount.setText(payheadModel.getAmount().toString());
        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            amount = itemView.findViewById(R.id.amount);
        }
    }


    ////


}

