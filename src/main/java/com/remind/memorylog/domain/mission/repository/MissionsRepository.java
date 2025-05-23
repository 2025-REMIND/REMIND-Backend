package com.remind.memorylog.domain.mission.repository;

import com.remind.memorylog.domain.mission.entity.Missions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionsRepository extends JpaRepository<Missions, Long> {
}
