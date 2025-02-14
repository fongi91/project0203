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
public class BioInvShipping extends BioInvShippingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;
    @Column(length = 255, nullable = false)
    private String productCode;
    @Column(length = 11, nullable = false)
    private Long quantity;
    @Column(length = 255, nullable = false)
    private String customer;
    @Column(length = 255, nullable = false)
    private String warehouseLocation;
    @Column(nullable = false)
    private LocalDate shippingDate;
    @Column(nullable = false)
    private LocalDate shelfLife;
    @Column(length = 255, nullable = false)
    private String registeredBy;
    @Column(nullable = false)
    private Boolean isShipped;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private BioProduct bioProduct;

    @Transient
    public String getProductName() {
        return bioProduct != null ? bioProduct.getProductName() : null;
    }

    public void change(String productCode, Long quantity, String customer, String warehouseLocation, LocalDate shippingDate, LocalDate shelfLife,String registeredBy, Boolean isShipped){
        this.productCode = productCode;
        this.quantity = quantity;
        this.customer = customer;
        this.warehouseLocation = warehouseLocation;
        this.shippingDate = shippingDate;
        this.registeredBy = registeredBy;
        this.isShipped = isShipped;
        this.shelfLife = shelfLife;
    }

    public void setProductCode(String productCode) {this.productCode = productCode;}
    public void setBioProduct(BioProduct bioProduct) {}
}
