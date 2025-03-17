package com.timeless.mianshi.blackfilter;

import com.timeless.mianshi.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 黑名单过滤器
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "BlackIpFilter")
public class BlackIpFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String ipAddr = NetUtils.getIpAddress((HttpServletRequest) servletRequest);
        log.info("请求 IP 地址：{}", ipAddr);
        if (BlackIpUtils.isBlackIp(ipAddr)) {
            servletResponse.setContentType("text/json;charset=UTF-8");
            servletResponse.getWriter().write("\"error\":\"403\",\"message\":\"您的 IP 地址在黑名单中\"");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
