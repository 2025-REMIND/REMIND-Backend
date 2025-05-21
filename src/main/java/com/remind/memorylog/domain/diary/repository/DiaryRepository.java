package com.remind.memorylog.domain.diary.repository;

import com.remind.memorylog.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
