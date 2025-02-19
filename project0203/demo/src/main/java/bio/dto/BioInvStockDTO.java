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
public class BioInvStockDTO {
    @NotEmpty
    private String productCode;
    @NotEmpty
    private String warehouseLocation;
    @NotNull
    private Long stockQuantity;
    private String stockStatus;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shelfLife;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastReceivedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastShippedDate;
    @NotEmpty
    private String productName;
}
