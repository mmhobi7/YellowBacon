package com.unas.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by aaahh on 9/7/14.
 */
public class layout extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("t", "p");
        getRotation(context);
        // Checks the orientation of the screen
        if (getRotation(context).equals("2")) {
            Log.d("t", "landscape");
        } else if (getRotation(context).equals("1")) {
            Log.d("t", "portrait");
        }
    }

    public String getRotation(Context context) {
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return "1";
            case Surface.ROTATION_90:
                return "2";
            case Surface.ROTATION_180:
                return "3";
            default:
                return "4";
        }
    }
}
