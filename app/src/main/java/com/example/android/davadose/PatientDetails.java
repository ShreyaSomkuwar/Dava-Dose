package com.example.android.davadose;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientDetails extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mPUID;
    //String name,dob,mail,height,weight;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String puid=new String();
    PatientsAdapter mPatientsAdapter;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        mPUID=mFirebaseDatabase.getReference().child("Doctors").child(uid).child("Patients");

        mListView=findViewById(R.id.patientListView);

        List<Patients> pDetails=new ArrayList<>();
        mPatientsAdapter=new PatientsAdapter(this, R.layout.item_patients, pDetails);
        mListView.setAdapter(mPatientsAdapter);


        mPUID.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PatientData.pid=dataSnapshot.getValue(String.class);

                mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients").child(PatientData.pid);
                mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("User Details").child("fullname").getValue(String.class);
                        String dob=dataSnapshot.child("User Details").child("dob").getValue(String.class);
                        String height=dataSnapshot.child("Medical Data").child("Height").getValue(String.class);
                        String weight=dataSnapshot.child("Medical Data").child("Weight").getValue(String.class);
                        Patients a= new Patients();
                        a.dob=dob;
                        a.height=height;
                        a.weight=weight;
                        a.name=name;
                        mPatientsAdapter.add(a);
                        /*PatientData.mail=dataSnapshot.child("User Details").child("email").getValue(String.class);
                        TextView mTMail=findViewById(R.id.pdEmail);
                        mTMail.setText(PatientData.mail);
                        PatientData.name=dataSnapshot.child("User Details").child("fullname").getValue(String.class);
                        TextView mTName=findViewById(R.id.pdName);
                        mTName.setText(PatientData.name);
                        PatientData.dob=dataSnapshot.child("User Details").child("dob").getValue(String.class);
                        TextView mTDob=findViewById(R.id.pdDOB);
                        mTDob.setText(PatientData.dob);
                        PatientData.height=dataSnapshot.child("Medical Data").child("Height").getValue(String.class);
                        TextView mTHeight=findViewById(R.id.pdHeight);
                        mTHeight.setText(PatientData.height);
                        PatientData.weight=dataSnapshot.child("Medical Data").child("Weight").getValue(String.class);
                        TextView mTWeight=findViewById(R.id.pdWeight);
                        mTWeight.setText(PatientData.weight);*/

                        /*if(dataSnapshot.child("Medical Data").child("Asthma").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhAsthma);
                            a.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Medical Data").child("BP").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhBP);
                            a.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Medical Data").child("Cancer").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhCancer);
                            a.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Medical Data").child("Diabetic").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhDiabetic);
                            a.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Medical Data").child("Heart Diesease").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhHeart);
                            a.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Medical Data").child("Therapies effecting Immunity").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhImmunity);
                            a.setVisibility(View.VISIBLE);
                        }
                        if(dataSnapshot.child("Medical Data").child("Artificial or Prosthetic Joint").getValue(boolean.class)){
                            TextView a=findViewById(R.id.mhJoint);
                            a.setVisibility(View.VISIBLE);
                        }*/
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


    }
}
