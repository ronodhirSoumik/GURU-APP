package com.example.hp.myapplication.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class overviewFragment extends Fragment {
    View view;
    public static RecyclerView overRecycleView;
    public static List<itemOverview> overViewlist = new ArrayList<>();
    public static overviewRecyclerAdapter adapter;


    public overviewFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.overview_fragment, container, false);
        overRecycleView = view.findViewById(R.id.overViewRecycle);
         setAdapter();
       // adapter.notifyDataSetChanged();
        // setList();
        overRecycleView.setAdapter(adapter);
        return view;
    }

    private void setAdapter() {
        adapter = new overviewRecyclerAdapter(getContext(), overViewlist);
        overRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setList();

    }

    public void setList() {

                observableData ob =new observableData();
                observerData observerData = new observerData(ob);

                //ob.setMeasurement(dataSnapshot.child("name").getValue().toString());
                   ob.setMeasurement();
                //overViewlist.add(new itemOverview(observerData.data));
               // Toast.makeText(getContext(), " "+ observerData.passData(), Toast.LENGTH_LONG).show();
    }



}



