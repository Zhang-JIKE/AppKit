package com.jike.leakradar.radar;

public abstract class Retryable {

    public enum Result{
        RETRY,
        DONE,
    }

    abstract Result run();
}
