package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 * <p>
 * mapper中
 * find
 * insert
 * update
 * delete
 *
 * @author 小红
 * @since 2022-09-01
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findAllWithRoles();

    List<Menu> findMenuWithChildrenByAdminId(@Param("adminId") Integer adminId);

    List<Menu> findMenusLevel(@Param("parentId") Integer parentId);

    List<Integer> findMenuIdsByRid(@Param("rid") Integer rid);
}
