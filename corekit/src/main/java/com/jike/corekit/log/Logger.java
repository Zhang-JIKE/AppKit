package com.jike.corekit.log;

import android.util.Log;

public class Logger {

    public static void E(String tag, String msg){
        Log.e(tag, msg);
    }

    public static void D(String tag, String msg){
        Log.d(tag, msg);
    }
}
