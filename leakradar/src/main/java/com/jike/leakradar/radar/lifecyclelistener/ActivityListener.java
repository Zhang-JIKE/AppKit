package com.jike.leakradar.radar.lifecyclelistener;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.jike.corekit.log.Logger;
import com.jike.leakradar.radar.RefWatcher;

public class ActivityListener {

    private final Context context;

    private final RefWatcher refWatcher;

    public ActivityListener(Context context, RefWatcher refWatcher) {
        this.context = context;
        this.refWatcher = refWatcher;
    }

    public static void install(Context context, RefWatcher refWatcher) {
        new ActivityListener(context, refWatcher).setListener();
    }

    private final Application.ActivityLifecycleCallbacks lifecycleCallbacks =
            new ActivityLifecycleCallbacksAdapter() {
                @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    Logger.E(activity.getClass().getName(), "onActivityCreated");
                }

                @Override public void onActivityStarted(Activity activity) {
                    Logger.E(activity.getClass().getName(), "onActivityStarted");

                }

                @Override public void onActivityResumed(Activity activity) {
                    Logger.E(activity.getClass().getName(), "onActivityResumed");

                }

                @Override public void onActivityPaused(Activity activity) {
                    Logger.E(activity.getClass().getName(), "onActivityPaused");

                }

                @Override public void onActivityStopped(Activity activity) {
                    Logger.E(activity.getClass().getName(), "onActivityStopped");

                }

                @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    Logger.E(activity.getClass().getName(), "onActivitySaveInstanceState");

                }

                @Override public void onActivityDestroyed(Activity activity) {
                    Logger.E(activity.getClass().getName(), "onActivityDestroyed");
                    refWatcher.watch(activity, "");
                }

            };

    public void setListener() {
        // Make sure you don't get installed twice.
        stopWatchingActivities();

        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    public void stopWatchingActivities(){
        Application application = (Application) context.getApplicationContext();
        application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
    }
}
