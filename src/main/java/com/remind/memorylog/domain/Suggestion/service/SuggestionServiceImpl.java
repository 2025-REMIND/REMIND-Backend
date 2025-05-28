package com.remind.memorylog.domain.Suggestion.service;

import com.remind.memorylog.domain.Suggestion.entity.ArchivedStatus;
import com.remind.memorylog.domain.Suggestion.entity.Suggestion;
import com.remind.memorylog.domain.Suggestion.repository.SuggestionRepository;
import com.remind.memorylog.domain.Suggestion.web.dto.ArchiveSuggestionReq;
import com.remind.memorylog.domain.Suggestion.web.dto.GetArchivePageRes;
import com.remind.memorylog.domain.Suggestion.web.dto.GetArchiveSuggestionRes;
import com.remind.memorylog.domain.Suggestion.web.dto.GetTodaySuggestionRes;
import com.remind.memorylog.domain.course.entity.Course;
import com.remind.memorylog.domain.course.repository.CourseRepository;
import com.remind.memorylog.domain.course.service.CourseService;
import com.remind.memorylog.domain.course.web.dto.GetCourseRes;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.CanNotAccessException;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.domain.mission.repository.MissionRepository;
import com.remind.memorylog.domain.mission.service.MissionService;
import com.remind.memorylog.domain.mission.web.dto.GetMissionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final CourseRepository courseRepository;
    private final MissionRepository missionRepository;
    private final CourseService courseService;
    private final MissionService missionService;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public GetTodaySuggestionRes getTodaySuggestion(Long memberId){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        GetCourseRes getCourseRes = courseService.getCourse(memberId);
        // Course가 존재하지 않는다면 -> CourseNotFoundException
        Course course = courseRepository.getCourseById(getCourseRes.courseId());

        GetMissionRes getMissionRes = missionService.getMission(memberId);
        // Mission이 존재하지 않는다면 -> MissionNotFoundException
        Mission mission = missionRepository.getMissionById(getMissionRes.missionId());

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        Suggestion suggestion = suggestionRepository
                .findByMember_MemberIdAndCreatedAtBetween(memberId, startOfDay, endOfDay)
                .orElseGet(() -> suggestionRepository.save(
                        Suggestion.toEntity(member, mission, course)
                ));
        return GetTodaySuggestionRes.of(suggestion, getMissionRes, getCourseRes);
    }

    @Transactional
    @Override
    public void archiveSuggestion(ArchiveSuggestionReq archiveSuggestionReq, Long suggestionId) {
        Suggestion suggestion = suggestionRepository.getSuggestionBySuggestionId(suggestionId);
        Member member = memberRepository.getMemberByMemberId(archiveSuggestionReq.memberId());

        if(!suggestion.getMember().equals(member)) throw new CanNotAccessException();

        suggestion.archiveSuggestion();
    }

    @Transactional
    @Override
    public void unArchiveSuggestion(ArchiveSuggestionReq archiveSuggestionReq, Long suggestionId) {
        Suggestion suggestion = suggestionRepository.getSuggestionBySuggestionId(suggestionId);
        Member member = memberRepository.getMemberByMemberId(archiveSuggestionReq.memberId());

        if(!suggestion.getMember().equals(member)) throw new CanNotAccessException();

        suggestion.unArchiveSuggestion();
    }

    @Override
    public GetArchivePageRes getArchiveSuggestion(Long memberId, int page, int size){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        int doneData = suggestionRepository.countArchivedSuggestionsDone(memberId, ArchivedStatus.ARCHIVED);
        int progressData = suggestionRepository.countArchivedSuggestionsProgress(memberId, ArchivedStatus.ARCHIVED);

        // 더 큰 쪽이 페이지 수 결정
        int totalPage = Math.max(doneData, progressData);

        Pageable pageable = PageRequest.of(page, 1);

        List<Suggestion> doneSuggestions = suggestionRepository.findArchivedSuggestionsDone(memberId, ArchivedStatus.ARCHIVED, pageable);
        List<Suggestion> progressSuggestions = suggestionRepository.findArchivedSuggestionsProgress(memberId, ArchivedStatus.ARCHIVED, pageable);

        List<GetArchiveSuggestionRes> doneRes = doneSuggestions.stream()
                .map(GetArchiveSuggestionRes::from)
                .collect(Collectors.toList());

        List<GetArchiveSuggestionRes> progressRes= progressSuggestions.stream()
                .map(GetArchiveSuggestionRes::from)
                .collect(Collectors.toList());

        return new GetArchivePageRes(doneRes, progressRes, totalPage, doneData + progressData);
    }



}
