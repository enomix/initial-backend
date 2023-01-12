package com.sp.project.common;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

import static com.sp.project.constant.CommonConstant.SECRET;

/**
 * jwt工具类, 生成和校验jwt
 */
public class TokenUtils {

    /**
     * 生成token, 设置token超时时间
     * @param userId
     * @return
     */
    public static String genToken(String userId) {
        return JWT.create().withAudience(userId) //将 User ID保存到token里面, 作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //超时设置, 两个小时后token过期
                .sign(Algorithm.HMAC256(SECRET));//以一个常量作为token的密钥
    }

}
