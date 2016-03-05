package com.apptive.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Utilities {
	
    public static void setGlobalFont(View view, Context con) {
    	Typeface mTypeface;
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        //((TextView) v).setTypeface(Typeface.MONOSPACE);
                    	mTypeface = Typeface.createFromAsset(con.getAssets(), "Nototo.ttf.mp3");
                    	((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v,con);
                }
            }
        } else {
            Log.e("setGlobalFont", "This is null  ");
        }
 
    }
 
}