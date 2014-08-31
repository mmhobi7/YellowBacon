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
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
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
    ToggleButton toggleButtonOnOff2;

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
        this.toggleButtonOnOff2 = ((ToggleButton) findViewById(R.id.toggleButtonOnOff2));
        this.toggleButtonOnOff2.setOnClickListener(this);
        this.buttonColor1 = ((Button) findViewById(2131034120));
        this.buttonColor1.setOnClickListener(this);
        this.buttonColor2 = ((Button) findViewById(2131034121));
        this.buttonColor2.setOnClickListener(this);
        mDBHelper = new MyDBHelper(this, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.FilterYN = mDBHelper.getKeyData(localSQLiteDatabase, "FilterYN");
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        int a = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        this.textViewPer.setText(a + "%");
        Common.Alpha = 200 - a * 2;
        int b = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"));
        Common.Height = (int) ((b / 100f) * 1920f);
        int c = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"));
        Common.Area = (int) ((((c - 50) * 2) / 100f) * 960 * -1);
        this.toggleButtonOnOff.setChecked(false);
        if (Common.FilterYN.equals("Y"))
            this.toggleButtonOnOff.setChecked(true);
        this.toggleButtonOnOff2.setChecked(true);
        int j = Common.converToDecimalFromHex(Common.BgColor);
        this.buttonColor1.setBackgroundColor(j);
        localSQLiteDatabase.close();
        this.seekBar1 = ((SeekBar) findViewById(2131034123));
        this.seekBar1.setMax(100);
        if (Common.passedonce) {
            this.seekBar1.setProgress(a);
        } else {
            this.seekBar1.setProgress(50);
            Common.passedonce = true;
        }
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

                    int a = paramAnonymousSeekBar.getProgress();
                    MainActivity.this.textViewPer.setText(a + "%");
                    SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Alpha", (Integer.toString(a)));
                    Common.Alpha = 200 - a * 2;
                    MainActivity.this.rService.setAlpha(Common.Alpha);
                } catch (IllegalStateException ignored) {
                }
            }
        });

        this.seekBar2 = ((SeekBar) findViewById(R.id.seekBar2));
        this.seekBar2.setMax(100);
        if (Common.passedonce) {
            this.seekBar2.setProgress(b);
        } else {
            this.seekBar2.setProgress(50);
            Common.passedonce = true;
        }
        this.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                Common.Height = (int) ((paramAnonymousInt / 100f) * 1920f);
                MainActivity.this.rService.setHeight(Common.Height);
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }

            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                try {
                    int b = paramAnonymousSeekBar.getProgress();
                    SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Height", (Integer.toString(b)));
                    Common.Height = (int) ((b / 100f) * 1920f);
                    MainActivity.this.rService.setHeight(Common.Height);
                } catch (IllegalStateException ignored) {
                }
            }
        });

        this.seekBar3 = ((SeekBar) findViewById(R.id.seekBar3));
        this.seekBar3.setMax(100);
        if (Common.passedonce) {
            this.seekBar3.setProgress(c);
        } else {
            this.seekBar3.setProgress(50);
            Common.passedonce = true;
        }
        this.seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                Common.Area = (int) ((((paramAnonymousInt - 50) * 2) / 100f) * 960 * -1);
                MainActivity.this.rService.setArea(Common.Area);
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }

            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                int c = paramAnonymousSeekBar.getProgress();
                SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Area", (Integer.toString(c)));
                Common.Area = (int) ((((c - 50) * 2) / 100f) * 960 * -1);
                MainActivity.this.rService.setArea(Common.Area);
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