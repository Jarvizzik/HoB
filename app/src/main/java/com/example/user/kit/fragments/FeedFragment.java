package com.example.user.kit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.kit.activities.DetailActivity;
import com.example.user.kit.models.Event;
import com.example.user.kit.R;
import com.example.user.kit.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private MainActivity feed;
    private RVAdapter adapter;
    private RecyclerView recyclerView;
    private String MY_LOG = "mylog";
    private EditText editText;
    private ArrayList<Event> searchList = new ArrayList<>();
    public FeedFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        feed = (MainActivity) getActivity();
        View view =  inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = view.findViewById(R.id.rv);
        editText=(EditText) view.findViewById(R.id.search);
        /*Button b = (Button)view.findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String kek = "Rostyk";
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("myName",kek);
                startActivity(intent);

            }

        });
        */
        initList();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    // reset listview
                    initList();
                } else {
                    // perform search
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        return view;
    }
    public void searchItem(String textToSearch){
        ArrayList<Event> eventsToRemove = new ArrayList<>();
        searchList.clear();
        for(int i = 0;i<feed.eventList.size();i++)
            searchList.add(feed.eventList.get(i));
        for(Event event:feed.eventList){
            if(!event.getEventMore().toLowerCase().contains(textToSearch.toLowerCase())){
                eventsToRemove.add(event);
            }
        }
        searchList.removeAll(eventsToRemove);
        adapter.notifyDataSetChanged();
    }
    public void initList(){
        searchList.clear();
        for(int i = 0;i<feed.eventList.size();i++)
            searchList.add(feed.eventList.get(i));
        adapter = new RVAdapter(searchList);
        recyclerView.setAdapter(adapter);
    }
    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder>{

        List<Event> events;

        public RVAdapter(List<Event> events) {
            this.events = events;
        }
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                //Log.d(MY_LOG, String.valueOf(itemPosition));
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("page",events.get(itemPosition).getEventPage());
                intent.putExtra("name",events.get(itemPosition).getEventName());
                intent.putExtra("image",events.get(itemPosition).getEventImage());
                intent.putExtra("tag",events.get(itemPosition).getEventMore());
                startActivity(intent);
            }
        };
        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
            EventViewHolder eventViewHolder = new EventViewHolder(v);
            v.setOnClickListener(onClickListener);
            return eventViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            holder.eventName.setText(events.get(position).getEventName());
            holder.eventDate.setText(events.get(position).getEventDate());
            holder.eventLocation.setText(events.get(position).getEventLocation());
            holder.eventPrice.setText(events.get(position).getEventPrice());
            holder.eventContent.setText(events.get(position).getEventContent());
            holder.eventMore.setText(events.get(position).getEventMore());
            holder.eventPage.setText(events.get(position).getEventPage());

            Picasso.get().load(events.get(position).getEventImage())
                    .fit()
                    .into(holder.eventImage);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public class EventViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            ImageView eventImage;
            TextView eventName,eventDate,eventLocation,eventPrice,eventContent,eventMore,eventPage;
            public EventViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cv);
                eventImage = itemView.findViewById(R.id.imageEvent);
                eventName = itemView.findViewById(R.id.eventName);
                eventDate = itemView.findViewById(R.id.eventDate);
                eventLocation = itemView.findViewById(R.id.eventLocation);
                eventPrice = itemView.findViewById(R.id.eventPrice);
                eventContent = itemView.findViewById(R.id.eventContent);
                eventMore = itemView.findViewById(R.id.eventMore);
                eventPage = itemView.findViewById(R.id.eventPage);

            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
    }
}
