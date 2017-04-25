package com.davebyrne.ironsight.activity;

/**
 * Created by Dave on 25/04/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, intent.getStringExtra("param"),Toast.LENGTH_SHORT).show();
    }

}
