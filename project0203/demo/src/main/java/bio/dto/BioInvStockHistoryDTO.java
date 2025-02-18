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
public class BioInvStockHistoryDTO {
    private Long historyId;
    @NotEmpty
    private String productCode;
    @NotEmpty
    private String warehouseLocation;
    @NotNull
    private Long quantityChange;
    private String changeType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate changeDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private String productName;
}
