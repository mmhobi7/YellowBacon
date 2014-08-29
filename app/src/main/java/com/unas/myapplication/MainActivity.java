package com.unas.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends Activity
        implements View.OnClickListener {
    static MyDBHelper mDBHelper;
    public static MainActivity mThis;
    Button buttonColor1;
    Button buttonColor2;
    private boolean rBound = false;
    private ServiceConnection rConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder) {
            FilterService.LocalBinder localLocalBinder = (FilterService.LocalBinder) paramAnonymousIBinder;
            MainActivity.this.rService = localLocalBinder.getService();
            MainActivity.this.rBound = true;
            if ((Common.FilterYN.equals("Y")) && (FilterService.vw == null)) {
                MainActivity.this.startService(new Intent(MainActivity.mThis, FilterService.class));
                MainActivity.this.rService.addView();
            }
        }

        public void onServiceDisconnected(ComponentName paramAnonymousComponentName) {
            MainActivity.this.rBound = false;
        }
    };
    FilterService rService;
    SeekBar seekBar1;
    TextView textViewPer;
    ToggleButton toggleButtonOnOff;

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case 2131034120:
            default:
                return;
            case 2131034119:
                SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
                if (this.toggleButtonOnOff.isChecked()) {
                    this.rService.startNotification();
                    mDBHelper.putKeyData(localSQLiteDatabase, "FilterYN", "Y");
                    startService(new Intent(this, FilterService.class));
                    this.rService.addView();
                    return;
                }
                //somewhat messy...
                if (!(this.toggleButtonOnOff.isChecked())) {
                    ((NotificationManager) getSystemService("notification")).cancelAll();
                    this.rService.endNotification();
                    mDBHelper.putKeyData(localSQLiteDatabase, "FilterYN", "N");
                    this.rService.removeView();
                    stopService(new Intent(this, FilterService.class));
                    return;
                }
            case 2131034121:
        }
        startActivity(new Intent(mThis, Color.class));
    }

    @SuppressLint({"NewApi"})
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(2130903040);
        mThis = this;
        this.textViewPer = ((TextView) findViewById(2131034122));
        this.toggleButtonOnOff = ((ToggleButton) findViewById(2131034119));
        this.toggleButtonOnOff.setOnClickListener(this);
        this.buttonColor1 = ((Button) findViewById(2131034120));
        this.buttonColor1.setOnClickListener(this);
        this.buttonColor2 = ((Button) findViewById(2131034121));
        this.buttonColor2.setOnClickListener(this);
        mDBHelper = new MyDBHelper(this, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.FilterYN = mDBHelper.getKeyData(localSQLiteDatabase, "FilterYN");
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        int i = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        this.textViewPer.setText(i + "%");
        Common.Alpha = 200 - i * 2;
        this.toggleButtonOnOff.setChecked(false);
        if (Common.FilterYN.equals("Y"))
            this.toggleButtonOnOff.setChecked(true);
        int j = Common.converToDecimalFromHex(Common.BgColor);
        this.buttonColor1.setBackgroundColor(j);
        localSQLiteDatabase.close();
        this.seekBar1 = ((SeekBar) findViewById(2131034123));
        this.seekBar1.setMax(100);
        this.seekBar1.setProgress(i);
        this.seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                MainActivity.this.textViewPer.setText(paramAnonymousInt + "%");
                Common.Alpha = 200 - paramAnonymousInt * 2;
                MainActivity.this.rService.setAlpha(Common.Alpha);
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }

            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                try {
                    int i = paramAnonymousSeekBar.getProgress();
                    MainActivity.this.textViewPer.setText(i + "%");
                    SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Alpha", (Integer.toString(i)));
                    Common.Alpha = 200 - i * 2;
                    MainActivity.this.rService.setAlpha(Common.Alpha);
                    return;
                } catch (IllegalStateException localIllegalStateException) {
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, FilterService.class), this.rConnection, 1);
    }

    public void onWindowFocusChanged(boolean paramBoolean) {
        super.onWindowFocusChanged(paramBoolean);
    }
}