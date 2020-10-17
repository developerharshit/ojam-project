package com.example.utube.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.utube.Adapter.TabAdapter;
import com.example.utube.Fragment.Content;
import com.example.utube.R;
import com.example.utube.Utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        for(String entry: Constants.categoryName){
            adapter.addFragment(new Content(), entry);
        }
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }
}