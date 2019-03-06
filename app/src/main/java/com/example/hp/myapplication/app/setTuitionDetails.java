package com.example.hp.myapplication.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setTuitionDetails extends AppCompatActivity {
    private Button offerBtn;

    private EditText cls;
    private EditText sub;
    private EditText ver;
    private EditText hours;
    private EditText days;
    private EditText place;

    private String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tuition_details);

        offerBtn = findViewById(R.id.offerBtn);
        cls = findViewById(R.id.cls);
        sub = findViewById(R.id.sub);
        ver = findViewById(R.id.ver);
        hours = findViewById(R.id.hours);
        days = findViewById(R.id.days);
        place = findViewById(R.id.place);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference user = ref.child("Tutors").child(checkTutorProfile.choice);

        offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(),"working "+checkTutorProfile.choice, Toast.LENGTH_LONG).show();
                String a = cls.getText().toString().trim();
                String b = ver.getText().toString().trim();
                String c= hours.getText().toString().trim();
                String d = days.getText().toString().trim();
                String e = place.getText().toString().trim();
                String f = sub.getText().toString().trim();

                user.child("Message").child(firstGuardian.getGuardianID()) .child("Guardian ID").setValue(firstGuardian.getGuardianID());
                user.child("Message").child(firstGuardian.getGuardianID()).child("Guardian Text").setValue("Hello from "+ firstGuardian.getGuardianID());
                user.child("Message").child(firstGuardian.getGuardianID()).child("class").setValue(a);
                user.child("Message").child(firstGuardian.getGuardianID()).child("subject").setValue(f);
                user.child("Message").child(firstGuardian.getGuardianID()).child("version").setValue(b);
                user.child("Message").child(firstGuardian.getGuardianID()).child("hours").setValue(c);
                user.child("Message").child(firstGuardian.getGuardianID()).child("days").setValue(d);
                user.child("Message").child(firstGuardian.getGuardianID()).child("place").setValue(e);

            }
        });


    }
}
