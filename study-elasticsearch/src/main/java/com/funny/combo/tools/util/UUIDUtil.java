package com.funny.combo.tools.util;

import java.util.UUID;

/**
 * @author Created by Joe on 2020-01-10.
 */
public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
