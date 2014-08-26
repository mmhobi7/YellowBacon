package com.unas.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Created by aaahh on 8/26/14.
 */
public class CDialog {

    private static Dialog m_loadingDialog = null;

    public CDialog() {
    }

    public static void hideLoading() {
        try {
            if (m_loadingDialog != null) {
                m_loadingDialog.dismiss();
                m_loadingDialog = null;
            }
            return;
        } catch (Exception exception) {
            Log.d("hideLoading", "hideLoading error ============> ");
        }
    }

    public static void showLoading(Context context) {
        hideLoading();
        if (m_loadingDialog == null) {
            m_loadingDialog = new Dialog(context, 0x7f090001);
            ProgressBar progressbar = new ProgressBar(context);
            android.view.ViewGroup.LayoutParams layoutparams = new android.view.ViewGroup.LayoutParams(-2, -2);
            m_loadingDialog.addContentView(progressbar, layoutparams);
            m_loadingDialog.setCancelable(false);
        }
        m_loadingDialog.show();
    }

    public static void showLoading2(Context context) {
        ProgressBar progressbar;
        android.view.WindowManager.LayoutParams layoutparams;
        hideLoading();
        m_loadingDialog = new Dialog(context, 0x7f090001);
        progressbar = new ProgressBar(context);
        m_loadingDialog.getWindow().setFlags(32, 32);
        layoutparams = new android.view.WindowManager.LayoutParams(-2, -2, 2006, 8, -3);
        if (m_loadingDialog != null) {
            return;
        } else {
            m_loadingDialog.addContentView(progressbar, layoutparams);
            if (m_loadingDialog == null) {
                return;
            } else {
                m_loadingDialog.setCancelable(true);
                m_loadingDialog.show();
                return;
            }
        }
    }
}