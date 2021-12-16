package com.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView firstNameLastNameProfileFragment;
        TextView userNameProfileFragment;
        TextView emailProfileFragment;
        TextView phoneProfileFragment;
        ImageView profileImage;
        profileImage = rootView.findViewById(R.id.profileImageSource);
        firstNameLastNameProfileFragment = rootView.findViewById(R.id.firstNameLastNameTextView);
        userNameProfileFragment = rootView.findViewById(R.id.userNameTextViewProfileFragment);
        emailProfileFragment = rootView.findViewById(R.id.emailTextViewProfileFragment);
        phoneProfileFragment = rootView.findViewById(R.id.phoneNumberProfileFragment);
        SharedPreferences shP = getActivity().getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String userName = shP.getString("username","");
        String firstName = shP.getString("firstname","");
        String lastName = shP.getString("lastname","");
        String email = shP.getString("email","");
        String avatarUrl = shP.getString("avatar","");
        String phoneNumber = shP.getString("phonenumber","");
        firstNameLastNameProfileFragment.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
        if(!avatarUrl.equals("")){
            Picasso.get().load("https://shanbe-back.herokuapp.com"+avatarUrl).placeholder(R.drawable.acount_circle).error(R.drawable.acount_circle).into(profileImage);
        }
        if(!userName.equals("")){
            userNameProfileFragment.setText(userName);
        }
        else{
            userNameProfileFragment.setText("NO USERNAME");
        }
        if(!email.equals("")){
            emailProfileFragment.setText(email);
        }
        else{
            emailProfileFragment.setText("NO EMAIL");
        }
        if(!phoneNumber.equals("")){
            phoneProfileFragment.setText(phoneNumber);
        }
        else{
            phoneProfileFragment.setText("NO PHONE");
        }
        return rootView;
    }
}