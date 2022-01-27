package com.jike.corekit.context;

import android.app.Activity;
import android.content.Context;

public class AppContextKit {

    private static Context context;

    private static Activity activity;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppContextKit.context = context;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        AppContextKit.activity = activity;
    }

    public static void release(){
        AppContextKit.context = null;
        AppContextKit.activity = null;
    }
}
