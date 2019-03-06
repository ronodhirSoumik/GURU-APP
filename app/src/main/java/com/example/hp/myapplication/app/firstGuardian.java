package com.example.hp.myapplication.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.ConditionVariable;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class firstGuardian extends AppCompatActivity {
    private TextView name;
    private String show;
    private static String guardianID;

    private EditText searchTxt;

    private RecyclerView searchList;

    private DatabaseReference tutorRef;

    private ArrayList<String>  IDTutors = new ArrayList<String>();
    private ArrayList<String> nameTutors= new ArrayList<String>();
    private ArrayList<String> phoneTutors = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_guardian);

        name = findViewById(R.id.name);

        searchTxt = findViewById(R.id.searchTxt);

        searchList = findViewById(R.id.searchlist);
        searchList.setHasFixedSize(true);
        searchList.setLayoutManager(new LinearLayoutManager(this));


        tutorRef = FirebaseDatabase.getInstance().getReference("Tutors");


        SharedPreferences sharedPreferences = getSharedPreferences(registerTutor.SHARED_PREF, MODE_PRIVATE);
         show = sharedPreferences.getString(userRegistration.idTutor, "");
        guardianID = show;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference user = ref.child("Guardians").child(show);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String n = dataSnapshot.child("name").getValue(String.class);
                String[] split = n.split(" ");

                SpannableString ss = new SpannableString(n);
                StyleSpan bold = new StyleSpan(Typeface.BOLD);
                StyleSpan normal = new StyleSpan(Typeface.NORMAL);

                ss.setSpan(bold, 0, split[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(normal, split[0].length() + 1, n.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                name.setText(ss);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         searchTxt.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {

             }

             @Override
             public void afterTextChanged(Editable s) {


                 if(!s.toString().isEmpty()){
                     setAdapter(s.toString());
                 }
                 else {
                     IDTutors.clear();
                     nameTutors.clear();
                     phoneTutors.clear();
                     searchList.removeAllViews();
                 }
             }
         });
    }

        private void setAdapter(final String st) {
            //Toast.makeText(firstGuardian.this, "String: "+st, Toast.LENGTH_SHORT).show();

            tutorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    IDTutors.clear();
                    nameTutors.clear();
                    phoneTutors.clear();
                    searchList.removeAllViews();

                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        String ID = snapshot.child("id").getValue(String.class);
                        String tutorName = snapshot.child("name").getValue(String.class);
                        String tutorPhone = snapshot.child("university").getValue(String.class);

                        String n = tutorName.toLowerCase();

                        if(n.contains(st.toLowerCase())){
                                IDTutors.add(ID);
                                nameTutors.add(tutorName);
                                phoneTutors.add(tutorPhone);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

            searchAdapter search =new searchAdapter(firstGuardian.this, nameTutors,phoneTutors,IDTutors);
            searchList.setAdapter(search);
        }

      static String getGuardianID(){
        return guardianID;
     }

    public void startBackendService(View v){
        Toast.makeText(getApplicationContext(), "Here is Guardian background...", Toast.LENGTH_LONG).show();
        startService(new Intent(firstGuardian.this, backgroundNotification.class));
    }
    public void stopService(View v){
        stopService(new Intent(firstGuardian.this, backgroundNotification.class));
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}
