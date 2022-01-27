package com.jike.leakradar.radar;

import static com.jike.leakradar.radar.Retryable.Result.DONE;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Environment;

import com.jike.corekit.log.Logger;
import com.jike.leakradar.LeakService;

import java.io.File;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RefWatcher {

    private final ReferenceQueue<Object> weakRefQueue;

    private Set<String> retainedKeys;

    private WatchExecutor watchExecutor;

    private Context context;

    private Set<Class> excludeRefClass = new HashSet<>();

    public RefWatcher addExcludeRef(Class cls){
        excludeRefClass.add(cls);
        return this;
    }


    public RefWatcher(Context context){
        weakRefQueue = new ReferenceQueue<>();
        retainedKeys = new HashSet<>();
        watchExecutor = new WatchExecutor(1000);
        this.context = context;
    }

    public void watch(Object refrenceObj, String name){
        if(excludeRefClass.contains(refrenceObj.getClass())){
            return ;
        }
        final long startedNanoTime = System.nanoTime();
        String key = UUID.randomUUID().toString();
        retainedKeys.add(key);
        KeyedWeakReference keyedWeakReference = new KeyedWeakReference(refrenceObj, key, refrenceObj.toString(), weakRefQueue);
        ensureGoneAsync(startedNanoTime, keyedWeakReference);
    }

    private void ensureGoneAsync(final long watchStartNanoTime, final KeyedWeakReference reference) {
        watchExecutor.execute(new Retryable() {
            @Override public Result run() {
                return ensureGone(reference, watchStartNanoTime);
            }
        });
    }

    private Retryable.Result ensureGone(final KeyedWeakReference reference, final long watchStartNanoTime) {

        Logger.E("LeakRadar", "Start detecting " + reference.name);

        removeWeaklyReachableReferences();

        if (gone(reference)) {
            Logger.E("LeakRadar", "Finish detecting " + reference.name + " Was Not Leaked");
            return DONE;
        }

        GcTrigger.DEFAULT.runGc(); //对象未回收，主动调用一次GC

        long gcStartNanoTime = System.nanoTime();
        long watchDurationMs = NANOSECONDS.toMillis(gcStartNanoTime - watchStartNanoTime);

        if(watchDurationMs < 1000){
//            return RETRY;
        }

        removeWeaklyReachableReferences();
        // 5
        if (!gone(reference)) {
            try {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dump.hprof";

                File dumpFile = new File(filePath);

                Debug.dumpHprofData(filePath);



            } catch (IOException e) {
                e.printStackTrace();
            }


            long startDumpHeap = System.nanoTime();
            long gcDurationMs = NANOSECONDS.toMillis(startDumpHeap - gcStartNanoTime);
            Logger.E("LeakRadar", "Finish detecting " + reference.name + " Was Leaked");

            Intent intent = new Intent(context, LeakService.class);
            intent.putExtra("Info","There Was A LEAK Has Been Detected!!!");
            intent.putExtra("Detail","Leaked Refrence: "+reference.name);
            context.startService(intent);
        }else {
            Logger.E("LeakRadar", "Finish detecting " + reference.name + " Was Not Leaked");

        }

        Intent intent = new Intent(context, LeakService.class);
        context.stopService(intent);
        return DONE;
    }

    private boolean gone(KeyedWeakReference reference) {
        return !retainedKeys.contains(reference.key);
    }

    private void removeWeaklyReachableReferences() {
        // WeakReferences are enqueued as soon as the object to which they point to becomes weakly
        // reachable. This is before finalization or garbage collection has actually happened.
        KeyedWeakReference ref;
        while ((ref = (KeyedWeakReference) weakRefQueue.poll()) != null) {
            retainedKeys.remove(ref.key);
        }
    }

}
