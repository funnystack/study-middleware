package com.funny.combo.tools.dto.enums;


import org.apache.commons.lang3.StringUtils;

/**
 */
public enum AsrChannelEnum {

    AUTO(1, "汽车之家"),
    ALI(2, "阿里云"),
    XF(3, "讯飞"),
    ;


    private int value;

    private String desc;

    AsrChannelEnum(int v, String desc) {
        this.value = v;
        this.desc = desc;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDesc(Integer status) {
        if (status == null) {
            return StringUtils.EMPTY;
        }
        String remark = "";
        for (AsrChannelEnum actionTypeEnum : AsrChannelEnum.values()) {
            if (actionTypeEnum.getValue() == status) {
                remark = actionTypeEnum.getDesc();
                break;
            }
        }
        return remark;
    }
}
