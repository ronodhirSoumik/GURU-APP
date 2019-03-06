package com.example.hp.myapplication.app;

import android.content.SharedPreferences;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
        private TextView imageView;
        private TextView idView;
        private TextView nameView;
        private TextView passView;
        private TextView mobileView;

        private ImageView userImage;
        private String show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageView = findViewById(R.id.imageView);
        idView= findViewById(R.id.idView);
        nameView = findViewById(R.id.nameView);
        passView = findViewById(R.id.passView);
        mobileView = findViewById(R.id.mobileView);

        userImage = findViewById(R.id.userImage);

        SharedPreferences sharedPreferences = getSharedPreferences(registerTutor.SHARED_PREF , MODE_PRIVATE);
        show = sharedPreferences.getString(registerTutor.idTutor, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference user = ref.child("Guardians").child(show);


        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.child("image").getValue(String.class);
                String id = dataSnapshot.child("id").getValue(String.class);
                String pass = dataSnapshot.child("password").getValue(String.class);
                String name = dataSnapshot.child("name").getValue(String.class);
                String mobile = dataSnapshot.child("mobile no").getValue(String.class);

               Picasso.get()
                       .load(image)
                       .resize(394 , 263 )
                       .into(userImage);

                imageView.setText(image);
                idView.setText(id);
                nameView.setText(name);
                passView.setText(pass);
                mobileView.setText(mobile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
