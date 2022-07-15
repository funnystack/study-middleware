package com.funny.combo.tools.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 机器人话术表
 * </p>
 *
 * @author fangli
 * @since 2022-04-21 03:56:32
 */
@Getter
@Setter
@TableName("unit_robot_words")
public class UnitRobotWordsEntity {

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
    @TableLogic
    private Integer isDel;

    /**
     * 业务线 租户id
     */
    @TableField("unit_robot")
    private String unitRobot;
    /**
     * 业务线 租户id
     */
    @TableField("biz_tenant_id")
    private String bizTenantId;
    /**
     * 话术
     */
    @TableField("words")
    private String words;

    /**
     * 话术类型 faq、flow
     */
    @TableField("words_type")
    private String wordsType;

    /**
     * 话术哈希
     */
    @TableField("words_hash")
    private String wordsHash;

    /**
     * 是否动态话术
     */
    @TableField("dynamic")
    private Boolean dynamic;

    /**
     * 人工录音
     */
    @TableField("human_audio_url")
    private String humanAudioUrl;

    /**
     * tts语音
     */
    @TableField("tts_audio_url")
    private String ttsAudioUrl;

    /**
     * 话术编号
     */
    @TableField("voice_num")
    private String voiceNum;


}
