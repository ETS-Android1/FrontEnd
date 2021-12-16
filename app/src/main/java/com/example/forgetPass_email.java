package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entity.User;
import com.example.myapplication.R;
import com.example.webService.UserAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class forgetPass_email extends AppCompatActivity {

    EditText eemail;
    CardView sendEmail;
    UserAPI userAPI;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_pass_email);

        init();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();

        Retrofit SignUpRefrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI =SignUpRefrofit.create(UserAPI.class);

        eemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                eemail.setBackgroundResource(R.drawable.border_selected);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                Email = eemail.getText().toString();
                eemail.setBackgroundResource(R.drawable.border);
                if(Email.equals(""))
                {
                    Toast.makeText(forgetPass_email.this, "please fill all blanks", Toast.LENGTH_SHORT).show();
                    eemail.setBackgroundResource(R.drawable.border_red);
                    view.setEnabled(true);
                }
                else if(!isEmailValid(Email)){
                    Toast.makeText(forgetPass_email.this, "email isn't valid.please fill it with correct email", Toast.LENGTH_SHORT).show();
                    eemail.setBackgroundResource(R.drawable.border_red);
                    view.setEnabled(true);
                }
                else{
                    Animation animation4 = AnimationUtils.loadAnimation(forgetPass_email.this, R.anim.blink_anim);
                    sendEmail.startAnimation(animation4);
                    User user = new User(eemail.getText().toString());
                    Call<Void> callBack = userAPI.sendEmail("application/json",user);
                    callBack.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(!response.isSuccessful())
                            {
                               // Toast.makeText(forgetPass_email.this, "error is:"+response.message(), Toast.LENGTH_LONG).show();
                                view.setEnabled(true);
                                view.clearAnimation();
                            }
                            else{
                                String code = Integer.toString(response.code());
                                Toast.makeText(forgetPass_email.this, "check your email for code", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(forgetPass_email.this,forgotpass.class);
                                startActivity(intent);
                                view.setEnabled(true);
                                view.clearAnimation();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                          //  Toast.makeText(forgetPass_email.this, "error is:"+t.getMessage(), Toast.LENGTH_LONG).show();
                            view.setEnabled(true);
                        }
                    });
                }
            }
        });
    }

    public void init()
    {
        eemail = findViewById(R.id.emailConfirmation);
        sendEmail=(CardView) findViewById(R.id.cardViewfgEmail);
    }

    public boolean isEmailValid(String email)
    {
        String regEX="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{3}+";
        CharSequence input=email;
        Pattern pattern=Pattern.compile(regEX,Pattern.UNICODE_CASE);
        Matcher matcher=pattern.matcher(input);
        if(matcher.matches())
        {
            return  true;
        }
        else
            return false;
    }

    public void ColorFilterEmailForget(View view) {
        eemail.getBackground().setColorFilter(null);
        eemail.setBackgroundResource(R.drawable.border);
        //view.setBackgroundResource(R.drawable.border_selected);
    }

    public void goNextPage(View view) {
        Intent intent = new Intent(forgetPass_email.this,forgotpass.class);
        startActivity(intent);
    }
}