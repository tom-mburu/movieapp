package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.example.movieapp.adapters.pagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    int tabsicon[]=new int[3];
    ArrayList<Fragment> fragments=new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText searchtext=findViewById(R.id.searchfield);
        ImageView searchbtn=findViewById(R.id.search);
        TabLayout tabLayout=findViewById(R.id.categories);
        ViewPager viewPager=findViewById(R.id.categoryContainer);

        fragments.clear();
        fragments.add(moviesfragment.newInstance("xyz","xyz"));
        fragments.add( tvshowsfragment.newInstance("xyz","xyz"));
       fragments.add( peoplefragment.newInstance("xyz","xyz"));




        tabsicon[0]=R.drawable.baseline_home_24;
        tabsicon[1]=R.drawable.baseline_ondemand_video_24;
        tabsicon[2]=R.drawable.baseline_people_24;
        tabLayout.setElevation(15);
        pagerAdapter fragmentPagerAdapter=new pagerAdapter(getSupportFragmentManager(),fragments,MainActivity.this,tabsicon);
        viewPager.setAdapter(fragmentPagerAdapter);
       // viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
       //



    }

    @Override
    protected void onStop() {
        super.onStop();
       // fragments.clear();
    }
}