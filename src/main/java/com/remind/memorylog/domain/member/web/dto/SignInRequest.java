package com.remind.memorylog.domain.member.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "아이디는 필수입니다.")
    private String id; // 로그인 ID

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 로그인 PWD
}
