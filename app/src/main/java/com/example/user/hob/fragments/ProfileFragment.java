package com.example.user.hob.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.hob.R;
import com.example.user.hob.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvAge;
    private TextView tvCity;
    private ImageView imageView;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivity user = (MainActivity) getActivity();
        String name = "  Name: " + user.user_name;
        String surname = "  Surname: " + user.user_surname;
        String age = "  Age: " + user.user_age;
        String city = "  City: " + user.user_city;
        String url = user.user_url;


        tvName = view.findViewById(R.id.tvName);
        tvName.setText(name);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvSurname.setText(surname);
        tvAge = view.findViewById(R.id.tvAge);
        tvAge.setText(age);
        tvCity = view.findViewById(R.id.tvCity);
        tvCity.setText(city);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get().load(url)
                .fit().centerCrop().into(imageView);

        return view;
    }
}
