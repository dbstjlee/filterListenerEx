package com.tenco.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 1. Filter 구현
 * 2. URL 패턴 설정(web.xml 파일에서 설정)
 */
public class IPBlockFilter implements Filter {

	// 192.168.0.45 <-- 내 아이피
	// http://192.168.0.45:8080/fl/home 
	// => 타인이 접근 가능하도록... 차단된 IP는 접근 불가
	
	// 차단할 IP 입력
	private static final String BLOCKED_IP_PREFIX = "192.168.0.25";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("IPBlockFilter 초기화");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 전처리 - 요청자의 IP 확인 
		String remoteIP = request.getRemoteAddr();
		System.out.println("Request from IP : " + remoteIP);
		
		// 차단시킬 코드 작성
		// remoteIP가 BLOCKED_IP_PREFIX와 같을 때
		if(remoteIP.startsWith(BLOCKED_IP_PREFIX)) {
			System.out.println("차단할 IP는 여기서 걸림");
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().println("Access Denied.");
			return;
		}
		chain.doFilter(request, response); // 다음 필터나 서블릿으로 요청 전달
	}
	// 후처리는 doFilter() 가 실행된 후에 작성하기 
	// -> 전처리에 모든 것을 처리한다고 해서 캐싱 객체?에 다 담기는 것이 아니기 때문
}
