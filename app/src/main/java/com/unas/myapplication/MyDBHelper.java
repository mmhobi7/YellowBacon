package com.unas.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public static String dbNm = "filter.db";
    public static int dbVer = 1;

    public MyDBHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt) {
        super(paramContext, paramString, paramCursorFactory, paramInt);
    }

    public String getKeyData(SQLiteDatabase paramSQLiteDatabase, String paramString) {
        Cursor localCursor = paramSQLiteDatabase.rawQuery("SELECT data FROM FilterConfig WHERE key='" + paramString + "'", null);
        if (localCursor.getCount() == 0) {
            localCursor.close();
            return null;
        }
        localCursor.moveToNext();
        String str = localCursor.getString(0);
        localCursor.close();
        return str;
    }

    public void insertData(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2) {
        paramSQLiteDatabase.execSQL("insert into FilterConfig (key ,data) values  ( '" + paramString1.replaceAll("'", "`") + "' , '" + paramString2.replaceAll("'", "`") + "' );");
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("CREATE TABLE FilterConfig(_id INTEGER PRIMARY KEY AUTOINCREMENT,  key TEXT, data TEXT  );");
        putKeyData(paramSQLiteDatabase, "FilterYN", "N");
        putKeyData(paramSQLiteDatabase, "GradientYN", "N");
        putKeyData(paramSQLiteDatabase, "BgColor", "#000000");
        putKeyData(paramSQLiteDatabase, "Alpha", "50");
        putKeyData(paramSQLiteDatabase, "Height", "50");
        putKeyData(paramSQLiteDatabase, "Area", "0");
        putKeyData(paramSQLiteDatabase, "GradientTypes", "1");
        putKeyData(paramSQLiteDatabase, "passedonce", "N");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS FilterConfig;");
        onCreate(paramSQLiteDatabase);
    }

    public void putKeyData(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2) {
        Cursor localCursor = paramSQLiteDatabase.rawQuery("SELECT data FROM FilterConfig WHERE key='" + paramString1 + "'", null);
        if (localCursor.getCount() == 0)
            paramSQLiteDatabase.execSQL("insert into FilterConfig (key ,data) values  ( '" + paramString1.replaceAll("'", "`") + "' , '" + paramString2.replaceAll("'", "`") + "' );");
        else {
            paramSQLiteDatabase.execSQL("update FilterConfig set data = '" + paramString2.replaceAll("'", "`") + "'  where key ='" + paramString1.replaceAll("'", "`") + "'  ");
        }
        localCursor.close();
    }
}