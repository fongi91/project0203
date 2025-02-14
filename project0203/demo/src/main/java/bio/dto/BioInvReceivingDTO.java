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
public class BioInvReceivingDTO {
    private Long receivingId;
    @NotEmpty
    private String productCode;
    @NotNull
    private Long quantity;
    @NotEmpty
    private String supplier;
    @NotEmpty
    private String warehouseLocation;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receivingDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shelfLife;
    @NotEmpty
    private String registeredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registeredDate;
    private Boolean isReceived;
    private String productName;
}
