package com.example.android.davadose;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ASUS on 3/26/2018.
 */

public class MyDosageAdapter extends ArrayAdapter<MedicineItem> {
    public MyDosageAdapter(Context context, int resource, List<MedicineItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_medicine, parent, false);
        }

        MedicineItem curr=getItem(position);

        TextView name=convertView.findViewById(R.id.miName);
        TextView morn=convertView.findViewById(R.id.miMorn);
        TextView aftern=convertView.findViewById(R.id.miAftern);
        TextView night=convertView.findViewById(R.id.miNight);
        TextView before=convertView.findViewById(R.id.miBefore);
        TextView after=convertView.findViewById(R.id.miAfter);

        morn.setBackgroundColor(convertView.getResources().getColor(R.color.defaultScreen));
        aftern.setBackgroundColor(convertView.getResources().getColor(R.color.defaultScreen));
        night.setBackgroundColor(convertView.getResources().getColor(R.color.defaultScreen));
        before.setBackgroundColor(convertView.getResources().getColor(R.color.defaultScreen));
        after.setBackgroundColor(convertView.getResources().getColor(R.color.defaultScreen));
        
        name.setText(curr.name);
        if(curr.morning)
            morn.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        if(curr.afternoon)
            aftern.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        if(curr.night)
            night.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        if(curr.isafter)
            after.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        else
            before.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));

        return convertView;
    }
}
