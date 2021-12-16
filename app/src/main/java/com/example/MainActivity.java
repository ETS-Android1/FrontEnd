package com.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.User;
import com.example.myapplication.R;
import com.example.webService.UserAPI;
import com.example.webService.UserSession;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {
    CustomCalendarView customCalendarView;
    DrawerLayout drawerLayout;
    NavigationView monthNavigationView;
    TextView userNameTextView;
    ImageView profileImage;
    ImageView smallProfileImage;

    Bundle extras ;
    UserAPI userAPI;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            Log.i("0","0");
            Uri u = data.getData();
            String filePath = getPath(u);
            Log.i("DATA",u.toString());
            Log.i("DATA2",filePath);
            profileImage = findViewById(R.id.profileImageSource);
            profileImage.setImageURI(data.getData());
            File file = new File(filePath);
            Log.i("file name",file.getName());
            Log.i("1","1");
            Log.i("3","3");
            SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
            String token = shP.getString("token", "");
            String firstName = shP.getString("firstname","");
            String lastName = shP.getString("lastname","");
            String email = shP.getString("email","");
            String phoneNumber = shP.getString("phonenumber","");
            Log.i("4","4");

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            RequestBody emailR =
                    RequestBody.create(MediaType.parse("multipart/form-data"), email);
            RequestBody firstNameR =
                    RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
            RequestBody lastNameR =
                    RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
            RequestBody phoneNumberR =
                    RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber);
            Call<User> userSessionCall = userAPI.editProfile("token "+ token,emailR,firstNameR,lastNameR,phoneNumberR,body);
            Log.i("5","5");
            userSessionCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                        Log.i("MOSHKEL",response.message());
                    }
                    else{
                        String code = Integer.toString(response.code());
                        Toast.makeText(MainActivity.this, "Photo Edited!", Toast.LENGTH_SHORT).show();
                        final String[] userAvatar = new String[1];
                        Call<User> userSessionCall1 = userAPI.showProfile("token "+ token);
                        userSessionCall1.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(!response.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String code = Integer.toString(response.code());
                                    User user = response.body();
                                    if(!(user.getAvatar() == null)){
                                        userAvatar[0] = user.getAvatar();
                                    }
                                    else{
                                        userAvatar[0] = "";
                                    }
                                    SharedPreferences UI = getSharedPreferences("userInformation",MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = UI.edit();
                                    myEdit.putString("avatar",userAvatar[0]);
                                    myEdit.apply();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                //Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    //Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
    public  void GoToEditProfile(View view){
        Intent editProfile = new Intent(MainActivity.this, EditProfile.class);
        startActivity(editProfile);
    }


    public void LogoutClicked(MenuItem button){
        showWarningDialog();
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.layout_warning_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("","");
                SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                String token = shP.getString("token", "");
                Call<UserSession> userSessionCall = userAPI.logOut("token "+ token);
                userSessionCall.enqueue(new Callback<UserSession>() {
                    @Override
                    public void onResponse(Call<UserSession> call, Response<UserSession> response) {
                        if(!response.isSuccessful())
                        {
                           // Toast.makeText(MainActivity.this, "username or password is not correct!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent logOut = new Intent(MainActivity.this, login.class);
                            startActivity(logOut);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserSession> call, Throwable t) {
                      //  Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);
        drawerLayout = findViewById(R.id.drawerMonthLayout);
        verifyStoragePermissions(this);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
                extras = getIntent().getExtras();
                SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                String userName = shP.getString("username", "");
                String avatarUrl = shP.getString("avatar","");
                if(!avatarUrl.equals("")){
                    smallProfileImage = findViewById(R.id.imageProfileSmall);
                    Picasso.get().load("https://shanbe-back.herokuapp.com"+avatarUrl).placeholder(R.drawable.acount_circle).error(R.drawable.acount_circle).into(smallProfileImage);
                }
                userNameTextView = findViewById(R.id.headerUsernameTextView);
                userNameTextView.setText(userName);
            }
        });
        monthNavigationView = findViewById(R.id.navigationView);
        monthNavigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(monthNavigationView,navController);
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

    public void EventClicked(MenuItem item) {
        Intent event = new Intent(MainActivity.this, event_activity.class);
        event.putExtra("location","");
        event.putExtra("category","");
        event.putExtra("s_time","");
        startActivity(event);
    }
    public void EventPrivateClicked(MenuItem item) {
        Intent event = new Intent(MainActivity.this, JoinPrivateEvent.class);
        startActivity(event);
    }


    public void MySessionsClicked(MenuItem item)
    {
        Intent sessions = new Intent(MainActivity.this, my_sessions.class);
        startActivity(sessions);
    }
    public void goMyEvent(MenuItem view) {
        Intent intent = new Intent(MainActivity.this,my_created_events.class);
        startActivity(intent);
    }
}