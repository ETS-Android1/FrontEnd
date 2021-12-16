package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.BroadCast.wifiBroadCast;
import com.example.entity.User;
import com.example.myapplication.R;
import com.example.webService.UserAPI;
import com.example.webService.UserSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    CardView cardviewsup ;
    TextView golog;
    EditText ename,eemail,epass,econfpass;
    UserAPI userAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        init();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit SignUpRefrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI =SignUpRefrofit.create(UserAPI.class);

        ename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ename.setBackgroundResource(R.drawable.border);
                eemail.setBackgroundResource(R.drawable.border);
                epass.setBackgroundResource(R.drawable.border);
                econfpass.setBackgroundResource(R.drawable.border);
                if(ename.getText().toString().length()<6)
                {
                    ename.setBackgroundResource(R.drawable.border_red);
                }
                else{
                    ename.setBackgroundResource(R.drawable.border_selected);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        eemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ename.setBackgroundResource(R.drawable.border);
                eemail.setBackgroundResource(R.drawable.border);
                epass.setBackgroundResource(R.drawable.border);
                econfpass.setBackgroundResource(R.drawable.border);
                if(!isEmailValid(eemail.getText().toString()))
                {
                    eemail.setBackgroundResource(R.drawable.border_red);
                }
                else{
                    eemail.setBackgroundResource(R.drawable.border_selected);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        epass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ename.setBackgroundResource(R.drawable.border);
                eemail.setBackgroundResource(R.drawable.border);
                epass.setBackgroundResource(R.drawable.border);
                econfpass.setBackgroundResource(R.drawable.border);
                epass.setBackgroundResource(R.drawable.border_selected);
                if(epass.getText().toString().length()<8 || !checkString(epass.getText().toString())|| !containsDigit(epass.getText().toString()))
                {
                    epass.setBackgroundResource(R.drawable.border_red);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        econfpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ename.setBackgroundResource(R.drawable.border);
                eemail.setBackgroundResource(R.drawable.border);
                epass.setBackgroundResource(R.drawable.border);
                econfpass.setBackgroundResource(R.drawable.border);
                if(!econfpass.getText().toString().equals(epass.getText().toString()))
                {
                    econfpass.setBackgroundResource(R.drawable.border_red);
                }
                else {
                    econfpass.setBackgroundResource(R.drawable.border_selected);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cardviewsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setBackgroundColor(getResources().getColor(R.color.darkBlueTheme));
                v.setEnabled(false);

//                ename.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
//                eemail.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
//                epass.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
//                econfpass.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);

                eemail.setBackgroundResource(R.drawable.border);
                ename.setBackgroundResource(R.drawable.border);
                epass.setBackgroundResource(R.drawable.border);
                econfpass.setBackgroundResource(R.drawable.border);

                String name=ename.getText().toString();
                String email=eemail.getText().toString();
                String pass=epass.getText().toString();
                String confpass=econfpass.getText().toString();
                if(isEmpty(name,email,pass,confpass)==true ) {
                    Toast.makeText(SignUp.this,"some field is empty.please fill empty field",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                }
                else {
                    if (isPassValid(pass, confpass) == false) {
                        Toast.makeText(SignUp.this, "password & confirm pass,don't match together", Toast.LENGTH_LONG).show();
                        //epass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        epass.setBackgroundResource(R.drawable.border_red);
                        v.setEnabled(true);
                    }
                    if (isEmailValid(email) == false && isPassValid(pass, confpass) == true) {
                        Toast.makeText(SignUp.this, "email isn't valid.please fill it with correct email", Toast.LENGTH_LONG).show();
                        //eemail.getBackground().setColorFilter("#000", PorterDuff.Mode.SRC_ATOP);
                        eemail.setBackgroundResource(R.drawable.border_red);
                        v.setEnabled(true);
                    }
                    if(isEmailValid(email)== true && isPassValid(pass,confpass) == true && name.length()<6)
                    {
                        Toast.makeText(SignUp.this,"your username should be at least 6 characters.",Toast.LENGTH_LONG);
                        //ename.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        ename.setBackgroundResource(R.drawable.border_red);
                        v.setEnabled(true);
                    }
                }
                if(isEmailValid(email)==true && isPassValid(pass, confpass) == true && isEmpty(name,email,pass,confpass)==false && name.length()>=4) {
                    String first_name = "";
                    String last_name = "";

                    Animation animation = AnimationUtils.loadAnimation(SignUp.this, R.anim.blink_anim);
                    cardviewsup.startAnimation(animation);

                    User user= new User(name, email, pass, confpass, first_name, last_name);
                    Call<UserSession> callBack =userAPI.createUser("application/json",user);
                    callBack.enqueue(new Callback<UserSession>() {
                        @Override
                        public void onResponse(Call<UserSession> call, Response<UserSession> response) {
                            if(!response.isSuccessful())
                            {
                                //Toast.makeText(SignUp.this, "this user has already exists!", Toast.LENGTH_LONG).show();
                                v.setEnabled(true);
                                v.clearAnimation();
                            }
                            else{
                                String code = Integer.toString(response.code());
                                UserSession responseSession = response.body();
                                String username = responseSession.getUser().getUsername();
                                String email = responseSession.getUser().getEmail();
                                int id = responseSession.getUser().getId();
                                String token = responseSession.getToken();
                                //this toast have to be deleted
                                //just for showing that connect is alive and correct...
                                if (token == null)
                                Log.i("THISSSSSSSSSSSSSS",email);
                                Intent gosignup=new Intent(SignUp.this,login.class);
                                gosignup.putExtra("token",token);
                                gosignup.putExtra("username",username);
                                SharedPreferences.Editor editor = ((ShanbehApp)getApplication()).sharedPreferences.edit();
                                editor.putString("token",token);
                                editor.putString("username",username);
                                editor.apply();
                                startActivity(gosignup);
                                finish();

                            }
                            v.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<UserSession> call, Throwable t) {
                            //Toast.makeText(SignUp.this, "error is:"+t.getMessage(), Toast.LENGTH_LONG).show();
                            v.setEnabled(true);
                        }
                    });
                }
            }
        });
        golog=(TextView) findViewById(R.id.loginclick);
        golog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1= AnimationUtils.loadAnimation(SignUp.this,R.anim.fadein);
                golog.startAnimation(animation1);
                Intent login=new Intent(SignUp.this,login.class);
                startActivity(login);
                finish();
            }
        });
    }

    public void init()
    {
        cardviewsup=(CardView) findViewById(R.id.cardViewsignup);
        ename=(EditText)findViewById(R.id.nameid);
        eemail=(EditText)findViewById(R.id.emailid);
        epass=(EditText)findViewById(R.id.passid);
        econfpass=(EditText)findViewById(R.id.confpassid);
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
    public boolean isPassValid(String pass,String confirm)
    {
        if(pass.length()>=8)
        {
            if(checkString(pass))
            {
                if(containsDigit(pass))
                {
                    if(pass.equals(confirm)==true)
                    {
                        return true;
                    }
                    else{
                        Toast.makeText(this, "password and confirmation of the password are not the same", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else{
                    Toast.makeText(this, "password should have at least one digit", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else{
                Toast.makeText(this, "password should have at least one capital letter", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Toast.makeText(this, "password have to be at least 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    public boolean isEmpty(String name,String  email,String pass,String confpass )
    {
        if(name.isEmpty() || email.isEmpty()|| pass.isEmpty() || confpass.isEmpty())
        {
            return true;
        }
        return false;
    }

    private static boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }
//EditText ename,eemail,epass,econfpass;
//    public void colorFilter(View view) {
//        ename.getBackground().setColorFilter(null);
//        eemail.getBackground().setColorFilter(null);
//        epass.getBackground().setColorFilter(null);
//        econfpass.getBackground().setColorFilter(null);
//        ename.setBackgroundResource(R.drawable.border);
//        eemail.setBackgroundResource(R.drawable.border);
//        epass.setBackgroundResource(R.drawable.border);
//        econfpass.setBackgroundResource(R.drawable.border);
//        view.getBackground().setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP);
//        view.setBackgroundResource(R.drawable.border_selected);
//    }
}