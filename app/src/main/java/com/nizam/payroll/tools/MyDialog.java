package com.nizam.payroll.tools;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.core.content.ContextCompat;

import com.nizam.payroll.R;

public class MyDialog {
    Dialog dialog;
    Button cancelSelect;
    Button confirmSelect;
    public MyDialog(Context context, String title) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_dialog);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.transparent_bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        cancelSelect = dialog.findViewById(R.id.cancelSelect);
        cancelSelect.setOnClickListener((view1)-> dialog.dismiss());
        confirmSelect = dialog.findViewById(R.id.confirmSelect);
        confirmSelect.setOnClickListener((view1)-> dialog.dismiss());

        TextView headTitle = dialog.findViewById(R.id.title);
        headTitle.setText(title);
    }
    public void setMessage(String message){
        TextView dialogMessage = dialog.findViewById(R.id.message);
        dialogMessage.setText(message);
    }
    public void setBody(int id ){
        View layout1 = LayoutInflater.from(dialog.getContext()).inflate(id, null);
        LinearLayout layout2 = dialog.findViewById(R.id.popupBody);
        layout2.removeAllViews();
        layout2.addView(layout1);
    }
    public void setLayoutParam(int width, int height){
        dialog.getWindow().setLayout(width,height);
    }
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable){
        dialog.getWindow().setBackgroundDrawable(drawable);
    }
    public void setCancelable(Boolean cancelable){
        dialog.setCancelable(cancelable);
    }
    public void setCancelOnClick(Boolean dismissAfter, Runnable method){
        cancelSelect.setOnClickListener(view -> {
            new Handler().post(method);
            if (dismissAfter){
                dialog.dismiss();
            }
        });
    }
    public void setConfirmOnclick(Boolean dismissAfter, Runnable method){
        confirmSelect.setOnClickListener(view -> {
            new Handler().post(method);
            if (dismissAfter){
                dialog.dismiss();
            }
        });
    }
    public void show(){
        dialog.show();
    }
    public void dismiss(){ dialog.dismiss(); }
    public <T extends android.view.View> T findViewById(@IdRes int id ){
        return dialog.findViewById(id);
    }
}
