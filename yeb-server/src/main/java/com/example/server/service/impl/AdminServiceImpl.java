package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.pojo.Admin;
import com.example.common.pojo.Role;
import com.example.server.exception.YebException;
import com.example.server.mapper.AdminMapper;
import com.example.server.mapper.RoleMapper;
import com.example.server.security.context.SecurityContextHolder;
import com.example.server.security.context.TokenBasedSecurityContextHolder;
import com.example.server.service.IAdminService;
import com.example.server.vo.RespBean;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.kerberos.EncryptionKey;
import javax.servlet.http.HttpSession;
import java.security.KeyFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Override
    public RespBean login(Admin admin, HttpSession session) {

        String captcha = (String) session.getAttribute("captcha");

        if (StringUtils.isEmpty(admin.getCode()) || !admin.getCode().equalsIgnoreCase(captcha)) {
            return RespBean.error(403, "验证码输入错误！");
        }

        // 根据用户名查数据
        Admin adminFromDb = adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", admin.getUsername()));

        if (adminFromDb != null) {
            // 比较密码
            if (BCrypt.checkpw(admin.getPassword(), adminFromDb.getPassword())) {

                // 同步当前用户的角色列表 adminId
                List<Role> roles = roleMapper.findAllByAdminId(adminFromDb.getId());
                adminFromDb.setRoles(roles);

                securityContextHolder.setUserDetails(adminFromDb);

                if (securityContextHolder instanceof TokenBasedSecurityContextHolder) {

                    byte[] encodedKey = Base64.decodeBase64("teasdasdasdasdasdasdddddddddddddddddddddddddddddadadst");
                    // 颁发一个jwt
                    String token = Jwts.builder()
                            .setSubject(adminFromDb.getUsername())
                            .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                            .signWith(new SecretKeySpec(encodedKey,0,encodedKey.length,"HmacSHA256")).compact();

                    Map<String, String> params = new HashMap<>();
                    params.put("tokenHead", "Bearer");
                    params.put("token", token);
                    return RespBean.success(params);
                }

                return RespBean.success();
            }
        }

        throw new YebException(402, "用户名或者密码不正确！");
    }

    @Override
    public List<Admin> getAdminsByKeywords(String keywords) {

        /**
         * 按照name模糊查询
         * 查询时，要带上roles列表
         * 查询的时候，不能查询到自己
         */
        Admin userDetails = (Admin) securityContextHolder.getUserDetails();
        Integer id = userDetails.getId();

        return adminMapper.findAdminsByKeywords(keywords, id);
    }
}
