package com.example.layout11;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class fragment_Graph extends Fragment {

    private String text;
    private String[] z = new String[100];
    private double y;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View mView = inflater.inflate(R.layout.activity_graph,container,false);

        //get graph from layout
        GraphView graph =mView.findViewById(R.id.graph);

        //form series (curve for graph)
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        SharedPreferences preferences = this.getActivity().getSharedPreferences(SHARED_PREFS,0);
        text = preferences.getString("werte","");

        z = text.split(" , ");

        for(int x=0;x<z.length;x++){

            if(TextUtils.isEmpty(text)){
                z[0]="0";
            }

            y = Double.valueOf(z[x]);
            series.appendData(new DataPoint(x,y),true,z.length);


        }

        //Einstellung Farben, Datenpunkte Durchmesser, Linienstärke
        series.setColor(Color.RED);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        //Titel vom Graphen
        graph.setTitle("Blutzucker-Kurve");
        graph.setTitleTextSize(90);
        graph.setTitleColor(Color.RED);

        //Achsenbeschriftung
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Entwicklung");
        gridLabel.setHorizontalAxisTitleTextSize(50);
        gridLabel.setVerticalAxisTitle("Messwerte");
        gridLabel.setVerticalAxisTitleTextSize(50);

        //Damit alle x-Werte sichtbar sind
        graph.getGridLabelRenderer().setNumHorizontalLabels(z.length);

        graph.addSeries(series);
        return mView;
    }
}