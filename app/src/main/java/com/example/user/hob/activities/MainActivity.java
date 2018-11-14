package com.example.user.hob.activities;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.user.hob.User;
import com.example.user.hob.fragments.ChatsFragment;
import com.example.user.hob.fragments.FeedFragment;
import com.example.user.hob.fragments.UsersFragment;
import com.example.user.hob.fragments.ProfileFragment;
import com.example.user.hob.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String MY_LOG = "mylog";
    public String json_users;
    public ArrayList<Event> eventList;
    public ArrayList<User> userList  = new ArrayList<>();
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private FeedFragment feedFragment;
    private UsersFragment usersFragment;
    private ChatsFragment chatsFragment;
    private ProfileFragment profileFragment;
    public String user_name;
    public String user_surname;
    public String user_age;
    public String user_city;
    public String user_url;
    public String event_names;

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
        event_names = intent.getStringExtra("names");
        eventList = intent.getParcelableArrayListExtra("event");
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        feedFragment = new FeedFragment();
        usersFragment = new UsersFragment();
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
                    case R.id.nav_users:
                        setFragment(usersFragment);
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
    private class BackgroundTask extends AsyncTask<Void,Void,Void> {
        String JSON_URL;
        @Override
        protected void onPreExecute() {
            JSON_URL ="https://jarvizz.000webhostapp.com/json_get_data.php";
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((json_users = bufferedReader.readLine()) != null){
                    stringBuilder.append(json_users);
                }
                json_users = stringBuilder.toString().trim();
                System.out.println("DADASDASDAS   " + json_users);
                JSONObject jsondata= new JSONObject(json_users);
                JSONArray JA = jsondata.getJSONArray("users");
                System.out.println(JA);
                for(int i =0 ;i < JA.length(); i++){
                    JSONObject jsonObject = (JSONObject) JA.get(i);
                    String name = (String) jsonObject.get("name");
                    String surname = (String) jsonObject.get("surname");
                    int age = Integer.parseInt((String)jsonObject.get("age"));
                    String city = (String) jsonObject.get("city");
                    String username = (String) jsonObject.get("username");
                    String imageUrl = (String) jsonObject.get("url");
                    Log.d(MY_LOG,"USER #"+ (i+1) + " : " + name + " , " + surname + " , " + age + " , " + city + " , " + imageUrl + " , " + username +" !!!!");
                    userList.add(new User(name,surname,age,city,imageUrl,username));
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
