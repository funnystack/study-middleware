package com.funny.combo.tools.dao.mapper;

import com.funny.combo.tools.dao.entity.SoftPhoneEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fangli
 * @since 2022-03-29 10:25:23
 */
@Mapper
public interface SoftPhoneMapper extends BaseMapper<SoftPhoneEntity> {

    /**
     *  插入注册座机信息
     * @param softPhoneEntity
     * @return
     */
    int insertSelective(SoftPhoneEntity softPhoneEntity);

    /**
     *  更新注册座机信息
     * @param softPhoneEntity
     * @return
     */
    int updateByPrimaryKeySelective(SoftPhoneEntity softPhoneEntity);

    /**
     * 根据注册账户id，删除注册账户信息
     * @param extensionNo
     */
    void deleteByExtensionNo(@Param("extensionNo") String extensionNo);


    /**
     * 查询分机账号的注册信息
     * @param softPhoneEntity
     * @return
     */
    SoftPhoneEntity  selectOneByEntity(SoftPhoneEntity softPhoneEntity);
}
