package com.elderly.common.config;

import com.elderly.common.vo.JSONReturn;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF（JWT 不需要）
            .csrf(AbstractHttpConfigurer::disable)
            
            // 无状态会话管理
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 配置权限规则
            .authorizeHttpRequests(auth -> auth
                // 公开接口 - 登录相关
                .requestMatchers("/login", "/register", "/captcha/**").permitAll()
                // 公开接口 - 首页信息
                .requestMatchers("/news", "/news/**").permitAll()
                .requestMatchers("/notice", "/notice/**").permitAll()
                .requestMatchers("/resthome", "/resthome/**").permitAll()
                .requestMatchers("/count").permitAll()
                // 公开接口 - 文件上传下载
                .requestMatchers("/file/**").permitAll()
                // 公开接口 - 测试接口
                .requestMatchers("/redis/**").permitAll()
                // 前端页面
                .requestMatchers("/", "/index.html", "/static/**", "/assets/**").permitAll()
                // 管理员接口
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // 护工接口（管理员也可访问用于管理）
                .requestMatchers("/employee/**").hasAnyRole("ADMIN", "EMPLOYEE")
                // 用户接口
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "EMPLOYEE")
                // 床位管理
                .requestMatchers("/bed/**").hasAnyRole("ADMIN", "EMPLOYEE")
                // 房间管理
                .requestMatchers("/room/**").hasAnyRole("ADMIN", "EMPLOYEE")
                // 入住管理
                .requestMatchers("/live/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 账单管理
                .requestMatchers("/bill/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 访客管理
                .requestMatchers("/visit/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 外出管理
                .requestMatchers("/leave/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 评论管理
                .requestMatchers("/comment/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 点赞管理
                .requestMatchers("/like/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 反馈管理
                .requestMatchers("/feedback/**").hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                // 其他接口需要认证
                .anyRequest().authenticated()
            )
            
            // 添加 JWT 过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            
            // 配置异常处理
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    // 返回未授权 JSON 响应
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter writer = response.getWriter();
                    writer.write(objectMapper.writeValueAsString(JSONReturn.failed("请先登录")));
                    writer.flush();
                    writer.close();
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 返回禁止访问 JSON 响应
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter writer = response.getWriter();
                    writer.write(objectMapper.writeValueAsString(JSONReturn.failed("权限不足")));
                    writer.flush();
                    writer.close();
                })
            );
        
        return http.build();
    }
}
