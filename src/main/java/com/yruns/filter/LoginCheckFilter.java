package com.yruns.filter;

import com.alibaba.fastjson.JSON;
import com.yruns.common.BaseContext;
import com.yruns.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

/**
 * LoginCheckFilter，拦截登录请求
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private String[] urls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String URI = request.getRequestURI();
        // 检查是否需要处理（放行静态资源）
        boolean check = check(URI);
        if (check) {
            // 放行
            filterChain.doFilter(request, response);
        } else {
            Object userInfo = request.getSession().getAttribute("userInfo");
            if (userInfo != null) {
                // 记录当前线程id
                Long employeeId = (Long) userInfo;
                BaseContext.setCurrentId(employeeId);

                // 已经登录，放行
                filterChain.doFilter(request, response);
                return;
            }
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        }
    }

    // 判断本次请求是否需要放行
    public boolean check(String URI) {
        for (String url: urls) {
            boolean match = PATH_MATCHER.match(url, URI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
