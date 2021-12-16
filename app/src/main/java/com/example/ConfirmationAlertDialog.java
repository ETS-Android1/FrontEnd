package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;

public class ConfirmationAlertDialog {
    public AlertDialog alertDialog;
    TextView titleTV;
    TextView messageTV;
    public Button btnOk;
    public Button cancelBtn;
    public ConfirmationAlertDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.confirmation_alert_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        titleTV = alertView.findViewById(R.id.titleTv);
        messageTV = alertView.findViewById(R.id.messageTv);
        btnOk = alertView.findViewById(R.id.okBtn);
        cancelBtn = alertView.findViewById(R.id.CancleBtn);

        titleTV.setText(title);
        messageTV.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
