package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;

public class CustomSuccessAlertDialog {
    AlertDialog alertDialog;
    TextView titleTV;
    TextView messageTV;
    Button btnOk;
    public CustomSuccessAlertDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.successfull_alert_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        titleTV = alertView.findViewById(R.id.titleTv_s);
        messageTV = alertView.findViewById(R.id.messageTv_s);
        btnOk = alertView.findViewById(R.id.okBtn_s);

        titleTV.setText(title);
        messageTV.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
