package com.unas.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends Activity
        implements View.OnClickListener {
    public static MainActivity mThis;
    private static MyDBHelper mDBHelper;
    Button buttonColor1;
    FilterService rService;
    private final ServiceConnection rConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder) {
            FilterService.LocalBinder localLocalBinder = (FilterService.LocalBinder) paramAnonymousIBinder;
            MainActivity.this.rService = localLocalBinder.getService();
            if ((Common.FilterYN.equals("Y")) && (FilterService.vw == null)) {
                MainActivity.this.startService(new Intent(MainActivity.mThis, FilterService.class));
                MainActivity.this.rService.addView();
            }
        }

        public void onServiceDisconnected(ComponentName paramAnonymousComponentName) {
        }
    };
    ToggleButton toggleButtonOnOff2;
    private Button checkBox;
    private TextView textViewPer;
    private ToggleButton toggleButtonOnOff;

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case 2131034120:
            default:
                return;
            case 2131034119:
                SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
                if (this.toggleButtonOnOff.isChecked()) {
                    Common.Receiver = true;
                    this.rService.startNotification();
                    mDBHelper.putKeyData(localSQLiteDatabase, "FilterYN", "Y");
                    startService(new Intent(this, FilterService.class));
                    this.rService.addView();
                    toggleButtonOnOff2.setEnabled(false);
                    return;
                }
                //somewhat messy...
                Common.Receiver = false;
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancelAll();
                this.rService.endNotification();
                Common.Notif = false;
                mDBHelper.putKeyData(localSQLiteDatabase, "FilterYN", "N");
                this.rService.removeView();
                stopService(new Intent(this, FilterService.class));
                toggleButtonOnOff2.setEnabled(true);
                return;
            case 2131034121:
        }
        startActivity(new Intent(mThis, Color.class));
    }

    public void gradientmenu(View view) {
        Log.d("e", Common.GradientType);
        if (toggleButtonOnOff2.isChecked()) {
            SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
            mDBHelper.putKeyData(localSQLiteDatabase, "GradientYN", "Y");
            localSQLiteDatabase.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String[] Lists = {"Top only", "All", "Bottom only"};
                    new AlertDialog.Builder(mThis)
                            .setTitle("Where")
                            .setItems(Lists, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        Common.GradientType = String.valueOf(1);
                                        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
                                        MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "GradientType", (Integer.toString(i)));
                                    } else {
                                        if (i == 1) {
                                            Common.GradientType = String.valueOf(2);
                                            SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
                                            MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "GradientType", (Integer.toString(i)));

                                        } else {
                                            if (i == 2) {
                                                Common.GradientType = String.valueOf(3);
                                                SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
                                                MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "GradientType", (Integer.toString(i)));

                                            }
                                        }
                                    }
                                }
                            })
                            .show();
                }
            });
        } else {
            SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
            mDBHelper.putKeyData(localSQLiteDatabase, "GradientYN", "N");
            localSQLiteDatabase.close();
        }
    }

    public void boot() {
        if (checkBox.isActivated()) {
            Common.toboot = "Y";
        } else {
            Common.toboot = "N";
        }
        mDBHelper = new MyDBHelper(this, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        if (Common.toboot.contains("Y")) {
            mDBHelper.putKeyData(localSQLiteDatabase, "toboot", "Y");
        } else {
            mDBHelper.putKeyData(localSQLiteDatabase, "toboot", "N");
        }
        localSQLiteDatabase.close();
    }

    @SuppressLint({"NewApi"})
    public void onCreate(Bundle paramBundle) {

        super.onCreate(paramBundle);
        setContentView(2130903040);
        if (Common.boot) {
            moveTaskToBack(true);
        }
        mThis = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        this.textViewPer = ((TextView) findViewById(2131034122));
        this.toggleButtonOnOff = ((ToggleButton) findViewById(2131034119));
        this.toggleButtonOnOff.setOnClickListener(this);
        this.toggleButtonOnOff2 = ((ToggleButton) findViewById(R.id.toggleButtonOnOff2));
        this.buttonColor1 = ((Button) findViewById(2131034120));
        this.buttonColor1.setOnClickListener(this);
        Button buttonColor2 = ((Button) findViewById(2131034121));
        buttonColor2.setOnClickListener(this);
        this.checkBox = ((CheckBox) findViewById(R.id.checkBox));
        mDBHelper = new MyDBHelper(this, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();
        Common.FilterYN = mDBHelper.getKeyData(localSQLiteDatabase, "FilterYN");
        Common.GradientYN = mDBHelper.getKeyData(localSQLiteDatabase, "GradientYN");
        Common.GradientType = mDBHelper.getKeyData(localSQLiteDatabase, "GradientTypes");
        Common.BgColor = mDBHelper.getKeyData(localSQLiteDatabase, "BgColor");
        int a = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Alpha"));
        Common.passedonce = mDBHelper.getKeyData(localSQLiteDatabase, "passedonce");
        Common.toboot = (mDBHelper.getKeyData(localSQLiteDatabase, "toboot"));
        if (Common.toboot.contains("Y")) {
            checkBox.setActivated(true);
        } else {
            checkBox.setActivated(false);
        }
//        this.textViewPer.setText(a + "%");
        Common.Alpha = 200 - a * 2;
        this.checkBox.setActivated(true);
        int b = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Height"));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
        float screenHeight = displaymetrics.heightPixels;
        Common.Height = (int) ((b / 100f) * screenHeight);
        int c = Integer.parseInt(mDBHelper.getKeyData(localSQLiteDatabase, "Area"));
        Common.Area = (int) ((((c - 50) * 2) / 100f) * (screenHeight / 2) * -1);
        this.toggleButtonOnOff.setChecked(false);
        if (Common.FilterYN.equals("Y")) {
            this.toggleButtonOnOff.setChecked(true);
            this.toggleButtonOnOff2.setEnabled(false);
            Common.Receiver = true;
        }
        if (Common.GradientYN.equals("Y")) {
            this.toggleButtonOnOff2.setChecked(true);
        } else {
            this.toggleButtonOnOff2.setChecked(false);
        }
        int j = Common.converToDecimalFromHex(Common.BgColor);
        this.buttonColor1.setBackgroundColor(j);
        SeekBar seekBar1 = ((SeekBar) findViewById(2131034123));
        seekBar1.setMax(100);
        Log.d("q", String.valueOf(Common.passedonce));
        if (Common.passedonce == null) {
            Common.passedonce = "N";
        }
        if (Common.passedonce.equals("Y")) {
            seekBar1.setProgress(a);
        } else {
            seekBar1.setProgress(50);
            MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "passedonce", ("Y"));
        }
        localSQLiteDatabase.close();
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    Common.Alpha = 200 - a * 2;
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Alpha", (Integer.toString(a)));
                    MainActivity.this.rService.setAlpha(Common.Alpha);
                } catch (IllegalStateException ignored) {
                }
            }
        });

        SeekBar seekBar2 = ((SeekBar) findViewById(R.id.seekBar2));
        seekBar2.setMax(100);
        Log.d("r", String.valueOf(Common.passedonce));
        if (Common.passedonce.equals("Y")) {
            seekBar2.setProgress(b);
        } else {
            seekBar2.setProgress(50);
            localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
            MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "passedonce", ("Y"));
        }
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
                float screenHeight = displaymetrics.heightPixels;
                Common.Height = (int) ((paramAnonymousInt / 100f) * screenHeight);
                MainActivity.this.rService.setHeight(Common.Height);
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }

            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                try {
                    int b = paramAnonymousSeekBar.getProgress();
                    SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                    MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Height", (Integer.toString(b)));
                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
                    float screenHeight = displaymetrics.heightPixels;
                    Common.Height = (int) ((b / 100f) * screenHeight);
                    MainActivity.this.rService.setHeight(Common.Height);
                } catch (IllegalStateException ignored) {
                }
            }
        });

        SeekBar seekBar3 = ((SeekBar) findViewById(R.id.seekBar3));
        seekBar3.setMax(100);
        if (Common.passedonce.equals("Y")) {
            seekBar3.setProgress(c);
        } else {
            seekBar3.setProgress(50);
            localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
            MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "passedonce", ("Y"));
        }
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
                float screenHeight = displaymetrics.heightPixels;
                Common.Area = (int) ((((paramAnonymousInt - 50) * 2) / 100f) * (screenHeight / 2) * -1);
                MainActivity.this.rService.setArea(Common.Area);
            }

            public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {
            }

            public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {
                int c = paramAnonymousSeekBar.getProgress();
                SQLiteDatabase localSQLiteDatabase = MainActivity.mDBHelper.getWritableDatabase();
                MainActivity.mDBHelper.putKeyData(localSQLiteDatabase, "Area", (Integer.toString(c)));
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
                float screenHeight = displaymetrics.heightPixels;
                Common.Area = (int) ((((c - 50) * 2) / 100f) * (screenHeight / 2) * -1);
                MainActivity.this.rService.setArea(Common.Area);
            }
        });
//        MainActivity.this.textViewPer.setText(seekBar1.getProgress() + "%");
        if (Common.boot) {
            toggleButtonOnOff.setChecked(true);
            Common.boot = false;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Common.Receiver = false;
        unbindService(rConnection);
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
}