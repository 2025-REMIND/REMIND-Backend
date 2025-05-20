package com.remind.memorylog.domain.member.service;


import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.*;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.domain.member.web.dto.SignInRequest;
import com.remind.memorylog.domain.member.web.dto.SignInResponse;
import com.remind.memorylog.domain.member.web.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    @Override
    public void signup(SignUpRequest signUpRequest) {

        // 아이디 중복 검증
        if (memberRepository.existsByLoginId(signUpRequest.getId())) {
            throw new UserAlreadyExistException();  // 네가 만든 커스텀 예외
        }

        // 비밀번호 아이디 동일여부 검증
        if(signUpRequest.getId().equals(signUpRequest.getPassword())) {
            throw new UserIdPasswordSameException();
        }

        // 비밀번호 암호화
        String hashed = passwordEncoder.encode(signUpRequest.getPassword());

        // Member Entity 생성
        Member member = Member.builder()
                .loginId(signUpRequest.getId())
                .loginPwd(hashed)
                .build();



        // repository에 Member 저장 (memberRepository 사용)
        memberRepository.save(member);


    }

    @Transactional
    @Override
    public SignInResponse signin(SignInRequest signInRequest) {

        // 1. ID 존재 여부 확인
        Member member = memberRepository.findByLoginId(signInRequest.getId())
                .orElseThrow(UserNotFoundException::new);

        // 2. 비밀번호 일치 여부 검증
        if (!member.getLoginPwd().equals(signInRequest.getPassword())) {
            throw new PasswordMismatchException();
        }

        // 3. 사용자 기본키 반환
        return new SignInResponse(member.getMemberId());

    }
}

