package bio.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class BioProduct extends BioProductEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bioNo;
    @Column(unique = true)
    private String productCode;
    @Column
    private String productName;
    @Column
    private String currentCategory;
    @Column
    private String packagingUnit;
    @Column
    private String efficacyGroup;
    @Column
    private String productionType;
    @Column
    private String registeredBy;
    @Column
    private String description;

    public void change(String productName, String currentCategory, String packagingUnit, String efficacyGroup, String productionType, String description){
        this.productName = productName;
        this.currentCategory = currentCategory;
        this.packagingUnit = packagingUnit;
        this.efficacyGroup = efficacyGroup;
        this.productionType = productionType;
        this.description = description;
    }
}
