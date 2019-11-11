package com.example.segproject2019.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.segproject2019.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ClinicService> {

    ArrayList<ClinicService> clinicServices;
    Context context;

    public CustomAdapter(Context context, ArrayList<ClinicService> clinicServices) {
        super(context, R.layout.service_content_list, clinicServices);
        this.context = context;
        this.clinicServices = clinicServices;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.service_content_list, parent, false);
        }

        TextView serviceTextView = convertView.findViewById(R.id.service_tv);
        TextView roleTextView = convertView.findViewById(R.id.role_tv);


        final ClinicService s = (ClinicService) this.getItem(position);

        serviceTextView.setText("Service: " +s.getService());
        roleTextView.setText("Role: " +s.getRole());

        return convertView;
    }

}
