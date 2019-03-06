package com.example.hp.myapplication.app;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class observerData implements Observer {
    Observable observable;
    public ArrayList<String> list = new ArrayList<>();
    public ArrayList<String> header = new ArrayList<>();




    public observerData(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        observableData od = (observableData) o;
        //this.data = od.getData();
        this.list = od.getList();
        passData();
    }

    public void passData() {
        header.clear();
        header.add("name");
        header.add("phone number");

        header.add("gender");
        header.add("place");
        header.add("profession");
        header.add("university");
        header.add("department");
        header.add("education medium");
        header.add("interested subjects");

       // list.add("edit here");
        //list.add("edit here");
       // list.add("edit here");
        //list.add("edit here");
       //list.add("edit here");
        //list.add("edit here");
     //   list.add("edit here");


        overviewFragment.overViewlist.clear();
        overviewFragment.adapter.notifyDataSetChanged();

        for (int i = 0; i < list.size(); i++) {
            overviewFragment.overViewlist.add(new itemOverview( list.get(i), header.get(i)));
        }

        overviewFragment.adapter.notifyItemInserted(overviewFragment.overViewlist.size() - 1);
       // Log.d("data in observer ", data);
    }
}
