package com.wuhunyu.code_gen.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuhunyu.code_gen.common.constants.CommonConstant;
import com.wuhunyu.code_gen.common.response.Result;
import com.wuhunyu.code_gen.common.utils.JwtUtil;
import com.wuhunyu.code_gen.system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证配置
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 14:23
 */

@Component("authHandlerInterceptor")
@RequiredArgsConstructor
@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {

    /**
     * 认证信息
     */
    private static final String AUTHORIZATION = "Authorization";

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 认证信息
        String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            this.fillNoValidResponse(response);
            return false;
        }
        Long userId;
        try {
            // 解析出 userId
            userId = JwtUtil.parseToken(authorization);
            // 校验该用户是否存在
            if (!userService.existsUserByUserId(userId)) {
                this.fillNoValidResponse(response);
                return false;
            }
        } catch (JWTDecodeException e) {
            log.error("解析token异常: {}", e.getLocalizedMessage(), e);
            this.fillNoValidResponse(response);
            return false;
        }
        // 存放userId于session中
        HttpSession session = request.getSession();
        session.setAttribute(JwtUtil.USER_ID_STR, userId);
        return true;
    }

    /**
     * 封装无权限访问结果
     *
     * @param response 响应对象
     */
    @SuppressWarnings("deprecation")
    private void fillNoValidResponse(HttpServletResponse response) throws IOException {
        // 返回结果类型
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        // 返回无权限访问提示
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(
                Result.self(CommonConstant.NO_AUTH_CODE, CommonConstant.NO_AUTH_MSG, null));
        writer.println(res);
    }

}
