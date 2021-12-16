package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.webService.UserSession;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfile extends AppCompatActivity {
    EditText emailEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText phoneNumberEditText;
    UserAPI userAPI;
    String token;
    CardView editbutton;
    public void EditButtonClicked(View view){
        editbutton = (CardView) findViewById(R.id.cardViewEditProfile);
        Animation animation10 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_anim);
        editbutton.startAnimation(animation10);

        User user = new User(firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),emailEditText.getText().toString(),phoneNumberEditText.getText().toString());
        Call<User> userSessionCall = userAPI.editProfile("token "+ token, user);

        userSessionCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                   // Toast.makeText(EditProfile.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                    Log.i("MOSHKEL",response.message());
                }
                else{
                    String code = Integer.toString(response.code());
                    User user = response.body();
                    Toast.makeText(EditProfile.this, "Profile Edited!", Toast.LENGTH_SHORT).show();
                    SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = shP.edit();
                    myEdit.putString("firstname",firstNameEditText.getText().toString());
                    myEdit.putString("lastname",lastNameEditText.getText().toString());
                    myEdit.putString("email",emailEditText.getText().toString());
                    myEdit.putString("phonenumber",phoneNumberEditText.getText().toString());
                    myEdit.apply();
                    Intent intent = new Intent(EditProfile.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
               // Toast.makeText(EditProfile.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        emailEditText = findViewById(R.id.eemailid);
        firstNameEditText = findViewById(R.id.firstNameIde);
        lastNameEditText = findViewById(R.id.lastNameIde);
        phoneNumberEditText = findViewById(R.id.phoneNumberIde);

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        token = shP.getString("token", "");
        String firstName = shP.getString("firstname","");
        String lastName = shP.getString("lastname","");
        String email = shP.getString("email","");
        String phoneNumber = shP.getString("phonenumber","");
        if(!firstName.equals("")){
            firstNameEditText.setText(firstName);
        }
        else{
            firstNameEditText.setHint(R.string.empty);
        }
        if(!lastName.equals("")){
            lastNameEditText.setText(lastName);
        }
        else{
            lastNameEditText.setHint(" " +R.string.empty);
        }
        if(!email.equals("")){
            emailEditText.setText(email);
        }
        else{
            emailEditText.setHint(R.string.empty);
        }
        if(!phoneNumber.equals("")){
            phoneNumberEditText.setText(phoneNumber);
        }
        else{
            phoneNumberEditText.setHint(R.string.empty);
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit LoginRetrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI =LoginRetrofit.create(UserAPI.class);

    }
}