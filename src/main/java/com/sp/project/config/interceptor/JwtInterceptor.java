package com.sp.project.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sp.project.common.ErrorCode;
import com.sp.project.exception.BusinessException;
import com.sp.project.model.User;
import com.sp.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sp.project.constant.CommonConstant.SECRET;

/**
 * jwt拦截器, 验证token再放行
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod)handler;
        //判断如果请求的类是swagger的控制器，直接通行。
        if(handlerMethod.getBean().getClass().getName().equals("springfox.documentation.oas.web.OpenApiControllerWebMvc")){
            return  true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "没有权限");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "token验证失败，请重新登录");
        }
        // 根据token中的userid查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "用户不存在，请重新登录");
        }
        // 加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "token验证失败，请重新登录");
        }
        return true;
    }
}
