package com.example.lambda.lambdaorganizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Date;


/**
 * Dialog for selecting a date and time.
 * To use, create a class that implements the DateTimeListener interface
 * and overrides the onDateTimePickerConfirm method.
 * After creating an instance of the DateTimePicker,
 * call the setDateTimeListener method and pass in an instance
 * of the class you created that implements the DateTimeListener interface.
 * Then call the show() method on the DateTimePicker instance.
 * After pressing the "Select" button in the dialog,
 * the onDateTimePickerConfirm(Date d) method that you overrode will
 * be called with d being the Date object represented by the users input
 * from the dialog.
 */
public class DateTimePicker extends DialogFragment {

    public interface DateTimeListener {
        public void onDateTimePickerConfirm(Date d);
    }

    DateTimeListener mDateTimeListener = null;

    /** Register a DateTimeListener with the DateTimePicker.
     *  Call the before showing the dialog.
     *  @param listener A DateTimeListener that overrides the onDateTimePickerConfirm method.
     */
    public void setDateTimeListener(DateTimeListener listener) {
        mDateTimeListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Android docs say not to directly instantiate a Dialog class...
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Set the view for the dialog to the custom dialog defined in
        // res/layout/datepicker.xml
        builder.setView(inflater.inflate(R.layout.datepicker, null))
            // Add action buttons
           .setPositiveButton("Select", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   // Retreive DateTime information from the pickers.
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
