package com.example.layout11;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph extends AppCompatActivity {

    private String text;
    private String[] z = new String[100];
    private double y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //get graph from layout
        GraphView graph = (GraphView) findViewById(R.id.graph);

        //form series (curve for graph)
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        text = sharedPreferences.getString("werte","");

        z = text.split(" , ");

        for(int x=0;x<z.length;x++){

            if(TextUtils.isEmpty(text)){
                z[0]="0";
            }

            y = Double.valueOf(z[x]);
            series.appendData(new DataPoint(x,y),true,z.length);
        }

        graph.addSeries(series);

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
        






    }
}