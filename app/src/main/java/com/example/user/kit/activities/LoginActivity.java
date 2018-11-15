package com.example.user.kit.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.kit.Event;
import com.example.user.kit.requests.LoginRequest;
import com.example.user.kit.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private String MY_LOG = "mylog";
    public List<Event> eventList = new ArrayList<>();
    public String names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final CircularProgressButton login = (CircularProgressButton) findViewById(R.id.login);
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
                login.startAnimation();
                final String user_username = username.getText().toString();
                final String user_password = password.getText().toString();
                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            System.out.println("success = " + success);
                            if (success) {
                                new ParseAllEvents().execute();
                                AsyncTask<String,String,String> waiter1 = new AsyncTask<String, String, String>() {
                                    @Override
                                    protected String doInBackground(String... voids) {
                                        try{
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
                                            intent.putExtra("names",names);
                                            intent.putParcelableArrayListExtra("event",(ArrayList<? extends Parcelable>) eventList);
                                            LoginActivity.this.startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return "success";
                                    }
                                    @Override
                                    protected void onPostExecute(String s){
                                        if(s.equals("success")){
                                            Toast.makeText(LoginActivity.this, "Welcome " + user_username, Toast.LENGTH_SHORT).show();
                                            login.revertAnimation();
                                        }
                                    }
                                };
                                waiter1.execute();
                            } else {
                                login.revertAnimation();
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
    }
    class ParseAllEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int amountOfPages = 1;
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
                int n = 0;
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("div[class=col50 m-cola] > article[class=b-postcard]");
                for(Element element: elements){
                    image = element.select("img").attr("src");
                    name = element.select("h2[class=title] > a").text();
                    names += name + ",";
                    date = element.select("div[class=when-and-where] > span[class=date]").text();
                    price = element.select("div[class=when-and-where] > span").text();
                    price = price.replace(date,"");
                    location = element.select("div[class=when-and-where]").text();
                    location = location.replace(date,"").replace(price,"");
                    content = element.select("p[class=b-typo]").text();
                    if(content.length() < 115)
                        n = content.length();
                    else
                        n = 115;
                    content = content.substring(0,n)+"...";
                    more = element.select("div[class=more]").text();
                    members = element.select("div[class=more] > span").text();
                    more = more.replace(members,"");
                    page = element.select("div[class=title] > a").attr("href");

                    //Log.d(MY_LOG,image + " ," + name + " ,"+ date+" ," + location+" ," + price + " ,"+ content + " ,"+ more + " ,"+page+"!!!!!");
                    //Log.d(MY_LOG,image +"!!!");
                    eventList.add(new Event(image, name, date,location, price, content, more, page));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}