package com.example.android.davadose;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class ConsultedDoctors extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mD;
    DoctorsAdapter mDoctorsAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulted_doctors);

        mListView=findViewById(R.id.doctorsConsulted);

        List<Doctors> dDetails=new ArrayList<>();
        final List<Doctors> demo=new ArrayList<>();
        mDoctorsAdapter=new DoctorsAdapter(this, R.layout.item_doctors, dDetails);
        mListView.setAdapter(mDoctorsAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients").child(uid).child("Doctors");
        mMessagesDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String duid=dataSnapshot.getValue(String.class);
                mD=FirebaseDatabase.getInstance().getReference().child("Doctors").child(duid);
                mD.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Doctors d=new Doctors();
                        d.org=dataSnapshot.child("User Details").child("Organisation").getValue(String.class);
                        d.name=dataSnapshot.child("User Details").child("Full Name").getValue(String.class);
                        d.id=dataSnapshot.child("User Details").child("ID").getValue(String.class);
                        d.phone=dataSnapshot.child("User Details").child("mobile").getValue(String.class);
                        mDoctorsAdapter.add(d);
                        demo.add(d);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Doctors test = demo.get(i);
                    String ph = test.phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Log.v("Shreya", ph + " ");
                    intent.setData(Uri.parse("tel:" + ph));
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                        startActivity(intent);

            }
        });


    }

}
