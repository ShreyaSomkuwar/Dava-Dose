package com.example.android.davadose;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.firebase.ui.auth.AuthUI;

public class DoctorHome extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        Button signout=findViewById(R.id.signOutD);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(DoctorHome.this);
                finish();

            }
        });

        Button newPrescription=findViewById(R.id.newPrescription);
        newPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity=new Intent(DoctorHome.this, NewPrescriptionActivity.class);
                startActivityForResult(activity,1);
            }
        });

        Button patientDetails=findViewById(R.id.patientDetails);
        patientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity=new Intent(DoctorHome.this, PatientDetails.class);
                startActivityForResult(activity,1);
            }
        });
    }

}
