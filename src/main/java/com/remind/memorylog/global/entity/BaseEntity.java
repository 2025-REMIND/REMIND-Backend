package com.remind.memorylog.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 역할 : 엔티티 공통 필드인 createdAt, updateAt을 자동으로 처리해주는 시간 자동 기록용 부모 클래스이다. 다른 엔티티가 이걸 상속만 하면 알아서 시간 기록이 된다.
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate // 이 엔티티가 처음 저장될 때 날짜를 자동으로 기록해줌 (persist() 시점에 자동으로 재워진다)
    @Column(name = "created_at") // DB에 들어갈 컬럼 이름을 지정
    private LocalDateTime createdAt;

    @LastModifiedDate // 이 엔티티가 수정될 때마다 자동으로 현재 시간으로 갱신됨 (save()나 merge()와 같은 update 작업 시 적용)
    @Column(name = "updated_at") // DB에 들어갈 컬럼 이름을 지정
    private LocalDateTime updateAt;
}
