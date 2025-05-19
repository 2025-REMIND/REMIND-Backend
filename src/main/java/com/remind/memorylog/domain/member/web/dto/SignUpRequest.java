package com.remind.memorylog.domain.member.web.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
        @NotBlank(message = "아이디는 필수입니다.")
        private String id; // 회원가입 ID

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password; // 회원가입 PWD
}
