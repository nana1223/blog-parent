package zn.blog.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 对于需要登陆后才能访问的接口 需要被这个拦截器处理
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    //在执行controller方法之前执行（springmvc把controller叫做handler）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1.判断 请求的接口路径 是否为Handler方法（Controller方法）
         * 2.判断 token是否为空，若为空则是未登录需要登录；若不为空 则登陆验证 loginService checkToken，若认证成功，放行即可
         */
        return false;
    }
}
