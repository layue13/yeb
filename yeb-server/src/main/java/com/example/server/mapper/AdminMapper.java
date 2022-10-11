package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> findAdminsByKeywords(@Param("keywords") String keywords,
                                     @Param("id") Integer id);
}
