package com.funny.combo.tools.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author fangli
 * @since 2022-03-29 10:25:23
 */
@Getter
@Setter
@TableName("soft_phone")
public class SoftPhoneEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * freeswitch的ip+端口
     */
    @TableField("freeswitch_host")
    private String freeswitchHost;

    @TableField("extension_no")
    private String extensionNo;

    @TableField("extension_user_name")
    private String extensionUserName;

    @TableField("extension_pwd")
    private String extensionPwd;

    /**
     * 被叫号码
     */
    @TableField("call_to")
    private String callTo;

    /**
     * 注册状态 0 未注册 1 已注册
     */
    @TableField("register_status")
    private Integer registerStatus;

    /**
     * 工作状态 0 未外呼 1 外呼中
     */
    @TableField("work_status")
    private Integer workStatus;
    /**
     * 创建时间
     */
    @TableField("created_stime")
    private Date createdStime;

    /**
     * 修改时间
     */
    @TableField("modified_stime")
    private Date modifiedStime;

    /**
     * 是否删除 0 正常 1 删除
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;
    /**
     * 分布式Key
     */
    @TableField("distribution_key")
    private String distributionKey;

}
