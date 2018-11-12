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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String MY_LOG = "mylog";
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

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
