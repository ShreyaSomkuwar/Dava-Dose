package com.example.android.davadose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class PatientSignupThree extends AppCompatActivity {

    int breakfastTime,lunchTime,dinnerTime;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup_three);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients");
        mType=mFirebaseDatabase.getReference();

        Button submit=findViewById(R.id.patientSignupThreeSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView height=findViewById(R.id.height);
                TextView weight=findViewById(R.id.weight);

                TextView bT=findViewById(R.id.breakfastTime);
                TextView lT=findViewById(R.id.lunchTime);
                TextView dT=findViewById(R.id.dinnerTime);

                String breakfast=new String();
                String lunch=new String();
                String dinner=new String();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();

                String heightS=height.getText().toString();
                String weightS=weight.getText().toString();

                mMessagesDatabaseReference.child(uid).child("Medical Data").child("Height").setValue(heightS);
                mMessagesDatabaseReference.child(uid).child("Medical Data").child("Weight").setValue(weightS);

                if(breakfastTime==0){breakfast = bT.getText() + " AM";}
                else if(breakfastTime==1){breakfast = bT.getText() + " PM";}
                if(lunchTime==0){lunch = lT.getText() + " AM";}
                else if(lunchTime==1){lunch = lT.getText() + " PM";}
                if(dinnerTime==0){dinner =dT.getText() + " AM";}
                else if(dinnerTime==1){dinner = dT.getText() + " PM";}

                mMessagesDatabaseReference.child(uid).child("Medicine").child("Breakfast Time").setValue(breakfast);
                mMessagesDatabaseReference.child(uid).child("Medicine").child("Lunch Time").setValue(lunch);
                mMessagesDatabaseReference.child(uid).child("Medicine").child("Dinner Time").setValue(dinner);

                mType.child("Users").child(uid).child("filled").setValue(3);
                Intent activity=new Intent(PatientSignupThree.this, PatientHome.class);
                startActivityForResult(activity,1);
                Toast.makeText(PatientSignupThree.this,"You have completed the Signup!",Toast.LENGTH_SHORT).show();
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

    public void setBreakfastTime(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.bfTimeRadioAM:
                if (checked)
                    breakfastTime=0;
                break;
            case R.id.bfTimeRadioPM:
                if (checked)
                    breakfastTime=1;
                break;

        }
    }

    public void setLunchTime(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.luTimeRadioAM:
                if (checked)
                    lunchTime=0;
                break;
            case R.id.luTimeRadioPM:
                if (checked)
                    lunchTime=1;
                break;

        }
    }

    public void setDinnerTime(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.diTimeRadioAM:
                if (checked)
                    dinnerTime=0;
                break;
            case R.id.diTimeRadioPM:
                if (checked)
                    dinnerTime=1;
                break;

        }
    }
}
