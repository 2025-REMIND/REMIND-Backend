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
        @Size(min=4, max=10, message = "아이디는 4자~10자 이내로 작성해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "아이디는 영문과 숫자를 조합해주세요")
        private String id; // 회원가입 ID

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min=4, max=10, message = "비밀번호는 4자~10자 이내로 작성해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "비밀번호는 영문과 숫자를 조합해주세요")
        private String password; // 회원가입 PWD
}
