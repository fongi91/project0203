package bio.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeesDTO {
    private Long eno;
    @NotEmpty
    private String employeeid;
    @NotEmpty
    private String employeename;
    @NotEmpty
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate terminationdate;
    @NotEmpty
    private String department;
    @NotEmpty
    private String position;
    @NotEmpty
    private String contactnumber;

    private String registeredby;

    private LocalDateTime registereddate;
}
