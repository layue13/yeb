package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.pojo.Admin;
import com.example.common.pojo.Menu;
import com.example.common.pojo.MenuRole;
import com.example.server.exception.YebException;
import com.example.server.mapper.MenuMapper;
import com.example.server.mapper.MenuRoleMapper;
import com.example.server.security.context.SecurityContextHolder;
import com.example.server.service.IMenuService;
import com.example.server.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Override
    public List<Menu> getMenuWithChildrenByAdminId() {

        /**
         * 缓存的机制
         * 1 先到redis中查看之前缓存的信息
         * 2 如果可以查询到，直接返回就可以
         * 2.2 如果没有查询到
         * 2.3 使用mapper到数据库查询该数据
         * 2.4 需要将本次查询的结果写到redis中
         * 2.5 返回本次结果
         */
        Admin userDetails = (Admin) securityContextHolder.getUserDetails();

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        List<Menu> menusFromRedis = (List<Menu>) operations.get("menu_" + userDetails.getId());

        if (CollectionUtils.isEmpty(menusFromRedis)) {

            /**
             * 1 生产环境在上线的时候，会做一个 缓存预热，把一些热门的数据先放到缓存中 （redis将数据保存到内存中的，因此这些数据在读取的时候，效率远远高于MySQL）
             * 2 进行缓存预热的时候，数据都设置了有一个终止时间，这些时间都设置一样了
             * 3 当这批数据的有效时间过了，那么这些数据都会被从redis中删除，用户再去请求的时候，就会访问到数据库
             * 4 就会造成单位时间内，有打量的请求击穿到数据库上
             * 5 对于用户来说，突然一下子特别特别卡
             * 6 每隔一段时间，网站就会卡个一小段时间
             *
             */
            log.info("从数据库中查看信息");
            List<Menu> menuFromDb = menuMapper.findMenuWithChildrenByAdminId(userDetails.getId());

            Random random = new Random();
            operations.set("menu_" + userDetails.getId(), menuFromDb,
                    30 + random.nextInt(30), TimeUnit.MINUTES);

            return menuFromDb;
        } else {

            log.info("从缓存中查看信息");
            return menusFromRedis;
        }
    }

    @Override
    public List<Menu> getMenusLevel() {

        return menuMapper.findMenusLevel(null);
    }

    @Override
    public List<Integer> getMenuIdsByRid(Integer rid) {
        return menuMapper.findMenuIdsByRid(rid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {

        /**
         * 1 在menuRole 根据rid 删掉这个角色对应的所有的关系
         * 2 再根据mids，去新增该角色的所有关系
         */

        int delete = menuRoleMapper
                .delete(new QueryWrapper<MenuRole>().eq("rid", rid));

        int result = menuRoleMapper.insertBatch(rid, mids);

        if (result != mids.length) {
            throw new YebException(500, "新增角色权限失败！");
        }
        return RespBean.success();
    }
}
