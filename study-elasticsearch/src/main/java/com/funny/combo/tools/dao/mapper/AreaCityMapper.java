package com.funny.combo.tools.dao.mapper;

import java.util.List;

import com.funny.combo.tools.dao.entity.AreaCityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AreaCityMapper extends BaseMapper<AreaCityEntity> {

    default List<AreaCityEntity> selectAll() {
        return new LambdaQueryChainWrapper<>(this)
                .eq(AreaCityEntity::getIsDel, 0)
                .list();
    }
}
