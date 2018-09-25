package com.example.android.davadose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDosage extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    ListView mListView;
    MyDosageAdapter dosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dosage);
        mFirebaseDatabase=FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        mListView=findViewById(R.id.doMedi);

        final List<MedicineItem> medicines=new ArrayList<>();
        dosage=new MyDosageAdapter(this, R.layout.item_medicine, medicines);
        mListView.setAdapter(dosage);

        mMessagesDatabaseReference=mFirebaseDatabase.getReference().child("Patients").child(uid).child("Prescription");
        mMessagesDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //dosage.clear();
                if(!dosage.isEmpty())
                    dosage.clear();
                Prescription a=dataSnapshot.getValue(Prescription.class);
                TextView name=findViewById(R.id.doDr);
                TextView re=findViewById(R.id.doRemarks);
                name.setText(a.by);
                re.setText(a.description);
                //dosage.add(b);
                /*if(!a.medicine.isEmpty()){
                    Log.v("h","hello");
                    TextView j=findViewById(R.id.t);
                    MedicineItem b=new MedicineItem();
                    b.name="abc";
                    j.setText(b.name);
                }*/

                for(int i=0;i<a.medicine.size();i++){
                    dosage.add(a.medicine.get(i));
                }
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
