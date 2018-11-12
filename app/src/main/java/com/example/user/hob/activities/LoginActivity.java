package com.example.user.hob.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.hob.Event;
import com.example.user.hob.requests.LoginRequest;
import com.example.user.hob.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private String MY_LOG = "mylog";
    public List<Event> eventList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);
        final TextView registerLink = (TextView) findViewById(R.id.registerHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_username = username.getText().toString();
                final String user_password = password.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String user_name = jsonResponse.getString("name");
                                String user_surname = jsonResponse.getString("surname");
                                String user_age = jsonResponse.getString("age");
                                String user_city = jsonResponse.getString("city");
                                String user_url = jsonResponse.getString("url");


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("name", user_name);
                                intent.putExtra("surname", user_surname);
                                intent.putExtra("age", user_age);
                                intent.putExtra("city", user_city);
                                intent.putExtra("url", user_url);
                                intent.putParcelableArrayListExtra("event",(ArrayList<? extends Parcelable>) eventList);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(user_username, user_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
        new ParseAllEvents().execute();
    }
    class ParseAllEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int amountOfPages = 14;
            for(int i = 1;i <= amountOfPages; i++){
                String urlOfPage = "https://dou.ua/calendar/page-" + i;
                itemEvents(urlOfPage);
            }
            System.out.println(eventList.toString());
            return null;

        }


        private void itemEvents(String url){
            try {
                String image, name, date,location, price, content, more, page;
                String members;
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("div[class=col50 m-cola] > article[class=b-postcard]");
                for(Element element: elements){
                    image = element.select("img").attr("src");
                    name = element.select("h2[class=title] > a").text();
                    date = element.select("div[class=when-and-where] > span[class=date]").text();
                    price = element.select("div[class=when-and-where] > span").text();
                    price = price.replace(date,"");
                    location = element.select("div[class=when-and-where]").text();
                    location = location.replace(date,"").replace(price,"");
                    content = element.select("p[class=b-typo]").text();
                    more = element.select("div[class=more]").text();
                    members = element.select("div[class=more] > span").text();
                    more = more.replace(members,"");
                    page = element.select("div[class=title] > a").attr("href");

                    //Log.d(MY_LOG,image + " ," + name + " ,"+ date+" ," + location+" ," + price + " ,"+ content + " ,"+ more + " ,"+page+"!!!!!");

                    eventList.add(new Event(image, name, date,location, price, content, more, page));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}