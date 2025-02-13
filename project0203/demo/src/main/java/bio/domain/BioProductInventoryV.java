package bio.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity // jpa 엔티티임을 나타냄. 이 클래스가 db 테이블과 매핑되는 엔티티임.
@Getter
@Setter
public class BioProductInventoryV {
    @Id
    private String productCode;
    private String productName;
    private String currentCategory;
    private String efficacyGroup;
    private String productionType;
    private LocalDate date;
    private int totalReceived;
    private int totalShipped;
    private int stockQuantity;
    private long no;

}
