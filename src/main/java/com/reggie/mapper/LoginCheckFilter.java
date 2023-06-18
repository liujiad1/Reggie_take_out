package com.reggie.mapper;



import com.alibaba.fastjson.JSON;
import com.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")//过滤器名称 以及 拦截所有路径
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 前置过滤器
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取本次请求路径
        String requestURI = request.getRequestURI();

        //排除不需要拦截的路径 直接放行
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**", // 静态资源
                "/front/**"
        };

        //判断是否需要处理
        boolean check = check(urls,requestURI);

        //不需要处理 直接放行
        if(check){
            log.info("请求 {} 不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        // check不为 true,判断当前用户的登录状态
        if(request.getSession().getAttribute("employee") != null){
            //用户已经登录，放行
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        //用户没有登录 则直接返回登录页面
        log.info("用户没有登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 判断路径是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    private boolean check(String[] urls, String requestURI) {
        for(String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                //不需要处理
                return true;
            }
        }

        return false;
    }
}
