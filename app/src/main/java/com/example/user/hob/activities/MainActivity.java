package com.example.user.hob.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.user.hob.Event;
import com.example.user.hob.fragments.ChatsFragment;
import com.example.user.hob.fragments.FeedFragment;
import com.example.user.hob.fragments.NotificationsFragment;
import com.example.user.hob.fragments.ProfileFragment;
import com.example.user.hob.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String MY_LOG = "mylog";
    public String JSON_STRING;
    public ArrayList<Event> eventList;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private FeedFragment feedFragment;
    private NotificationsFragment notificationsFragment;
    private ChatsFragment chatsFragment;
    private ProfileFragment profileFragment;
    public String user_name;
    public String user_surname;
    public String user_age;
    public String user_city;
    public String user_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BackgroundTask().execute();
        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        user_surname = intent.getStringExtra("surname");
        user_age = intent.getStringExtra("age");
        user_city = intent.getStringExtra("city");
        user_url = intent.getStringExtra("url");
        eventList = intent.getParcelableArrayListExtra("event");
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        feedFragment = new FeedFragment();
        notificationsFragment = new NotificationsFragment();
        chatsFragment = new ChatsFragment();
        profileFragment = new ProfileFragment();

        setFragment(feedFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_feed:
                        setFragment(feedFragment);
                        return true;
                    case R.id.nav_notifications:
                        setFragment(notificationsFragment);
                        return true;
                    case R.id.nav_chats:
                        setFragment(chatsFragment);
                        return true;
                    case R.id.nav_profile:
                        setFragment(profileFragment);
                        return true;
                    default:
                        return false;

                }
            }
        });
    }
    private class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_URL;
        @Override
        protected void onPreExecute() {
            JSON_URL ="https://jarvizz.000webhostapp.com/json_get_data.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING);
                }
                JSON_STRING = stringBuilder.toString().trim();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return JSON_STRING;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
