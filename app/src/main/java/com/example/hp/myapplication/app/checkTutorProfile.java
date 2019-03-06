package com.example.hp.myapplication.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class checkTutorProfile extends AppCompatActivity {

    private Button sendBtn;
    private TextView name;
    private TextView phone;
    private TextView uni;
    private TextView gender;
    private TextView subject;

    public static String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_tutor_profile);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        sendBtn =findViewById(R.id.sendBtn);
        uni = findViewById(R.id.uni);
        gender = findViewById(R.id.gender);
        subject =findViewById(R.id.subjects);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            choice = extras.getString("ID");
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference user = ref.child("Tutors").child(choice);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n = dataSnapshot.child("name").getValue(String.class);
                String m = dataSnapshot.child("place").getValue(String.class);
                String a = dataSnapshot.child("university").getValue(String.class);
                String b = dataSnapshot.child("gender").getValue(String.class);
                String c = dataSnapshot.child("interested subjects").getValue(String.class);

                name.setText("Name: "+n);
                phone.setText("Place: "+m);
                gender.setText("Gender: "+b);
                uni.setText("University: "+a);
                subject.setText("Interested subjects "+c);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   user.child("Message").child(firstGuardian.getGuardianID()).child("Guardian ID").setValue(firstGuardian.getGuardianID());
              //  user.child("Message").child(firstGuardian.getGuardianID()).child("Guardian Text").setValue("Hello from "+ firstGuardian.getGuardianID());

               Intent intent =  new Intent(checkTutorProfile.this, setTuitionDetails.class);
               startActivity(intent);
            }
        });

    }
}


