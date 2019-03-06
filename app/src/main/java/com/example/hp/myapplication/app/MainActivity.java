package com.example.hp.myapplication.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private slideHolder slideholder;
    private int[] layouts;
    private Button buttonNext;
    private Button buttonSkip;
    private LinearLayout layoutDot;
    private TextView[] dotstv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!FirstTimeCheck()) {
            //startMainActivity();
            SharedPreferences sharedPreferences = getSharedPreferences(userRegistration.SHARED_USER, MODE_PRIVATE);
            String user = sharedPreferences.getString(userRegistration.user, "");

            Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();

            if(user.equals("Tutor")){



            startActivity(new Intent(MainActivity.this , ScrollingActivityforTutor.class));
            finish(); }
            else {

                startActivity(new Intent(MainActivity.this , firstGuardian.class));
                finish();
            }
        }



        setStatusBarTransparent();

        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPage);
        layoutDot = findViewById(R.id.dotLayout);
        buttonNext = findViewById(R.id.buttonNext);
        buttonSkip = findViewById(R.id.buttonSkip);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem() + 1;

                if (currentPage < layouts.length) {
                    viewPager.setCurrentItem(currentPage);
                } else {
                    startMainActivity();
                }
            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        layouts = new int[]{R.layout.slide_1, R.layout.slide_2};
        slideholder = new slideHolder(layouts, getApplicationContext());
        viewPager.setAdapter(slideholder);

        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( position == layouts.length -1){
                    buttonNext.setText("start");
                    buttonSkip.setVisibility(View.GONE);
                }
                else{
                    buttonNext.setText("next");
                    buttonSkip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setDotStatus(0);
    }

    private void startMainActivity() {
        setFirstTimeStatus(false);
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        finish();
    }

    private void setStatusBarTransparent(){
        if(Build.VERSION.SDK_INT >=21){
            getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private boolean FirstTimeCheck() {
        SharedPreferences ref = getApplicationContext().getSharedPreferences("SlideShow", MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }

    private void setFirstTimeStatus(boolean a){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("SlideShow" , MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartFlag",a);
        editor.commit();
    }

    private void setDotStatus(int page) {
        layoutDot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for (int i = 0; i < dotstv.length; i++) {
             dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            layoutDot.addView(dotstv[i]);
        }

        if (dotstv.length > 0) {
            dotstv[page].setTextColor(Color.parseColor("#ffffff"));
        }

    }


}
