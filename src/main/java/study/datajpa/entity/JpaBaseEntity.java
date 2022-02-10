package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //데이터 속성만 공유 (없으면 테이블에 컬럼 추가 안됨)
public class JpaBaseEntity {

    @Column(updatable = false) //변경 방지
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    //Persist 하기 전에 이벤트 발생
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    //Update 하기 전에 이벤트 발생
    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
