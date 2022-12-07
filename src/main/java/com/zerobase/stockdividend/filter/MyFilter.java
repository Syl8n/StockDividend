package com.zerobase.stockdividend.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 커스텀 초기화
        Filter.super.init(filterConfig);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain)
        throws IOException, ServletException {
        // 필터링 수행
        // 모든 필터는 FilterConfig 오브젝트에 접근 가능.
        // ServletContext에도 접근 가능.
        HttpServletRequest r1 = (HttpServletRequest) request;
        log.info("convert the object to this URL is ::" + r1.getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 커스텀 파괴자
        Filter.super.destroy();
    }
}