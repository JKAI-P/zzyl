package com.zzyl.framework.interceptor;

import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.UserThreadLocal;
import com.zzyl.framework.web.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class MemberInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();//得到请求路径
        log.info("请求了路径：{}", url);

        // 如果不是Controller层的请求，直接放行
        // handler 就是controller中的方法Method对象
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //3. 获取请求头中的令牌（token）
        String token = request.getHeader("authorization");
        //4. 判断令牌是否存在，
        if(!StringUtils.hasLength(token)){
            log.info("请求头中无token");
            //如果不存在，响应 401
            //response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            throw new BaseException("认证失败");
        }
        //5. 解析token，
        try {
            Claims claims = tokenService.parseToken(token);
            //获取用户id
            Long userId = claims.get("userId", Long.class);
            //保存线程变量里
            UserThreadLocal.set(userId);
            log.info("解析到token中的信息：{}", claims);
            //6. 放行
            log.info("令牌校验通过，放行");
            return true;
        } catch (Exception e) {
            log.error("解析token失败", e);
            //如果解析失败，响应 401
            //response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            throw new BaseException("认证失败");
        }
    }
}
