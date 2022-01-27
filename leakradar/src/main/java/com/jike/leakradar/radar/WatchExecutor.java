package com.jike.leakradar.radar;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;

import androidx.annotation.NonNull;

public final class WatchExecutor {

    private long initialDelayMillis;

    private Handler mainHandler;

    private Handler backgroundHandler;

    private float maxBackoffFactor;

    public WatchExecutor(long initialDelayMillis){
        this.initialDelayMillis = initialDelayMillis;
        mainHandler = new Handler(Looper.getMainLooper());

        HandlerThread handlerThread = new HandlerThread("LEAK_RADAR_THREAD");
        handlerThread.start();

        backgroundHandler = new Handler(handlerThread.getLooper());
        maxBackoffFactor = Long.MAX_VALUE / initialDelayMillis;
    }

    public void execute(@NonNull Retryable retryable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            waitForIdle(retryable, 0);
        } else {
            postWaitForIdle(retryable, 0);
        }
    }

    private void postWaitForIdle(final Retryable retryable, final int failedAttempts) {
        mainHandler.post(new Runnable() {
            @Override public void run() {
                waitForIdle(retryable, failedAttempts);
            }
        });
    }

    private void waitForIdle(final Retryable retryable, final int failedAttempts) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override public boolean queueIdle() {
                postToBackgroundWithDelay(retryable, failedAttempts);
                return false;
            }
        });
    }

    private void postToBackgroundWithDelay(final Retryable retryable, final int failedAttempts) {
        long exponentialBackoffFactor = (long) Math.min(Math.pow(2, failedAttempts), maxBackoffFactor);
        // 1
        long delayMillis = initialDelayMillis * exponentialBackoffFactor;
        // 2
        backgroundHandler.postDelayed(new Runnable() {
            @Override public void run() {
                // 3
                Retryable.Result result = retryable.run();
                // 4
                if (result == Retryable.Result.RETRY) {
                    postWaitForIdle(retryable, failedAttempts + 1);
                }
            }
        }, delayMillis);
    }
}
