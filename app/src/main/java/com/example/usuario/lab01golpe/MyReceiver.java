package com.example.usuario.lab01golpe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    final String TAG = "Lab01_TAG ";
    static final String COUNTER = "counter";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i;
        switch (intent.getAction()) {
            case Intent.ACTION_POWER_DISCONNECTED:
                i = new Intent(context, MainActiv.class );
                context.startActivity(i);
                break;
            case Intent.ACTION_POWER_CONNECTED:
                i = new Intent(context, ParamActiv.class );
                i.putExtra(COUNTER,-1);
                context.startActivity(i);
                break;
            case Intent.ACTION_EDIT:
                Log.d(TAG,"Recibido");
            default:
                return;
        }
    }
}
