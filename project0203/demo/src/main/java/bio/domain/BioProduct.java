package bio.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class BioProduct extends BioProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bioNo;
    @Column
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

    public void change(String efficacyGroup, String productionType){
        this.efficacyGroup = efficacyGroup;
        this.productionType = productionType;
    }
}
