package com.example.android.davadose;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CharacterSelection extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mD,mP;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    /*@Override
    public void onBackPressed() {
        finish();

        return;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference();
        mD=mFirebaseDatabase.getReference();
        mP=mFirebaseDatabase.getReference();


        ImageButton doctor = (ImageButton)findViewById(R.id.doctor);
        ImageButton patient=(ImageButton)findViewById(R.id.patient);

                doctor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                Intent activity=new Intent(CharacterSelection.this, DoctorSignup.class);
                startActivityForResult(activity,1);
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity=new Intent(CharacterSelection.this, PatientSignupOne.class);
                startActivityForResult(activity,1);
            }
        });

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user !=null){
                    mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid=user.getUid();
                            int filled,type=dataSnapshot.child("Users").child(uid).child("type").getValue(int.class);
                            if(type==1){
                                filled=dataSnapshot.child("Users").child(uid).child("filled").getValue(int.class);
                                if(filled==0){
                                    Intent activity=new Intent(CharacterSelection.this, DoctorSignup.class);
                                    startActivityForResult(activity,1);
                                }else if(filled==1){
                                    Intent activity=new Intent(CharacterSelection.this, DoctorHome.class);
                                    startActivityForResult(activity,1);
                                }
                            }else if(type==2){
                                filled=dataSnapshot.child("Users").child(uid).child("filled").getValue(int.class);
                                if(filled==0){
                                    Intent activity1=new Intent(CharacterSelection.this, PatientSignupOne.class);
                                    startActivityForResult(activity1,1);
                                }else if(filled==1){
                                    Intent activity1=new Intent(CharacterSelection.this, PatientSignupTwo.class);
                                    startActivityForResult(activity1,1);
                                }else if(filled==2){
                                    Intent activity1=new Intent(CharacterSelection.this, PatientSignupThree.class);
                                    startActivityForResult(activity1,1);
                                }else if(filled==3){
                                    Intent activity1=new Intent(CharacterSelection.this, PatientHome.class);
                                    startActivityForResult(activity1,1);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }



    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            if(resultCode == RESULT_CANCELED){
                finish();
                //System.exit(1);
                //Toast.makeText(this,"Sign in cancelled!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

}
