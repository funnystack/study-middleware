package com.funny.combo.tools.util;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 分段锁
 * @author fangli
 * @version 1.0
 * @date 2022-04-10
 */
public class SegmentLock<T> {

    private final HashMap<Integer, ReentrantLock> lockMap = new HashMap<>();
    /**
     * 默认分段数量
     */
    private int segments = 16;

    private SegmentLock() {
        init(null, false);
    }

    private SegmentLock(Integer counts, boolean fair) {
        init(counts, fair);
    }

    public static <T> SegmentLock<T> createLock() {
        return new SegmentLock<>();
    }

    public static <T> SegmentLock<T> createLock(Integer counts, boolean fair) {
        return new SegmentLock<>(counts, fair);
    }

    private void init(Integer counts, boolean fair) {
        if (counts != null) {
            segments = counts;
        }
        for (int i = 0; i < segments; i++) {
            lockMap.put(i, new ReentrantLock(fair));
        }
    }

    private ReentrantLock getLock(T key) {
        return lockMap.get((key.hashCode() >>> 1) % segments);
    }

    public void lock(T key) {
        this.getLock(key).lock();
    }

    public void unlock(T key) {
        this.getLock(key).unlock();
    }

    public <R> R tryWithLock(T key,
                             Supplier<R> supplier) {
        this.lock(key);
        try {
            return supplier.get();
        } finally {
            this.unlock(key);
        }
    }

}
