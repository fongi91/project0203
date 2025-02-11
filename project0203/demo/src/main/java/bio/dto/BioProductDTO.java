package bio.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BioProductDTO {

    private Long bioNo;

    private String description;

    private String productCode;

    private String productName;
    // @NotBlank(message="카테고리는 필수입력 항목입니다.")
    private String currentCategory;

    private String packagingUnit;

    private String efficacyGroup;
    //@Size(min=1, message="생산유형은 최소 1자 이상입니다.")
    private String productionType;

    private String registeredBy;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}

