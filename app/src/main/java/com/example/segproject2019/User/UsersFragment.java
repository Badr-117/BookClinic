package com.example.segproject2019.User;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.segproject2019.R;
import com.example.segproject2019.Services.ClinicService;
import com.example.segproject2019.Services.CustomAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class UsersFragment extends Fragment {

    LinearLayout emptyView;
    DatabaseReference databaseReference;
    UserAdapter adapter;
    ArrayList<User> users;
    ListView userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_users, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        userList = (ListView) v.findViewById(R.id.user_list);
        emptyView = (LinearLayout) v.findViewById(R.id.emptyView_user_layout);
        userList.setEmptyView(emptyView);

        users = new ArrayList<>();

        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                deleteDialog(user.getUserId());
                return true;
            }
        });

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous service list
                users.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting service
                    User user = postSnapshot.getValue(User.class);
                    //adding service to the list
                    users.add(user);
                }

                //creating adapter
                adapter = new UserAdapter(getContext(), users);
                //attaching adapter to the listView
                userList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteDialog(final String id){

        final AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setTitle("Delete user?");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(id);
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.show();
    }

    private boolean deleteUser(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User").child(id);
        //removing service
        dR.removeValue();

        Toast.makeText(getContext(), "user Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}
