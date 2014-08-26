package com.unas.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aaahh on 8/26/14.
 */

public class MyDBHelper extends SQLiteOpenHelper
{

    public static String dbNm = "eonFilter.db";
    public static int dbVer = 1;

    public MyDBHelper(Context context, String s, android.database.sqlite.SQLiteDatabase.CursorFactory cursorfactory, int i)
    {
        super(context, s, cursorfactory, i);
    }

    public String getKeyData(SQLiteDatabase sqlitedatabase, String s)
    {
        Cursor cursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT data FROM FilterConfig WHERE key='")).append(s).append("'").toString(), null);
        if (cursor.getCount() == 0)
        {
            cursor.close();
            return null;
        } else
        {
            cursor.moveToNext();
            String s1 = cursor.getString(0);
            cursor.close();
            return s1;
        }
    }

    public void insertData(SQLiteDatabase sqlitedatabase, String s, String s1)
    {
        sqlitedatabase.execSQL((new StringBuilder("insert into FilterConfig (key ,data) values  ( '")).append(s.replaceAll("'", "`")).append("' , '").append(s1.replaceAll("'", "`")).append("' );").toString());
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase.execSQL("CREATE TABLE FilterConfig(_id INTEGER PRIMARY KEY AUTOINCREMENT,  key TEXT, data TEXT  );");
        putKeyData(sqlitedatabase, "FilterYN", "N");
        putKeyData(sqlitedatabase, "BgColor", "#000000");
        putKeyData(sqlitedatabase, "Alpha", "50");
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS FilterConfig;");
        onCreate(sqlitedatabase);
    }

    public void putKeyData(SQLiteDatabase sqlitedatabase, String s, String s1)
    {
        Cursor cursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT data FROM FilterConfig WHERE key='")).append(s).append("'").toString(), null);
        if (cursor.getCount() == 0)
        {
            sqlitedatabase.execSQL((new StringBuilder("insert into FilterConfig (key ,data) values  ( '")).append(s.replaceAll("'", "`")).append("' , '").append(s1.replaceAll("'", "`")).append("' );").toString());
        } else
        {
            sqlitedatabase.execSQL((new StringBuilder("update FilterConfig set data = '")).append(s1.replaceAll("'", "`")).append("'  where key ='").append(s.replaceAll("'", "`")).append("'  ").toString());
        }
        cursor.close();
    }
}