package com.tenco.listeners;

import java.util.logging.Logger;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * 세션이 생성될 때 감지...리스너 등록
 */
@WebListener
public class MySessionListener implements HttpSessionListener {

	private static final Logger logger = 
			Logger.getLogger(MySessionListener.class.getName());
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// 세션 생성 시 실행됨.
		logger.info("새로운 세션이 생성됨 : " + se.getSession().getId());
		se.getSession().setAttribute("loginTime", System.currentTimeMillis()); // key, values
		// WAS의 대량 메모리에 key 값으로 저장해둠. - 로그인 확인
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// 세션 소멸 시 실행됨. 
		Long loginTime = (Long) se.getSession().getAttribute("loginTime"); // 다운 캐스팅
		Long logoutTime = System.currentTimeMillis(); // 지금 일어난 일임. 
		
		if (loginTime != null) {
			// 얼마 동안 로그인 되어 있는지 시간 계산 가능
			Long sessionDurationMs = logoutTime - loginTime; // 밀리초 단위
			double sessionDurationSec = sessionDurationMs / 1000.0; // 초 단위로 변환
			System.out.println("세션 지속 시간 : " + sessionDurationSec);
		}
	}
}
