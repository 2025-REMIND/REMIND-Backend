package com.remind.memorylog.domain.course.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.entity.CourseDetail;
import com.remind.memorylog.global.entity.enums.Status;
import com.remind.memorylog.domain.course.exception.CourseNotFoundException;
import com.remind.memorylog.domain.course.repository.CourseDetailRepository;
import com.remind.memorylog.domain.course.repository.CourseRepository;
import com.remind.memorylog.domain.course.web.dto.*;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.CanNotAccessException;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.global.external.OpenAiService;
import com.remind.memorylog.global.external.exception.AiResultParsingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseDetailRepository courseDetailRepository;
    private final OpenAiService openAiService;

    /**
     * 코스 생성
     */
    @Transactional
    @Override
    public void createCourse(Long memberId) {
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // OpenAI API를 통해 데이트코스 생성
        String res = openAiService.createCourse(memberId);

        ObjectMapper mapper = new ObjectMapper();
        CreateCourseReq createCourseReq = null;
        try {
            createCourseReq = mapper.readValue(res, CreateCourseReq.class);
        } catch (Exception e) {
            throw new AiResultParsingException();
        }

        Course course = Course.toEntity(createCourseReq, member);
        courseRepository.save(course);

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

    /**
     * 데이트 코스 조회
     */
    @Override
    public GetCourseRes getCourse(Long memberId){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        // 오늘의 코스 찾기
        Optional<Course> todayCourse = courseRepository.findByMember_MemberIdAndCreatedAtBetween(memberId, startOfDay, endOfDay);

        // 존재하지 않는다면 코스 생성
        if(todayCourse.isEmpty()) createCourse(memberId);

        todayCourse = courseRepository
                .findByMember_MemberIdAndCreatedAtBetween(memberId, startOfDay, endOfDay);

        Course course = todayCourse.orElseThrow(CourseNotFoundException::new);

        return GetCourseRes.from(course);
    }

    /**
     * 코스 디테일 상태 변경
     */
    @Override
    @Transactional
    public void updateCourseDetailStatus(Long courseDetailId, UpdateCourseReq updateCourseReq){
        Long memberId = updateCourseReq.memberId();

        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // CourseDetail이 존재하지 않는다면 -> CourseDetailNotFoundException
        CourseDetail courseDetail = courseDetailRepository.getByCourseDetailId(courseDetailId);
        System.out.println("Current status: " + courseDetail.getStatus());

        // Course가 존재하지 않는다면 -> CourseNotFoundException
        Course course = courseRepository.getCourseById(courseDetail.getCourse().getId());
        // 사용자의 Course가 아니라면 -> CanNotAccessException
        if(!course.getMember().equals(member)) throw new CanNotAccessException();

        courseDetail.updateStatus();
        System.out.println("Updated status: " + courseDetail.getStatus());
        courseDetailRepository.save(courseDetail);

        List<CourseDetail> courseDetailList = courseDetailRepository.findByCourseId(courseDetail.getCourse().getId());
        if(courseDetailList.stream().allMatch(detail -> detail.getStatus() == Status.DONE)){
            course.updateStatus(Status.DONE);
            courseRepository.save(course);
        }

    }
}
