package com.example.server.conf;

import com.example.server.security.AuthenticatedInterceptor;
import com.example.server.security.AuthorizeInterceptor;
import com.example.server.security.TokenLoadingInterceptor;
import com.example.server.security.authorize.HasAnyRoleAuthorizeDecider;
import com.example.server.security.authorize.JdbcResourceMetaDataProvider;
import com.example.server.security.context.SecurityContextHolder;
import com.example.server.security.context.SessionBasedSecurityContextHolder;
import com.example.server.security.context.TokenBasedSecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration // 交给spring 管理； 定位： 配置类 xml
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Value("${yeb.contextType}")
    private String contextType;

    @Autowired
    private AuthenticatedInterceptor authenticatedInterceptor;

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Autowired
    private JdbcResourceMetaDataProvider jdbcResourceMetaDataProvider;

    @Autowired
    private HasAnyRoleAuthorizeDecider hasAnyRoleAuthorizeDecider;

    @Autowired
    private TokenLoadingInterceptor tokenLoadingInterceptor;


    // 相当于@Component
    @Bean
    public SecurityContextHolder securityContextHolder() {

        if (contextType.equals("session")) {
            return new SessionBasedSecurityContextHolder();
        } else if (contextType.equals("token")) {
            return new TokenBasedSecurityContextHolder();
        } else {
            return new SessionBasedSecurityContextHolder();
        }
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) { // 添加拦截器

        authorizeInterceptor.setResourceMetaDataProvider(jdbcResourceMetaDataProvider);
        authorizeInterceptor.setAuthorizeDecider(hasAnyRoleAuthorizeDecider);

        if ("token".equals(contextType)) {

            setInterceptorPath(registry, tokenLoadingInterceptor);
        }

        setInterceptorPath(registry, authenticatedInterceptor);

        setInterceptorPath(registry, authorizeInterceptor);
    }

    /**
     * 添加拦截器
     * 配置默认的拦截地址
     *
     * @param registry           拦截注册器
     * @param handlerInterceptor 拦截器
     */
    private void setInterceptorPath(InterceptorRegistry registry, HandlerInterceptor handlerInterceptor) {

        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/captcha")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/asserts/**")
                .excludePathPatterns("/swagger-resources");

    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) { // 添加静态资源

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}