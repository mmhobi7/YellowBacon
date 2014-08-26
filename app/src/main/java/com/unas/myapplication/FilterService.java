package com.unas.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by aaahh on 8/26/14.
 */
public class FilterService extends Service
{
    static MyDBHelper mDBHelper;
    public static FilterService mThis;
    public static View vw;
    private final IBinder rBinder = new LocalBinder();

    public void addView()
    {
        vw = new View(this);
        Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
        localDisplay.getWidth();
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(10 * localDisplay.getWidth(), 10 * localDisplay.getHeight(), 2006, 1288, -3);
        WindowManager localWindowManager = (WindowManager)getSystemService("window");
        localLayoutParams.gravity = 53;
        localLayoutParams.x = -100;
        localLayoutParams.y = -100;
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        localSQLiteDatabase.close();
        int i = Common.converToDecimalFromHex(Common.BgColor);
        vw.setBackgroundColor(i);
        vw.getBackground().setAlpha(Common.Alpha);
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
        if (vw != null)
            ((WindowManager)getSystemService("window")).removeView(vw);
        vw = null;
    }

    public void setAlpha(int paramInt)
    {
        if (vw == null)
            return;
        vw.getBackground().setAlpha(paramInt);
    }

    public void setConfig()
    {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        int i = Common.converToDecimalFromHex(Common.BgColor);
        vw.setBackgroundColor(i);
        vw.getBackground().setAlpha(Common.Alpha);
        localSQLiteDatabase.close();
    }

    public void startNotification()
    {
        //played with...
        Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
        localIntent.addFlags(872415232);
        PendingIntent localPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, localIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification localNotification = new Notification(2130837531, "Screen Filter", System.currentTimeMillis());
        localNotification.setLatestEventInfo(this, getResources().getString(2131099674), "Screen Filter", localPendingIntent);
        mNotificationManager.notify(1, localNotification);
        startForeground(1, localNotification);
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
