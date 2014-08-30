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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by aaahh on 8/26/14. .
 */
public class FilterService extends Service
{
    static MyDBHelper mDBHelper;
    public static FilterService mThis;
    public static View vw;
    private final IBinder rBinder = new LocalBinder();
    public WindowManager.LayoutParams localLayoutParams;
    public WindowManager localWindowManager;

    public void addView()
    {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        Common.Height = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"));
        Common.Area = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"));
        Log.d("p", String.valueOf(Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"))));
        localSQLiteDatabase.close();
        vw = new View(this);
        Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
        localLayoutParams = new WindowManager.LayoutParams( 1080, 50, 2006, 1288, -3);
        localWindowManager = (WindowManager)getSystemService("window");
        localLayoutParams.y = 50;
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
        Log.d("g", "qq");
        if (vw == null)
            return;
        vw.getBackground().setAlpha(paramInt);
    }

    public void setHeight(int height)
    {
        if (vw == null) {
        } else {
            localLayoutParams.height = Common.Height;
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setArea (){
        if (vw == null) {
        } else {
            localLayoutParams.x = Common.Area;
            localWindowManager.updateViewLayout(vw, localLayoutParams);
        }
    }

    public void setConfig()
    {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        Common.Alpha = 200 - 2 * Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        Common.Height = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"));
        Common.Area = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"));
        int i = Common.converToDecimalFromHex(Common.BgColor);
        vw.setBackgroundColor(i);
        vw.getBackground().setAlpha(Common.Alpha);
        localSQLiteDatabase.close();
    }

    public void startNotification()
    {
        Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
        localIntent.addFlags(872415232);
        PendingIntent localPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, localIntent, 0);
        NotificationManager n = ((NotificationManager)getSystemService("notification"));
        Notification localNotification = new Notification(2130837531, "Screen Filter", System.currentTimeMillis());
        localNotification.setLatestEventInfo(this, getResources().getString(2131099674), "Screen Filter", localPendingIntent);
        n.notify(1, localNotification);
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
