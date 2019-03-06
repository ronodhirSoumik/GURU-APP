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
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class backgroundNotification extends Service {
    private String show;
    public  int channal_id = 6;    //
    String CHANNEL_ID = "my_channel_01";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int s =0;
        SharedPreferences sharedPreferences = getSharedPreferences(registerTutor.SHARED_PREF , MODE_PRIVATE);
        show = sharedPreferences.getString(userRegistration.idTutor, "");
        Toast.makeText(getApplicationContext(), "It is showing" +show, Toast.LENGTH_SHORT).show();


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference user = ref.child("Tutors").child(show).child("Message");

            user.limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    // Toast.makeText(getApplicationContext(), "New Message arrived", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String sender = dataSnapshot.child("Guardian ID").getValue().toString();
                    String text = dataSnapshot.child("Guardian Text").getValue().toString();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setAutoCancel(true);
                    builder.setSmallIcon(R.mipmap.logo);
                    builder.setContentTitle("New offer have arrived");
                    builder.setContentText("New offer from " + sender + " .Tap to open it");

                    Intent intent = new Intent(getApplicationContext(), offerToDetails.class);
                    intent.putExtra("guardian_id", sender);
                    PendingIntent pintent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pintent);

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    //   Since android Oreo notification channel is needed.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_DEFAULT);
                        nm.createNotificationChannel(channel);
                    }
                    builder.setChannelId(CHANNEL_ID);
                    //  channal_id++;
                    nm.notify(0, builder.build());

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
