package com.remind.memorylog.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebConfig 역할 : 프론트엔드(React)가 다른 포트나 도메인에서 요청할 때, CORS 오류 없이 Spring 서버가 요청을 받을 수 있도록 설정해주는 역할
// CORS : 다른 출처에서 오는 요청 허락 여부를 정하는 보안 정책
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 이 서버의 모든 API 요청에 대해 CORS 허용 설정 적용
                .allowedOrigins("http://localhost:3000") // 이 주소에서 온 요청은 허용(실제 프론트 주소)
                .allowedMethods("GET", "POST", "PUT","DELETE","OPTIONS") // 이와 같은 메서드 허용
                .allowCredentials(true); // 쿠키, 세션 같은 인증 정보 포함도 허용. true로 해야 로그인 정보나 사용자 상태 유지 같은 기능이 제대로 동작함
    }
}
