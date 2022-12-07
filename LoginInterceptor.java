package com.kh.spring.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.member.Model.vo.Member;

public class LoginInterceptor implements HandlerInterceptor{
	/*
	 * Interceptor (정확히는 HandlerInterceptor)
	 * - 해당 Controller가 실행되기 전, 실행된 후에 요청을 가로채 실행할 내용을 작성할 수 있다.
	 * - 주로 로그인 유무판단, 권한 확인등에 사용된다.
	 * 
	 * Interceptor 메서드 종류 (오버라이딩해서 사용)
	 * - preHandler(전처리): DispatcherServlet에서 컨트롤러를 호출하기 전에 실행되는 영역
	 * - postHandler(후처리): 컨트롤러에서 요청처리 후 DispatcherServlet으로 뷰 정보가 리턴되는 순간 실행되는 영역
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
        HttpSession session = request.getSession();
        Member loginUser = (Member)session.getAttribute("loginUser");
        if(loginUser != null) {//로그인 되어있는 정보가 있다면 Controller 요청 실행
        	return true;
        }
        session.setAttribute("alertMsg", "로그인 후 가능한 서비스입니다.");
        response.sendRedirect(request.getContextPath());
        return false;
    }
}





















