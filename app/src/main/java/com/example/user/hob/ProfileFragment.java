package com.example.user.hob;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvAge;
    private TextView tvCity;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivity user = (MainActivity) getActivity();
        String name = "Name: " + user.user_name;
        String surname = "Surname: " + user.user_surname;
        String age = "Age: " + user.user_age;
        String city = "City: " + user.user_city;
        tvName = view.findViewById(R.id.tvName);
        tvName.setText(name);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvSurname.setText(surname);
        tvAge = view.findViewById(R.id.tvAge);
        tvAge.setText(age);
        tvCity = view.findViewById(R.id.tvCity);
        tvCity.setText(city);


        return view;
    }

}
