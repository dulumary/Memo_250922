package com.marondal.memo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class PermissionInterceptor implements HandlerInterceptor  {

    @Override
    public boolean preHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler) throws IOException {

        HttpSession session = request.getSession();
        Long userId = (Long)session.getAttribute("userId");

        // /memo/list
        String uri = request.getRequestURI();

        if(userId == null) {
            // 로그인이 안된 상태에서는 메모와 관련된 페이지 접근을 막는다
            // /memo 로 시작하는 url 접근을 막는다
            if(uri.startsWith("/memo")) {
                // 로그인 페이지로 이동
                // 현재 접근하는 요청을 중단하고, 로그인 페이지로 리다이렉트 한다
                response.sendRedirect("/user/login");
                return false;
            }
        } else {
            // 로그인이 된 상태에서 회원 기능과 관련된 페이지 접근을 막는다
            // /user 로 시작하는 url 접근을 막는다
            if(uri.startsWith("/user")) {
                // 현재 접근하는 요청 중단하고, 리스트 페이지로 리다이렉트
                response.sendRedirect("/memo/list");
                return false;
            }

        }

        return true;


    }
}
