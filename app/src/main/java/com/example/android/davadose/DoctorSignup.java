package com.example.android.davadose;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class DoctorSignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] Gender = { "Male", "Female", "Other"};
    RadioButton yes,no;
    LinearLayout clinic;
    public static final int RC_SIGN_IN = 1;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mType;
    //private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

   /* @Override
    public void onBackPressed() {
        finish();
        return;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Doctors");
        mType=mFirebaseDatabase.getReference();

        Button submit=(Button)findViewById(R.id.doctorSignupSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();
                EditText fullname=findViewById(R.id.drFullName);
                EditText mail=findViewById(R.id.drEmail);
                EditText age=findViewById(R.id.drAge);
                EditText id=findViewById(R.id.drId);
                EditText org=findViewById(R.id.drOrg);
                EditText ph=findViewById(R.id.drPhone);
                mMessagesDatabaseReference.child(uid).child("User Details").child("Full Name").setValue(fullname.getText().toString());
                //mMessagesDatabaseReference.child(uid).child("User Details").setValue(mail.getText().toString());
                mMessagesDatabaseReference.child(uid).child("User Details").child("age").setValue(age.getText().toString());
                mMessagesDatabaseReference.child(uid).child("User Details").child("ID").setValue(id.getText().toString());
                mMessagesDatabaseReference.child(uid).child("User Details").child("Organisation").setValue(org.getText().toString());
                mMessagesDatabaseReference.child(uid).child("User Details").child("mobile").setValue(ph.getText().toString());
                //mMessagesDatabaseReference.child(uid).child("User Details").child("filled").setValue(1);
                mType.child("Users").child(uid).child("filled").setValue(1);
                Intent activity=new Intent(DoctorSignup.this, DoctorHome.class);
                startActivityForResult(activity,RC_SIGN_IN);
                Toast.makeText(DoctorSignup.this,"You have filled the Signup Info!",Toast.LENGTH_SHORT).show();
            }
        });
        yes = (RadioButton)findViewById(R.id.pcy);
        no = (RadioButton)findViewById(R.id.pcn);
        clinic=(LinearLayout) findViewById(R.id.privateClinic);
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    clinic.setVisibility(View.VISIBLE);
                    //rb2.setChecked(false);
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    clinic.setVisibility(View.GONE);
                    //rb2.setChecked(false);
                }
            }
        });



        Spinner spin = (Spinner) findViewById(R.id.gender);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user != null){
                    //onSignedInInitialize(user.getDisplayName());
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    String uid = user.getUid();

                    //mType.child(uid).child("Profile").setValue(1);
                    //CheckDoctorInfo info=new CheckDoctorInfo();
                    mMessagesDatabaseReference.child(uid).child("User Details").child("email").setValue(email);
                    mType.child("Users").child(uid).child("type").setValue(1);
                    Log.v("Shreya","h");
                    mType.child("Users").child(uid).child("email").setValue(user.getEmail());
                    mType.addValueEventListener(new ValueEventListener() {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email=user.getUid();
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DataSnapshot snapshot=dataSnapshot.child("Users").child(email);
                            int f;
                            if(snapshot.hasChild("filled")){
                                f=snapshot.child("filled").getValue(int.class);
                                if(f==1){
                                    Intent activity=new Intent(DoctorSignup.this, DoctorHome.class);
                                    startActivityForResult(activity,RC_SIGN_IN);
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

                    //String m= mMessagesDatabaseReference.child(uid).child("User Details").child("email").toString();
                        //Toast.makeText(DoctorSignup.this,"You are now signed in 1!",Toast.LENGTH_SHORT).show();
                        //mMessagesDatabaseReference.child("users").child(uid).child("name").setValue(name);

                        //mMessagesDatabaseReference.child("users").child(uid).child("registered").setValue(true);
                        //mType.child(uid).child("Doctor").setValue("true");
                        //Toast.makeText(DoctorSignup.this,"You are now signed in 2!",Toast.LENGTH_SHORT).show();


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
                    /*CheckDoctorInfo info1=new CheckDoctorInfo();
                    if(info1.getFilled()==1){
                        Intent activity=new Intent(DoctorSignup.this, DoctorHome.class);
                        startActivityForResult(activity,RC_SIGN_IN);
                    }*/
                }
            }
        };
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                finish();
                //Toast.makeText(this,"Sign in cancelled!",Toast.LENGTH_SHORT).show();
                //System.exit(1);
            }
        }
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getApplicationContext(),country[position] ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
