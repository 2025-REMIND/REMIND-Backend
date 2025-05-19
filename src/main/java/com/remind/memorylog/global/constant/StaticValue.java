package com.remind.memorylog.global.constant;

// 역할 : Spring 프로젝트 내 여러 곳에서 공통적으로 사용될 수 있는 HTTP 상태 코드를 상수로 정의
public class StaticValue {

    // Success Code
    public static final int OK = 200; // 요청 성공
    public static final int CREATED = 201; // 리소스 생성 성공
    public static final int NO_CONTENT = 204; // 성공했지만 반환할 콘텐츠 없음

    // Error Code
    public static final int BAD_REQUEST = 400; // 잘못된 요청
    public static final int UNAUTHORIZED = 401; // 인증 필요
    public static final int FORBIDDEN = 403; // 권한 없음
    public static final int NOT_FOUND = 404; // 요청 리소스 없음
    public static final int METHOD_NOT_ALLOWED = 405; // 허용되지 않은 HTTP 메서드
    public static final int CONFLICT = 409; // 리소스 충돌
    public static final int INTERNAL_SERVER_ERROR = 500; // 서버 내부 에러
}
