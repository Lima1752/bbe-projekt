package com.example.layout11;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.fragment.app.Fragment;

public class fragmentC extends Fragment {
    CalendarView calender;
    TextView date_view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View mView = inflater.inflate(R.layout.calender_layout,container,false);
        calender = mView.findViewById(R.id.calendarView);
        date_view = mView.findViewById(R.id.dateView);
        SimpleDateFormat sdf= new SimpleDateFormat("d. M. YYYY");
        String currentDate = sdf.format(new Date());
        date_view.setText(currentDate);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
               String Date = dayOfMonth + ". " + (month +1) + ". " +year;
               date_view.setText(Date);

           }
       });  return mView;
    }
}

