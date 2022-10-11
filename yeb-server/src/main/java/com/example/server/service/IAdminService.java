package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.pojo.Admin;
import com.example.server.vo.RespBean;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(Admin admin, HttpSession session);

    List<Admin> getAdminsByKeywords(String keywords);
}
