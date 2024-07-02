package com.tenco.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/session-test")
public class SessionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SessionTestServlet() {
		super();
	}

	// 주소 설계 - http://localhost:8080/fl/session-test
	// => GET 방식으로 요청하면 세션이 생성됨.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 새로운 세션을 생성해보자.
		HttpSession session = request.getSession(); // getSession을 긁어 올 수 있음.(담겨 있음)
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print("당신의 세션 아이디는 : " + session.getId());

		// 세션 무효화 버튼
		response.getWriter().println("<form action='session-test' method='POST'>");
		response.getWriter().println("<button type='submit'>세션 종료(로그아웃)</button>");
		response.getWriter().println("</form>");

		// SSR
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost 실행 확인()");
		HttpSession session = request.getSession(false); // 기존 세션을 반환함. 없으면 null을 반환
		if (session != null) {
			session.invalidate(); // 세션 무효화(제거)
		}
		response.sendRedirect("session-test"); // 페이지 새로 고침
		// 새로운 request, response 객체가 새로 생성됨.
		// sendRedirect --> 클라이언트로 돌아감(= 다시 브라우저로 돌아감)
		// --> get 방식으로 http://localhost:8080/fl/session-test으로 오게 됨
	}
}
