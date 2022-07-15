package com.funny.combo.tools.common;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

/**
 * 枚举抽象接口
 * @param <T>
 * @author jinhaifeng
 */
public interface BaseEnum<E extends Enum<E> & BaseEnum<E, T>, T> {

    /**
     * 枚举取值
     * @return
     */
    T getValue();

    /**
     * 描述字段信息
     * @return
     */
    String getDesc();

    /**
     * 取值是否相同
     * @param t
     * @return
     */
    default boolean valueEquals(T t) {
        return Objects.equals(getValue(), t);
    }

    /**
     * 取值是否相同
     * @param t
     * @param defaultIfNullValue
     * @return
     */
    default boolean valueEquals(T t, T defaultIfNullValue) {
        return this.valueEquals(ObjectUtils.defaultIfNull(t, defaultIfNullValue));
    }

    /**
     * 获取默认值
     * @return
     */
    default E getDefault() {
        return null;
    }

    /**
     * 根据Value字段查找对应的枚举
     * @param type
     * @param value
     * @param <T>
     * @param <E>
     * @return
     */
    static <T, E extends Enum<E> & BaseEnum<E, T>> E getByValue(Class<E> type, T value) {

        if (value == null) {
            return null;
        }

        EnumSet<E> enums = EnumSet.allOf(type);
        for (E e : enums) {
            if (e.valueEquals(value)) {
                return e;
            }
        }
        // 没有找到匹配值，则找默认值
        if (!enums.isEmpty()) {
            for (E e : enums) {
                return e.getDefault();
            }
        }
        // 空Enum,则返回null
        return null;
    }

    /**
     * 根据Value字段查找对应的枚举
     * @param type
     * @param value
     * @param defaultVal
     * @param <T>
     * @param <E>
     * @return
     */
    static <T, E extends Enum<E> & BaseEnum<E, T>> E getByValue(Class<E> type, T value, E defaultVal) {
        E e = getByValue(type, value);
        return Optional.ofNullable(e).orElse(defaultVal);
    }

    /**
     * 根据描述信息查找对应的枚举
     * @param type
     * @param desc
     * @param <E>
     * @return
     */
    static <E extends Enum<E> & BaseEnum> E getByDesc(Class<E> type, String desc) {

        if (StringUtils.isEmpty(desc)) {
            return null;
        }

        EnumSet<E> enums = EnumSet.allOf(type);
        for (E e : enums) {
            if (e.getDesc().equals(desc)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据Value获取Desc
     * @param type
     * @param value
     * @param <T>
     * @param <E>
     * @return
     */
    static <T, E extends Enum<E> & BaseEnum<E,T>> String getDescByValue(Class<E> type, T value) {
        E e = getByValue(type,value);
        if (e != null) {
            return e.getDesc();
        } else {
            return null;
        }
    }

    /**
     * 实现了BaseEnum接口的一些通用的工具类
     */
    final class Utils {

        /**
         * 根据Value字段查找对应的枚举
         * @param type
         * @param value
         * @param <T>
         * @param <E>
         * @return
         */
        public static <T, E extends Enum<E> & BaseEnum<E,T>> E getByValue(Class<E> type, T value) {
            return BaseEnum.getByValue(type,value);
        }

        /**
         * 根据描述信息查找对应的枚举
         * @param type
         * @param desc
         * @param <E>
         * @return
         */
        public static <E extends Enum<E> & BaseEnum> E getByDesc(Class<E> type, String desc) {
            return BaseEnum.getByDesc(type,desc);
        }

        /**
         * 根据枚举的Value获取Desc
         * @param type
         * @param value
         * @param <T>
         * @param <E>
         * @return
         */
        public static <T, E extends Enum<E> & BaseEnum<E,T>> String getDescByValue(Class<E> type, T value){
            return BaseEnum.getDescByValue(type,value);
        }
    }


}
