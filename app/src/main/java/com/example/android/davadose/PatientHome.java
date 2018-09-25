package com.example.android.davadose;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PatientHome extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    //TextView[] arr=new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);


        //NotificationScheduler.setReminder(PatientHome.this,AlarmReceiver.class,10,42);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference();

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();
                String m=dataSnapshot.child("Patients").child(uid).child("Medicine").child("Breakfast Time").getValue(String.class);
                String a=dataSnapshot.child("Patients").child(uid).child("Medicine").child("Lunch Time").getValue(String.class);
                String d=dataSnapshot.child("Patients").child(uid).child("Medicine").child("Dinner Time").getValue(String.class);
                Reminder.setAlarm(PatientHome.this,m,a,d);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Button signout=findViewById(R.id.signOutP);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(PatientHome.this);
                //System.exit(1);
                finish();
            }
        });
        Button doc=findViewById(R.id.consultedD);
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity=new Intent(PatientHome.this, ConsultedDoctors.class);
                startActivityForResult(activity,1);
            }
        });
        Button presc=findViewById(R.id.myPrescription);
        presc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity=new Intent(PatientHome.this, MyPrescription.class);
                startActivityForResult(activity,1);
            }
        });
        Button dosage=findViewById(R.id.myDosage);
        dosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity=new Intent(PatientHome.this, MyDosage.class);
                startActivityForResult(activity,1);
            }
        });
        /*TextView a1=findViewById(R.id.pPrescription1);
        TextView a2=findViewById(R.id.pPrescription2);
        TextView a3=findViewById(R.id.pPrescription3);
        TextView a4=findViewById(R.id.pPrescription4);
        TextView a5=findViewById(R.id.pPrescription5);
        arr[0]=a1;
        arr[1]=a2;
        arr[2]=a3;
        arr[3]=a4;
        arr[4]=a5;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid=user.getUid();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients").child(uid).child("Prescription");*/
        /*mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String prescription=dataSnapshot.child("Patients").child(uid).child("Prescription").getValue(String.class);
                TextView pres=findViewById(R.id.pPrescription);
                Log.v("h","hello"+prescription);
                pres.setText(prescription);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

       /* mMessagesDatabaseReference.addChildEventListener(new ChildEventListener() {
            int i=0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String prescription=dataSnapshot.getValue(String.class);
                //TextView pres=arr[i++];
                Log.v("h","hello"+prescription);
                //pres.setText(prescription);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

}
