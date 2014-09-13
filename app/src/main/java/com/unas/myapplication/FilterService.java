package com.unas.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;


import java.net.CookieHandler;


/**
 * Created by aaahh on 8/26/14. .
 */
public class FilterService extends Service
{
    static MyDBHelper mDBHelper;
    public static FilterService mThis;
    public static View vw;
    public static GradientDrawable gt;
    private final IBinder rBinder = new LocalBinder();
    public WindowManager.LayoutParams localLayoutParams;
    public WindowManager localWindowManager;

    public void addView()
    {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager)getSystemService("window")).getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        float screenHeight = displaymetrics.heightPixels;
        Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height")))/100f)*screenHeight);
        Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area")))- 50) * 2) / 100f)) * (screenWidth/2) * -1);
        localSQLiteDatabase.close();
        vw = new View(this);
        localLayoutParams = new WindowManager.LayoutParams( screenWidth, (int) screenHeight, 2006, 1288, -3);
        localWindowManager = (WindowManager)getSystemService("window");
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
        }else {
            vw.setBackgroundColor(i);
        }
        vw.getBackground().setAlpha(Common.Alpha);
        vw.getBackground().setDither(true);
        vw.setRotation(0);
        localWindowManager.addView(vw, localLayoutParams);

    }

    public void endNotification()
    {
        startForeground(0, new Notification());
    }

    public IBinder onBind(Intent paramIntent)
    {
        return this.rBinder;
    }

    @SuppressLint({"NewApi"})
    public void onCreate()
    {
        super.onCreate();
        mThis = this;
    }

    public void onDestroy()
    {
        super.onDestroy();
        removeView();
    }

    public void removeView()
    {
        if (vw != null) {
            ((WindowManager) getSystemService("window")).removeView(vw);
        }
        vw = null;
    }

    public void setAlpha(int paramInt)
    {
        if (vw == null) {
            return;
        }
        vw.getBackground().setAlpha(paramInt);
    }

    public void setHeight(int paramInt)
    {
        if (vw == null) {
        } else {
            localLayoutParams.height = (paramInt);
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setArea (int paramInt){
        if (vw == null) {
        } else {
            localLayoutParams.y = (paramInt);
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setConfig()
    {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager)getSystemService("window")).getDefaultDisplay().getMetrics(displaymetrics);
        float screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        Common.Height = (int) (((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height")))/100f)*screenWidth);
        Common.Area = (int) ((((((Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area")))- 50) * 2) / 100f)) * (screenHeight/2) * -1);
        localLayoutParams.height = (Common.Height);
        localLayoutParams.y = (Common.Area);
        localWindowManager.updateViewLayout(vw, localLayoutParams);
        int i = Common.converToDecimalFromHex(Common.BgColor);
        String fade = Common.BgColor.replace("#","#00");
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
        localSQLiteDatabase.close();
    }

    public void startNotification()
    {
        Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
        localIntent.addFlags(872415232);
        PendingIntent localPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, localIntent, 0);
        //NotificationManager n = ((NotificationManager)getSystemService("notification"));
        //Notification localNotification = new Notification(2130837531, "Screen Filter", System.currentTimeMillis());
        //localNotification.setLatestEventInfo(this, getResources().getString(2131099674), "Screen Filter", localPendingIntent);
        //startForeground(1, localNotification);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Screen Filter")
                .setContentText("Activated")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(localPendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        noti.defaults |= Notification.DEFAULT_ALL;
        noti.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        notificationManager.notify(1, noti);

    }

    public class LocalBinder extends Binder
    {
        public LocalBinder()
        {
        }
        FilterService getService()
        {
            return FilterService.this;
        }
    }
}
