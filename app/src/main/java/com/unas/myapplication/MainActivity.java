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
import android.util.Log;
import android.view.Gravity;
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
    SeekBar seekBar2;
    SeekBar seekBar3;

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
        this.seekBar1.setProgress(50);
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

        this.seekBar2 = ((SeekBar) findViewById(R.id.seekBar2));
        this.seekBar2.setMax(100);
        this.seekBar2.setProgress(50);
        this.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                Common.Height = (int) ((paramAnonymousInt/100f)* 1920f);
                MainActivity.this.rService.setHeight(Common.Height);
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }

            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                try {
                    int i = paramAnonymousSeekBar.getProgress();
                    SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Height", (Integer.toString(i)));
                    Common.Height = (int) ((i/100f)* 1920f);
                    Log.d("aaas", String.valueOf(Common.Height));
                    MainActivity.this.rService.setHeight(Common.Height);
                    return;

                } catch (IllegalStateException localIllegalStateException) {
                }
            }
        });

        this.seekBar3 = ((SeekBar) findViewById(R.id.seekBar3));
        this.seekBar3.setMax(100);
        this.seekBar3.setProgress(50);
        this.seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                if (FilterService.vw == null) {
                } else {
                    Common.Area = (int) ((((paramAnonymousInt - 50) * 2) / 100f) * 960 * -1);
                    Log.d("p", String.valueOf(Common.Height / 1920));
                    MainActivity.this.rService.localLayoutParams.y = (Common.Area);
                    MainActivity.this.rService.localWindowManager.updateViewLayout(MainActivity.this.rService.vw, MainActivity.this.rService.localLayoutParams);
                }
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }
            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                /*
                try {
                    int i = paramAnonymousSeekBar.getProgress();
                    SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Area", (Integer.toString(i)));
                    Common.Area = (int) ((i/100f)* 1920f);
                    MainActivity.this.rService.setArea();
                    return;

                 catch (IllegalStateException localIllegalStateException) {
                }
                */
            }
        });



    }

    public void Cikcs(View v1){
        MainActivity.this.rService.localLayoutParams.y = ((MainActivity.this.rService.localLayoutParams.y)-50);
        Log.d("aaaaaaa", String.valueOf(MainActivity.this.rService.localLayoutParams.y));
        MainActivity.this.rService.localWindowManager.updateViewLayout(MainActivity.this.rService.vw, MainActivity.this.rService.localLayoutParams);
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