package com.wuhunyu.code_gen.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.wuhunyu.code_gen.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 认证配置
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 14:23
 */

@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {

    /**
     * 认证信息
     */
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 认证信息
        String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            return false;
        }
        Long userId = null;
        try {
            // 解析出 userId
            userId = JwtUtil.parseToken(authorization);
        } catch (JWTDecodeException e) {
            log.error("解析token异常: {}", e.getLocalizedMessage(), e);
            return false;
        }
        // 存放userId于session中
        HttpSession session = request.getSession();
        session.setAttribute(JwtUtil.USER_ID_STR, userId);
        return true;
    }

}
