package com.example.user.hob.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.hob.R;
import com.example.user.hob.activities.MainActivity;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {
    private String my_log = "mylog";
    private TextView js;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        MainActivity notifications = (MainActivity) getActivity();
        Log.d(my_log,notifications.JSON_STRING);
        String json = notifications.JSON_STRING;
        js= (TextView) view.findViewById(R.id.json);
        js.setText(json.toString());

        return view;
    }

}
