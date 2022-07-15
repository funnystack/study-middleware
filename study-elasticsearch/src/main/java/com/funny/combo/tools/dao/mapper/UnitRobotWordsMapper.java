package com.funny.combo.tools.dao.mapper;

import com.funny.combo.tools.dao.entity.UnitRobotWordsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 机器人话术表 Mapper 接口
 * </p>
 *
 * @author fangli
 * @since 2022-04-21 03:56:32
 */
@Mapper
public interface UnitRobotWordsMapper extends BaseMapper<UnitRobotWordsEntity> {

    default List<UnitRobotWordsEntity> selectAll() {
        return new LambdaQueryChainWrapper<>(this)
                .eq(UnitRobotWordsEntity::getIsDel, 0)
                .isNotNull(UnitRobotWordsEntity::getBizTenantId)
                .list();
    }
}
