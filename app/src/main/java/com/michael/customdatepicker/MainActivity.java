package com.michael.customdatepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.btn_year).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DateUnit.Year);
            }
        });
        findViewById(R.id.btn_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DateUnit.Month);
            }
        });
        findViewById(R.id.btn_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DateUnit.Day);
            }
        });
    }

    private void showDialog(DateUnit unit){

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);//得到年
        final int month = calendar.get(Calendar.MONTH);//得到月
        final int day = calendar.get(Calendar.DAY_OF_MONTH);//得到天
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);//得时间
        final int minue = calendar.get(Calendar.MINUTE);//得分
        final int second = calendar.get(Calendar.SECOND);//得秒
        new CustomDatePickerDialog(this, calendar, unit,new CustomDatePickerDialog.onDateTimeSetListener() {
            @Override
            public void onDateSet(DatePicker view, Calendar calendar) {
                int year = calendar.get(Calendar.YEAR);//得到年
                int month = calendar.get(Calendar.MONTH);//得到月
                int day = calendar.get(Calendar.DAY_OF_MONTH);//得到天

                calendar.set(year,month,day);
//                if(onDateSelectListener!=null){
//                    onDateSelectListener.onDateSelected(calendar.getTime());
//                }
                Date date = new Date(String.valueOf(calendar.getTime()));
                textView.setText(date.toString());
                view.clearFocus();
            }
        }).show();



    }
}
