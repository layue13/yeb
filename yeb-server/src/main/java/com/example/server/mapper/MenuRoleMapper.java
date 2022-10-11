package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    int insertBatch(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
