package com.michael.customdatepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Michael on 2018/1/17.
 */

public class CustomDatePickerDialog extends AlertDialog implements DialogInterface.OnClickListener{

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private DateUnit unit = DateUnit.Day;

    private final DatePicker mDatePicker;
    private final onDateTimeSetListener onDateTimeSetListener;
    private Calendar mCalendar;

    public interface onDateTimeSetListener {
        void onDateSet(DatePicker view, Calendar calendar);
    }

    public CustomDatePickerDialog(Context context, Calendar calendar, onDateTimeSetListener listener) {
        this(context, 0,calendar, DateUnit.Day, listener);
    }


    public CustomDatePickerDialog(Context context, Calendar calendar, DateUnit unit, onDateTimeSetListener listener) {
        this(context, 0,calendar,unit, listener);
    }

    static int resolveDialogTheme(Context context, int resId) {
        if (resId == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.timePickerDialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return resId;
        }
    }

    public CustomDatePickerDialog(Context context, int themeResId, Calendar calendar, DateUnit unit, onDateTimeSetListener listener ) {
        super(context, resolveDialogTheme(context, themeResId));
        this.mCalendar = calendar;
        onDateTimeSetListener = listener;

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.custom_date_picker_dialog, null);
        setView(view);
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this);

        int year = calendar.get(Calendar.YEAR);//得到年
        int month = calendar.get(Calendar.MONTH);//得到月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//得到天
        int hour = calendar.get(Calendar.HOUR_OF_DAY);//得时间
        int minute = calendar.get(Calendar.MINUTE);//得分
        int second = calendar.get(Calendar.SECOND);//得秒

        mDatePicker = (DatePicker) view.findViewById(R.id.datetimepicker_datePicker);

        if(unit == DateUnit.Year){
            mDatePicker.findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setVisibility(View.GONE);
            mDatePicker.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        }else if(unit == DateUnit.Month){
            mDatePicker.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        }

        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR,year);
                mCalendar.set(Calendar.MONTH,monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            }
        });

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (onDateTimeSetListener != null) {
                    onDateTimeSetListener.onDateSet(mDatePicker, mCalendar);
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(YEAR, mDatePicker.getYear());
        state.putInt(MONTH, mDatePicker.getMonth());
        state.putInt(DAY, mDatePicker.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int year = savedInstanceState.getInt(YEAR);
        final int month = savedInstanceState.getInt(MONTH);
        final int day = savedInstanceState.getInt(DAY);
        mDatePicker.init(year, month, day, null);
    }
}