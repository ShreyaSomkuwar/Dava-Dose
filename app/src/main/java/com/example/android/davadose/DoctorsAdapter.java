package com.example.android.davadose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ASUS on 3/25/2018.
 */

public class DoctorsAdapter extends ArrayAdapter<Doctors> {
    public DoctorsAdapter(Context context, int resource, List<Doctors> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_doctors, parent, false);

        }

        LinearLayout wrapper=(LinearLayout)convertView;

        Doctors doctor=getItem(position);
        TextView name=(TextView)convertView.findViewById(R.id.pdName);
        TextView org=(TextView)convertView.findViewById(R.id.pdOrg);
        TextView id=(TextView)convertView.findViewById(R.id.pdId);
        //ImageView ph=(ImageView)convertView.findViewById(R.id.callDr);

        name.setText(doctor.name);
        org.setText(doctor.org);
        id.setText(doctor.id);



        return convertView;
    }
}
