package com.jike.leakradar.radar.lifecyclelistener;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jike.corekit.log.Logger;
import com.jike.leakradar.radar.RefWatcher;

public class FragmentListener {

    private final RefWatcher refWatcher;

    private final Context context;

    public FragmentListener(Context context, RefWatcher refWatcher) {
        this.refWatcher = refWatcher;
        this.context = context;
    }

    private final FragmentManager.FragmentLifecycleCallbacks lifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            Logger.E(f.getClass().getName(), "onFragmentViewDestroyed");

            View view = f.getView();
            if (view != null) {
                refWatcher.watch(view, "");
            }

        }

        @Override
        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentDestroyed(fm, f);
            Logger.E(f.getClass().getName(), "onFragmentDestroyed");
            refWatcher.watch(f, "");
        }

    };


    public static void install(Context context, RefWatcher refWatcher) {
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter(){
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                super.onActivityCreated(activity, bundle);
                new FragmentListener(application, refWatcher).setListener((AppCompatActivity) activity);
            }
        });
    }


    public void setListener(AppCompatActivity activity) {
        stopWatchingFragments(activity);
        activity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(lifecycleCallbacks, true);
    }

    public void stopWatchingFragments(AppCompatActivity activity){
        activity.getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(lifecycleCallbacks);
    }

}
