package com.example.lambda.lambdaorganizer.Scheduler;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;

public class TaskInfoDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String task_info = getArguments().getString("info");
        builder.setMessage(task_info.replace(';', '\n'))
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // dismiss
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
