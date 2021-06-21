package com.example.layout11;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.layout11.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fmenu,fgraph, fBluetooth;
    Float translationYaxis= 100f;
    Boolean menuOpen = false;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        ShowMenu();


    }

    ;


    private void ShowMenu() {
        fmenu=findViewById(R.id.menu);
        fgraph=findViewById(R.id.graph);
        fBluetooth =findViewById(R.id.bluetooth);

        fgraph.setAlpha(0f);
        fBluetooth.setAlpha(0f);

        fgraph.setTranslationY(translationYaxis);
        fBluetooth.setTranslationY(translationYaxis);

        fmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuOpen){
                    CloseMenu();
                }else{
                    OpenMenu();
                }
            }
        });

        fgraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "show Graph", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Graph.class);

                startActivity(intent);
            }
        });


        fBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Bluetooth.class);

                startActivity(intent);
            }
        });


    }

    private void OpenMenu() {

        menuOpen= !menuOpen;
        fgraph.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fBluetooth.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void CloseMenu() {

        menuOpen= !menuOpen;
        fgraph.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fBluetooth.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }

    ;
}