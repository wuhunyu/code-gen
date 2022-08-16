package com.wuhunyu.code_gen.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * jwt处理工具类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 14:33
 */

public final class JwtUtil {

    /**
     * 加密盐
     */
    private static final String SECRET = "qwertyuiopasdfghjklzxcvbnm1234567890";

    /**
     * userId
     */
    public static final String USER_ID_STR = "userId";

    private JwtUtil() {
    }

    /**
     * 构建一个token
     *
     * @param userId 用户id
     * @return token
     */
    public static String createToken(Long userId) {
        if (userId == null) {
            throw new NullPointerException("userId不能为空");
        }
        return JWT.create()
                .withClaim(USER_ID_STR, userId)
                .withIssuer(String.valueOf(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解析token，获取userId
     *
     * @param token token
     * @return 用户id
     */
    public static Long parseToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new NullPointerException("token不能为空");
        }
        Map<String, Claim> claims = JWT.decode(token)
                .getClaims();
        Claim claim = claims.get(USER_ID_STR);
        if (claim == null) {
            throw new IllegalArgumentException("解析token异常");
        }
        return claim.asLong();
    }

}
