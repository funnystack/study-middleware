package com.funny.combo.tools.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 机器人话术表
 * </p>
 *
 * @author fangli
 * @since 2022-04-21 03:56:31
 */
@Getter
@Setter
@TableName("unit_robot")
public class UnitRobotEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("created_stime")
    private LocalDateTime createdStime;

    /**
     * 修改时间
     */
    @TableField("modified_stime")
    private LocalDateTime modifiedStime;

    /**
     * 是否删除
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 业务线 租户id
     */
    @TableField("biz_tenant_id")
    private String bizTenantId;

    /**
     * 话术名称
     */
    @TableField("robot_name")
    private String robotName;

    /**
     * 百度unit的token
     */
    @TableField("unit_robot")
    private String unitRobot;

    /**
     * 百度unit的json文件
     */
    @TableField("unit_file")
    private String unitFile;


}
