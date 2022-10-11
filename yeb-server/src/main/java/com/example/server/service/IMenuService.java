package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.pojo.Menu;
import com.example.server.vo.RespBean;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface IMenuService extends IService<Menu> {


    List<Menu> getMenuWithChildrenByAdminId();

    List<Menu> getMenusLevel();

    List<Integer> getMenuIdsByRid(Integer rid);

    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
