package com.unas.myapplication;

import android.database.Cursor;
import android.graphics.Color;

import java.util.Scanner;

/**
 * Created by aaahh on 8/26/14.
 */
public class Common
{

    public static int Alpha = 50;
    public static int Height = 50;
    public static int Area = 0;
    public static boolean passedonce;
    public static String BgColor = "#FFFFFF";
    public static int ColorType = -1;
    public static String FgColor = "#000000";
    public static String FilterYN = "N";
    public static int ScrollY = 0;
    public static String pattern[] = {
            ".txt", ".ini", ".csv", ".js", ".css", ".xml", ".config"
    };

    public Common()
    {
    }

    public static String CursorToJson(Cursor cursor)
    {
        do
        {
            if (!cursor.moveToNext())
            {
                return "";
            }
            int i = 0;
            while (i < cursor.getCount())
            {
                cursor.getString(0);
                i++;
            }
        } while (true);
    }

    public static int cH(String s)
    {
        return Integer.parseInt(s, 16);
    }

    public static int converToDecimalFromHex(String s)
    {
        return Color.rgb(cH(s.substring(1, 3)), cH(s.substring(3, 5)), cH(s.substring(5, 7)));
    }

       public static int hx(String args) {
           // TODO Auto-generated method stub
           System.out.print("Hexadecimal Input:");
           // read the hexadecimal input from the console
           Scanner s = new Scanner(System.in);
           String inputHex = String.valueOf(s);
           try {
               // actual conversion of hex to decimal
               Integer outputDecimal = Integer.parseInt(inputHex, 16);
               return outputDecimal;
           } catch (NumberFormatException ne) {
               // Printing a warning message if the input is not a valid hex number
               return 0;
           } finally {
               s.close();
           }
       }

    public static String toStringYN(boolean flag)
    {
        if (flag)
        {
            return "Y";
        } else
        {
            return "N";
        }
    }
}