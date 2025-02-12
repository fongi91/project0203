package bio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BioInvShippingDTO {
    private Long shippingId;
    @NotEmpty
    private String productCode;

    private String productName;
    @NotNull
    private Long quantity;
    @NotEmpty
    private String customer;
    @NotEmpty
    private String warehouseLocation;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippingDate;
    @NotEmpty
    private String registeredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registeredDate;

    private Boolean isShipped;
}
