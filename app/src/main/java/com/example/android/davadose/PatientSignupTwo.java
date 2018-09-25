package com.example.android.davadose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientSignupTwo extends AppCompatActivity {

    boolean isAsthma,isJoint,isBp,isCancer,isDiabetes,isImmune,isHeart;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup_two);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients");
        mType=mFirebaseDatabase.getReference();

        Button next=(Button)findViewById(R.id.patientSignupTwoNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("Asthma").setValue(isAsthma);
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("BP").setValue(isBp);
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("Cancer").setValue(isCancer);
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("Diabetic").setValue(isDiabetes);
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("Heart Diesease").setValue(isHeart);
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("Therapies effecting Immunity").setValue(isImmune);
                    mMessagesDatabaseReference.child(uid).child("Medical Data").child("Artificial or Prosthetic Joint").setValue(isJoint);
                mType.child("Users").child(uid).child("filled").setValue(2);
                Intent activity=new Intent(PatientSignupTwo.this, PatientSignupThree.class);
                startActivityForResult(activity,1);
                Toast.makeText(PatientSignupTwo.this,"You have completed Step 2!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            if(resultCode == RESULT_CANCELED){
                finish();
                //Toast.makeText(this,"Sign in cancelled!",Toast.LENGTH_SHORT).show();
                //System.exit(1);
            }
        }

    }

    public void asthma(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.asthmaY:
                if (checked)
                    isAsthma = true;
                    break;
            case R.id.asthmaN:
                if (checked)
                    isAsthma = false;
                    break;

        }
    }

    public void bp(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.BPY:
                if (checked)
                    isBp = true;
                    break;


            case R.id.BPN:
                if (checked)
                    isBp = false;
                    break;

        }

    }

    public void immune(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.immuneY:
                if (checked)
                    isImmune = true;
                    break;
            case R.id.immuneN:
                if (checked)
                    isImmune = false;
                    break;
        }
    }

    public void joint(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.jointY:
                if (checked)
                    isJoint = true;
                    break;
            case R.id.jointN:
                if (checked)
                    isJoint = false;
                    break;
        }
    }

    public void diabetic(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked){
            isDiabetes = true;
        }
        else{
            isDiabetes = false;
        }
    }

    public void cancer(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked){
            isCancer = true;
        }
        else{
            isCancer = false;
        }
    }

    public void heart(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if(checked){
            isHeart = true;
        }
        else{
            isHeart = false;
        }
    }
}
