package com.funny.combo.tools.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @Classname ThreadUtil
 * @Description TODO
 * @Date 2022/5/6 21:17
 * @Created by jinhaifeng
 */
@Slf4j
public final class ThreadUtil {

    public static void threadSleep(long millis) {
        if (millis <= 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("thread InterruptedException", e);
        }
    }

    /**
     * 计算时间间隔
     * @param millis
     * @return
     */
    public static long calcTimeInterval(long millis) {
        return System.currentTimeMillis() - millis;
    }
}
