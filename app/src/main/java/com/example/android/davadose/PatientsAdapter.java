package com.example.android.davadose;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ASUS on 3/25/2018.
 */

public class PatientsAdapter extends ArrayAdapter<Patients> {
    public PatientsAdapter(Context context, int resource, List<Patients> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_patients, parent, false);
        }

        Patients patient=getItem(position);
        TextView name=(TextView)convertView.findViewById(R.id.dpName);
        TextView dob=(TextView)convertView.findViewById(R.id.dpDOB);
        TextView height=(TextView)convertView.findViewById(R.id.dpHeight);
        TextView weight=(TextView)convertView.findViewById(R.id.dpWeight);

        name.setText(patient.name);
        dob.setText(patient.dob);
        height.setText(patient.height);
        weight.setText(patient.weight);

        return convertView;
    }
}
