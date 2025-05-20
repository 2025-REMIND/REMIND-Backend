package com.remind.memorylog.domain.member.web.controller;

import com.remind.memorylog.domain.member.service.MemberService;
import com.remind.memorylog.domain.member.web.dto.SignInRequest;
import com.remind.memorylog.domain.member.web.dto.SignUpRequest;
import com.remind.memorylog.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {
    // 의존성 부여
    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signup(@RequestBody @Valid SignUpRequest signUpRequest) {

        // 서비스 계층 위임
        memberService.signup(signUpRequest);

        // 반환
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.empty());

    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signin(@RequestBody @Valid SignInRequest signInRequest) {

        // 서비스 계층 위임
        memberService.signin(signInRequest);

        // 반환
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.empty());

    }
}
