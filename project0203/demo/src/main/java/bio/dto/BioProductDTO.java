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

    @NotBlank(message="제품코드는 필수입력 항목입니다.")
    private String productCode;
    @NotBlank(message="제품명은 필수입력 항목입니다.")
    private String productName;
    @NotBlank(message="카테고리는 필수입력 항목입니다.")
    private String currentCategory;
    @NotBlank(message="포장단위는 필수입력 항목입니다.")
    private String packagingUnit;
    @NotBlank(message="효능군은 필수입력 항목입니다.")
    private String efficacyGroup;
    @NotBlank(message="생산유형은 필수입력 항목입니다.")
    private String productionType;
    @NotBlank(message="설명란은 필수입력 항목입니다.")
    private String description;

    private String registeredBy;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String imageFileName;
}

