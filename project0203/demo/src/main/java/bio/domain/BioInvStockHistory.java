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
public class BioInvStockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    @Column
    private String productCode;
    @Column
    private String warehouseLocation;
    @Column
    private Long quantityChange;
    @Column
    private String changeType;
    @Column
    private LocalDate changeDate;
    @Column
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private BioProduct bioProduct;

    @Transient
    public String getProductName() {
        return bioProduct != null ? bioProduct.getProductName() : null;
    }
}
