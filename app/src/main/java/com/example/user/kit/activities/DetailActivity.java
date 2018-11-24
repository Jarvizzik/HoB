package com.example.user.kit.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.kit.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private String page;
    private String MY_LOG = "mylog";
    private TextView eventName;
    private ImageView eventImage;
    private TextView eventInfo;
    private TextView eventDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        eventName = findViewById(R.id.eventName);
        eventImage = findViewById(R.id.eventImage);
        eventInfo = findViewById(R.id.eventInfo);
        eventDetail = findViewById(R.id.eventDetail);
        page = getIntent().getStringExtra("page");
        Log.d(MY_LOG, page);
        eventName.setText(getIntent().getStringExtra("name"));
        new EventLoad().execute();
    }

    class EventLoad extends AsyncTask<Void, Void, Void> {
        String image,details,info;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(page).get();
                Elements elements = doc.select("div[class=cell g-right-shadowed mobtab-maincol]");
                for (Element element : elements) {
                    image = element.select("img").attr("srcset");
                    image = image.substring(0, image.length() - 5);
                    Elements es1 = doc.select("div[class=event-info] > div[class=event-info-row]");
                    int k = 0;
                    for(Element e1: es1) {
                        if(k>=es1.size()-1)
                            break;
                        info += e1.text() + "\n";
                        k++;
                    }
                    info = info.replace("null","").replace("ПідуЯ иду", "");
                    Elements es = doc.select("div[class=cell g-right-shadowed mobtab-maincol] > article[class=b-typo]");
                    String[] detailParts = es.text().split("●");
                    for(int i = 0;i<detailParts.length;i++)
                        details += detailParts[i] + "\n";
                    details = details.replace("null","");
                    //Log.d(MY_LOG,info);
                   // Log.d(MY_LOG,details);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Picasso.get().load(image).fit().into(eventImage);
            eventInfo.setText(info);
            eventDetail.setText(details);
        }
    }
}
