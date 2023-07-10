package sample.cafekiosk.spring.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 기본 Audit 속성 클래스
 */
@Getter
@MappedSuperclass // 상속하여 사용하기 위함
@EntityListeners(AuditingEntityListener.class) // JPA에서 변경사항을 감지하기 위함
public abstract class BaseEntity {
    /**
     * 생성 시간
     */
    @CreatedDate
    private LocalDateTime createDateTime;

    /**
     * 수정 시간
     */
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;
}
