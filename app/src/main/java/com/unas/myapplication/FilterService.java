package com.unas.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by aaahh on 8/26/14. Edited 9/13/14
 */
public class FilterService extends Service {
    public static FilterService mThis;
    public static View vw;
    public static GradientDrawable gt;
    static MyDBHelper mDBHelper;
    public final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("android.intent.action.CONFIGURATION_CHANGED")) {
                Common.O = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
                FilterService.mThis.setConfig();
            }
        }
    };
    private final IBinder rBinder = new LocalBinder();
    public WindowManager.LayoutParams localLayoutParams;
    public WindowManager localWindowManager;

    public void addView() {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        float screenHeight = displaymetrics.heightPixels;
        Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"))) / 100f) * screenHeight);
        Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"))) - 50) * 2) / 100f)) * (screenHeight / 2) * -1);
        localSQLiteDatabase.close();
        vw = new View(this);
        localLayoutParams = new WindowManager.LayoutParams(screenWidth, (int) screenHeight, 2006, 1288, -3);
        localWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        localLayoutParams.height = Common.Height;
        localLayoutParams.y = Common.Area;
        localLayoutParams.format = (PixelFormat.RGBA_8888);
        int i = Common.converToDecimalFromHex(Common.BgColor);
        String fade = Common.BgColor.replace("#", "#00");
        if (MainActivity.mThis.toggleButtonOnOff2.isChecked()) {
            int b = (Color.parseColor(fade));
            gt = new GradientDrawable();
            if (Common.GradientType.contains("1")) {
                int colors[] = {b, i};
                gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gt.setColors(colors);
            }
            if (Common.GradientType.contains("2")) {
                int colors[] = {b, i, b};
                gt.setColors(colors);

            }
            if (Common.GradientType.contains("3")) {
                int colors[] = {b, i};
                gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                gt.setColors(colors);
            }
            vw.setBackground(gt);
        } else {
            vw.setBackgroundColor(i);
        }
        vw.getBackground().setAlpha(Common.Alpha);
        vw.getBackground().setDither(true);
        vw.setRotation(0);
        localWindowManager.addView(vw, localLayoutParams);
        if (!Common.Notif) {
            startNotification();
        }
        rotationReceiver();
    }

    public void endNotification() {
        startForeground(0, new Notification());
    }

    public IBinder onBind(Intent paramIntent) {
        return this.rBinder;
    }

    @SuppressLint({"NewApi"})
    public void onCreate() {
        super.onCreate();
        mThis = this;
    }

    public void onDestroy() {
        super.onDestroy();
        removeView();
    }

    public void removeView() {
        if (vw != null) {
            ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).removeView(vw);
        }
        vw = null;
    }

    public void setAlpha(int paramInt) {
        if (vw == null) {
            return;
        }
        vw.getBackground().setAlpha(paramInt);
    }

    public void setHeight(int paramInt) {
        if (!(vw == null)) {
            localLayoutParams.height = (paramInt);
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setArea(int paramInt) {
        if (!(vw == null)) {
            localLayoutParams.y = (paramInt);
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setConfig() {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        float screenHeight = displaymetrics.heightPixels;
        float screenWidth = displaymetrics.widthPixels;
        int i = Common.converToDecimalFromHex(Common.BgColor);
        String fade = Common.BgColor.replace("#", "#00");
        Log.d("1", String.valueOf(Common.O));
        if (Common.O == 0) {
            Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"))) / 100f) * screenHeight);
            Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"))) - 50) * 2) / 100f)) * (screenHeight / 2) * -1);
            localLayoutParams.width = (int) screenWidth;
            localLayoutParams.height = (Common.Height);
            localLayoutParams.x = 0;
            localLayoutParams.y = (Common.Area);
            if (MainActivity.mThis.toggleButtonOnOff2.isChecked()) {
                int b = (Color.parseColor(fade));
                gt = new GradientDrawable();
                if (Common.GradientType.contains("1")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("2")) {
                    int colors[] = {b, i, b};
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("3")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                    gt.setColors(colors);
                }
                vw.setBackground(gt);
            } else {
                vw.setBackgroundColor(i);
            }
        }
        if (Common.O == 1) {
            Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"))) / 100f) * screenHeight);
            Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"))) - 50) * 2) / 100f)) * (screenHeight / 2) * -1);
            localLayoutParams.height = (Common.Height);
            localLayoutParams.width = (int) screenWidth;
            localLayoutParams.x = (Common.Area);
            localLayoutParams.y = 0;

            if (MainActivity.mThis.toggleButtonOnOff2.isChecked()) {
                int b = (Color.parseColor(fade));
                gt = new GradientDrawable();
                if (Common.GradientType.contains("1")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("2")) {
                    int colors[] = {b, i, b};
                    gt.setColors(colors);
                    gt.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                }
                if (Common.GradientType.contains("3")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                    gt.setColors(colors);
                }
                vw.setBackground(gt);
            } else {
                vw.setBackgroundColor(i);
            }
        }
        if (Common.O == 2) {
            Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"))) / 100f) * screenHeight);
            Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"))) - 50f) * 2f) / 100f)) * (screenHeight / 2f) * -1f);
            localLayoutParams.height = (Common.Height);
            localLayoutParams.width = (int) screenWidth;
            localLayoutParams.x = 0;
            localLayoutParams.y = ((Common.Area) * -1);

            if (MainActivity.mThis.toggleButtonOnOff2.isChecked()) {
                int b = (Color.parseColor(fade));
                gt = new GradientDrawable();
                if (Common.GradientType.contains("1")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("2")) {
                    int colors[] = {b, i, b};
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("3")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    gt.setColors(colors);
                }
                vw.setBackground(gt);
            } else {
                vw.setBackgroundColor(i);
            }
        }
        if (Common.O == 3) {
            Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"))) / 100f) * screenHeight);
            Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"))) - 50) * 2) / 100f)) * (screenWidth / 2) * -1);
            localLayoutParams.height = (Common.Height);
            localLayoutParams.width = (int) screenWidth;
            localLayoutParams.x = ((Common.Area) * -1);
            localLayoutParams.y = 0;

            if (MainActivity.mThis.toggleButtonOnOff2.isChecked()) {
                int b = (Color.parseColor(fade));
                gt = new GradientDrawable();
                if (Common.GradientType.contains("1")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                    gt.setColors(colors);
                }
                if (Common.GradientType.contains("2")) {
                    int colors[] = {b, i, b};
                    gt.setColors(colors);
                    gt.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                }
                if (Common.GradientType.contains("3")) {
                    int colors[] = {b, i};
                    gt.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    gt.setColors(colors);
                }
                vw.setBackground(gt);
            } else {
                vw.setBackgroundColor(i);
            }
        }
        localWindowManager.updateViewLayout(vw, localLayoutParams);
        vw.getBackground().setAlpha(Common.Alpha);
        localSQLiteDatabase.close();
    }

    public void startNotification() {
        Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent localPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, localIntent, 0);
        NotificationManager n = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        Notification localNotification = new Notification.Builder(this)
                .setContentTitle("Filter Screen")
                .setContentText("Activated")
                .setSmallIcon(android.R.drawable.ic_input_get)
                .setContentIntent(localPendingIntent)
                .build();
        n.notify(1, localNotification);
        startForeground(1, localNotification);
        Common.Notif = true;
    }

    public void rotationReceiver() {
        IntentFilter filter1 = new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
        if (Common.Receiver) {
            registerReceiver(myReceiver, filter1);
        }
        if (!Common.Receiver) {
            unregisterReceiver(myReceiver);
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        FilterService getService() {
            return FilterService.this;
        }
    }
}
