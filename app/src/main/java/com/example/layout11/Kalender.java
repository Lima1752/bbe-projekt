package com.example.layout11;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Kalender extends Fragment{
    CalendarView calendar;
    TextView date_view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kalender_layout,container,false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        calendar.findViewById(R.id.calendarView);
        date_view.findViewById(R.id.dateView);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf= new SimpleDateFormat("d. M. YYYY");
        String currentDate = sdf.format(new Date());
        date_view.setText(currentDate);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
                String Date = dayOfMonth + ". " + (month +1) + ". " +year;
                date_view.setText(Date);
            }
        });
    }

    private void setContentView() {
    }
}
