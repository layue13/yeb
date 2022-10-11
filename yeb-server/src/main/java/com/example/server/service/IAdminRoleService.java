package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.pojo.AdminRole;
import com.example.server.vo.RespBean;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface IAdminRoleService extends IService<AdminRole> {

    RespBean editAdminRoles(Integer adminId, Integer[] rids);
}
