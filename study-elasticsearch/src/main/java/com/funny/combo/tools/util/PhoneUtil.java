package com.funny.combo.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @Classname PhoneUtil
 * @Description 电话号码工具类
 * @Date 2022/4/25 14:06
 * @Created by jinhaifeng
 */
public class PhoneUtil {

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[0123456789]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }

    /**
     * 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。</p>
     * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 检查是否为外部号码,暂时先实现为超过6位的为外部号码
     * @param phoneNumber
     * @return
     */
    public static boolean checkOutBoundNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return false;
        }
        return phoneNumber.length() > 6;
    }

    /**
     * 是否为测试外显号码
     * @param phoneNumber
     * @return
     */
    public static boolean isTestOutBoundNumber(String phoneNumber) {
        return checkOutBoundNumber(phoneNumber) && phoneNumber.charAt(0) == '9';
    }

    /**
     *  真是外呼
     * @param phoneNumber
     * @return
     */
    public static boolean isProductOutBoundNumber(String phoneNumber) {
        return checkOutBoundNumber(phoneNumber) && phoneNumber.charAt(0) == '0';
    }
}
