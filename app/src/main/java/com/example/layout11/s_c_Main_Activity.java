package com.example.layout11;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class s_c_Main_Activity extends AppCompatActivity {
    private TextView textView;
    private TextView dataView;
    private EditText editText;
    private Button save;
    private Button reset;
    private Button calculate;
    private Button resetAverage;
    private String text;
    private String[] x = new String[1000];
    private double[] average = new double[1000];
    private String summe;
    private double sum = 0.0;
    private double avg;
    private int k=0;

    private Button btn_Graph;
    private Button btn_back;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String WERTE = "werte";
    public static final String MITTEL = "mittel";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_c_);


        textView = findViewById(R.id.textview);
        dataView = findViewById(R.id.dataview);
        editText = findViewById(R.id.edittext);
        save = findViewById(R.id.apply_text_button);
        reset = findViewById(R.id.apply_text_button1);
        calculate = findViewById(R.id.apply_text_button2);
        resetAverage = findViewById(R.id.apply_text_button3);

        btn_Graph = (Button) findViewById(R.id.btn_graph);
        btn_back=(Button)findViewById(R.id.btn_back);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,0);

        textView.setText(sharedPreferences.getString("Messwerte", "Default"));
        dataView.setText(sharedPreferences.getString("Durchschnitte", "Default"));

        loadData();
        updateView();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
                saveData();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetData();
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAverage();
            }
        });
        resetAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMittel();
            }
        });

        btn_Graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(s_c_Main_Activity.this, "show Graph", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(s_c_Main_Activity.this, Graph.class);

                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(s_c_Main_Activity.this, "Back", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(s_c_Main_Activity.this, Bluetooth.class);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Messwerte", textView.getText().toString());
        editor.putString("Durchschnitte", dataView.getText().toString());

        editor.commit();

    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String wertVorher = sharedPreferences.getString(WERTE,"");
        if (wertVorher.isEmpty()){
            editor.putString(WERTE, textView.getText().toString());
        }else{
            editor.putString(WERTE, wertVorher + " , " + textView.getText().toString());

        }
        editor.apply();
        Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show();
        loadData();
        updateView();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(WERTE,"");
    }

    public void updateView(){
        textView.setText(text);
    }

    public void resetData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WERTE, "");
        editor.apply();
        Toast.makeText(this,"Data reseted", Toast.LENGTH_SHORT).show();
        loadData();
        updateView();
    }

    public void resetMittel(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MITTEL, "");
        editor.apply();
        Toast.makeText(this,"Average reseted", Toast.LENGTH_SHORT).show();
        loadAverage();
        updateAverage();
    }

    public void loadAverage(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        summe = sharedPreferences.getString(MITTEL,"");
    }
    public void updateAverage(){
        dataView.setText(summe);
    }

    public void calcAverage(){
        x = text.split(" , ");
        sum = 0;

        for (int a = 0; a<x.length;a++){

                double d = Double.valueOf(x[a]);
                average[a] = d;
                sum += average[a];

        }
        avg =  sum/x.length;

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String mittelVorher = sharedPreferences.getString(MITTEL,"");

        if (mittelVorher.isEmpty()){
          editor.putString(MITTEL, String.valueOf(round(avg,2)));
       }else{
            editor.putString(MITTEL, mittelVorher + " , " + String.valueOf(round(avg,2)));
        }

        editor.apply();
        loadAverage();
        updateAverage();
    }

    private double round(double avg, int i) {
        double d2 = Math.pow(10, i);
        return Math.round(avg*d2)/d2;
    }
}

