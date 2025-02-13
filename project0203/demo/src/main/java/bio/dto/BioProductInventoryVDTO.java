package bio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BioProductInventoryVDTO {
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
