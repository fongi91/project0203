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
public class BioInvReceiving extends BioInvReceivingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivingId;
    @Column
    private String productCode;
    @Column(length = 11, nullable = false)
    private Long quantity;
    @Column(length = 255, nullable = false)
    private String supplier;
    @Column(length = 255, nullable = false)
    private String warehouseLocation;
    @Column(nullable = false)
    private LocalDate receivingDate;
    @Column(length = 255, nullable = false)
    private LocalDate shelfLife;
    @Column(length = 255, nullable = false)
    private String registeredBy;
    @Column(nullable = false)
    private Boolean isReceived;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private BioProduct bioProduct;

    @Transient
    public String getProductName() {
        return bioProduct != null ? bioProduct.getProductName() : null;
    }

    public void change(String productCode, Long quantity, String supplier, String warehouseLocation, LocalDate receivingDate, String registeredBy, Boolean isReceived){
        this.productCode = productCode;
        this.quantity = quantity;
        this.supplier = supplier;
        this.warehouseLocation = warehouseLocation;
        this.receivingDate = receivingDate;
        this.registeredBy = registeredBy;
        this.isReceived = isReceived;
    }

    public void setProductCode(String productCode) {this.productCode = productCode;}
    public void setBioProduct(BioProduct bioProduct) {}
}
