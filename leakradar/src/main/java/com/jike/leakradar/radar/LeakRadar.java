package com.jike.leakradar.radar;

import android.app.Application;

import com.jike.leakradar.radar.lifecyclelistener.ActivityListener;
import com.jike.leakradar.radar.lifecyclelistener.FragmentListener;

public class LeakRadar {

    public static RefWatcher refWatcher;

    public static RefWatcher install(Application application){

        RefWatcher watcher = new RefWatcher(application.getApplicationContext());

        ActivityListener.install(application.getApplicationContext(), watcher);

        FragmentListener.install(application.getApplicationContext(), watcher);

        refWatcher = watcher;

        return watcher;
    }
}
