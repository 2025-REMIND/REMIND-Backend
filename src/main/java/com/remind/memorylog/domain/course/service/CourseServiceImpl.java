package com.remind.memorylog.domain.course.service;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final MemberRepository memberRepository;

    @Override
    public void createCourse(Course course, Long memberId) {
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

    }
}
