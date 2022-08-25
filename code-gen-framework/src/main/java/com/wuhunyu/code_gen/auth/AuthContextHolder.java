package com.wuhunyu.code_gen.auth;

import com.wuhunyu.code_gen.common.utils.JwtUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户身份信息
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 15:29
 */

public class AuthContextHolder {

    /**
     * 环境id
     */
    public static final String USE_ENVIRONMENT_ID = "useEnvironmentId";

    /**
     * 获取当前用户id
     *
     * @return 用户id
     */
    public static Long getUserId() {
        // 获取session对象中的 用户id
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute(JwtUtil.USER_ID_STR);
        if (userId == null) {
            return null;
        }
        return Long.parseLong(userId.toString());
    }

    /**
     * 获取当前用户的环境id
     *
     * @return 环境id
     */
    public static Long getUseEnvironmentId() {
        // 获取session对象中的 用户id
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute(AuthContextHolder.USE_ENVIRONMENT_ID);
        if (userId == null) {
            return null;
        }
        return Long.parseLong(userId.toString());
    }

}
