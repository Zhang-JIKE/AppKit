package com.jike.leakradar.radar;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class KeyedWeakReference extends WeakReference<Object> {

    public final String key;

    public final String name;

    public KeyedWeakReference(Object referent, String key, String name, ReferenceQueue queue) {
        super(referent, queue);
        this.key = key;
        this.name = name;
    }
}
