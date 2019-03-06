package com.example.hp.myapplication.app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;

public class offerToDetails extends AppCompatActivity {
    TextView sender_details;
    TextView clsText;
    TextView verText;
    TextView hoursText;
    TextView daysText;
    TextView subText;
    TextView placeText;

    Button acceptBtn;

    String sender;
    String show;
    String phone;
    String name;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_to_details);

        sender_details = findViewById(R.id.sender_details);
        acceptBtn = findViewById(R.id.acceptBtn);

        clsText = findViewById(R.id.clsText);
        subText = findViewById( R.id.subText);
        verText = findViewById(R.id.verText);
        daysText  = findViewById(R.id.daysText);
        hoursText = findViewById(R.id.hoursText);
        placeText =findViewById(R.id.placeText);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sender = extras.getString("guardian_id");
        }

        SharedPreferences sharedPreferences = getSharedPreferences(registerTutor.SHARED_PREF, MODE_PRIVATE);
        show = sharedPreferences.getString(userRegistration.idTutor, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference user = ref.child("Tutors").child(show);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ID = dataSnapshot.child("Message").child(sender).child("Guardian ID").getValue().toString();
                showData(ID);
                String t = dataSnapshot.child("Message").child(sender).child("Guardian Text").getValue().toString();
              //  phone = dataSnapshot.child("mobile no").getValue().toString();
               // name = dataSnapshot.child("name").getValue().toString();

                String a = dataSnapshot.child("Message").child(sender).child("class").getValue().toString();
                String f = dataSnapshot.child("Message").child(sender).child("subject").getValue().toString();
                String b =dataSnapshot.child("Message").child(sender).child("version").getValue().toString();
                String c = dataSnapshot.child("Message").child(sender).child("hours").getValue().toString();
                String d = dataSnapshot.child("Message").child(sender).child("days").getValue().toString();
                String e =dataSnapshot.child("Message").child(sender).child("place").getValue().toString();

               // sender_details.setText("Sender: "+t);
                clsText.setText("Class: "+a);
                subText.setText("Subject(s): "+ f);
                verText.setText("Version: "+b);
                hoursText.setText("Hours per day: "+c);
                daysText.setText("Days per week: "+d);
                placeText.setText("Place: "+e);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = "tel:";
                // call = phone + "01773";


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference guardian = ref.child("Guardians").child(sender);
                guardian.child("Notifications").child(show).child("Tutorname").setValue(show);
                //guardian.child("Notifications").child(show).child("phone").setValue(phone);

//                // make phone call
                guardian.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String call = dataSnapshot.child("mobile no").getValue().toString();
                        call = "tel:" + call;

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(call));
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        });

    }
    public void showData(String d){
        Toast.makeText(getApplicationContext(), "Guardian ID: "+d, Toast.LENGTH_LONG).show();
        DatabaseReference referGuardian = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference guardianName = referGuardian.child("Guardians").child(sender);

        guardianName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String  n = dataSnapshot.child("name").getValue().toString();
                sender_details.setText("Sender: "+n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
