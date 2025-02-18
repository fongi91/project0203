package bio.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@IdClass(BioInvStockId.class)  // 복합 키 클래스를 지정
public class BioInvStock {
    @Id
    @Column
    private String productCode;
    @Id
    @Column
    private String warehouseLocation;
    @Column
    private Long stockQuantity;
    @Column
    private String stockStatus;
    @Column
    private LocalDate shelfLife;
    @Column
    private LocalDate lastReceivedDate;
    @Column
    private LocalDate lastShippedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private BioProduct bioProduct;

    @Transient
    public String getProductName() {
        return bioProduct != null ? bioProduct.getProductName() : null;
    }
}
