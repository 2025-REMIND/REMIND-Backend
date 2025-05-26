package com.remind.memorylog.domain.mission.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.CanNotAccessException;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.domain.mission.entity.MissionDetail;
import com.remind.memorylog.domain.mission.exception.MissionNotFoundException;
import com.remind.memorylog.domain.mission.repository.MissionDetailRepository;
import com.remind.memorylog.domain.mission.repository.MissionRepository;
import com.remind.memorylog.domain.mission.web.dto.*;
import com.remind.memorylog.global.entity.enums.Status;
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

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final OpenAiService openAiService;
    private final MissionDetailRepository missionDetailRepository;

    /**
     * 미션 생성
     */
    @Transactional
    @Override
    public void createMission(Long memberId) {
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // OpenAI API를 통해 미션 생성
        String res = openAiService.createMission(memberId);

        ObjectMapper mapper = new ObjectMapper();
        CreateMissionReq createMissionReq = null;
        try {
           createMissionReq = mapper.readValue(res, CreateMissionReq.class);
        } catch (Exception e) {
            throw new AiResultParsingException();
        }

        Mission mission = Mission.toEntity(createMissionReq, member);
        missionRepository.save(mission);
    }

    /**
     * 미션 상세 조회
     */
    @Override
    public GetOneMissionRes getOneMission(Long memberId, Long missionId){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // Mission이 존재하지 않는다면 -> MissionNotFoundException
        Mission mission = missionRepository.getMissionById(missionId);

        // 사용자의 Mission이 아니라면 -> CanNotAccessException
        if(!mission.getMember().equals(member)) throw new CanNotAccessException();

        List<MissionDetail> missionDetailList = missionDetailRepository.findByMissionId(missionId);

        List<GetMissionDetailRes> missionDetailResList = missionDetailList.stream()
                .map(GetMissionDetailRes::of)
                .collect(Collectors.toUnmodifiableList());

        return GetOneMissionRes.of(mission, missionDetailResList);
    }

    @Override
    public GetMissionRes getMission(Long memberId) {
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        // 오늘의 미션 찾기
        Optional<Mission> todayMission = missionRepository.findByMember_MemberIdAndCreatedAtBetween(memberId, startOfDay, endOfDay);

        // 존재하지 않는다면 미션 생성
        if (todayMission.isEmpty()) createMission(memberId);

        todayMission = missionRepository.findByMember_MemberIdAndCreatedAtBetween(memberId, startOfDay, endOfDay);
        Mission mission = todayMission.orElseThrow(MissionNotFoundException::new);

        // 7. 응답 생성
        return GetMissionRes.from(mission);
    }

    @Transactional
    @Override
    public void saveMemo(Long missionId, SaveMemoReq saveMemoReq){
        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(saveMemoReq.memberId());

        // Mission이 존재하지 않는다면 -> MissionNotFoundException
        Mission mission = missionRepository.getMissionById(missionId);
        // 사용자의 Mission이 아니라면 -> CanNotAccessException
        if (!mission.getMember().equals(member)) throw new CanNotAccessException();

        // MissionDetail이 존재하지 않는다면 -> MissionDetailNotFoundException
        MissionDetail missionDetail1 = missionDetailRepository.getByMissionDetailId(saveMemoReq.missionDetailId1());
        missionDetail1.saveMemo(saveMemoReq.memo1());

        // MissionDetail이 존재하지 않는다면 -> MissionDetailNotFoundException
        MissionDetail missionDetail2 = missionDetailRepository.getByMissionDetailId(saveMemoReq.missionDetailId2());
        missionDetail2.saveMemo(saveMemoReq.memo2());

        // MissionDetail이 존재하지 않는다면 -> MissionDetailNotFoundException
        MissionDetail missionDetail3 = missionDetailRepository.getByMissionDetailId(saveMemoReq.missionDetailId3());
        missionDetail3.saveMemo(saveMemoReq.memo3());
    }



    /**
     * 미션 디테일 상태 변경
     */
    @Transactional
    @Override
    public void updateMissionDetailStatus(Long missionDetailId, UpdateMissionReq updateMissionReq) {
        Long memberId = updateMissionReq.memberId();

        // Member가 존재하지 않는다면 -> MemberNotFoundException
        Member member = memberRepository.getMemberByMemberId(memberId);

        // MissionDetail이 존재하지 않는다면 -> MissionDetailNotFoundException
        MissionDetail missionDetail = missionDetailRepository.getByMissionDetailId(missionDetailId);

        // Mission이 존재하지 않는다면 -> MissionNotFoundException
        Mission mission = missionRepository.getMissionById(missionDetail.getMission().getId());
        // 사용자의 Mission이 아니라면 -> CanNotAccessException
        if (!mission.getMember().equals(member)) throw new CanNotAccessException();

        missionDetail.updateStatus();
        missionDetailRepository.save(missionDetail);

        List<MissionDetail> missionDetailList = missionDetailRepository.findByMissionId(mission.getId());
        if (missionDetailList.stream().allMatch(detail -> detail.getStatus() == Status.DONE)) {
            mission.updateStatus(Status.DONE);
            missionRepository.save(mission);
        }
    }

}
