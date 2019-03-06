package com.example.hp.myapplication.app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class fragmentActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        tab= findViewById(R.id.tabID);
        v = findViewById(R.id.v);

        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new overviewFragment(),"Overview");
        adapter.addFragment(new offerFragment(), "Offer");

        v.setAdapter(adapter);
        tab.setupWithViewPager(v);
    }
}
