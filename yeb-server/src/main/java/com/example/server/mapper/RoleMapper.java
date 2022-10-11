package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findAllByAdminId(@Param("adminId") Integer adminId);
}
