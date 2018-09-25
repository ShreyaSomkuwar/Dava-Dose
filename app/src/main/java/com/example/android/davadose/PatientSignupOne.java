package com.example.android.davadose;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class PatientSignupOne extends AppCompatActivity {

    String[] Gender = { "Male", "Female", "Other"};
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mType;
    //private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final int RC_SIGN_IN = 1;

    /*@Override
    public void onBackPressed() {
        System.exit(1);

        return;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup_one);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients");
        mType=mFirebaseDatabase.getReference();

        Button next=(Button)findViewById(R.id.patientSignupOneNext);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();
                EditText fullname=findViewById(R.id.PFullName);
                EditText dob=findViewById(R.id.Pdob);
                EditText PN=findViewById(R.id.PPhN);
                mMessagesDatabaseReference.child(uid).child("User Details").child("fullname").setValue(fullname.getText().toString());
                mMessagesDatabaseReference.child(uid).child("User Details").child("dob").setValue(dob.getText().toString());
                mMessagesDatabaseReference.child(uid).child("User Details").child("phone no").setValue(PN.getText().toString());
                mType.child("Users").child(uid).child("filled").setValue(1);
                Intent activity=new Intent(PatientSignupOne.this, PatientSignupTwo.class);
                startActivityForResult(activity,RC_SIGN_IN);
                Toast.makeText(PatientSignupOne.this,"You have completed Step1!",Toast.LENGTH_SHORT).show();
            }
        });





    mAuthStateListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user != null){
                //onSignedInInitialize(user.getDisplayName());
                String name = user.getDisplayName();
                String email = user.getEmail();
                String uid = user.getUid();
                mType.child("Users").child(uid).child("type").setValue(2);
                mType.child("Users").child(uid).child("uid").setValue(user.getUid());
                mType.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email=user.getUid();
                        DataSnapshot snapshot=dataSnapshot.child("Users").child(email);
                        if(snapshot.hasChild("filled")){
                            int f=snapshot.child("filled").getValue(int.class);
                            if(f==1){
                                Intent activity1=new Intent(PatientSignupOne.this, PatientSignupTwo.class);
                                startActivityForResult(activity1,RC_SIGN_IN);
                            }else if(f==2){
                                Intent activity1=new Intent(PatientSignupOne.this, PatientSignupThree.class);
                                startActivityForResult(activity1,RC_SIGN_IN);
                            }else if(f==3){
                                Intent activity1=new Intent(PatientSignupOne.this, PatientHome.class);
                                startActivityForResult(activity1,RC_SIGN_IN);
                            }
                        }
                        else{
                            mType.child("Users").child(email).child("filled").setValue(0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //String m= mMessagesDatabaseReference.child(uid).child("User Details").child("name").toString();
                    //Toast.makeText(PatientSignupOne.this,"You are now signed in 1!",Toast.LENGTH_SHORT).show();
                    //mMessagesDatabaseReference.child("users").child(uid).child("name").setValue(name);
                    mMessagesDatabaseReference.child(uid).child("User Details").child("email").setValue(email);
                    //mMessagesDatabaseReference.child("users").child(uid).child("registered").setValue(true);
                    //mType.child(uid).child("Patient").setValue("true");
                    //Toast.makeText(PatientSignupOne.this,"You are now signed in 2!",Toast.LENGTH_SHORT).show();


            }
            else{
                //onSignOutCleanup();

                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                );

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
        }
    };

           /*Spinner spin = (Spinner) findViewById(R.id.genderP);
        spin.setOnItemSelectedListener(this.);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);*/

    /* public void showDatePickerDialog(View v) {
         DialogFragment newFragment = new DatePickerFragment();
         newFragment.show(getSupportFragmentManager(), "datePicker");
     }*/

    /*public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }*/

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_CANCELED){
                finish();
                //Toast.makeText(this,"Sign in cancelled!",Toast.LENGTH_SHORT).show();
                //System.exit(1);
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


