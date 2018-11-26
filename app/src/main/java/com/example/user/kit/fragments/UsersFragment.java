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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.kit.R;
import com.example.user.kit.activities.DetailActivity;
import com.example.user.kit.activities.UserProfileActivity;
import com.example.user.kit.transformations.RoundedCornersTransform;
import com.example.user.kit.models.User;
import com.example.user.kit.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    private MainActivity notifications;
    private UsersFragment.RVAdapter2 adapter;
    private RecyclerView recyclerView;
    private String MY_LOG = "mylog";
    private GridLayoutManager manager;
    private EditText editText;
    private ArrayList<User> searchList = new ArrayList<>();
    String[] items;
    public UsersFragment() {
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
        notifications = (MainActivity) getActivity();
        View view =  inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.rv2);
        editText=(EditText) view.findViewById(R.id.search2);
        initList2();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    // reset listview
                    initList2();
                } else {
                    // perform search
                    searchItem2(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        return view;
    }
    public void searchItem2(String textToSearch){
        ArrayList<User> usersToRemove = new ArrayList<>();
        searchList.clear();
        for(int i = 0;i<notifications.userList.size();i++)
            searchList.add(notifications.userList.get(i));
        for(User user:notifications.userList){
            if(!(user.getName()+ " " + user.getSurname()).toLowerCase().contains(textToSearch.toLowerCase())){
                usersToRemove.add(user);
            }
        }
        searchList.removeAll(usersToRemove);
        adapter.notifyDataSetChanged();
    }
    public void initList2(){
        searchList.clear();
        for(int i = 0;i<notifications.userList.size();i++)
            searchList.add(notifications.userList.get(i));
        adapter = new RVAdapter2(searchList);
        recyclerView.setAdapter(adapter);
    }
    public class RVAdapter2 extends RecyclerView.Adapter<UsersFragment.RVAdapter2.UserViewHolder>{

        List<User> users;

        public RVAdapter2(List<User> users) {
            this.users = users;
        }
        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                //Log.d(MY_LOG, String.valueOf(itemPosition));
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra("info",users.get(itemPosition).getName()+
                        " " + users.get(itemPosition).getSurname() + ", " + users.get(itemPosition).getAge());
                intent.putExtra("city",users.get(itemPosition).getCity());
                intent.putExtra("image",users.get(itemPosition).getUrl());
                startActivity(intent);
            }
        };
        @NonNull
        @Override
        public UsersFragment.RVAdapter2.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card_view,viewGroup,false);
            UsersFragment.RVAdapter2.UserViewHolder userViewHolder = new UsersFragment.RVAdapter2.UserViewHolder(v);
            v.setOnClickListener(onClickListener);
            return userViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UsersFragment.RVAdapter2.UserViewHolder holder, int position) {
            holder.userName.setText(users.get(position).getName()+" " + users.get(position).getSurname());

            Picasso.get().load(users.get(position).getUrl())
                    .fit().transform(new RoundedCornersTransform())
                    .into(holder.imageUser);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            ImageView imageUser;
            TextView userName;
            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cv2);
                imageUser = itemView.findViewById(R.id.imageUser);
                userName = itemView.findViewById(R.id.userName);

            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
    }
}