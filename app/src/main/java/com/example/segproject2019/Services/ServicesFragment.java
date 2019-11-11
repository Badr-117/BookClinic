package com.example.segproject2019.Services;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.segproject2019.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ServicesFragment extends Fragment {


    Context mContext;
    LinearLayout emptyView;
    DatabaseReference databaseReference;
    CustomAdapter adapter;
    ArrayList<ClinicService> clinicServices;
    ListView serviceList;
    ClinicService clinicService;

    EditText serviceTxt, editServicetxt;
    Spinner roleSpinner, editRoleSpinner;

    String role;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

        serviceList = (ListView) v.findViewById(R.id.service_list);
        emptyView = (LinearLayout) v.findViewById(R.id.emptyView_layout);
        serviceList.setEmptyView(emptyView);

        clinicServices = new ArrayList<>();

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceList.smoothScrollToPosition(4);
                displayInputDialog();
            }
        });

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClinicService clinicService = clinicServices.get(position);
                UpdateAndDeleteDialog(clinicService.getId());
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    private void addService(String service, String role) {
        //checking if the value is provided
        if (!TextUtils.isEmpty(service)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our service
            String id = databaseReference.push().getKey();

            //creating a service Object
            ClinicService newService = new ClinicService(id, service, role);

            //Saving the service
            databaseReference.child(id).setValue(newService);

            //displaying a success toast
            Toast.makeText(mContext, "Service added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(mContext, "Please enter a service", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous service list
                clinicServices.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting service
                    ClinicService service = postSnapshot.getValue(ClinicService.class);
                    //adding service to the list
                    clinicServices.add(service);
                }

                //creating adapter
                CustomAdapter serviceAdapter = new CustomAdapter(mContext, clinicServices);
                //attaching adapter to the listView
                serviceList.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //dialog add service and role
    private void displayInputDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Add");
        dialog.setContentView(R.layout.add_service);

        serviceTxt = dialog.findViewById(R.id.add_service);

        roleSpinner = dialog.findViewById(R.id.add_role_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext, R.array.roles,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(arrayAdapter);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("doctor")) {
                        role = "doctor";
                    } else if (selection.equals("nurse")) {
                        role = "nurse";
                    } else {
                        role = "staff";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                role = "staff";
            }
        });

        Button mSaveBtn = dialog.findViewById(R.id.saveBtn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myService = serviceTxt.getText().toString();
                String myRole = role;
                //String id = helper.databaseReference.push().getKey();

                clinicService = new ClinicService(null, myService, myRole);

                if (myService != null && myService.length() > 0) {
                    //save data to firebase
                    addService(myService, myRole);
                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Name Must Not Be Empty Please", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }


    //dialog update and delete service and role
    private void UpdateAndDeleteDialog(final String serviceId) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Edit");
        dialog.setContentView(R.layout.edit_service);

        editServicetxt = dialog.findViewById(R.id.edit_service);

        editRoleSpinner = dialog.findViewById(R.id.update_role_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext, R.array.roles,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editRoleSpinner.setAdapter(arrayAdapter);
        editRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("doctor")) {
                        role = "doctor";
                    } else if (selection.equals("nurse")) {
                        role = "nurse";
                    } else {
                        role = "staff";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                role = "staff";
            }
        });

        Button mUpdateBtn = dialog.findViewById(R.id.updateBtn);

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myService = editServicetxt.getText().toString();
                String myRole = role;

                if (myService != null && myService.length() > 0) {
                    //save data to firebase
                    if (updateService(serviceId, myService, role)) {
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getContext(), "Name Must Not Be Empty Please", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mDeleteBtn = dialog.findViewById(R.id.deleteBtn);

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService(serviceId);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private boolean updateService(String id, String service, String role) {
        //getting the specified service reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Services").child(id);

        //updating service
        ClinicService artist = new ClinicService(id, service, role);
        dR.setValue(artist);
        Toast.makeText(mContext, "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteService(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Services").child(id);
        //removing service
        dR.removeValue();

        Toast.makeText(mContext, "service Deleted", Toast.LENGTH_LONG).show();

        return true;
    }


}