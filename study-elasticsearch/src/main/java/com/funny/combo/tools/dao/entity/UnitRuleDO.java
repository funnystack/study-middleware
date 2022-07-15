package com.funny.combo.tools.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 机器人规则表
 * </p>
 *
 * @author fangli
 * @since 2022-05-27 03:33:10
 */
@Getter
@Setter
@TableName("unit_rule")
public class UnitRuleDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 是否删除
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     */
    @TableField("rule_type")
    private String ruleType;

    /**
     */
    @TableField("like_mode")
    private String likeMode;

    /**
     * 例句
     */
    @TableField("sentence")
    private String sentence;

    /**
     */
    @TableField("action")
    private String action;

    @TableField("similar_rate")
    private Double similarRate;

    @TableField("pattern")
    private String pattern;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
