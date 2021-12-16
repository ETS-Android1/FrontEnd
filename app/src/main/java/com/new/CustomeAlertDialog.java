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

public class CustomeAlertDialog {

    AlertDialog alertDialog;
    TextView titleTV;
    TextView messageTV;
    Button btnOk;
    public CustomeAlertDialog(Context context,String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        titleTV = alertView.findViewById(R.id.titleTv);
        messageTV = alertView.findViewById(R.id.messageTv);
        btnOk = alertView.findViewById(R.id.okBtn);

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
