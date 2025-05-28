package com.remind.memorylog.domain.member.service;

import com.remind.memorylog.domain.member.web.dto.SignInRequest;
import com.remind.memorylog.domain.member.web.dto.SignInResponse;
import com.remind.memorylog.domain.member.web.dto.SignUpRequest;
import com.remind.memorylog.domain.member.web.dto.SignUpResponse;

public interface MemberService {

    // 회원가입
    SignUpResponse signup(SignUpRequest signUpRequest);

    // 회원가입시, 아이디 중복 확인
    boolean isLoginIdDuplicate(String loginId);

    // 로그인
    SignInResponse signin(SignInRequest signInRequest);
}
