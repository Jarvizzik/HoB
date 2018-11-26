package com.example.user.kit.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.kit.R;
import com.example.user.kit.transformations.RoundedCornersTransform;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {
    private String MY_LOG = "mylog";
    private TextView userName;
    private TextView userCity;
    private ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.tvName);
        userCity = findViewById(R.id.tvCity);
        userImage = findViewById(R.id.imageView);
        userName.setText(getIntent().getStringExtra("info"));
        userCity.setText(getIntent().getStringExtra("city"));
        String image = getIntent().getStringExtra("image");
        Picasso.get().load(image).fit().transform(new RoundedCornersTransform()).into(userImage);
    }
}
