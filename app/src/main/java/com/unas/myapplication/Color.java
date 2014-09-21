package com.unas.myapplication;

/**
 * Created by aaahh on 8/26/14. edited some
 */

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

public class Color extends Activity {
    public static Color mThis;
    static MyDBHelper mDBHelper;
    final Handler
            setColor = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            Color.mDBHelper = new MyDBHelper(Color.mThis, MyDBHelper.dbNm, null, MyDBHelper.dbVer);
            SQLiteDatabase localSQLiteDatabase = Color.mDBHelper.getWritableDatabase();
           Common.FilterYN = Color.mDBHelper.getKeyData(localSQLiteDatabase, "FilterYN");
           int i = Common.converToDecimalFromHex(Common.BgColor);
            Color.mDBHelper.putKeyData(localSQLiteDatabase, "BgColor", Common.BgColor);
         localSQLiteDatabase.close();
            MainActivity.mThis.buttonColor1.setBackgroundColor(i);
           if (Common.FilterYN.equals("Y"))
             MainActivity.mThis.rService.setConfig();
           Color.this.finish();
       }
    };
    final Handler setCancel = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            Color.this.finish();
        }
    };

   // public static void goUrl() {
        //webView.loadUrl("file:///android_asset/a.html");
  //  }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        setContentView(R.layout.colors);
        final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
        SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

        Log.d("colo(u)r", String.valueOf(picker.getColor()));

        picker.setOldCenterColor(picker.getColor());
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                Log.d("colo(u)r", String.valueOf(picker.getColor()));
            }
        });
        picker.setShowOldCenterColor(true);
        saturationBar.setOnSaturationChangedListener(new SaturationBar.OnSaturationChangedListener() {
            @Override
            public void onSaturationChanged(int saturation) {
            }
        });
        valueBar.setOnValueChangedListener(new ValueBar.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
            }
        });
    }
        /*webView = (WebView) findViewById(R.id.webView1);
        webView.setWebViewClient(new HelloWebViewClient());
        WebChromeClient localWebChromeClient = new WebChromeClient();
        webView.setWebChromeClient(localWebChromeClient);
        webView.setWillNotCacheDrawing(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setBackgroundColor(0);
        webView.addJavascriptInterface(new JavaScriptInterface(mThis), "android");
        goUrl();

        //getWindow().setFlags(4, 4);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        //Both of these are used...
        @JavascriptInterface
        public void setCancel(String paramAnonymousString) {
            //Message localMessage = Color.this.setCancel.obtainMessage();
            //Color.this.setCancel.sendMessage(localMessage);
            Log.d("0", paramAnonymousString);
        }

        @JavascriptInterface
        public void setColor(String paramAnonymousString) {
            Log.d("colo(u)r", paramAnonymousString);
            Common.BgColor = "#" + paramAnonymousString.replaceAll("#", "");
            Log.d("colo(u)r", Common.BgColor);
            Message localMessage = Color.this.setColor.obtainMessage();
            Color.this.setColor.sendMessage(localMessage);
        }
    }
/*
    class HelloWebViewClient extends WebViewClient {

        public void onPageFinished(WebView paramWebView, String paramString) {
            super.onPageFinished(paramWebView, paramString);
            //Color.webView.loadUrl("javascript:document.write(\"" + ColorH.getH() + "\")");
        }

        public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2) {
            if (paramString2.equals("에러 url"))
                Toast.makeText(Color.this.getApplicationContext(), "Oh no! " + paramString2, Toast.LENGTH_LONG).show();
        }

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
            //paramWebView.loadUrl(paramString);
            return true;
        }
    }
    */
}