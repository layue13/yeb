package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.pojo.AdminRole;
import com.example.server.exception.YebException;
import com.example.server.mapper.AdminRoleMapper;
import com.example.server.service.IAdminRoleService;
import com.example.server.vo.RespBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBean editAdminRoles(Integer adminId, Integer[] rids) {

        /**
         * 删除所有的关系
         * 批量新增新的关系
         *
         * 如果调用一个被@Transactional修饰的方法
         * 该方法中调用了其他被@Transactional修饰的方法
         * --> 事务的传播
         */

        boolean remove = remove(new QueryWrapper<AdminRole>().eq("adminId", adminId));

        List<AdminRole> collect = Arrays.asList(rids).stream()
                .map(x -> new AdminRole().setAdminId(adminId).setRid(x))
                .collect(Collectors.toList());

        boolean saveBatch = saveBatch(collect);

        if (remove && saveBatch) {
            return RespBean.success();
        } else {
            throw new YebException(500, "修改用户角色失败");
        }
    }
}
