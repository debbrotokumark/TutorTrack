package com.raselraihande.tutortrackbd.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.raselraihande.tutortrackbd.R;

import java.util.Objects;

public class NetworkChangeListener extends BroadcastReceiver {

    private AlertDialog dialog; // Keep a reference to the dialog

    @SuppressLint({"ResourceAsColor", "UnsafeProtectedBroadcastReceiver"})
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return; // Prevent crash if context is not an activity or activity is finishing
        }

        if (!Common.isConnectedToInternet(context)) {
            // If internet is not connected and dialog is not already showing
            if (dialog == null || !dialog.isShowing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View layout_dialog = LayoutInflater.from(context).inflate(R.layout.no_internet_dialog, null);
                builder.setView(layout_dialog);

                TextView btnRetry = layout_dialog.findViewById(R.id.btnRetry);

                // Show dialog
                dialog = builder.create();
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                    dialog.getWindow().setGravity(Gravity.CENTER);
                }
                dialog.setCancelable(false);

                btnRetry.setOnClickListener(view -> {
                    dialog.dismiss();
                    // Retry checking for internet connectivity
                    onReceive(context, intent);
                });
            }
        } else {
            // If internet is connected, dismiss the dialog if it's showing
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
