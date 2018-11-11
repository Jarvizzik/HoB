package com.example.user.hob;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvAge;
    private TextView tvCity;
    private ImageView ivUrl;

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
        String url = user.user_url;

        System.out.println("URLLLL = " + url);

        tvName = view.findViewById(R.id.tvName);
        tvName.setText(name);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvSurname.setText(surname);
        tvAge = view.findViewById(R.id.tvAge);
        tvAge.setText(age);
        tvCity = view.findViewById(R.id.tvCity);
        tvCity.setText(city);

        new DownloadImageTask((ImageView) view.findViewById(R.id.imageView))
                .execute(url);

        return view;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
