package com.myJwtTest;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 토큰 검증 제외 요청 URI 리스트
        String[] excludeUri = {
                "/login"
        };
        
        String requestURI = httpServletRequest.getRequestURI();

        if (Arrays.asList(excludeUri).contains(requestURI)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else { 
            try {
                // 헤더에서 Authorization 값을 가져와 검증
                String authorization = httpServletRequest.getHeader("Authorization");
                System.out.println("authorization : "+ authorization);
                String token = authorization.replace("Bearer ", "");
                System.out.println("authorization.replace(\"Bearer \", \"\")  token : "+ token);
                if (JwtUtils.verifyToken(token)) {
                    chain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    httpServletResponse.getWriter().println("Unauthorized");
                }
            } catch (Exception e) {
                httpServletResponse.getWriter().println("Unauthorized");
            }
        }
    }
}
