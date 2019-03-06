package com.example.hp.myapplication.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class backendNotificationGuardian extends Service {

    private String show;
    public int channal_id = 1;    //imaginary
    String CHANNEL_ID = "my_channel_01";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "It is showing", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences(registerTutor.SHARED_PREF , MODE_PRIVATE);
        show = sharedPreferences.getString(userRegistration.idTutor, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference u = ref.child("Tutors").child(show).child("Message");

        u.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // Since android Oreo notification channel is needed.
                //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                //    "Channel human readable title",
                //        NotificationManager.IMPORTANCE_DEFAULT);
                //    nm.createNotificationChannel(channel);
                //    }
                //  builder.setChannelId(channal_id);

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
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
