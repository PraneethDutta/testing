package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView textviewtime;
    private AlarmManager alarmManager;
    private Calendar calendar;
    private PendingIntent pi;
    private MaterialTimePicker materialTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewtime=findViewById(R.id.textViewviewtime);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("remainder","REMAINDER", NotificationManager.IMPORTANCE_HIGH);
            //String des="Channel for the notification of the remainder";
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void OnClickButtonSelectTime(View view) {
        showTimePicker();

    }

    private void showTimePicker() {
        materialTimePicker=new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .build();
        materialTimePicker.show(getSupportFragmentManager(),"remainder");
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,materialTimePicker.getHour());
                calendar.set(Calendar.MINUTE,materialTimePicker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm aa");
                textviewtime.setText("TIME :"+sdf.format(calendar.getTime()));

            }
        });
    }

    public void OnClickButtonSetAlarm(View view) {
        setAlarm();

    }

    private void setAlarm() {
        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmManager.class);
        pi=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pi);
        Toast.makeText(this,"ALARM is set Successfully",Toast.LENGTH_SHORT).show();


    }
}