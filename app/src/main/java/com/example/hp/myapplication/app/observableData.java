package com.example.hp.myapplication.app;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Observable;

public class observableData extends Observable {
    private String data;
    public  ArrayList<String> list = new ArrayList<String >();

    public observableData() { }

    public void setMeasurement(){
        final DatabaseReference n = ScrollingActivityforTutor.db();

        n.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = (String) dataSnapshot.child("name").getValue();
                String b = (String) dataSnapshot.child("mobile no").getValue();

                String c = (String) dataSnapshot.child("gender").getValue();
                String d = (String) dataSnapshot.child("place").getValue();
                String  e = (String) dataSnapshot.child("profession").getValue();
                String f = (String) dataSnapshot.child("university").getValue();
                String g = (String) dataSnapshot.child("department").getValue();
                String h = (String) dataSnapshot.child("education medium").getValue();
                String i = (String) dataSnapshot.child("interested subjects").getValue();

                Log.d("name from database ", a);
                data =a;

                list.clear();
                list.add(a);
                list.add(b);
                list.add(c);
                list.add(d);
                list.add(e);
                list.add(f);
                list.add(g);
                list.add(h);
                list.add(i);




//                overviewFragment.overViewlist.add(new itemOverview(data));

                // data = "Soumik" ;
                //Log.d("Outside database ", data);
                measurementChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//         data = "Soumik" ;
       // Log.d("Outside database ", data);
//        measurementChanged();
    }

    public void measurementChanged(){
        setChanged();
        notifyObservers();
    }

    public String getData() {
        return data;
    }

    public ArrayList<String > getList(){
        return list;
    }

}
