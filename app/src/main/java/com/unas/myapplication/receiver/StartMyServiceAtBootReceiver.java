package com.unas.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.unas.myapplication.Common;
import com.unas.myapplication.MainActivity;
import com.unas.myapplication.MyDBHelper;

/**
 * Created by aaahh on 9/5/14.
 */
public class StartMyServiceAtBootReceiver extends BroadcastReceiver {
    public static MainActivity mThis;
    static MyDBHelper mDBHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        mDBHelper = new MyDBHelper(mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.toboot = (mDBHelper.getKeyData(localSQLiteDatabase, "toboot"));
        if (Common.toboot.contains("Y")) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent serviceIntent = new Intent(context, MainActivity.class);
                serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(serviceIntent);
            }
        }
    }
}