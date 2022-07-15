package com.funny.combo.tools.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("area_city")
public class AreaCityEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @TableField("city_id")
    private Long cityId;


    @TableField("city_name")
    private String cityName;


    @TableField("province_id")
    private Integer provinceId;


    @TableField("province_name")
    private String provinceName;


    @TableField("similar_names")
    private String similarNames;
}
