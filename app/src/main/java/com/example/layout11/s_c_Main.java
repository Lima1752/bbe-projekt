package com.example.layout11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class s_c_Main extends Fragment {
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



    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String WERTE = "werte";
    public static final String MITTEL = "mittel";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View mView = inflater.inflate(R.layout.s_c_main,container,false);

        textView = mView.findViewById(R.id.textview);
        dataView = mView.findViewById(R.id.dataview);
        editText = mView.findViewById(R.id.edittext);
        save = mView.findViewById(R.id.apply_text_button);
        reset = mView.findViewById(R.id.apply_text_button1);
        calculate = mView.findViewById(R.id.apply_text_button2);
        resetAverage = mView.findViewById(R.id.apply_text_button3);


        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS,0);

        textView.setText(preferences.getString("Messwerte", "Default"));
        dataView.setText(preferences.getString("Durchschnitte", "Default"));

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

        return mView;
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS,0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Messwerte", textView.getText().toString());
        editor.putString("Durchschnitte", dataView.getText().toString());

        editor.commit();

    }

    public void saveData(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String wertVorher = preferences.getString(WERTE,"");
        if (wertVorher.isEmpty()){
            editor.putString(WERTE, textView.getText().toString());
        }else{
            editor.putString(WERTE, wertVorher + " , " + textView.getText().toString());

        }
        editor.apply();
        Toast.makeText(this.getActivity(),"Data saved", Toast.LENGTH_SHORT).show();
        loadData();
        updateView();
    }

    public void loadData(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = preferences.getString(WERTE,"");
    }

    public void updateView(){
        textView.setText(text);
    }

    public void resetData(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(WERTE, "");
        editor.apply();
        Toast.makeText(this.getActivity(),"Data reseted", Toast.LENGTH_SHORT).show();
        loadData();
        updateView();
    }

    public void resetMittel(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MITTEL, "");
        editor.apply();
        Toast.makeText(this.getActivity(),"Average reseted", Toast.LENGTH_SHORT).show();
        loadAverage();
        updateAverage();
    }

    public void loadAverage(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        summe = preferences.getString(MITTEL,"");
    }
    public void updateAverage(){
        dataView.setText(summe);
    }

    public void calcAverage(){

        if(TextUtils.isEmpty(text)){
            Toast.makeText(this.getActivity(),"Bitte Messwerte eingeben", Toast.LENGTH_SHORT).show();
            return;
        }


        x = text.split(" , ");
        sum = 0;

        for (int a = 0; a<x.length;a++){

                double d = Double.valueOf(x[a]);
                average[a] = d;
                sum += average[a];

        }
        avg =  sum/x.length;

        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        String mittelVorher = preferences.getString(MITTEL,"");

        if (mittelVorher.isEmpty()){
          editor.putString(MITTEL, String.valueOf(round(avg,2)));
       }else{
            editor.putString(MITTEL, mittelVorher + " , " + String.valueOf(round(avg,2)));
        }

        editor.apply();
        loadAverage();
        updateAverage();
    }

    public void startgraph(){
        Intent intent = new Intent(this.getActivity(), Graph.class);
        startActivity(intent);

    }

    private double round(double avg, int i) {
        double d2 = Math.pow(10, i);
        return Math.round(avg*d2)/d2;

    }

}

