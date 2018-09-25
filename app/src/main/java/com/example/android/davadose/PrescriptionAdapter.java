package com.example.android.davadose;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ASUS on 3/25/2018.
 */

public class PrescriptionAdapter extends ArrayAdapter<Prescription> {
    public PrescriptionAdapter(Context context, int resource, List<Prescription> objects) {
        super(context,resource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_prescription, parent, false);
        }

        Prescription p=getItem(position);

        TextView dr=(TextView)convertView.findViewById(R.id.byDName);
        TextView re=(TextView)convertView.findViewById(R.id.remarks);
        TextView da=(TextView)convertView.findViewById(R.id.consulted_on);

        dr.setText(p.by);
        re.setText(p.description);
        p.date.setYear(18);
        String date=p.date.getDate()+"-"+p.date.getMonth()+"-"+p.date.getYear()+", "+p.date.getHours()+":"+p.date.getMinutes();
        da.setText(date);

        return convertView;
    }
}
