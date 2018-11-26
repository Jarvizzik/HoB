package com.example.user.kit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.kit.R;
import com.example.user.kit.transformations.RoundedCornersTransform;
import com.example.user.kit.activities.MainActivity;
import com.squareup.picasso.Picasso;


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
        String name = user.user_name;
        String surname = user.user_surname;
        String age = user.user_age;
        String city =  user.user_city;
        String url = user.user_url;


        tvName = view.findViewById(R.id.tvName);
        tvName.setText(name + " " + surname+", "+ age);
        tvCity = view.findViewById(R.id.tvCity);
        tvCity.setText(city);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get().load(url)
                .fit().transform(new RoundedCornersTransform()).into(imageView);

        return view;
    }
}
