package com.example.hp.myapplication.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScrollingActivityforTutor extends AppCompatActivity {
    Toolbar toolbar;
    private String show;
    String name;
    SpannableString ss;

    private TabLayout tablayout;
    private ViewPager viewpager;

    public static DatabaseReference d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_activityfor_tutor);
         toolbar = findViewById(R.id.toolbar);

         tablayout = findViewById(R.id.tablayout);
         viewpager = findViewById(R.id.vp);


        SharedPreferences sharedPreferences = getSharedPreferences(registerTutor.SHARED_PREF , MODE_PRIVATE);
        show = sharedPreferences.getString(userRegistration.idTutor, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
         final DatabaseReference user = ref.child("Tutors").child(show);
         d = user;

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 String n = dataSnapshot.child("name").getValue(String.class);

                name =n;

                String[] split = n.split(" ");

                 ss= new SpannableString(n);
                StyleSpan bold = new StyleSpan(Typeface.BOLD);
                StyleSpan normal = new StyleSpan(Typeface.NORMAL);

                ss.setSpan(bold, 0, split[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(normal, split[0].length()+1, n.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(ss);
            //    getSupportActionBar().setSubtitle("Hell Yeah");
             //   toolbar.setLogo(android.R.drawable.ic_menu_info_details);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new overviewFragment(),"Overview");
        adapter.addFragment(new offerFragment(), "Offer");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

    }

    static  public DatabaseReference db(){
        return d;
    }

    // For the background notification
    public void startService(View v){
        Toast.makeText(getApplicationContext(), "Background Starting .....", Toast.LENGTH_LONG).show();
        startService(new Intent(this, backgroundNotification.class));
    }
    public void stopService(View v){
        stopService(new Intent(this, backgroundNotification.class));
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
