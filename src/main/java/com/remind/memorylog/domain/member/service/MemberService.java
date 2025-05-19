package com.remind.memorylog.domain.member.service;

import com.remind.memorylog.domain.member.web.dto.SignUpRequest;

public interface MemberService {

    // 회원가입
    void signup(SignUpRequest signUpRequest);
}
