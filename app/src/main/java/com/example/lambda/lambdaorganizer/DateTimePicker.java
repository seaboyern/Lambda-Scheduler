package com.example.lambda.lambdaorganizer;

import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Dialog;
import android.app.AlertDialog;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.view.LayoutInflater;
import android.content.DialogInterface;


public class DateTimePicker extends DialogFragment {

    public interface DateTimeListener {
        public void onDateTimePickerConfirm(Date d);
    }

    DateTimeListener mDateTimeListener = null;

    public void setDateTimeListener(DateTimeListener listener) {
        mDateTimeListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.datepicker, null))
            // Add action buttons
           .setPositiveButton("Select", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   // Retreive DateTime information.
                   Dialog dialog_view = DateTimePicker.this.getDialog();
                   DatePicker dp = (DatePicker)dialog_view.findViewById(R.id.datePicker);
                   TimePicker tp = (TimePicker)dialog_view.findViewById(R.id.timePicker);
                   Date d = new Date(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(),
                           tp.getCurrentHour(), tp.getCurrentMinute());
                   if(mDateTimeListener != null) {
                       mDateTimeListener.onDateTimePickerConfirm(d);
                   } else {
                        throw new NullPointerException( "Must call setDateTimeListener with a valid DateTimeListener instance before showing the DateTimePicker dialog");
                   }
               }
           })
           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   DateTimePicker.this.getDialog().cancel();
               }
           });
        return builder.create();
    }
}
