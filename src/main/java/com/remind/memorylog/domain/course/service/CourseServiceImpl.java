package com.remind.memorylog.domain.course.service;

import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.entity.CourseDetail;
import com.remind.memorylog.domain.course.repository.CourseDetailRepository;
import com.remind.memorylog.domain.course.repository.CourseRepository;
import com.remind.memorylog.domain.course.web.dto.GetCourseDetailRes;
import com.remind.memorylog.domain.course.web.dto.GetOneCourseRes;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.CanNotAccessException;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseDetailRepository courseDetailRepository;

    /**
     * 코스 생성
     */
    @Transactional
    @Override
    public void createCourse(Long memberId) {
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // OpenAI API를 통해 데이트코스 생성

    }

    /**
     * 코스 하나 조회
     */
    @Override
    public GetOneCourseRes getOneCourse(Long memberId, Long courseId){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // Course가 존재하지 않는다면 -> CourseNotFoundException
        Course course = courseRepository.getCourseById(courseId);

        // 사용자의 Course가 아니라면 -> CanNotAccessException
        if(!course.getMember().equals(member)) throw new CanNotAccessException();

        List<CourseDetail> courseDetailList = courseDetailRepository.findByCourseId(courseId);

        List<GetCourseDetailRes> courseDetailResList = courseDetailList.stream()
                .map(GetCourseDetailRes::of)
                .collect(Collectors.toUnmodifiableList());

        return GetOneCourseRes.of(course, courseDetailResList);
    }
}
