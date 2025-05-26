package com.remind.memorylog.domain.mission.repository;

import com.remind.memorylog.domain.mission.entity.Mission;
import com.remind.memorylog.domain.mission.exception.MissionNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    Optional<Mission> findByMember_MemberIdAndCreatedAtBetween(Long memberId, LocalDateTime start, LocalDateTime end);

    default Mission getMissionById(Long missionId) {
        return findById(missionId).orElseThrow(MissionNotFoundException::new);
    }
}
