package com.example.segproject2019.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.segproject2019.R;
import com.example.segproject2019.Services.ClinicService;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    ArrayList<User> users;
    Context context;

    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.user_content_list, users);
        this.context = context;
        this.users = users;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_content_list, parent, false);
        }

        TextView userNameTextView = convertView.findViewById(R.id.userName_tv);
        TextView EmailTextView = convertView.findViewById(R.id.userEmail_tv);
        TextView typeTextView = convertView.findViewById(R.id.userType_tv);




        final User s = (User) this.getItem(position);

        userNameTextView.setText("UserName: " + s.getUsername());
        EmailTextView.setText("Email: " +s.getUserEmail());
        typeTextView.setText("Type: " +s.getUserType());



        return convertView;
    }

}
