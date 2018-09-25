package com.example.android.davadose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewPrescriptionTwo extends AppCompatActivity {

    private LinearLayout medicines;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference,mP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescription_two);
        medicines = (LinearLayout) findViewById(R.id.medicines);


        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mP=mFirebaseDatabase.getReference();
        mP.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Patients").child(PatientData.pid).child("User Details").child("name").getValue(String.class);
                TextView patientsName=findViewById(R.id.patient_name);
                patientsName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void addMedicine(View view) {
        final View newView = getLayoutInflater().inflate(R.layout.tablet_entry, null);
        medicines.addView(newView);
        ImageButton removeBtn = (ImageButton) newView.findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                medicines.removeView(newView);
            }
        });
    }

    public void onSubmit(View view) {

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mMessagesDatabaseReference=mFirebaseDatabase.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        LinearLayout medicines = (LinearLayout) findViewById(R.id.medicines);
        java.util.ArrayList<MedicineItem> medicineList = new ArrayList<MedicineItem>();

        for (int i = 0; i < medicines.getChildCount(); i++) {
            MedicineItem item = new MedicineItem();
            LinearLayout medicineItem = (LinearLayout) medicines.getChildAt(i);
            EditText name = (EditText) medicineItem.findViewById(R.id.tabletName);
            item.name = name.getText().toString();
            RadioButton after = (RadioButton) medicineItem.findViewById(R.id.after);
            if (after.isChecked())
                item.isafter = true;
            else
                item.isafter = false;

            CheckBox morning = (CheckBox) medicineItem.findViewById(R.id.morning);
            CheckBox afternoon = (CheckBox) medicineItem.findViewById(R.id.afternoon);
            CheckBox night = (CheckBox) medicineItem.findViewById(R.id.night);

            if (morning.isChecked())
                item.morning = true;
            else
                item.morning = false;

            if (afternoon.isChecked())
                item.afternoon = true;
            else
                item.afternoon = false;

            if (night.isChecked())
                item.night = true;
            else
                item.night = false;

            medicineList.add(item);
        }
        EditText desc=findViewById(R.id.patientDescription);
        mMessagesDatabaseReference.child("Doctors").child(uid).child("Patients").push().setValue(PatientData.mail);
        Prescription p=new Prescription();
        p.medicine=medicineList;
        p.by=user.getDisplayName();
        p.description=desc.getText().toString();
        Date d= Calendar.getInstance().getTime();
        p.date=d;
        mMessagesDatabaseReference.child("Patients").child(PatientData.mail).child("Prescription").push().setValue(p);
        mMessagesDatabaseReference.child("Patients").child(PatientData.mail).child("Doctors").push().setValue(uid);
        Toast.makeText(NewPrescriptionTwo.this,"Prescription generated!",Toast.LENGTH_SHORT).show();
        Intent activity=new Intent(NewPrescriptionTwo.this, DoctorHome.class);
        startActivityForResult(activity,1);

        /*int classSize = medicineList.size();
        Toast t;

        for (int i = 0; i < classSize; i++) {
            t = Toast.makeText(getApplicationContext(), medicineList.get(i).name + medicineList.get(i).isafter, Toast.LENGTH_SHORT);
            t.show();
        }*/
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

