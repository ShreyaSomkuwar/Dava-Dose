package com.example.android.davadose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPrescriptionActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescription);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference();


        /*Button generate=findViewById(R.id.generatePresciption);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText presc=findViewById(R.id.presc);
                String prescription=presc.getText().toString();

                EditText pid=findViewById(R.id.puid);
                String puid=pid.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid=user.getUid();

                mMessagesDatabaseReference.child("Doctors").child(uid).child("Patients").push().setValue(puid);
                mMessagesDatabaseReference.child("Patients").child(puid).child("Prescription").push().setValue(prescription);

                Toast.makeText(NewPrescriptionActivity.this,"Prescription generated!",Toast.LENGTH_SHORT).show();

                Intent activity=new Intent(NewPrescriptionActivity.this, DoctorHome.class);
                startActivityForResult(activity,1);
            }
        });*/
        Button confirm=findViewById(R.id.patientConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText patientID=findViewById(R.id.puid);
                String puid=patientID.getText().toString();
                PatientData.mail=puid;
                Intent activity=new Intent(NewPrescriptionActivity.this, NewPrescriptionTwo.class);
                startActivityForResult(activity,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }
}
