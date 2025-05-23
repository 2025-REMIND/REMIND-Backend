package com.remind.memorylog.domain.diary.repository;

import com.remind.memorylog.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findTop10ByMemberMemberIdOrderByCreatedAtDesc(Long memberId);

}
