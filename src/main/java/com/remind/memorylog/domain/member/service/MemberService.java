package com.remind.memorylog.domain.member.service;

import com.remind.memorylog.domain.member.web.dto.SignInRequest;
import com.remind.memorylog.domain.member.web.dto.SignUpRequest;

public interface MemberService {

    // 회원가입
    void signup(SignUpRequest signUpRequest);

    // 로그인
    void signin(SignInRequest signInRequest);
}
