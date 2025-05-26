package com.remind.memorylog.domain.mission.repository;

import com.remind.memorylog.domain.mission.entity.MissionDetail;
import com.remind.memorylog.domain.mission.exception.MissionDetailNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionDetailRepository extends JpaRepository<MissionDetail, Long> {
    List<MissionDetail> findByMissionId(Long missionId);

    default MissionDetail getByMissionDetailId(Long missionDetailId) {
        return findById(missionDetailId).orElseThrow(MissionDetailNotFoundException::new);
    }
}
