package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;

public class CustomErrorAlertDialog {
    AlertDialog alertDialog;
    TextView titleTV;
    TextView messageTV;
    Button btnOk;
    public CustomErrorAlertDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.error_alert_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        titleTV = alertView.findViewById(R.id.titleTv_error);
        messageTV = alertView.findViewById(R.id.messageTv_error);
        btnOk = alertView.findViewById(R.id.okBtn_error);

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
