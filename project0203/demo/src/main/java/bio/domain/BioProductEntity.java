package bio.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value= AuditingEntityListener.class)
@Getter
public class BioProductEntity {
    @CreatedDate // insert 일 경우에, 기본값으로 생성시간을 주입하는 어노테이션 실제 테이블의 컬럼이름을 레그데이터로 , 업데이트 문에는 컬럼이 들어가지 않도록 설정)
    @Column(name="regDate" , updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate // update할 경우에 마지막으로 update 하는 시간으로 세팅
    @Column(name="modDate")
    private LocalDateTime modDate;
}
